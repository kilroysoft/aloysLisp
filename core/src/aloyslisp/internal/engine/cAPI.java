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
	 * Lambda keywords
	 */
	@Symb(name = "lambda-list-keywords", doc = "v_lambda")
	public static tSYMBOL	ALLOW_OTHER_KEYS	= sym("&allow-other-keys");
	public static tSYMBOL	AUX					= sym("&aux");
	public static tSYMBOL	BODY				= sym("&body");
	public static tSYMBOL	ENVIRONMENT			= sym("&environment");
	public static tSYMBOL	KEY					= sym("&key");
	public static tSYMBOL	OPTIONAL			= sym("&optional");
	public static tSYMBOL	REST				= sym("&rest");
	public static tSYMBOL	WHOLE				= sym("&whole");

	public static tLIST		LambdaListKeywords	= list(ALLOW_OTHER_KEYS, AUX,
														BODY, ENVIRONMENT, KEY,
														OPTIONAL, REST, WHOLE);

	/**
	 * argument representation in the java side.
	 * They will be set from LISP definition or from Methods for Java code.
	 */
	tLIST					vars				= NIL;

	Boolean					AllowOtherKeys		= false;

	Integer					basePos				= -1;

	tSYMBOL					rest				= null;

	tSYMBOL					whole				= null;

	tT						doc					= NIL;

	tENV					environment			= null;

	Boolean					special				= null;

	/**
	 * or lambda APIs
	 * 
	 * @param args
	 */
	public cAPI(tLIST args, tT doc, tLIST decl)
	{
		SET_API_ARGS(args);
		SET_API_DOC(doc);
		SET_API_DECL(decl);
		environment = e.topEnv;
	}

	/**
	 * For Code APIs
	 */
	public cAPI(Boolean special)
	{
		this.special = special;
		environment = e.topEnv;
	}

	private static tSYMBOL	DECLARE	= sym("declare");

	@Static(name = "api-parse-func")
	public static tLIST API_PARSE_FUNC(tLIST func)
	{
		tT doc = func.CAR();

		if (func.LENGTH() == 1 && doc instanceof tSTRING)
			return list(doc, NIL, func.CDR());

		if (doc instanceof tSTRING)
			func = (tLIST) func.CDR();
		else
			doc = NIL;

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
	 * @see aloyslisp.internal.engine.tAPI#API_ARGS()
	 */
	@Override
	public tLIST API_ARGS()
	{
		return null;
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
		LISTIterator iter = new LISTIterator(args);
		boolean key = false;
		while (iter.hasNext())
		{
			tT elem = iter.next();

		}
		return null;
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
		return null;
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
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#API_FUNCALL(aloyslisp.core.sequences.tLIST
	 * )
	 */
	@Override
	public tT[] FUNCALL(tLIST args)
	{
		tT[] res = new tT[]
		{ NIL };
		cENV closure = new cENV_CLOSURE(environment);
		try
		{
			API_PUSH_ENV(args);
			try
			{
				res = API_CALL(args);
			}
			finally
			{
				API_POP_ENV();
			}
		}
		finally
		{
			closure.ENV_STOP();
		}
		return res;
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
		return special ? value : value.EVAL()[0];
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
		API_INTERN_ARGS();
		LISTIterator iter = new LISTIterator(NIL, true);
		if (special)
		{
			for (tT elem : values)
			{
				iter.append(API_EVAL_ARG(elem));
			}
			values = (tLIST) iter.getFinal();
		}

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

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_GET_MAC()
	 */
	@Override
	public String API_GET_MAC()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#API_OBJECT(aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public tLIST API_OBJECT(tLIST args)
	{
		return args;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#API_CALL(aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public tT[] API_CALL(tLIST args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.functions.tFUNCTION#e(java.lang.Object[])
	 */
	@Override
	public tT[] e(Object... args)
	{
		// TODO Auto-generated method stub
		return FUNCALL(list(args));
	}

}
