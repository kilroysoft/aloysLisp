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
// IP 14 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;

import aloyslisp.core.common.*;
import aloyslisp.core.plugs.Primitives;
import aloyslisp.core.types.*;

/**
 * fpGLOBAL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class fpGLOBAL extends fpPRIMITIVE
{

	/**
	 * @param args
	 * @param doc
	 * @param declare
	 */
	public fpGLOBAL()
	{
		super(list("func", "&rest", "args"), "(%global func &rest args)", NIL);
	}

	static final tSYMBOL	sType	= key("type-class");

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.commonlisp.common_lisp.fpPRIMITIVE#impl()
	 */
	@SuppressWarnings("unchecked")
	public tT[] impl()
	{
		// Search method
		Class<Primitives> c;
		try
		{
			c = (Class<Primitives>) Class
					.forName("aloyslisp.core.plugs.Primitives");
		}
		catch (ClassNotFoundException e1)
		{
			ERROR("%global : System error Primitives not found", NIL);
			return null;
		}

		// get function name
		tT func = arg(0);
		String name = null;
		if (func instanceof tSTRING)
			name = ((tSTRING) func).getString();
		else if (func instanceof tSYMBOL)
			name = ((tSYMBOL) func).SYMBOL_NAME().getString();
		else
		{
			throw new LispException(
					"%global : func argument should be a string or a symbol: "
							+ func);
		}

		return invoke(c, null, name, (tLIST) arg());
	}

}
