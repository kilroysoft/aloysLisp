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

import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;
import static aloyslisp.commonlisp.L.*;

/**
 * sLET
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class sLET extends SPECIAL_OPERATOR
{

	/**
	 * 
	 */
	public sLET()
	{
		super(list("decl", "&rest", "blocks"), //
				"(sLET ({var|(var [value])}*){declaration}*{form}*)", //
				NIL //
		);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.functions.IFunc#impl()
	 */
	@Override
	public tT[] impl()
	{
		return new tT[]
		{ arg(0), arg() };
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.SPECIAL_OPERATOR#implSpecial(aloyslisp.core
	 * .plugs.Cell[])
	 */
	public tT[] implSpecial(tT[] args)
	{
		tT arg = args[0];
		tT func = args[1];

		if (!(arg instanceof tLIST))
		{
			System.out.println("" + arg + " "
					+ arg.getClass().getCanonicalName());
			throw new LispException("Arguments of sLET not in a list : " + arg);
		}

		if (!(func instanceof tLIST))
		{
			throw new LispException("Function definition of sLET not a list : "
					+ func);
		}

		// execute unnamed block with optional arguments (args are not passed
		// but defined as default)
		newBlock(null, (tLIST) list("&optional").APPEND(arg), (tLIST) func, NIL);

		tT res[] = e.exec();
		e.popBlock();

		return res;
	}

}
