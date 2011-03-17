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

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.iterators.*;
import static aloyslisp.core.L.*;

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
	protected tSYMBOL		name				= null;

	protected tLIST			vars				= NIL;

	protected tHASH_TABLE	keys				= null;

	protected Boolean		allowOtherKeys		= false;

	protected Integer		basePos				= -1;

	protected Integer		obl					= 0;

	protected tSYMBOL		rest				= null;

	protected tSYMBOL		whole				= null;

	protected tT			doc					= NIL;

	protected tENV			environment			= null;

	protected Boolean		special				= false;

	protected Boolean		macro				= false;

	/**
	 * or lambda APIs
	 * 
	 * @param args
	 */
	public cAPI(tLIST args, tT doc, tLIST decl)
	{
		vars = SET_API_ARGS(args);
		SET_API_DOC(doc);
		SET_API_DECL(decl);
		environment = e.topEnv;
	}

	/**
	 * For Code APIs
	 */
	public cAPI()
	{
		keys = cHASH_TABLE.MAKE_HASH_TABLE();
		environment = e.topEnv;
	}

	private static tSYMBOL	DECLARE	= sym("declare");

	@Static(name = "api-parse-func")
	public static tLIST API_PARSE_FUNC(tLIST func)
	{
		// System.out.println("API-PARSE-FUNC : " + func);
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
		LISTIterator res = new LISTIterator(NIL);
		if (whole != null)
		{
			res.append(WHOLE);
			res.append(whole);
		}

		int count = 1;
		Boolean isKey = false;
		Boolean isRest = false;
		Boolean isAllow = false;
		Boolean isAux = false;
		for (tT var : vars)
		{
			if (count == obl && ((cARG) var).base)
				res.append(OPTIONAL);
			if (!isKey && var instanceof cARG_KEY)
			{
				if (this.rest != null)
				{
					res.append(REST);
					res.append(this.rest);
					isRest = true;
				}
				res.append(KEY);
				isKey = true;
			}
			if (!isAux && !(var instanceof cARG_KEY) && !((cARG) var).base)
			{
				if (this.rest != null && !isRest)
				{
					res.append(REST);
					res.append(this.rest);
					isRest = true;
				}
				if (allowOtherKeys)
				{
					res.append(ALLOW_OTHER_KEYS);
					isAllow = true;
				}
				res.append(AUX);
				isAux = true;
			}
			res.append(((tARG) var).getOrig());
			count++;
		}
		if (this.rest != null && !isRest)
		{
			res.append(REST);
			res.append(this.rest);
		}
		if (allowOtherKeys && !isAllow)
			res.append(ALLOW_OTHER_KEYS);

		return (tLIST) res.getFinal();
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
		// System.out.println("SET-API-ARGS" + args);
		LISTIterator iter = new LISTIterator(args);
		LISTIterator newArgs = new LISTIterator(NIL);
		boolean key = false;
		boolean orig = true;
		int count = 0;
		while (iter.hasNext())
		{
			tT elem = iter.next();
			if (elem instanceof tSYMBOL)
			{
				String keyword = getKeyword((tSYMBOL) elem);
				if (keyword != null)
				{
					tT temp;
					if (keyword.equals("&whole"))
					{
						temp = iter.next();
						if (!(temp instanceof tSYMBOL))
							throw new LispException("&whole with non symbol : "
									+ temp);
						whole = (tSYMBOL) temp;
					}
					else if (keyword.equals("&rest"))
					{
						temp = iter.next();
						if (!(temp instanceof tSYMBOL))
							throw new LispException("&rest with non symbol : "
									+ temp);
						rest = (tSYMBOL) temp;
					}
					else if (keyword.equals("&key"))
					{
						key = true;
						orig = false;
					}
					else if (keyword.equals("&aux"))
					{
						key = false;
						orig = false;
					}
					else if (keyword.equals("&optional"))
					{
						obl = count;
					}
					else if (keyword.equals("&allow-other-keys"))
					{
						allowOtherKeys = true;
					}
					continue;
				}
			}
			count++;
			tARG var = null;
			if (key)
			{
				var = new cARG_KEY(elem);
				keys.SET_GETHASH(var, ((cARG_KEY) var).key);
			}
			else
				var = new cARG(elem, orig);

			newArgs.append(var);
		}

		tT res = newArgs.getFinal();
		// System.out.println("SET_API_ARGS (lambda): " + res);
		return (tLIST) res;
	}

	/**
	 * Return lambda list keyword if found
	 * 
	 * @param sym
	 * @return
	 */
	private String getKeyword(tSYMBOL sym)
	{
		tSYMBOL res = (tSYMBOL) LambdaListKeywords.FIND(sym).CAR();
		if (res == NIL)
			return null;
		return res.SYMBOL_NAME().toLowerCase();
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

	/**
	 * FORWARD definition of tFUNCTION's FUNCALL
	 * 
	 * @param args
	 * @return
	 */
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_EVAL(aloyslisp.core.tT)
	 */
	public tT API_EVAL_ARG(tT value)
	{
		return (special || macro) ? value : value.EVAL()[0];
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#API_EVAL_LIST(aloyslisp.core.sequences
	 * .tLIST)
	 */
	@Override
	public tLIST API_EVAL_LIST(tLIST list)
	{
		if (special)
			return list;

		LISTIterator iter = new LISTIterator(NIL, true);
		for (tT elem : list)
		{
			iter.append(API_EVAL_ARG(elem));
		}

		return (tLIST) iter.getFinal();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#API_PUSH_ENV(aloyslisp.core.sequences.
	 * tLIST)
	 */
	@Override
	public tLIST API_PUSH_ENV(tLIST values, tENV let)
	{
		// System.out.println("API_PUSH_ENV : " + values + " on " + this.vars
		// + " with " + DESCRIBE());
		cARG arg = null;
		values = API_EVAL_LIST((tLIST) values);
		// Init all vars
		LISTIterator var = new LISTIterator(this.vars);
		while (var.hasNext())
		{
			arg = (cARG) var.next();

			if (!arg.base)
				break;

			let.ENV_LET_INTERN(arg.orig, API_EVAL_ARG(arg.SYMBOL_VALUE()));
			if (arg.exists != null)
				let.ENV_LET_INTERN(arg.exists, NIL);
		}

		// mandatory and &optional
		LISTIterator iter = new LISTIterator(NIL, true);
		LISTIterator val = new LISTIterator(values);
		Boolean isKey = false;
		var = new LISTIterator(this.vars);

		if (macro && whole != null)
			// Add & whole
			let.ENV_LET_INTERN(whole, (tLIST) val.getFinal());

		arg = null;
		while (var.hasNext())
		{
			arg = (cARG) var.next();

			if (!arg.base)
			{
				isKey = true;
				var = new LISTIterator(var.getNode());
				break;
			}

			tT elem = null;
			if (val.hasNext())
			{
				elem = val.next();
				if (arg.exists != null)
					let.SET_ENV_LET_GET(arg.exists, T);
			}
			else
				elem = API_EVAL_ARG(arg.SYMBOL_VALUE());

			let.SET_ENV_LET_GET(arg.orig, elem);
			// System.out.println("Argument : " + elem.DESCRIBE());
			iter.append(elem);
		}

		tLIST res = (tLIST) iter.getFinal();

		// Add &rest
		if (rest != null)
			if (val.hasNext())
			{
				tLIST r = NIL;
				val.next();
				r = val.getNode();

				// reinitialize val for optional keys
				val = new LISTIterator(r);
				let.ENV_LET_INTERN(rest, r);
				res = (tLIST) res.APPEND(r);
			}
			else
				let.ENV_LET_INTERN(rest, NIL);

		while (var.hasNext())
		{
			arg = (cARG) var.next();
			let.ENV_LET_INTERN(arg.orig, API_EVAL_ARG(arg.SYMBOL_VALUE()));
			if (arg.exists != null)
				let.ENV_LET_INTERN(arg.exists, NIL);
		}

		// next &key
		while (isKey && val.hasNext())
		{
			tT key = val.next();
			// System.out.println("Key = " + key);
			if (!(key instanceof tSYMBOL))
				throw new LispException("argument not a key : " + key);

			tT keyArg = keys.GETHASH(key, NIL)[0];
			if (keyArg == NIL)
			{
				if (!((tSYMBOL) key).KEYWORDP())
					throw new LispException(
							"Non declared key is not a keyword :" + key);
				key = sym(((tSYMBOL) key).SYMBOL_NAME());
			}
			else
			{
				key = ((cARG_KEY) keyArg).orig;
			}

			if (!val.hasNext())
				throw new LispException("Value of key doesn't exists: " + key);
			tT value = val.next();
			let.ENV_LET_INTERN((tSYMBOL) key, value);
		}

		// return call arguments
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_GET_MAC()
	 */
	@Override
	public String API_GET_MAC()
	{
		// Standard no macro
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
		throw new LispException("Function action on LET environment");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tAPI#API_CALL(aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public tT[] API_CALL(tLIST args)
	{
		throw new LispException("Function action on LET environment");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_SPECIAL()
	 */
	@Override
	public Boolean API_SPECIAL()
	{
		return special;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#SET_API_SPECIAL(java.lang.Boolean)
	 */
	@Override
	public Boolean SET_API_SPECIAL(Boolean special)
	{
		return this.special = special;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_MACRO()
	 */
	@Override
	public Boolean API_MACRO()
	{
		return macro;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#SET_API_MACRO(java.lang.Boolean)
	 */
	@Override
	public Boolean SET_API_MACRO(Boolean macro)
	{
		return this.macro = macro;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#DESCRIBE()
	 */
	public String DESCRIBE()
	{
		return "#<API " + vars + " " + rest + " " + obl + " " + basePos
				+ " #<SPECIAL " + (special ? T : NIL) + "> #<MACRO "
				+ (macro ? T : NIL) + "> " + environment + ">";
	}

}
