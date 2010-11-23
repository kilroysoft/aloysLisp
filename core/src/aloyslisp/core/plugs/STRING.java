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
// IP 12 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.types.*;

/**
 * STRING
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class STRING extends VECTOR implements tSTRING
{
	/**
	 * Constructor
	 */
	public STRING(String string)
	{
		for (int i = 0; i < string.length(); i++)
		{
			this.SETF_ELT(i, new CHARACTER(string.charAt(i)));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.IString#getString()
	 */
	@Override
	public String getString()
	{
		StringBuilder res = new StringBuilder("");
		for (int i = 0; i < this.LENGTH(); i++)
			res.append(ELT(i).printable());
		return res.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#printable()
	 */
	@Override
	public String printable()
	{
		// System.out.println("String Escape : " + printEscape.get());
		boolean esc = !(printEscape.SYMBOL_VALUE() instanceof NIL);
		if (esc)
			return "\"" + getString() + "\"";
		else
			return getString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#equal(aloyslisp.core.types.tT)
	 */
	public boolean EQUAL(tT cell)
	{
		if (!(cell instanceof tSTRING))
			return false;

		return getString().equals(((tSTRING) cell).getString());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#equalp(aloyslisp.core.types.tT)
	 */
	public boolean EQUALP(tT cell)
	{
		if (!(cell instanceof tSTRING))
			return false;

		return getString().equalsIgnoreCase(((tSTRING) cell).getString());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTRING#toUpperCase()
	 */
	public tSTRING STRING_UPCASE()
	{
		return new STRING(getString().toUpperCase());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTRING#toLowerCase()
	 */
	public tSTRING STRING_DOWNCASE()
	{
		return new STRING(getString().toLowerCase());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTRING#STRING_CAPITALIZE()
	 */
	@Override
	public tSTRING STRING_CAPITALIZE()
	{
		// TODO Capitalize
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTRING#STRING_TRIM()
	 */
	@Override
	public tSTRING STRING_TRIM()
	{
		return new STRING(getString().trim());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTRING#STRING_LEFT_TRIM()
	 */
	@Override
	public tSTRING STRING_LEFT_TRIM()
	{
		// TODO Left trim
		return new STRING(getString().trim());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTRING#STRING_RIGHT_TRIM()
	 */
	@Override
	public tSTRING STRING_RIGHT_TRIM()
	{
		// TODO Right trim
		return new STRING(getString().trim());
	}

}
