package aloyslisp.ide.action;

import aloyslisp.ide.cIDE;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * RunLOadFileTask executes the "(load "")" command.
 */
public class RunLoadFileTask extends ApplicationAction
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5691590741647340589L;

	protected cIDE			f_listener			= null;

	protected JFileChooser		f_fileChooser		= new JFileChooser();

	public RunLoadFileTask(cIDE listener, String name, Icon icon,
			String tooltip, KeyStroke acceleratorKey, Integer mnemonic)
	{
		super(name, icon, tooltip, acceleratorKey, mnemonic);

		f_listener = listener;
		f_fileChooser.setDialogTitle("Select file to load into LISP");
		f_fileChooser.setMultiSelectionEnabled(false);
	}

	public void actionPerformed(ActionEvent e)
	{
		String filename = null;

		if (f_fileChooser.showOpenDialog(f_listener) == JFileChooser.APPROVE_OPTION)
		{
			File file = f_fileChooser.getSelectedFile();
			filename = file.getAbsolutePath();
			f_listener.eval("(load \"" + filename + "\")");
		}
	}

}
