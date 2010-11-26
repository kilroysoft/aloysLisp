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
// IP 21 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * sSETQ
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class sSETQ extends SPECIAL_OPERATOR
{
	// private static final Integer list = 0;

	/**
	 * @param args
	 * @param commentary
	 * @param declare
	 */
	public sSETQ()
	{
		super(decl("&rest", "list"), //
				"(sSETQ &rest list)", //
				NIL);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.IFunc#impl(aloyslisp.core.plugs.collections
	 * .IList)
	 */
	@Override
	public tT[] impl()
	{
		return new tT[]
		{ arg() };
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.SPECIAL_OPERATOR#implSpecial(aloyslisp.core
	 * .plugs.Cell[])
	 */
	public tT[] implSpecial(tT[] args)
	{
		// we are in the block part values should be found in environment
		tT list = args[0];

		if (list instanceof NIL)
		{
			// no values to set
			return new tT[]
			{ NIL };
		}

		tT var = null;
		tT val = null;

		// loop on each pair var, value
		for (tT cell : (tLIST) list)
		{
			if (var == null)
			{
				// get var
				var = cell.CAR();
				if (!(var instanceof tSYMBOL))
				{
					ERROR("SETQ : ~s should be a SYMBOL", var);
				}
			}
			else
			{
				// get and set value
				val = cell.CAR().EVAL()[0];
				((tSYMBOL) var).SET_SYMBOL_VALUE(val);

				// prepare to get next variable
				var = null;
			}
		}

		return new tT[]
		{ val };
	}
}
