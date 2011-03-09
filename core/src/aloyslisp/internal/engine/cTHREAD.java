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
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;

/**
 * cTHREAD
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cTHREAD extends cCELL implements tENV
{
	/**
	 * cLEXICAL stack pointer
	 */
	public tENV	topEnv;

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

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_PUSH()
	 */
	@Override
	public tENV ENV_PUSH()
	{
		return topEnv.ENV_PUSH();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_PREVIOUS()
	 */
	@Override
	public tT[] ENV_PREVIOUS()
	{
		return topEnv.ENV_PREVIOUS();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_PREVIOUS_LEXICAL()
	 */
	@Override
	public tT[] ENV_PREVIOUS_LEXICAL()
	{
		return topEnv.ENV_PREVIOUS_LEXICAL();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_POP()
	 */
	@Override
	public tENV ENV_POP()
	{
		return topEnv.ENV_POP();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#ENV_LET_GET(aloyslisp.core.packages.tSYMBOL
	 * )
	 */
	@Override
	public tT[] ENV_LET_GET(tSYMBOL var)
	{
		return topEnv.ENV_LET_GET(var);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#SET_ENV_LET_GET(aloyslisp.core.packages
	 * .tSYMBOL, aloyslisp.core.tT)
	 */
	@Override
	public tT[] SET_ENV_LET_GET(tSYMBOL var, tT value)
	{
		return topEnv.SET_ENV_LET_GET(var, value);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#ENV_LET_INTERN(aloyslisp.core.packages
	 * .tSYMBOL, aloyslisp.core.tT)
	 */
	@Override
	public tDYN_SYMBOL ENV_LET_INTERN(tSYMBOL var, tT val)
	{
		return topEnv.ENV_LET_INTERN(var, val);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#ENV_TAG_GET(aloyslisp.core.packages.tSYMBOL
	 * )
	 */
	@Override
	public tT[] ENV_TAG_GET(tSYMBOL tag)
	{
		return topEnv.ENV_TAG_GET(tag);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#SET_ENV_TAG_GET(aloyslisp.core.packages
	 * .tSYMBOL, aloyslisp.core.packages.tSYMBOL)
	 */
	@Override
	public tT[] SET_ENV_TAG_GET(tSYMBOL tag, tSYMBOL value)
	{
		return topEnv.SET_ENV_TAG_GET(tag, value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_TAG_TST(aloyslisp.core.tT)
	 */
	@Override
	public tLIST ENV_TAG_TST(tT tag)
	{
		return topEnv.ENV_TAG_TST(tag);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#ENV_TAG_INTERN(aloyslisp.core.packages
	 * .tSYMBOL, aloyslisp.core.packages.tSYMBOL)
	 */
	@Override
	public tT[] ENV_TAG_INTERN(tSYMBOL tag, tSYMBOL value)
	{
		return topEnv.ENV_TAG_INTERN(tag, value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_STEP()
	 */
	@Override
	public tT[] ENV_STEP()
	{
		return topEnv.ENV_STEP();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#ENV_BLOCK_TST(aloyslisp.core.packages.
	 * tSYMBOL)
	 */
	@Override
	public cENV_BLOCK ENV_BLOCK_TST(tSYMBOL name)
	{
		return topEnv.ENV_BLOCK_TST(name);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_DUMP()
	 */
	@Override
	public tLIST ENV_DUMP()
	{
		return topEnv.ENV_DUMP();
	}

}
