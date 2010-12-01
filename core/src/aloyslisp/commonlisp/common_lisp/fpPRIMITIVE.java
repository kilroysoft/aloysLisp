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
// IP 11 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * fpPRIMITIVE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class fpPRIMITIVE extends SYSTEM_FUNCTION
{

	/**
	 * @param args
	 * @param doc
	 * @param declare
	 */
	public fpPRIMITIVE()
	{
		super(decl("obj", "func", "&rest", "args"),
				"(%primitive obj func &rest args)", NIL);
	}

	public fpPRIMITIVE(tLIST args, String doc, tLIST declare)
	{
		super(args, doc, declare);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tFUNCTION#impl()
	 */
	@Override
	public tT[] impl()
	{
		Class<?> c = arg(1).getClass();

		// get function name
		tT func = arg(0);
		String name = null;
		if (func instanceof tSTRING)
			name = ((tSTRING) func).getString();
		else if (func instanceof tSYMBOL)
			name = ((tSYMBOL) func).SYMBOL_NAME();
		else
		{
			throw new LispException(
					"%primitive : func argument should be a string or a symbol: "
							+ func);
		}
		return invoke(c, arg(1), name, (tLIST) arg());
	}

}
