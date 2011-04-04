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
// IP 15 sept. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp;

import aloyslisp.core.conditions.LispException;
import aloyslisp.core.streams.cPATHNAME;
import aloyslisp.internal.engine.cTHREAD;
import static aloyslisp.core.streams.cSTREAM.*;
import static aloyslisp.internal.engine.cINTERNAL_CLASS.*;
import static aloyslisp.core.L.*;

/**
 * Lisp
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class Lisp
{

	/**
	 * REPL
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		// Should be the first to allow READTABLE to be active in class
		// loading...
		MAKE_INTERNAL_CLASS("aloyslisp.core.streams.cREADTABLE");
		MAKE_INTERNAL_CLASS("aloyslisp.internal.engine.tINTERNAL_CLASSES");
		MAKE_INTERNAL_CLASS("aloyslisp.internal.engine.cINTERNAL_CLASSES");
		MAKE_INTERNAL_CLASS("aloyslisp.packages.common_lisp.SpecialOperators");

		// Load classes and repl definition
		LOAD(new cPATHNAME("class.lisp"), false, false, false);

		// Load first lisp file (REPL definition)
		eval("(print \"aloysLisp v.V314\")");

		// loop recovering errors
		for (;;)
		{
			try
			{
				eval("(repl)");
			}
			catch (Exception ex)
			{
				debug(ex);

				e = new cTHREAD();
			}
		}
	}

	/**
	 * @param ex
	 */
	public static void debug(Exception ex)
	{
		System.err.println(ex.getLocalizedMessage());
		// System.err.println("*trace* = " + sym("*trace*").SYMBOL_VALUE());
		if (ex instanceof LispException)
		{
			if (((LispException) ex).trace)
				ex.printStackTrace();
		}
		else
			ex.printStackTrace();
	}
}
