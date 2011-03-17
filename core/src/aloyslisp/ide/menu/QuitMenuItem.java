package aloyslisp.ide.menu;

public class QuitMenuItem extends ActiveMenuItem
{
	private static final long	serialVersionUID	= 8730507036123350261L;

	public QuitMenuItem(String label)
	{
		super(label);
	}

	public boolean doAction()
	{
		System.exit(0);
		return false;
	}
}
