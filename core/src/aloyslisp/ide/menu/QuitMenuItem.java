package aloyslisp.ide.menu;

import aloyslisp.annotations.*;

/**
 * QuitMenuItem
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aJavaInternal
public class QuitMenuItem extends ActiveMenuItem
{
	private static final long	serialVersionUID	= 8730507036123350261L;

	/**
	 * @param label
	 */
	public QuitMenuItem(String label)
	{
		super(label);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.ide.menu.ActiveMenuItem#doAction()
	 */
	public boolean doAction()
	{
		System.exit(0);
		return false;
	}
}
