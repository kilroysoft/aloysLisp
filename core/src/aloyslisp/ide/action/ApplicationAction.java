package aloyslisp.ide.action;

import javax.swing.*;

import aloyslisp.annotations.*;

/**
 * ApplicationAction
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aJavaInternal
public abstract class ApplicationAction extends AbstractAction
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3769207973905602088L;

	protected String			f_name				= null;

	/**
	 * Creates a new Action.
	 * 
	 * @param mainApp
	 *            The application managing this action.
	 * @param name
	 *            The name of the menu item corresponding to this action.
	 * @param tooltip
	 *            A string to be displayed as brief user help.
	 * @param acceleratorKey
	 *            The KeyStroke used to access this action as a menu item.
	 * @param mnemonic
	 *            The (int) character used to access this action when it is a
	 *            button.
	 */
	public ApplicationAction(String name, Icon icon, String tooltip,
			KeyStroke acceleratorKey, Integer mnemonic)
	{
		super(name, icon);
		f_name = name;

		// Enabled by default.
		this.setEnabled(true);

		// Set various fields
		if (acceleratorKey != null)
			this.putValue(ACCELERATOR_KEY, acceleratorKey);

		if (mnemonic != null)
			this.putValue(MNEMONIC_KEY, mnemonic);

		if (name != null)
			this.putValue(NAME, name);

		if (tooltip != null)
			this.putValue(SHORT_DESCRIPTION, tooltip);
	}

	/**
	 * @return
	 */
	public String getText()
	{
		return (String) getValue(NAME);
	}

	/**
	 * @param text
	 */
	public void setText(String text)
	{
		putValue(NAME, text);
	}

	/**
	 * @return
	 */
	public ImageIcon getIcon()
	{
		return (ImageIcon) getValue(SMALL_ICON);
	}

	/**
	 * @param icon
	 */
	public void setIcon(ImageIcon icon)
	{
		putValue(SMALL_ICON, icon);
	}

	/**
	 * @return
	 */
	public Integer getMnemonicKey()
	{
		return (Integer) getValue(MNEMONIC_KEY);
	}

	/**
	 * @param mnemonicKey
	 */
	public void setMnemonicKey(Integer mnemonicKey)
	{
		putValue(MNEMONIC_KEY, mnemonicKey);
	}

	/**
	 * @return
	 */
	public String getDescription()
	{
		return (String) getValue(SHORT_DESCRIPTION);
	}

	/**
	 * @param description
	 */
	public void setDescription(String description)
	{
		putValue(SHORT_DESCRIPTION, description);
	}

}
