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
// IP 22 oct. 2010 Creation
// IP UB10 cAPI order mandatory, optional, rest, key
// IP UB10 &aux variables
// IP UB10 &allow-other-keys
// IP UB10 Complete key definition ((keyword variable) default)
// IP UB19 A function to print arguments in lisp mode.
// IP UB19 Transform class name for compiled function classes
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import static aloyslisp.internal.engine.L.*;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.iterators.*;

/**
 * Main API definition.
 * This will define the ENV_LET environment for special forms execution. <li>
 * Only mandatory arguments</li> <li>Passed values are evaluated</li>
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cAPI extends cCELL implements tAPI
{
	/**
	 * argument representation in the java side.
	 * Normally vars + rest + mandatory = args + decl. It should exist an unique
	 * transformation function between them (not complete now, but it should...)
	 */
	tLIST	vars		= NIL;

	tSYMBOL	rest		= NIL;

	Integer	mandatory	= 0;

	/**
	 * argument representation in the lisp side
	 */
	tLIST	args		= null;

	/**
	 * Declare statements
	 */
	tLIST	decl		= NIL;

	/**
	 * Commentary on functions
	 */
	tT		doc			= NIL;

	/**
	 * Current environment at the time of API creation
	 */
	tENV	environment	= null;

	/**
	 * @param args
	 */
	public cAPI(tLIST args, tT doc, tLIST decl)
	{
		this.args = args;
		this.doc = doc;
		this.decl = decl;
		environment = e.topEnv;
	}

	private static tSYMBOL	DECLARE	= sym("declare");

	@Static(name = "api-parse-func")
	public static tLIST API_PARSE_FUNC(tLIST func)
	{
		tT doc = func.CAR();

		if (func.LENGTH() == 1)
		{
			if (doc instanceof tSTRING)
				return list(NIL, NIL, func);
		}

		LISTIterator iterDecl = new LISTIterator(NIL);
		LISTIterator iterFunc = new LISTIterator(func);
		while (iterFunc.hasNext())
		{
			tT item = iterFunc.next();

			if (!(item instanceof tLIST) || item.CAR() != DECLARE)
				break;

			iterFunc.append(item);
		}

		return list(doc, iterDecl.getFinal(), iterFunc.getNode());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_VARS()
	 */
	@Override
	public tLIST API_VARS()
	{
		return vars;
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
		return this.vars = vars;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_ARGS()
	 */
	@Override
	public tLIST API_ARGS()
	{
		return args;
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
		return this.args = args;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_DOC()
	 */
	@Override
	public tT API_DOC()
	{
		return doc;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#SET_API_DOC(aloyslisp.core.tT)
	 */
	@Override
	public tT SET_API_DOC(tT doc)
	{
		return this.doc = doc;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_DECL()
	 */
	@Override
	public tLIST API_DECL()
	{
		return decl;
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
		return this.decl = decl;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_INTERN_ARGS()
	 */
	@Override
	public cENV_LET API_INTERN_ARGS()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#API_INIT_VALUES(aloyslisp.core.sequences
	 * .tLIST)
	 */
	@Override
	public cENV_LET API_INIT_VALUES(tLIST values)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_EVAL(aloyslisp.core.tT)
	 */
	public tT API_EVAL_ARG(tT value)
	{
		return value.EVAL()[0];
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#API_PUSH_ENV(aloyslisp.core.sequences.
	 * tLIST)
	 */
	@Override
	public tT[] API_PUSH_ENV(tLIST values)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_POP_ENV()
	 */
	@Override
	public tAPI API_POP_ENV()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
