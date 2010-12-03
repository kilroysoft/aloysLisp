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
// IP 7 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.system;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * fpPUTD
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class fpPUTD extends SPECIAL_OPERATOR
{

	/**
	 * @param eval
	 */
	public fpPUTD()
	{
		super(decl("atom", "func"), //
				"(%putd atom func)", //
				NIL);
	}

	/**
	 * @return
	 */
	public tT[] IMPL()
	{
		return new tT[]
		{ arg(0), arg(1) };
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.ISpecialForm#implSpecial(aloyslisp.core
	 * .plugs.Cell[])
	 */
	@Override
	public tT[] implSpecial(tT[] args)
	{
		tT atom = args[0];
		tT func = args[1];

		if (!(atom instanceof tSYMBOL))
		{
			throw new LispException("Can't %PUTD on a non atom " + atom);
		}

		if (func instanceof tCONS)
		{
			func = func.EVAL()[0];
		}

		if (!(func instanceof tFUNCTION))
		{
			throw new LispException("Can't %PUTD with a non function " + func);
		}

		((tSYMBOL) atom).SET_SYMBOL_FUNCTION((tFUNCTION) func);

		return new tT[]
		{ NIL };
	}

}
