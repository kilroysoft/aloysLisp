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
// IP 10 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * sRETURN_FROM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class sRETURN_FROM extends SPECIAL_OPERATOR
{

	/**
	 * @param args
	 * @param doc
	 * @param declare
	 */
	public sRETURN_FROM()
	{
		super(decl("tag", "&optional", "value"), //
				"(RETURN-FROM tag &optional )", //
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
		e.returnFrom(args[0], args[1].EVAL());
		return new tT[] {};
	}

}
