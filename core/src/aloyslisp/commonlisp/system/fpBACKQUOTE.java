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
// IP 4 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.system;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * fpBACKQUOTE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class fpBACKQUOTE extends SPECIAL_OPERATOR
{

	/**
	 * Macrochar ','
	 * 
	 * @param def
	 */
	public fpBACKQUOTE()
	{
		super(decl("obj"), //
				"(fpBACKQUOTE obj)", //
				NIL);
		mac = "`";
	}

	/**
	 * @return
	 */
	public tT[] IMPL()
	{
		// Pass code to special executor
		return new tT[]
		{ arg(0) };
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.SPECIAL_OPERATOR#implSpecial(aloyslisp.core
	 * .plugs.Cell[])
	 */
	public tT[] implSpecial(tT[] code)
	{
		tT res = code[0];

		if (res instanceof tCONS)
		{
			res = walk(res);
			res = res.CAR();
		}

		return new tT[]
		{ res };
	}

	private static final tSYMBOL	BACKQUOTE	= sym("sys::%backquote");
	private static final tSYMBOL	UNQUOTE		= sym("sys::%unquote");
	private static final tSYMBOL	SPLICE		= sym("sys::%splice");
	private static final tSYMBOL	NSPLICE		= sym("sys::%nsplice");

	/**
	 * @param cons
	 * @return
	 * @throws ExceptionLisp
	 */
	public tT walk(tT cons)
	{
		// System.out.println("walk:" + cons);

		// atom are themself
		if (!(cons instanceof tCONS))
			return list(cons);

		// test macro functions
		tT res = NIL;
		tT func = cons.CAR();

		if (func == BACKQUOTE) // internal ` = fpBACKQUOTE
		{
			return list(sym("lisp::list")).APPEND(cons);
		}
		else if (func == UNQUOTE) // , = fpUNQUOTE
		{
			cons = cons.CDR();
			cons = cons.CAR();
			return list(cons.EVAL()[0]);
		}
		else if (func == SPLICE) // ,@ = fpSPLICE
		{
			cons = cons.CDR();
			cons = cons.CAR();
			return cons.EVAL()[0];
		}
		else if (func == NSPLICE) // ,. = fpNSPLICE
		{
			// TODO fpNSPLICE is implemented as fpSPLICE
			cons = cons.CDR();
			cons = cons.CAR();
			return cons.EVAL()[0];
		}

		// Walk through the conses
		while (cons != NIL)
		{
			tT first = cons.CAR();

			// Recurse
			cons = cons.CDR();

			res = ((tLIST) res).APPEND(walk(first));
		}

		// New cons because it's set to the upper cdr , and ,@ diffs
		// (a) -> append .a // ((a)) will append a
		return list(res);
	}
}
