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
import aloyslisp.core.packages.tSYMBOL;
import aloyslisp.core.sequences.tLIST;

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
	 * Environment stack
	 */
	protected static tENV	top			= null;

	/**
	 * previous environment in the stack
	 */
	protected tENV			previous	= null;

	/**
	 * Base constructor
	 * 
	 * @param previous
	 */
	public cENV()
	{
		this.previous = top;
		top = this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_PREVIOUS()
	 */
	public tT[] ENV_PREVIOUS()
	{
		if (previous == null)
			return new tT[]
			{ L.NIL, L.NIL };
		else
			return new tT[]
			{ previous, L.T };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_STOP()
	 */
	@Override
	public tENV ENV_STOP()
	{
		top = previous;
		return this;
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
	 * @see
	 * aloyslisp.internal.engine.tENV#ENV_LET_GET(aloyslisp.core.packages.tSYMBOL
	 * )
	 */
	@Override
	public tT[] ENV_LET_GET(tSYMBOL var)
	{
		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == L.NIL)
			return prev;

		return ((cENV) prev[0]).ENV_LET_GET(var);
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
		if (prev[1] == L.NIL)
			return prev;

		return ((cENV) prev[0]).SET_ENV_LET_GET(var, value);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#ENV_LET_INTERN(aloyslisp.core.packages
	 * .tSYMBOL, aloyslisp.core.packages.tSYMBOL)
	 */
	@Override
	public tT[] ENV_LET_INTERN(tSYMBOL var)
	{
		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == L.NIL)
			return prev;

		return ((cENV) prev[0]).ENV_LET_INTERN(var);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#TST_BLOCK(aloyslisp.core.packages.tSYMBOL)
	 */
	@Override
	public cENV_BLOCK ENV_BLOCK_TST(tSYMBOL name)
	{
		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == L.NIL)
			return null;

		return ((cENV) prev[0]).ENV_BLOCK_TST(name);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_STEP()
	 */
	@Override
	public tT[] ENV_STEP()
	{
		tT[] prev = ENV_PREVIOUS();
		if (prev[1] == L.NIL)
			return null;

		return ((cENV) prev[0]).ENV_STEP();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_NEXT_STEP()
	 */
	@Override
	public tLIST ENV_NEXT_STEP()
	{
		tT[] prev = ENV_PREVIOUS();
		if (prev[1] == L.NIL)
			return null;

		return ((cENV) prev[0]).ENV_NEXT_STEP();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_TAG_TST(aloyslisp.core.tT)
	 */
	@Override
	public tT ENV_TAG_TST(tT tag)
	{
		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == L.NIL)
			return null;

		return ((cENV) prev[0]).ENV_TAG_TST(tag);
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
		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == L.NIL)
			return null;

		return ((cENV) prev[0]).ENV_TAG_GET(tag);
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
		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == L.NIL)
			return null;

		return ((cENV) prev[0]).SET_ENV_TAG_GET(tag, value);
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
		tT[] prev = ENV_PREVIOUS_LEXICAL();
		if (prev[1] == L.NIL)
			return null;

		return ((cENV) prev[0]).ENV_TAG_INTERN(tag, value);
	}

}
