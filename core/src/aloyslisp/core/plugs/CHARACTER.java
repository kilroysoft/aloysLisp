/**
 * aloysLisp.
 * <p>
 * A LISP interpreter, compiler and library.
 * <p>
 * Copyright (C) 2010 kilroySoft <aloyslisp@kilroysoft.ch>
 * 
 * <p>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
// --------------------------------------------------------------------------
// history
// --------------------------------------------------------------------------
// IP 20 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.common.*;
import aloyslisp.core.types.*;

/**
 * CHARACTER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class CHARACTER extends CELL implements tCHARACTER
{
	private Character			value;

	public static final String	standardChar	= "!\"#$%&'()*+,-./0123456789:;<=>?"
														+ "@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_"
														+ "`abcdefghijklmnopqrstuvwxyz{|}~";
	public static final String	userChar		= "[]{}?!^_~";
	public static final String	atomChar		= "$%&*+-./0123456789<=>"
														+ "@ABCDEFGHIJKLMNOPQRSTUVWXYZ"
														+ "abcdefghijklmnopqrstuvwxyz";
	public static final String	spaceChar		= "\n\r\t \u000e\u00ff";

	/**
	 * @param value
	 */
	public CHARACTER(Character value)
	{
		this.value = value;
	}

	/**
	 * @param value
	 */
	public CHARACTER(tSTRING value)
	{
		this.value = nameToChar(value.getString());
	}

	/**
	 * @param value
	 */
	public CHARACTER(String value)
	{
		this.value = nameToChar(value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#printable()
	 */
	public String printable()
	{
		return "" + value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tCHARACTER#getChar()
	 */
	public Character getChar()
	{
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tCHARACTER#getFormated()
	 */
	public String getFormated()
	{
		switch (value)
		{
			case '\n':
				return "#\\LineFeed";

			case '\r':
				return "#\\Return";

			case '\t':
				return "#\\Tab";

			case ' ':
				return "#\\Space";

		}
		return "#\\" + value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#compile()
	 */
	public String COMPILE()
	{
		return "new " + getClass().getCanonicalName() + "(\"" + value + "\")";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#copy()
	 */
	public tT copy()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#eql(aloyslisp.core.types.tT)
	 */
	public boolean EQL(tT cell)
	{
		if (this.getClass() != cell.getClass())
			return false;
		return value == ((tCHARACTER) cell).getChar();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#coerce(aloyslisp.core.types.tT)
	 */
	public tT COERCE(tT type)
	{
		// IMPLEMENT Coerce
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tCHARACTER#CHAR_UPCASE()
	 */
	@Override
	public tCHARACTER CHAR_UPCASE()
	{
		return c(Character.toUpperCase(value));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tCHARACTER#CHAR_DOWNCASE()
	 */
	@Override
	public tCHARACTER CHAR_DOWNCASE()
	{
		return c(Character.toLowerCase(value));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tCHARACTER#CHAR_NAME()
	 */
	@Override
	public tCHARACTER CHAR_NAME()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tCHARACTER#CHAR_INT()
	 */
	@Override
	public tT CHAR_INT()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param name
	 * @return
	 */
	Character nameToChar(String name)
	{
		if (name.length() == 1)
		{
			return name.charAt(0);
		}
		else if (name.equalsIgnoreCase("space"))
		{
			return ' ';
		}
		else if (name.equalsIgnoreCase("return"))
		{
			return '\r';
		}
		else if (name.equalsIgnoreCase("newline"))
		{
			return '\n';
		}
		else if (name.equalsIgnoreCase("backspace"))
		{
			return ' ';
		}
		else if (name.equalsIgnoreCase("tab"))
		{
			return '\t';
		}
		else if (name.equalsIgnoreCase("escape"))
		{
			return ' ';
		}
		else
		{
			throw new LispException("Invalid char : " + name);
		}
	}

}
