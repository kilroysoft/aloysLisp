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
// IP 10 mars 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import static aloyslisp.core.L.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.engine.*;

/**
 * cFUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class cFUNCTION extends cAPI implements tFUNCTION
{
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.functions.tFUNCTION#e(java.lang.Object[])
	 */
	@Override
	public tT[] e(Object... args)
	{
		return FUNCALL(list(args));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.functions.tFUNCTION#FUNCALL(aloyslisp.core.sequences.tLIST
	 * )
	 */
	public tT[] FUNCALL(tLIST args)
	{
		// System.out.println("FUNCALL : " + name + " " + args + " " +
		// DESCRIBE());
		tT[] res = new tT[]
		{ NIL };

		tENV closure = null;
		tENV let = new cENV_LET();
		let.ENV_PUSH();
		try
		{
			args = API_PUSH_ENV(args, let);
			if (!(special || macro))
			{
				closure = new cENV_CLOSURE(name, environment);
				((cENV) closure).previous = ((cENV) let).previous;
				((cENV) let).previous = closure;
				e.topEnv = let;
			}
			// System.out.println("API-CALL :" + DESCRIBE() + "\nargs : " +
			// args);
			res = API_CALL(args);
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new LispException("Non lisp error in " + this + " : "
					+ e.getLocalizedMessage());
		}
		finally
		{
			if (!(special || macro) && closure != null)
			{
				let.ENV_POP();
				closure.ENV_POP();
			}
		}
		return res;
	}

}
