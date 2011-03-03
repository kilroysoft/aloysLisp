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
// IP 21 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.core.*;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;

/**
 * cENV_LET
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cENV_LET extends cENV
{
	/**
	 * Local variables storage
	 */
	private cHASH_TABLE	vars	= null;

	/**
	 * @param name
	 * @param block
	 * @param previous
	 */
	public cENV_LET()
	{
		super();
		vars = new cHASH_TABLE(null, L.nInt(1), L.nFloat((float) 0.75));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.cENV#ENV_LET_GET(aloyslisp.core.packages.tSYMBOL
	 * )
	 */
	public tT[] ENV_LET_GET(tSYMBOL var)
	{
		tT[] res = new tT[]
		{ L.NIL, L.NIL };

		res = vars.GETHASH(var, L.NIL);
		if (res[1] == L.NIL)
		{
			if (previous == null)
				return new tT[]
				{ L.NIL, L.NIL };

			return previous.ENV_LET_GET(var);
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#ENV_LET_SET(aloyslisp.core.packages.tSYMBOL
	 * , aloyslisp.core.packages.tSYMBOL)
	 */
	@Override
	public tT[] SET_ENV_LET_GET(tSYMBOL var, tT value)
	{
		tT[] res = new tT[]
		{ L.NIL, L.NIL };

		res = vars.GETHASH(var, L.NIL);
		if (res[1] == L.NIL)
		{
			if (previous == null)
				return new tT[]
				{ L.NIL, L.NIL };

			return previous.ENV_LET_GET(var);
		}

		tDYN_SYMBOL dyn = (tDYN_SYMBOL) res[0];
		if (!dyn.BOUNDP())
			throw new LispException("Variable not bound : " + dyn);

		return new tT[]
		{ dyn.SET_SYMBOL_VALUE(value), L.T };
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#ENV_LET_INTERN(aloyslisp.core.packages
	 * .tSYMBOL)
	 */
	@Override
	public tDYN_SYMBOL ENV_LET_INTERN(tSYMBOL var)
	{
		return (tDYN_SYMBOL) vars.SET_GETHASH(null, var, L.NIL);
	}

}
