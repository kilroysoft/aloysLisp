package aloyslisp.ide.action;

import aloyslisp.annotations.*;
import aloyslisp.ide.cIDE;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * RunTask executes the "(apropos "")" command.
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 *
 */
@aJavaInternal
public class RunTask extends ApplicationAction
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4501525622629758140L;
	protected cIDE				f_listener			= null;
	protected String			f_action			= "";

	public RunTask(cIDE listener, String name, Icon icon, String tooltip,
			KeyStroke acceleratorKey, Integer mnemonic, String action)
	{
		super(name, icon, tooltip, acceleratorKey, mnemonic);

		f_listener = listener;
		f_action = action;
	}

	public void actionPerformed(ActionEvent e)
	{
		f_listener.eval(f_action);
	}

}
