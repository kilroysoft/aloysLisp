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
// IP 7 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * sIF
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class sIF extends SPECIAL_OPERATOR
{

	/**
	 * 
	 */
	public sIF()
	{
		super(list("cond", "then", "&optional", "else"), //
				"(sIF then &optional else)", //
				NIL //
		);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.commonlisp.common_lisp.sPROGN#impl()
	 */
	public tT[] impl()
	{
		return new tT[]
		{ arg(0), arg(1), arg(2) };
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.SPECIAL_OPERATOR#implSpecial(aloyslisp.core
	 * .plugs.Cell[])
	 */
	public tT[] implSpecial(tT[] rest)
	{
		// Proceed with test
		boolean test = rest[0].EVAL()[0] != NIL;

		// return value
		tT res[] = test ? rest[1].EVAL() : rest[2].EVAL();

		return res;
	}
}
