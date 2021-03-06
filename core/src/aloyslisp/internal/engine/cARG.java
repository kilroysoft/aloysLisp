/**
 * aloysLisp.
 * <p>
 * A LISP interpreter, compiler and library.
 * <p>
 * Copyright (C) 2010-2011 kilroySoft <aloyslisp@kilroysoft.ch>
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
// IP 26 f�vr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.core.*;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.packages.*;
import static aloyslisp.core.L.*;

/**
 * cARG
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cARG extends cDYN_SYMBOL implements tARG
{
	public Boolean	base	= true;
	public tSYMBOL	exists	= null;

	/**
	 * @param orig
	 * @param value
	 */
	public cARG(tSYMBOL orig, tT value, Boolean base)
	{
		super(orig, value);
		this.base = base;
	}

	public cARG(tT arg, Boolean base)
	{
		super(NIL, NIL);
		this.base = base;
		if (arg instanceof tSYMBOL)
		{
			this.SET_SYMBOL_ORIG((tSYMBOL) arg);
			return;
		}
		tT symbol = arg.CAR();
		if (!(symbol instanceof tSYMBOL))
			throw new LispException("Argument is not a symbol : " + symbol);
		this.SET_SYMBOL_ORIG((tSYMBOL) arg.CAR());
		arg = arg.CDR();
		this.value = arg.CAR();
		arg = arg.CDR();
		if (arg.CAR() != NIL)
		{
			arg = arg.CAR();
			if (!(arg instanceof tSYMBOL))
				throw new LispException("Exist argument should be a SYMBOL : "
						+ arg);
			exists = (tSYMBOL) arg;
		}
	}

	/**
	 * @param orig
	 * @param value
	 */
	public cARG(String orig, String value)
	{
		super(sym(orig), value.equals("") ? NIL : lisp(value));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#DESCRIBE()
	 */
	public String DESCRIBE()
	{
		return "#<DYN_ARG " + orig.TO_STRING() + " " + value + ""
				+ (special ? T : NIL) + " " + (base ? T : NIL) + " " + value
				+ ">";
	}

}
