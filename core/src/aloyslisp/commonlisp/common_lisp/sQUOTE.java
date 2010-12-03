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
// IP 15 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * sQUOTE
 * 
 * <p>
 * [Special Form]
 * <p>
 * quote object
 * 
 * <p>
 * (quote x) simply returns x. The object is not evaluated and may be any Lisp
 * object whatsoever. This construct allows any Lisp object to be written as a
 * constant value in a program.
 * 
 * @see <a href='http://clm.aloys.li/node78.html'>7.1. Reference</a>
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class sQUOTE extends SPECIAL_OPERATOR
{
	// private static final Integer cell = 0;

	/**
	 * @param eval
	 */
	public sQUOTE()
	{
		super(decl("cell"), //
				"(sQUOTE cell)", //
				NIL);
		mac = "'";
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.FUNCTION#IMPL(aloyslisp.core.plugs.CELL)
	 */
	public tT[] IMPL()
	{
		return new tT[]
		{ arg(0) };
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.ISpecialForm#implSpecial(aloyslisp.core
	 * .plugs.Cell[])
	 */
	public tT[] implSpecial(tT[] args)
	{
		return args;
	}
}
