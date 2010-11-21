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
// IP 14 oct. 2010 Creation
// IP 04 nov. 2010 Works
// --------------------------------------------------------------------------
// To do
// --------------------------------------------------------------------------
// Set transformation table as an hash table
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import java.util.HashMap;
import java.util.Map;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.common.*;
import aloyslisp.core.types.*;

/**
 * READTABLE
 * 
 * <p>
 * There is a data structure called the readtable that is used to control the
 * reader. It contains information about the syntax of each character equivalent
 * to that in table 22-1. It is set up exactly as in table 22-1 to give the
 * standard Common Lisp meanings to all the characters, but the user can change
 * the meanings of characters to alter and customize the syntax of characters.
 * It is also possible to have several readtables describing different syntaxes
 * and to switch from one to another by binding the variable *readtable*.
 * 
 * @see <a href='http://clm.aloys.li/clm/node192.html'>22.1.5. The Readtable</a>
 * @see <a
 *      href='http://clm.aloys.li/clm/node188.html#StandardCharacterSyntaxTable'
 *      >
 *      Table 22-1: Standard Character Syntax Types
 *      </a>
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 */
public class READTABLE extends CELL implements tREADTABLE
{
	/**
	 * ReadTable data
	 */
	Map<String, tLIST>	map	= new HashMap<String, tLIST>();

	/**
	 * case for atom : :upcase, :downcase, :preserve and :invert
	 */
	CaseType			caseVal;

	/**
	 * caseType is the definition of case changes for read tables
	 * 
	 * @author Ivan Pierre {ivan@kilroysoft.ch}
	 * @author George Kilroy {george@kilroysoft.ch}
	 * 
	 */
	public enum CaseType
	{
		/**
		 * All in upper case
		 */
		UPCASE,

		/**
		 * All in downcase
		 */
		DOWNCASE,

		/**
		 * Don't change case
		 */
		PRESERVE,

		/**
		 * Invert case
		 */
		INVERT
	}

	/**
	 * 
	 */
	public READTABLE()
	{
		// default value
		caseVal = CaseType.DOWNCASE;

		// standard macro char
		setMacroCharacter('"', sym("sys::%string-reader").SYMBOL_FUNCTION(),
				false);
		setMacroCharacter('\'', sym("sys::%quote-reader").SYMBOL_FUNCTION(),
				false);
		setMacroCharacter('(', sym("sys::%parent-reader").SYMBOL_FUNCTION(),
				false);
		setMacroCharacter(')', sym("sys::%close-parent-reader")
				.SYMBOL_FUNCTION(), false);
		setMacroCharacter(',', sym("sys::%unquote-reader").SYMBOL_FUNCTION(),
				false);
		setMacroCharacter(';', sym("sys::%comment-reader").SYMBOL_FUNCTION(),
				false);
		setMacroCharacter('`', sym("sys::%backquote-reader").SYMBOL_FUNCTION(),
				false);

		// as standard only # as dispatch macro
		makeDispatchMacroCharacter('#', true);

		// reserved
		setDispatchMacroCharacter('#', '!', null);
		setDispatchMacroCharacter('#', '?', null);
		setDispatchMacroCharacter('#', '[', null);
		setDispatchMacroCharacter('#', ']', null);
		setDispatchMacroCharacter('#', '{', null);
		setDispatchMacroCharacter('#', '}', null);

		// signal error
		setDispatchMacroCharacter('#', '<', null);
		setDispatchMacroCharacter('#', '\b', null);
		setDispatchMacroCharacter('#', '\t', null);
		setDispatchMacroCharacter('#', '\r', null);
		setDispatchMacroCharacter('#', '\n', null);
		setDispatchMacroCharacter('#', '\f', null);
		setDispatchMacroCharacter('#', ' ', null);
		setDispatchMacroCharacter('#', ')', null);

		// undefined
		setDispatchMacroCharacter('#', '"', null);
		setDispatchMacroCharacter('#', '$', null);
		setDispatchMacroCharacter('#', '%', null);
		setDispatchMacroCharacter('#', '&', null);
		setDispatchMacroCharacter('#', ';', null);
		setDispatchMacroCharacter('#', '>', null);
		setDispatchMacroCharacter('#', '@', null);
		setDispatchMacroCharacter('#', '^', null);
		setDispatchMacroCharacter('#', '_', null);
		setDispatchMacroCharacter('#', '`', null);
		setDispatchMacroCharacter('#', '~', null);
		setDispatchMacroCharacter('#', '/', null);

		// macro char
		setDispatchMacroCharacter('#', '#', null);
		setDispatchMacroCharacter('#', '\'', sym("sys::%function-reader")
				.SYMBOL_FUNCTION());
		setDispatchMacroCharacter('#', '(', null);
		setDispatchMacroCharacter('#', '*', null);
		setDispatchMacroCharacter('#', ',', null);
		setDispatchMacroCharacter('#', ':', sym("sys::%unintern-reader")
				.SYMBOL_FUNCTION());
		setDispatchMacroCharacter('#', '=', null);
		setDispatchMacroCharacter('#', '\\', sym("sys::%character-reader")
				.SYMBOL_FUNCTION());
		setDispatchMacroCharacter('#', '|', null);
		setDispatchMacroCharacter('#', '+', null);
		setDispatchMacroCharacter('#', '-', null);
		setDispatchMacroCharacter('#', '.', null);
		setDispatchMacroCharacter('#', 'A', null);
		setDispatchMacroCharacter('#', 'B', null);
		setDispatchMacroCharacter('#', 'C', null);
		setDispatchMacroCharacter('#', 'O', null);
		setDispatchMacroCharacter('#', 'P', null);
		setDispatchMacroCharacter('#', 'R', null);
		setDispatchMacroCharacter('#', 'S', null);
		setDispatchMacroCharacter('#', 'X', null);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#copy()
	 */
	@Override
	public tT copy()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#printable()
	 */
	@Override
	public String printable()
	{
		return "#<READTABLE " + this.caseVal + ">";
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.collections.IReadTable#isConstituent(java.lang.Character
	 * )
	 */
	public boolean isConstituent(Character car)
	{
		switch (car)
		{
			case '\n':
			case '\r':
			case '\t':
			case ' ':
				return false;
			default:
				return map.get("" + car) == null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.collections.IReadTable#makeDispatchMacroCharacter
	 * (java.lang.Character, boolean)
	 */
	public void makeDispatchMacroCharacter(Character c, boolean nonTerm)
	{
		map.put("" + c, cons(null, NIL));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.collections.IReadTable#setDispatchMacroCharacter
	 * (java.lang.Character, java.lang.Character,
	 * aloyslisp.core.plugs.functions.IFunc)
	 */
	public void setDispatchMacroCharacter(Character disp, Character sub,
			tFUNCTION func)
	{
		map.put("" + disp + sub, cons(func, T));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.collections.IReadTable#getDispatchMacroCharacter
	 * (java.lang.Character, java.lang.Character)
	 */
	public tLIST getDispatchMacroCharacter(Character disp, Character sub)
	{
		return map.get("" + disp + sub);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.collections.IReadTable#setMacroCharacter(java.lang
	 * .Character, aloyslisp.core.plugs.functions.IFunc, boolean)
	 */
	public void setMacroCharacter(Character c, tFUNCTION func, boolean nonTerm)
	{
		map.put("" + c, cons(func, nonTerm ? NIL : T));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.collections.IReadTable#getMacroCharacter(java.lang
	 * .Character)
	 */
	public tLIST getMacroCharacter(Character c)
	{
		return map.get("" + c);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.collections.IReadTable#getCase()
	 */
	public tSYMBOL getCase()
	{
		return key(caseVal.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.collections.IReadTable#setCase(aloyslisp.core.plugs
	 * .atoms.ISymbol)
	 */
	public void setCase(tSYMBOL c)
	{
		// caseVal = Enum.valueOf(CaseType, c.getName().toUpperCase());
		String name = c.SYMBOL_NAME().getString();
		if (name.equalsIgnoreCase("downcase"))
		{
			caseVal = CaseType.DOWNCASE;
		}
		else if (name.equalsIgnoreCase("upcase"))
		{
			caseVal = CaseType.UPCASE;
		}
		else if (name.equalsIgnoreCase("invert"))
		{
			caseVal = CaseType.INVERT;
		}
		else if (name.equalsIgnoreCase("preserve"))
		{
			caseVal = CaseType.PRESERVE;
		}
		else
		{
			throw new LispException(
					"Invalid table case ahould be :upcase, :downcase, :preserve or :invert : "
							+ c);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.collections.IReadTable#changeCase(java.lang.Character
	 * )
	 */
	public Character changeCase(Character c)
	{
		switch (caseVal)
		{
			case DOWNCASE:
				return Character.toLowerCase(c);

			case UPCASE:
				return Character.toUpperCase(c);

			case INVERT:
				return Character.isLowerCase(c) ? Character.toUpperCase(c)
						: Character.toLowerCase(c);

			case PRESERVE:
				return c;

			default:
				throw new LispException("bad tableReader case : " + caseVal);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#coerce(aloyslisp.core.types.tT)
	 */
	@Override
	public tT COERCE(tT type)
	{
		// IMPLEMENT Coerce
		return null;
	}
}
