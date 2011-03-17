package aloyslisp.ide.menu;

import java.awt.*;

public class ActiveMenuItem extends MenuItem
{
	private static final long	serialVersionUID	= -777180505294093320L;

	public ActiveMenuItem(String label)
	{
		super(label);
	}

	public boolean doAction()
	{
		// Default menu item has no action.
		return false;
	}
}
