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

package aloyslisp.core.sequences;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.math.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.streams.*;
import static aloyslisp.core.L.*;

/**
 * cSTRING
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@BuiltIn(classOf = "string", doc = "t_string")
public class cSTRING extends cVECTOR implements tSTRING
{
	/**
	 * Constructor
	 */
	public cSTRING(String string)
	{
		for (int i = 0; i < string.length(); i++)
		{
			this.SET_ELT(new cCHARACTER(string.charAt(i)), i);
		}
	}

	/**
	 * @param str
	 * @return
	 */
	@Static(name = "string", doc = "f_string")
	public static String STRING( //
			@Arg(name = "mess") tSTRING_DESIGNATOR str)
	{
		if (str instanceof tSTRING)
			return ((cSTRING) str).getString();
		if (str instanceof tSYMBOL)
			return ((tSYMBOL) str).SYMBOL_NAME();
		if (str instanceof tCHARACTER)
			return "" + ((cCHARACTER) str).getChar();
		throw new LispException("Type error");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.IString#getString()
	 */
	@Override
	public String getString()
	{
		StringBuilder res = new StringBuilder("");
		for (int i = 0; i < this.LENGTH(); i++)
			res.append(ELT(i).toString());
		return res.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.Cell#printable()
	 */
	@Override
	public String toString()
	{
		// System.out.println("String Escape : " + printEscape.get());
		boolean esc = false;
		if (printEscape != null)
			esc = !(printEscape.SYMBOL_VALUE() instanceof cNULL);
		if (esc)
			return getString();
		else
			return "\"" + getString() + "\"";
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
		return new cSTRING(getString().toUpperCase());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTRING#toLowerCase()
	 */
	public tSTRING STRING_DOWNCASE()
	{
		return new cSTRING(getString().toLowerCase());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTRING#STRING_CAPITALIZE()
	 */
	@Override
	public tSTRING STRING_CAPITALIZE()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTRING#STRING_TRIM()
	 */
	@Override
	public tSTRING STRING_TRIM()
	{
		return new cSTRING(getString().trim());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTRING#STRING_LEFT_TRIM()
	 */
	@Override
	public tSTRING STRING_LEFT_TRIM()
	{
		return new cSTRING(getString().trim());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTRING#STRING_RIGHT_TRIM()
	 */
	@Override
	public tSTRING STRING_RIGHT_TRIM()
	{
		return new cSTRING(getString().trim());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSTRING#PARSE_INTEGER(java.lang.Integer,
	 * aloyslisp.core.java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	public cINTEGER PARSE_INTEGER(Integer start, tT end, Integer radix,
			Boolean junk)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
