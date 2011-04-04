package aloyslisp.ide;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import aloyslisp.annotations.aJavaInternal;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.streams.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * cIDE_INPUT_STREAM is a text field that does parenthesis matching and sends
 * its input
 * off to a Lisp Evaluator.
 */
@aJavaInternal
class cIDE_INPUT_STREAM extends cSTRING_STREAM implements Runnable,
		ActionListener, KeyListener
{
	/**
	 * 
	 */
	private static final long				serialVersionUID			= -6163009742958415314L;

	public static boolean					DEBUG						= false;

	/* ------------------ PRIVATE variables ------------------------------ */
	protected JPanel						f_panel						= null;

	protected JTextArea						f_inputArea					= null;

	protected int							f_matchingPosition			= -1;

	protected boolean						f_flashing					= false;

	protected Thread						f_myThread					= null;

	protected Graphics						f_myGraphics				= null;

	protected String						f_input;

	// GUI stuff
	JButton									f_largerAreaButton			= new JButton(
																				"larger");

	JButton									f_smallerAreaButton			= new JButton(
																				"smaller");

	JButton									f_evalButton				= new JButton(
																				"Eval");

	JButton									f_clearButton				= new JButton(
																				"Clear");

	// Font info
	protected FontMetrics					fontInfo					= null;

	protected int							fontWidth					= 0;

	protected int							fontHeight					= 0;

	protected Color							fgColor						= null;

	protected Color							bgColor						= null;

	protected int							hFudge						= 8;

	protected int							vFudge						= 7;

	protected cIDE							f_parent;

	protected String						f_saveBuffer				= "";

	protected String						f_lastCommand				= "";

	protected boolean						f_firstCharOfCommand		= true;

	protected int							f_commandMultiplier			= 1;

	protected Font							f_defaultFont				= new Font(
																				"Courier",
																				Font.PLAIN,
																				12);

	// Used when matching parentheses
	protected Highlighter.HighlightPainter	f_goodPainter				= new DefaultHighlighter.DefaultHighlightPainter(
																				Color.cyan);

	protected Highlighter.HighlightPainter	f_badPainter				= new DefaultHighlighter.DefaultHighlightPainter(
																				Color.magenta);

	protected ArrayList<Object>				f_highlights				= new ArrayList<Object>(
																				10);

	protected Integer						f_highlightLock				= new Integer(
																				17);

	protected int							f_defaultNumberOfInputLines	= 15;

	/* ------------------ CONSTRUCTOR ------------------------------ */

	public cIDE_INPUT_STREAM(cIDE parent, int rows, int cols)
	{
		super("", 0, -1);
		f_panel = new JPanel();
		f_panel.setFont(new Font("Courier", Font.PLAIN, 12));

		f_parent = parent;

		f_panel.setLayout(new BoxLayout(f_panel, BoxLayout.Y_AXIS));
		f_panel.setBackground(new Color(170, 170, 170)); // new Color(255, 255,
		// 153)); // yellow

		// Set up the input area
		f_inputArea = new JTextArea(rows, cols);
		f_inputArea.setFont(f_defaultFont);
		f_inputArea.addKeyListener(this);
		f_inputArea.addCaretListener(new BracketMatcher(this));

		JScrollPane scroller = new JScrollPane(f_inputArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		f_panel.add(scroller);

		f_largerAreaButton.addActionListener(this);
		f_largerAreaButton.setActionCommand("largerArea");
		f_largerAreaButton.setBackground(f_panel.getBackground());

		f_smallerAreaButton.addActionListener(this);
		f_smallerAreaButton.setActionCommand("smallerArea");
		f_smallerAreaButton.setBackground(f_panel.getBackground());

		f_evalButton.addActionListener(this);
		f_evalButton.setActionCommand("eval");
		f_evalButton.setBackground(f_panel.getBackground());

		f_clearButton.addActionListener(this);
		f_clearButton.setActionCommand("clear");
		f_clearButton.setBackground(f_panel.getBackground());

		// Submit/Expand/Shrink buttons
		Box buttonPanel = new Box(BoxLayout.X_AXIS);
		buttonPanel.add(f_evalButton);
		buttonPanel.add(f_clearButton);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(f_largerAreaButton);
		buttonPanel.add(f_smallerAreaButton);

		f_panel.add(buttonPanel);
	}

	/* ------------------ PUBLIC methods ------------------------------ */

	/**
	 * @return
	 */
	public JPanel getPanel()
	{
		return f_panel;
	}

	// Set the font - can't do this until it is visible.
	public void setFontInfo()
	{
		f_myGraphics = f_panel.getGraphics();

		fontInfo = f_panel.getFontMetrics(f_panel.getFont());
		fontWidth = fontInfo.charWidth('A');
		fontHeight = fontInfo.getHeight();
		fgColor = f_panel.getForeground();
		bgColor = f_panel.getBackground().brighter(); // Motif is doing
														// something
		// to us.

		// hFudge = fontWidth - 2;
	}

	/**
	 * Input should be a regular string, which is parsed and evaluated.
	 */
	public void eval(String inputString)
	{
		inputString = inputString.trim();

		tT[] result = null;
		f_parent.message(inputString, true);
		try
		{
			result = L.lisp(inputString).EVAL();
		}
		catch (LispException e)
		{
			// Error in evaluation, print message
			f_parent.message(e.getLocalizedMessage() + "\n");
			System.err.println();
			e.printStackTrace();
		}
		catch (Exception e)
		{
			// Error in evaluation, print message
			f_parent.message(e.getLocalizedMessage() + "\n");
			System.err.println();
			e.printStackTrace();
		}

		f_parent.addHistoryItem(inputString);

		// result will be printed in the Output window.
		if (result != null)
			for (int i = 0; i < result.length; i++)
			{
				f_parent.message(result[i]);
			}

		f_lastCommand = inputString;

		// Select all the text in the input box
		setText(f_lastCommand);
		clearHighlights();
		selectAll();
	}

	/**
	 * Input should be a LispString.
	 * 
	 * @param input
	 *            a LispString containing a command.
	 */
	public void eval(tT input)
	{
		eval(input.TO_STRING());
	}

	// This is called when the user hits RETURN in the input window.
	/*
	 * (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();

		if (command.equals("comboBoxEdited") || command.equals("eval"))
		{
			String inStr = f_inputArea.getText();
			if (inStr.trim().equals(""))
				return;

			eval(inStr);
			f_firstCharOfCommand = true;
		}

		if (command.equals("comboBoxEdited") || command.equals("clear"))
		{
			f_inputArea.setText("");
			f_firstCharOfCommand = true;
		}

		else if (e.getActionCommand().equals("largerArea"))
			incrementEditorLines(1);

		else if (e.getActionCommand().equals("smallerArea"))
			incrementEditorLines(-1);

	}

	/**
	 * @param increment
	 */
	public void incrementEditorLines(int increment)
	{
		int numRows = f_inputArea.getRows() + increment;
		if (numRows > 0)
		{
			f_inputArea.setRows(numRows);
			f_parent.redraw();
			f_inputArea.setText("(setq *LISTENER-INPUT-ROWS* " + numRows + ")");
			ActionEvent newEvent = new ActionEvent(f_inputArea,
					ActionEvent.ACTION_PERFORMED, "comboBoxEdited");
			actionPerformed(newEvent);
		}
	}

	public void keyPressed(KeyEvent e)
	{
	}

	public void keyReleased(KeyEvent e)
	{
	}

	/**
	 * Implements parenthesis matching
	 */
	public void keyTyped(KeyEvent e)
	{
		char key = e.getKeyChar();

		// f_lisp.errorMess("cIDE_INPUT_STREAM.keyTyped: " + key + " (" +
		// e.getKeyCode()
		// +")");
		if ((key == Character.LINE_SEPARATOR) && (e.isControlDown()))
			// Toolkit.getDefaultToolkit().
			// getSystemEventQueue().
			// postEvent(new ActionEvent(e.getSource(),
			// ActionEvent.ACTION_PERFORMED,
			// "comboBoxEdited"));
			actionPerformed(new ActionEvent(e.getSource(),
					ActionEvent.ACTION_PERFORMED, "comboBoxEdited"));

	}

	/**
	 * Returns true if no further event handling is necessary.
	 */
	boolean handleEmacsCommand(KeyEvent e)
	{
		return false;

	}

	// Editing - save deleted text in a buffer
	void saveText(String s)
	{
		f_saveBuffer = s;
	}

	// Editing - restore deleted text from a buffer
	void restoreText()
	{
	}

	public void run()
	{
		int h, v; // Top left corner of character to be boxed.

		synchronized (f_myThread)
		{
			while (true)
			{
				try
				{
					f_myThread.wait();
				}
				catch (InterruptedException e)
				{
				}

				// can't do this until we are visible.
				if (fontInfo == null)
					setFontInfo();

				f_flashing = true;

				// Variable-width font
				h = hFudge
						+ fontInfo.stringWidth(f_input.substring(0,
								f_matchingPosition));
				v = vFudge;

				f_myGraphics.setXORMode(Color.white);
				f_myGraphics.fillRect(h, v, fontWidth, fontHeight);

				// Sleep for 0.5 sec or until user types another key
				try
				{
					f_myThread.wait(500L);
				}
				catch (InterruptedException e)
				{
				}
				finally
				{
					f_flashing = false;
					f_myGraphics.fillRect(h, v, fontWidth, fontHeight);
					f_myGraphics.setPaintMode();
				}
			}
		}
	}

	public String getText()
	{
		return f_inputArea.getText();
	}

	public void setText(String newText)
	{
		f_inputArea.setText(newText);
	}

	public void selectAll()
	{
		f_inputArea.selectAll();
	}

	public int getColumns()
	{
		return f_inputArea.getColumns();
	}

	public void setColumns(int newValue)
	{
		f_inputArea.setColumns(newValue);
	}

	public void setRows(int newValue)
	{
		f_inputArea.setRows(newValue);
	}

	public int getRows()
	{
		return f_inputArea.getRows();
	}

	public void addGoodHighlight(int start, int end)
	{
		addHighlight(start, end, f_goodPainter);
	}

	public void addBadHighlight(int start, int end)
	{
		addHighlight(start, end, f_badPainter);
	}

	public void addHighlight(int start, int end,
			Highlighter.HighlightPainter painter)
	{
		Highlighter highlighter = f_inputArea.getHighlighter();

		synchronized (f_highlightLock)
		{
			try
			{
				f_highlights.add(highlighter.addHighlight(start, end, painter));
			}
			catch (BadLocationException ble)
			{
				// ignore
			}
		}
	}

	public void clearHighlights()
	{
		Highlighter highlighter = f_inputArea.getHighlighter();

		if (highlighter != null)
		{
			synchronized (f_highlightLock)
			{
				for (Iterator<Object> iterator = f_highlights.iterator(); iterator
						.hasNext();)
				{
					Highlighter.Highlight highlight = (Highlighter.Highlight) iterator
							.next();
					highlighter.removeHighlight(highlight);
				}
				f_highlights.clear();
			}
		}
	}

}

@aJavaInternal
class BracketMatcher implements CaretListener
{
	protected cIDE_INPUT_STREAM	f_input;

	public BracketMatcher(cIDE_INPUT_STREAM input)
	{
		f_input = input;
	}

	public void caretUpdate(CaretEvent e)
	{
		JTextComponent source = (JTextComponent) e.getSource();
		int pos = e.getDot();
		Document doc = source.getDocument();
		char key = ' ';

		f_input.clearHighlights();

		try
		{
			key = doc.getText(e.getDot() - 1, 1).charAt(0);
		}
		catch (BadLocationException ble)
		{ // ?? when does this happen?
			return;
		}

		// PARENTHESIS MATCHING
		if (key == ')')
		{
			String input = f_input.getText();
			int quoteCount = 0;
			int parenCount = 0;

			// Count doublequotes - don't check if we are in a string
			for (int i = 0; i < pos; ++i)
				if (input.charAt(i) == '"')
					++quoteCount;

			if ((quoteCount % 2) != 0) // Don't do a paren match if we are
				// inside a string
				return;

			// Find the matching paren
			for (int i = pos - 1; i >= 0; --i)
			{
				if (input.charAt(i) == '"')
					while (input.charAt(--i) != '"')
						;
				else if (input.charAt(i) == ')')
					++parenCount;
				else if (input.charAt(i) == '(')
				{
					--parenCount;
					if (parenCount == 0) // Highlight the paren for an
					// instant
					{
						f_input.addGoodHighlight(i, i + 1);
						f_input.addGoodHighlight(pos - 1, pos);
						return;
					}
				}
			}

			// If we get here, we didn't match anything.
			f_input.addBadHighlight(pos - 1, pos);

		}
	}
}
