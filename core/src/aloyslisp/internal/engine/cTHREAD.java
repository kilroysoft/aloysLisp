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
// IP 21 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.core.*;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.packages.tSYMBOL;
import aloyslisp.core.sequences.*;
import static aloyslisp.internal.engine.L.*;

/**
 * cTHREAD
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cTHREAD
{
	/**
	 * cLEXICAL stack pointer
	 */
	public cENV	topEnv;

	/**
	 * Return
	 * 
	 * @param name
	 * @return
	 */
	public cDYN_SYMBOL arg(tSYMBOL name)
	{
		tT[] res = topEnv.ENV_LET_GET(name);
		if (res[1] == NIL)
			return null;

		return (cDYN_SYMBOL) res[0];
	}

	/**
	 * @param name
	 * @return
	 */
	public cDYN_SYMBOL read(tSYMBOL name)
	{
		return arg(name);
	}

	/**
	 * @param name
	 * @param val
	 * @return
	 */
	public cDYN_SYMBOL write(tSYMBOL name, tT val)
	{
		tT[] res = topEnv.SET_ENV_LET_GET(name, val);
		if (res[1] == NIL)
			return null;

		return (cDYN_SYMBOL) res[0];
	}

	/**
	 * @param name
	 * @return
	 */
	public tT readVal(tSYMBOL name)
	{
		cDYN_SYMBOL res = read(name);

		if (res == null)
			return null;

		return res.SYMBOL_VALUE();
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public tSYMBOL writeVal(tSYMBOL name, tT value)
	{
		cDYN_SYMBOL atom = read(name);
		if (atom == null)
			return null;
		return atom.SET_SYMBOL_VALUE(value);
	}

	/**
	 * Get special atom. We walk through all closure that could define the
	 * special variable.
	 * 
	 * @param name
	 * @return
	 */
	public cDYN_SYMBOL sRead(tSYMBOL name)
	{
		return topEnv.sRead(name);
	}

	/**
	 * @param name
	 * @return
	 */
	public cDYN_SYMBOL intern(tSYMBOL atom)
	{
		return topEnv.ENV_LET_INTERN(atom);
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public cDYN_SYMBOL intern(tSYMBOL atom, tT value)
	{
		return topEnv.intern(atom, value);
	}

}
