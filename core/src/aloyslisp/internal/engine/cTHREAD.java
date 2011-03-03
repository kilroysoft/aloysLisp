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
import aloyslisp.core.packages.tSYMBOL;
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
		if (topEnv == null)
			return null;
		tT[] res = topEnv.ENV_LET_GET(name);
		if (res[1] == NIL)
			return null;

		return (cDYN_SYMBOL) res[0];
	}

	/**
	 * @param name
	 * @param val
	 * @return
	 */
	public cDYN_SYMBOL writeEnv(tSYMBOL name, tT val)
	{
		if (topEnv == null)
			return null;
		tT[] res = topEnv.SET_ENV_LET_GET(name, val);
		if (res[1] == NIL)
			return null;

		return (cDYN_SYMBOL) res[0];
	}

	/**
	 * @param name
	 * @return
	 */
	public tT readEnv(tSYMBOL name)
	{
		cDYN_SYMBOL res = arg(name);

		if (res == null)
			return null;

		return res.SYMBOL_VALUE();
	}

}
