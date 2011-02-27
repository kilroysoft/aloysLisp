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
// IP 13 sept. 2010 Creation
// IP 01.12.2010 Pass function call to invoke() version
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import static aloyslisp.internal.engine.L.*;

import aloyslisp.core.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.engine.*;

/**
 * cFUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class cFUNCTION extends cCELL implements tFUNCTION, tAPI,
		tFUNCTION_DESIGNATOR
{
	/**
	 * Representation of arguments of the function
	 */
	public tAPI		api		= null;

	/**
	 * String used to represent the function in case of macrochar transform
	 */
	public String	mac		= null;

	/**
	 * Name of function
	 */
	tSYMBOL			name	= NIL;

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.IFunc#exec(aloyslisp.core.cCELL[])
	 */
	@Override
	public tT[] e(Object... args)
	{
		if (args.length == 0)
			return exec(NIL);
		return exec(list(args));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_VARS()
	 */
	@Override
	public tLIST API_VARS()
	{
		return api.API_VARS();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#SET_API_VARS(aloyslisp.core.sequences.
	 * tLIST)
	 */
	@Override
	public tLIST SET_API_VARS(tLIST vars)
	{
		return api.SET_API_VARS(vars);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_ARGS()
	 */
	@Override
	public tLIST API_ARGS()
	{
		return api.API_ARGS();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#SET_API_ARGS(aloyslisp.core.sequences.
	 * tLIST)
	 */
	@Override
	public tLIST SET_API_ARGS(tLIST args)
	{
		return api.SET_API_ARGS(args);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_DOC()
	 */
	@Override
	public tT API_DOC()
	{
		return api.API_DOC();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#SET_API_DOC(aloyslisp.core.tT)
	 */
	@Override
	public tT SET_API_DOC(tT doc)
	{
		return api.SET_API_DOC(doc);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_DECL()
	 */
	@Override
	public tLIST API_DECL()
	{
		return api.API_DECL();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#SET_API_DECL(aloyslisp.core.sequences.
	 * tLIST)
	 */
	@Override
	public tLIST SET_API_DECL(tLIST decl)
	{
		return api.SET_API_DECL(decl);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_INTERN_ARGS()
	 */
	@Override
	public cENV_LET API_INTERN_ARGS()
	{
		return api.API_INTERN_ARGS();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#API_INIT_VALUES(aloyslisp.internal.engine
	 * .cENV_LET, aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public cENV_LET API_INIT_VALUES(tLIST values)
	{
		return api.API_INIT_VALUES(values);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_EVAL(aloyslisp.core.tT)
	 */
	public tT API_EVAL_ARG(tT value)
	{
		return api.API_EVAL_ARG(value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_PUSH_ENV()
	 */
	@Override
	public tT[] API_PUSH_ENV(tLIST values)
	{
		return api.API_PUSH_ENV(values);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_POP_ENV()
	 */
	@Override
	public tAPI API_POP_ENV()
	{
		return api.API_POP_ENV();
	}

}
