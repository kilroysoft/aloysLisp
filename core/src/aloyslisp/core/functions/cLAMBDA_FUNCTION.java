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
// IP 27 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import aloyslisp.annotations.BuiltIn;
import aloyslisp.core.*;
import aloyslisp.core.packages.tSYMBOL;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.engine.cAPI;
import aloyslisp.internal.engine.cENV_PROGN;
import aloyslisp.packages.common_lisp.SpecialOperators;
import static aloyslisp.core.L.*;

/**
 * cLAMBDA_FUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@BuiltIn(classOf = "function", typeOf = "lambda-function", doc = "03_a")
public class cLAMBDA_FUNCTION extends cFUNCTION implements tLAMBDA_FUNCTION
{
	/**
	 * Lisp block to be executed
	 */
	protected tLIST	func	= NIL;

	/**
	 * @param args
	 * @param doc
	 * @param decl
	 */
	public cLAMBDA_FUNCTION(tSYMBOL name, tLIST args, tLIST compl,
			Boolean special, Boolean macro)
	{
		super();
		// System.out.println("cLAMBDA_FUNCTION " + name + " = " + compl);
		this.special = special;
		this.macro = macro;
		if (name != null)
			this.name = name;
		else
			this.name = NIL;
		tT doc = cAPI.API_PARSE_FUNC(compl);

		vars = SET_API_ARGS(args);
		SET_API_DOC(doc.CAR());
		SET_API_DECL((tLIST) doc.CDR().CAR());
		func = (tLIST) list(list(SpecialOperators.BLOCK, name).APPEND(
				doc.CDR().CDR().CAR()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tCODE#CODE_CALL(aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public tT[] API_CALL(tLIST args)
	{
		tT[] res = new tT[]
		{ NIL };
		// here the args are not used the environment is already set up
		cENV_PROGN prog = new cENV_PROGN(func.VALUES_LIST());
		prog.ENV_PUSH();
		try
		{
			res = prog.EVAL();
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		finally
		{
			prog.ENV_POP();
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.cAPI#DESCRIBE()
	 */
	public String DESCRIBE()
	{
		return "#<API-LAMBDA " + name + "" + API_ARGS() + "" + func + " "
				+ basePos + " #<SPECIAL " + (special ? T : NIL) + "> #<MACRO "
				+ (macro ? T : NIL) + "> " + "> #<ENV " + environment + ">"
				+ ">";
	}

}
