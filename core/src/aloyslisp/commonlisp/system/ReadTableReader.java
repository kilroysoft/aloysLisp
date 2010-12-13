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
// IP 7 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.system;

import static aloyslisp.commonlisp.L.*;

import java.io.EOFException;

import aloyslisp.core.common.*;
import static aloyslisp.core.plugs.INPUT_STREAM.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.annotations.*;
import aloyslisp.core.types.*;

/**
 * ReadTableReader
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class ReadTableReader
{

	// ***************************************************************************
	private static final tSYMBOL	BACKQUOTE	= sym("sys::%backquote");

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%backquote_reader")
	public tT BACKQUOTE_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		try
		{
			return list(BACKQUOTE, READ(in, false, NIL, true));
		}
		catch (EOFException e)
		{
			throw new LispException("Error in reading a value after "
					+ BACKQUOTE);
		}
	}

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%character_reader")
	public tT CHARACTER_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		try
		{
			// read atom (for example newline) if single char first char is
			// escaped to keep case
			return new CHARACTER(in.readAtom(false, NIL, true));
		}
		catch (EOFException e)
		{
			throw new LispException(
					"Error in reading a character definition after #\\");
		}
	}

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%comment_reader")
	public tT COMMENT_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		Character curr;

		try
		{
			// TODO COMMENT_READER should test on eol
			while ((curr = READ_CHAR(in, false, NIL, true)) != '\n'
					&& curr != '\r')
				;

			// we should reverse the list
			return READ(in, false, NIL, true);
		}
		catch (EOFException e)
		{
			// end of file reading a commentary, well nothing...
			return null;
		}
	}

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%close_parent_reader")
	public tT CLOSE_PARENT_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		return sym("sys::)");
	}

	// ***************************************************************************
	private static final tSYMBOL	FUNCTION	= sym("lisp::function");

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%function_reader")
	public tT FUNCTION_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		try
		{
			return list(FUNCTION, READ(in, false, NIL, true));
		}
		catch (EOFException e)
		{
			throw new LispException("Error in reading a value after "
					+ FUNCTION);
		}
	}

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%parent_reader")
	public tT PARENT_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		tLIST res = NIL;
		try
		{
			tT curr = READ(in, false, NIL, true);
			while (!(curr instanceof tSYMBOL)
					|| !((tSYMBOL) curr).SYMBOL_NAME().equals(")"))
			{
				// manage dotted lists
				if (curr instanceof tSYMBOL
						&& ((tSYMBOL) curr).SYMBOL_NAME().equals("."))
				{
					curr = READ(in, false, NIL, true);
					res = (tLIST) ((tLIST) res.REVERSE()).APPEND(curr);
					curr = READ(in, false, NIL, true);

					// dotted pair should end
					if (curr instanceof tSYMBOL
							&& ((tSYMBOL) curr).SYMBOL_NAME().equals(")"))
						return res;

					throw new LispException("Dotted list bad end");
				}

				// else append element of list
				res = cons(curr, res);
				curr = READ(in, false, NIL, true);
			}

			// we should reverse the list
			return res.REVERSE();
		}
		catch (EOFException e)
		{
			throw new LispException("Error in reading a list : " + res);
		}
	}

	// ***************************************************************************
	private static final tSYMBOL	QUOTE	= sym("lisp::quote");

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%quote_reader")
	public tT QUOTE_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		try
		{
			return quote(READ(in, false, NIL, true));
		}
		catch (EOFException e)
		{
			throw new LispException("Error in reading a value after " + QUOTE);
		}
	}

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%string_reader")
	public tT STRING_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		StringBuilder str = new StringBuilder();
		Character curr;

		try
		{
			while ((curr = READ_CHAR(in, false, NIL, true)) != '"')
			{
				if (curr == '\\')
				{
					curr = READ_CHAR(in, false, NIL, true);
				}

				str.append(curr);
			}

			// we should return as LISP String
			return str(str.toString());
		}
		catch (EOFException e)
		{
			throw new LispException("Error in reading a string : " + str);
		}
	}

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%unintern_reader")
	public tT UNINTERN_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		try
		{
			String varName = in.readAtom(false, NIL, true);
			if (varName.equals(""))
				throw new LispException(
						"#: should be followed by an symbol name");
			return new SYMBOL(varName, null);
		}
		catch (EOFException e)
		{
			throw new LispException("Error in reading an uninterned variable");
		}
	}

	// ***************************************************************************
	private static final tSYMBOL	SPLICE	= sym("sys::%splice");
	private static final tSYMBOL	NSPLICE	= sym("sys::%nsplice");
	private static final tSYMBOL	UNQUOTE	= sym("sys::%unquote");

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%unquote_reader")
	public tT UNQUOTE_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		Character sup = ' ';
		try
		{
			sup = PEEK_CHAR(in, NIL, false, NIL, true);
			switch (sup)
			{
				case '@':
					READ_CHAR(in, false, NIL, true);
					return list(SPLICE, READ(in, false, NIL, true));

				case '.':
					READ_CHAR(in, false, NIL, true);
					return list(NSPLICE, READ(in, false, NIL, true));

				default:
					return list(UNQUOTE, READ(in, false, NIL, true));
			}
		}
		catch (EOFException e)
		{
			throw new LispException("Error in reading a value after ," + sup);
		}
	}

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%backquote")
	@Special
	@Mac(prefix = "`")
	public tT BACKQUOTE( //
			@Arg(name = "sys::obj") tT obj)
	{
		if (obj instanceof tCONS)
		{
			obj = walk(obj);
			obj = obj.CAR();
		}

		return obj;
	}

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

	/***************************************************************************
	 * @param obj
	 * @return
	 */
	@Static(name = "sys::%nsplice")
	@Mac(prefix = ",.")
	public tT NSPLICE( //
			@Rest(name = "sys::obj") tT... obj)
	{
		ERROR(",. without `");
		return NIL;
	}

	/***************************************************************************
	 * @param obj
	 * @return
	 */
	@Static(name = "sys::%splice")
	@Mac(prefix = ",@")
	public tT SPLICE( //
			@Rest(name = "sys::obj") tT... obj)
	{
		ERROR(",@ without `");
		return NIL;
	}

	/***************************************************************************
	 * @param obj
	 * @return
	 */
	@Static(name = "sys::%unquote")
	@Mac(prefix = ",")
	public tT UNQUOTE( //
			@Rest(name = "sys::obj") tT... obj)
	{
		ERROR(", without `");
		return NIL;
	}

}
