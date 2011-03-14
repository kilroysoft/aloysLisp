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
import aloyslisp.core.conditions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import static aloyslisp.core.L.*;

/**
 * cENV
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cENV extends cCELL implements tENV
{
	/**
	 * previous environment in the stack
	 */
	public tENV	previous	= null;

	/**
	 * Base constructor
	 * 
	 * @param previous
	 */
	public cENV()
	{
	}

	public tENV ENV_PUSH()
	{
		if (this == e.topEnv)
		{
			throw new LispException("Try to repush " + this);
		}
		previous = e.topEnv;
		e.topEnv = this;

		// System.out.println("Push " + this + " previous = " + previous);

		// we return saved old stack state
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_PREVIOUS()
	 */
	public tT[] ENV_PREVIOUS()
	{
		if (previous == null)
			return new tT[]
			{ NIL, NIL };
		else
			return new tT[]
			{ previous, T };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_PREVIOUS_LEXICAL()
	 */
	@Override
	public tT[] ENV_PREVIOUS_LEXICAL()
	{
		// TODO Auto-generated method stub
		return ENV_PREVIOUS();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_STOP()
	 */
	@Override
	public tENV ENV_POP()
	{
		// System.out.println("Pop " + this + " previous = " + previous);

		e.topEnv = previous;
		previous = null;
		return this;
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
		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return prev;

		prev = ((cENV) prev[0]).ENV_LET_GET(var);
		return prev;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#SET_ENV_LET_GET(aloyslisp.core.packages
	 * .tSYMBOL, aloyslisp.core.packages.tSYMBOL)
	 */
	@Override
	public tT[] SET_ENV_LET_GET(tSYMBOL var, tT value)
	{
		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return prev;

		return ((cENV) prev[0]).SET_ENV_LET_GET(var, value);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#ENV_LET_INTERN_VAL(aloyslisp.core.packages
	 * .tSYMBOL, aloyslisp.core.tT)
	 */
	@Override
	public tDYN_SYMBOL ENV_LET_INTERN(tSYMBOL var, tT val)
	{
		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			throw new LispException("");

		return ((cENV) prev[0]).ENV_LET_INTERN(var, val);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#TST_BLOCK(aloyslisp.core.packages.tSYMBOL)
	 */
	@Override
	public cENV_BLOCK ENV_BLOCK_TST(tSYMBOL name)
	{
		cENV_BLOCK res = null;

		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).ENV_BLOCK_TST(name);
		if (res != null)
			return res;

		prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).ENV_BLOCK_TST(name);
		return res;

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_STEP()
	 */
	@Override
	public tT[] ENV_STEP()
	{
		tT[] res = null;

		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).ENV_STEP();
		if (res != null)
			return res;

		prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).ENV_STEP();
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_TAG_TST(aloyslisp.core.tT)
	 */
	@Override
	public tLIST ENV_TAG_TST(tT tag)
	{
		tLIST res = null;

		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).ENV_TAG_TST(tag);
		if (res != null)
			return res;

		prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).ENV_TAG_TST(tag);
		return res;
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
		tT[] res = null;

		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).ENV_TAG_GET(tag);
		if (res != null)
			return res;

		prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).ENV_TAG_GET(tag);
		return res;
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
		tT[] res = null;

		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).SET_ENV_TAG_GET(tag, value);
		if (res != null)
			return res;

		prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).SET_ENV_TAG_GET(tag, value);
		return res;
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
		tT[] res = null;

		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).ENV_TAG_INTERN(tag, value);
		if (res != null)
			return res;

		prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == NIL)
			return null;

		res = ((cENV) prev[0]).ENV_TAG_INTERN(tag, value);
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_DUMP()
	 */
	@Override
	public tLIST ENV_DUMP()
	{
		System.out.println(DESCRIBE());
		if (previous == null)
			return list(this);
		return (tLIST) list(this).APPEND(previous.ENV_DUMP());
	}

}
