package aloyslisp.ide;

import static aloyslisp.core.L.*;

import java.awt.*;
import java.awt.event.*;

import aloyslisp.core.*;
import aloyslisp.ide.action.*;

import javax.swing.*;

public class cIDE extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long		serialVersionUID		= -3421748092914023741L;

	protected static final int		NUM_INPUT_ROWS			= 15;

	protected static final int		NUM_OUTPUT_ROWS			= 30;

	protected static final int		NUM_COLS				= 100;

	protected cIDE_INPUT_STREAM		f_inputStream			= null;

	protected cIDE_OUTPUT_STREAM	f_outputStream			= null;

	protected int					f_numberOfHistoryitems	= 25;

	JMenuBar						f_menuBar				= new JMenuBar();

	JMenu							f_fileMenu				= new JMenu("File");

	JMenu							f_lispMenu				= new JMenu("LISP");

	JMenu							f_historyMenu			= new JMenu(
																	"History");

	JMenu							f_windowMenu			= new JMenu(
																	"Window");

	protected String				f_prompt;

	public cIDE(String title, String thePrompt)
	{
		super(title);

		f_prompt = thePrompt;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setupMenuBar();

		getContentPane().setLayout(new BorderLayout());
		setBackground(Color.white);
		setFont(new Font("", 0, 0));
		setResizable(true);

		f_inputStream = new cIDE_INPUT_STREAM(this, NUM_INPUT_ROWS, NUM_COLS);
		getContentPane().add("North", f_inputStream.getPanel());

		f_outputStream = new cIDE_OUTPUT_STREAM(NUM_OUTPUT_ROWS, NUM_COLS);
		message("aloysLISP v. V314\n");
		getContentPane().add("Center", f_outputStream.getPanel());

		pack();
		setVisible(true);
	}

	protected void setupMenuBar()
	{

		// menu bar
		// INFO
		JMenuItem infoItem = new JMenuItem("aloysLisp v. V314");
		infoItem.setEnabled(false);
		f_fileMenu.add(infoItem);
		f_fileMenu.addSeparator();

		// QUIT
		JMenuItem exitItem = new JMenuItem("Quit");
		exitItem.addActionListener(this);
		f_fileMenu.add(exitItem);

		// LISP

		// -- Apropos
		JMenuItem aproposItem = new JMenuItem(new RunAproposTask(this,
				"apropos", null, "Lists all defined symbols and functions",
				null, null));
		f_lispMenu.add(aproposItem);

		// -- Load
		JMenuItem loadItem = new JMenuItem(new RunLoadFileTask(this,
				"load file...", null, "Select and load a file into LISP", null,
				null));
		f_lispMenu.add(loadItem);

		// HISTORY

		// Main MENU BAR
		f_menuBar.add(f_fileMenu);
		f_menuBar.add(f_lispMenu);
		f_menuBar.add(f_historyMenu);
		setJMenuBar(f_menuBar);
	}

	public void message(tT expr)
	{
		message(expr.toString(), false);
	}

	public void message(tT expr, boolean showPrompt)
	{
		if (showPrompt)
			message(f_prompt, false);
		message(expr.toString(), false);
	}

	public void message(String msg, boolean showPrompt)
	{
		if (showPrompt)
			message(f_prompt, false);
		f_outputStream.PRINT(str(msg));
	}

	public void message(String msg)
	{
		message(msg, false);
	}

	public void redraw()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				// setVisible(false);
				// invalidate();
				pack();
				// setVisible(true);

			}
		});
	}

	public void addHistoryItem(String s)
	{
		JMenuItem newItem = new JMenuItem(s);
		newItem.addActionListener(this);
		f_historyMenu.add(newItem, 0);

		while (f_historyMenu.getItemCount() > f_numberOfHistoryitems)
			f_historyMenu.remove(f_numberOfHistoryitems);
	}

	public int getNumberOfHistoryitems()
	{
		return f_numberOfHistoryitems;
	}

	public void setNumberOfHistoryitems(int numberOfHistoryitems)
	{
		f_numberOfHistoryitems = numberOfHistoryitems;
	}

	public String getPrompt()
	{
		return f_prompt;
	}

	public void setPrompt(String prompt)
	{
		f_prompt = prompt;
	}

	/**
	 * ActionListener - handles menu events.
	 */
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() instanceof JMenuItem)
		{
			JMenuItem menuItem = (JMenuItem) event.getSource();
			String op = menuItem.getText();

			if (op.equals("Quit"))
				System.exit(0);
			else
				// Must be a history item
				eval(op);

		}
	}

	/**
	 * EVAL - evaluate the input, placing it in the history and displaying the
	 * output in the output area.
	 */
	public void eval(String s)
	{
		f_inputStream.eval(s);
	}

	public static void main(String[] args)
	{
		// Should be the first to allow READTABLE to be active in class
		// loading...
		// TODO put to core
		System.out.println(cl.PACKAGE_NAME());
		loadClasses("aloyslisp.core.streams");

		// Load the rest
		// TODO Should be systematized probably :
		// loadClasses("aloyslisp.core");
		// loadClasses("aloyslisp.internal");
		// loadClasses("aloyslisp.packages");
		loadClasses("aloyslisp.annotations");
		loadClasses("aloyslisp.core");
		loadClasses("aloyslisp.core.clos");
		loadClasses("aloyslisp.core.conditions");
		loadClasses("aloyslisp.core.functions");
		loadClasses("aloyslisp.core.math");
		loadClasses("aloyslisp.core.packages");
		loadClasses("aloyslisp.core.sequences");
		loadClasses("aloyslisp.core.streams");
		loadClasses("aloyslisp.internal.engine");
		loadClasses("aloyslisp.internal.flowcontrol");
		loadClasses("aloyslisp.internal.iterators");
		loadClasses("aloyslisp.packages.common_lisp");
		loadClasses("aloyslisp.packages.system");

		// Load first lisp file (REPL definition)
		sym("print").e(str("aloysLisp v.V314"));

		// Load first lisp file (REPL definition)
		sym("load").e(str("class.lisp"));

		new cIDE("aloysLisp v. V314", ">>");

	}

}
