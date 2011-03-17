package aloyslisp.ide.action;

import aloyslisp.ide.cIDE;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * RunAproposTask executes the "(apropos "")" command.
 */
public class RunAproposTask extends ApplicationAction
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4501525622629758140L;
	protected cIDE			f_listener			= null;

	public RunAproposTask(cIDE listener, String name, Icon icon,
			String tooltip, KeyStroke acceleratorKey, Integer mnemonic)
	{
		super(name, icon, tooltip, acceleratorKey, mnemonic);

		f_listener = listener;
	}

	public void actionPerformed(ActionEvent e)
	{
		f_listener.eval("(describe *package*)");
	}

}
