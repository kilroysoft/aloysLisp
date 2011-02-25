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
// IP 19 sept. 2010 Creation
// IP UB11 Correct function call, normally few orig call...
// IP UB12 Update commentaries
// IP UB20 Disconnect from tSYMBOL now own variable
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import static aloyslisp.internal.engine.L.*;
import aloyslisp.core.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;

/**
 * cDYN_SYMBOL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cDYN_SYMBOL extends cCELL implements tDYN_SYMBOL
{
	/**
	 * 
	 */
	protected tSYMBOL	orig;

	/**
	 * 
	 */
	protected tT		value;

	/**
	 * 
	 */
	protected tLIST		declare	= NIL;

	/**
	 * 
	 */
	protected boolean	special	= false;

	/**
	 * @param name
	 */
	public cDYN_SYMBOL(tSYMBOL orig, tT value)
	{
		this.orig = orig;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#set(aloyslisp.core.types.tT)
	 */
	public tSYMBOL SET_SYMBOL_VALUE(tT value)
	{
		this.value = value;
		return orig;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#fSet(aloyslisp.core.types.tFUNCTION)
	 */
	public tSYMBOL SET_SYMBOL_FUNCTION(tFUNCTION func)
	{
		this.value = func;
		return orig;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#get()
	 */
	public tT SYMBOL_VALUE()
	{
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#fGet()
	 */
	public tFUNCTION SYMBOL_FUNCTION()
	{
		if (value instanceof cFUNCTION)
			return (tFUNCTION) value;
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#unset()
	 */
	public tSYMBOL unset()
	{
		value = null;
		return orig;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#fUnset()
	 */
	public tSYMBOL fUnset()
	{
		value = null;
		return orig;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#getPack()
	 */
	public tPACKAGE SYMBOL_PACKAGE()
	{
		return orig.SYMBOL_PACKAGE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#setSpecial(boolean)
	 */
	public tSYMBOL setSpecial(boolean special)
	{
		this.special = special;
		return orig;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#isSpecial()
	 */
	public boolean isSpecial()
	{
		return special;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#setDeclare(aloyslisp.core.types.tLIST)
	 */
	public tSYMBOL setDeclare(tLIST declare)
	{
		this.declare = declare;
		return orig;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#getDeclare()
	 */
	public tLIST getDeclare()
	{
		return declare;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#getName()
	 */
	public String SYMBOL_NAME()
	{
		return orig.SYMBOL_NAME();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#getOrig()
	 */
	public tSYMBOL getOrig()
	{
		return orig;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#isSet()
	 */
	public boolean BOUNDP()
	{
		return SYMBOL_VALUE() != null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#isSetf()
	 */
	public boolean FBOUNDP()
	{
		tT res;
		return (res = SYMBOL_VALUE()) != null && res instanceof tFUNCTION;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return orig.hashCode();
	}

	public String toString()
	{
		return "#<DYN_SYMB " + orig + " " + value + ">";
	}

}
