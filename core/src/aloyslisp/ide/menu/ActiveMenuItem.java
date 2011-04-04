package aloyslisp.ide.menu;

import java.awt.*;

import aloyslisp.annotations.*;

/**
 * ActiveMenuItem
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aJavaInternal
public class ActiveMenuItem extends MenuItem
{
	private static final long	serialVersionUID	= -777180505294093320L;

	/**
	 * @param label
	 */
	public ActiveMenuItem(String label)
	{
		super(label);
	}

	/**
	 * @return
	 */
	public boolean doAction()
	{
		// Default menu item has no action.
		return false;
	}
}
