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
// IP 10 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * sFUNCTION
 * 
 * <p>
 * [Special Form]
 * <p>
 * function fn
 * 
 * <p>
 * The value of function is always the functional interpretation of fn; fn is
 * interpreted as if it had appeared in the functional position of a function
 * invocation. In particular, if fn is a symbol, the functional definition
 * associated with that symbol is returned; see symbol-function. If fn is a
 * lambda-expression, then a ``lexical closure'' is returned, that is, a
 * function that when invoked will execute the body of the lambda-expression in
 * such a way as to observe the rules of lexical scoping properly.
 * 
 * @see <a href='http://clm.aloys.li/node78.html'>7.1. Reference</a>
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class sFUNCTION extends SPECIAL_OPERATOR
{
	// private static final Integer func = 0;

	/**
	 * @param eval
	 */
	public sFUNCTION()
	{
		super(list("func"), //
				"(function func)", //
				NIL);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.IFunc#impl(aloyslisp.core.plugs.collections
	 * .IList)
	 */
	@Override
	public tT[] impl()
	{
		return new tT[]
		{ arg(0) };
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.ISpecialForm#implSpecial(aloyslisp.core
	 * .plugs.Cell[])
	 */
	@Override
	public tT[] implSpecial(tT[] args)
	{
		tT res = NIL;
		tT aFunc = args[0];

		// System.out.println("function:" + args[0]);

		if (aFunc instanceof tFUNCTION)
		{
			res = aFunc;
		}
		else if (aFunc instanceof tSYMBOL)
		{
			tFUNCTION func = ((tSYMBOL) aFunc).SYMBOL_FUNCTION();
			if (func == null)
			{
				System.out.println(cl.dump());
				throw new LispException(aFunc + " is not a function");
			}
			res = func;
		}
		else if (aFunc instanceof tCONS)
		{
			// anonymous function (lambda)
			res = getLambda(aFunc);
			if (res == null)
			{
				throw new LispException(aFunc
						+ " is not a correct lambda function");
			}
		}
		else
		{
			throw new LispException(aFunc + " is not a function");
		}

		// System.out.println("function (real):" + res);
		return new tT[]
		{ res };
	}

	/**
	 * Read a lambda form
	 * 
	 * @param lambda
	 * @return
	 */
	private LAMBDA_FUNCTION getLambda(tT lambda)
	{
		tLIST walk = (tLIST) lambda;
		tT func = NIL;
		tT args = NIL;

		// test if list is correct
		if (!(lambda instanceof tCONS) || ((tCONS) lambda).LENGTH() < 3)
			return null;

		// test lambda header
		if (!(walk.CAR() instanceof tSYMBOL)
				|| !((tSYMBOL) walk.CAR()).SYMBOL_NAME().getString()
						.equalsIgnoreCase("lambda"))
			return null;

		walk = (tLIST) walk.CDR();
		args = walk.CAR();
		func = ((tLIST) walk.CDR());

		if (!(args instanceof tLIST))
			return null;

		System.out.println("args:" + args + " func:" + func);

		return new LAMBDA_FUNCTION(key("lamda"), (tLIST) args, (tLIST) func);
	}
}
