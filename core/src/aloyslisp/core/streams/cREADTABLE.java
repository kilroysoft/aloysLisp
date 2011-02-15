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
// IP 14 oct. 2010 Creation
// IP 04 nov. 2010 Works
// --------------------------------------------------------------------------
// To do
// --------------------------------------------------------------------------
// Set transformation table as an hash table
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import java.io.EOFException;
import java.util.*;

import static aloyslisp.core.engine.L.*;
import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.packages.cSYMBOL;
import aloyslisp.core.packages.tSYMBOL;
import aloyslisp.core.sequences.*;

/**
 * cREADTABLE
 * 
 * <p>
 * There is a data structure called the readtable that is used to control the
 * reader. It contains information about the syntax of each character equivalent
 * to that in table 22-1. It is set up exactly as in table 22-1 to give the
 * standard Common Lisp meanings to all the characters, but the user can change
 * the meanings of characters to alter and customize the syntax of characters.
 * It is also possible to have several readtables describing different syntaxes
 * and to switch from one to another by binding the variable *readtable*.
 * 
 * @see <a href='http://clm.aloys.li/clm/node192.html'>22.1.5. The Readtable</a>
 * @see <a
 *      href='http://clm.aloys.li/clm/node188.html#StandardCharacterSyntaxTable'
 *      >
 *      Table 22-1: Standard Character Syntax Types
 *      </a>
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 */
public class cREADTABLE extends cCELL implements tREADTABLE
{
	/**
	 * ReadTable data
	 */
	Map<String, tLIST>	map	= new HashMap<String, tLIST>();

	/**
	 * case for atom : :upcase, :downcase, :preserve and :invert
	 */
	CaseType			caseVal;

	/**
	 * caseType is the definition of case changes for read tables
	 * 
	 * @author Ivan Pierre {ivan@kilroysoft.ch}
	 * @author George Kilroy {george@kilroysoft.ch}
	 * 
	 */
	public enum CaseType
	{
		/**
		 * All in upper case
		 */
		UPCASE,

		/**
		 * All in downcase
		 */
		DOWNCASE,

		/**
		 * Don't change case
		 */
		PRESERVE,

		/**
		 * Invert case
		 */
		INVERT
	}

	/**
	 * 
	 */
	public cREADTABLE()
	{
		// default value
		caseVal = CaseType.DOWNCASE;

		trace("-------->" + sym("%comment-reader").DESCRIBE());

		// standard macro char
		SET_MACRO_CHARACTER('"', sym("sys::%string-reader"), false, this);
		SET_MACRO_CHARACTER('\'', sym("sys::%quote-reader"), false, this);
		SET_MACRO_CHARACTER('(', sym("sys::%parent-reader"), false, this);
		SET_MACRO_CHARACTER(')', sym("sys::%close-parent-reader"), false, this);
		SET_MACRO_CHARACTER(',', sym("sys::%unquote-reader"), false, this);
		SET_MACRO_CHARACTER(';', sym("%comment-reader"), false, this);
		SET_MACRO_CHARACTER('`', sym("sys::%backquote-reader"), false, this);

		// as standard only # as dispatch macro
		MAKE_DISPATCH_MACRO_CHARACTER('#', true, this);

		// reserved
		SET_DISPATCH_MACRO_CHRACTER('#', '!', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '?', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '[', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', ']', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '{', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '}', NIL, this);

		// signal error
		SET_DISPATCH_MACRO_CHRACTER('#', '<', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '\b', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '\t', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '\r', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '\n', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '\f', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', ' ', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', ')', NIL, this);

		// undefined
		SET_DISPATCH_MACRO_CHRACTER('#', '"', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '$', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '%', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '&', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', ';', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '>', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '@', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '^', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '_', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '`', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '~', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '/', NIL, this);

		// macro char
		SET_DISPATCH_MACRO_CHRACTER('#', '#', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '\'', sym("sys::%function-reader"),
				this);
		SET_DISPATCH_MACRO_CHRACTER('#', '(', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '*', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', ',', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', ':', sym("sys::%unintern-reader"),
				this);
		SET_DISPATCH_MACRO_CHRACTER('#', '=', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '\\', sym("sys::%character-reader"),
				this);
		SET_DISPATCH_MACRO_CHRACTER('#', '|', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '+', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '-', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', '.', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', 'A', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', 'B', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', 'C', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', 'O', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', 'P', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', 'R', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', 'S', NIL, this);
		SET_DISPATCH_MACRO_CHRACTER('#', 'X', NIL, this);
	}

	/**
	 * @param character
	 * @param stream
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	@Static(name = "read-delimited-list", doc = "f_rd_del")
	public static tT READ_DELIMITED_LIST(
			@Arg(name = "character") Character character, //
			@BaseArg @Opt(name = "input-stream", def = "*standard-input*") tINPUT_STREAM stream, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws EOFException
	{
		return null;
	}

	@Static(name = "read-preserving-whitespace", doc = "f_rd_rd")
	public static tT READ_PRESERVING_WHITESPACE()
	{
		return null;
	}

	@Static(name = "read-from string", doc = "f_rd_fro")
	public static tT READ_FROM_STRING()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.Cell#copy()
	 */
	@Override
	public tT copy()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.Cell#printable()
	 */
	@Override
	public String toString()
	{
		return "#<cREADTABLE " + this.caseVal + ">";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tREADTABLE#isConstituent(java.lang.Character)
	 */
	public Boolean isConstituent(Character car)
	{
		switch (car)
		{
			case '\n':
			case '\r':
			case '\t':
			case ' ':
				return false;
			default:
				return map.get("" + car) == null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tREADTABLE#MAKE_DISPATCH_MACRO_CHARACTER(java.lang
	 * .Character, java.lang.Boolean, aloyslisp.core.types.tREADTABLE)
	 */
	public tT MAKE_DISPATCH_MACRO_CHARACTER(Character c, Boolean nonTerm,
			tREADTABLE readtable)
	{
		map.put("" + c, cons(null, nonTerm ? T : NIL));
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tREADTABLE#SET_DISPATCH_MACRO_CHRACTER(java.lang
	 * .Character, java.lang.Character,
	 * aloyslisp.core.types.tFUNCTION_DESIGNATOR,
	 * aloyslisp.core.types.tREADTABLE)
	 */
	public tT SET_DISPATCH_MACRO_CHRACTER(Character disp, Character sub,
			tFUNCTION_DESIGNATOR func, tREADTABLE readTable)
	{
		map.put("" + disp + sub, cons(func, NIL));
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tREADTABLE#GET_DISPATCH_MACRO_CHARACTER(java.lang
	 * .Character, java.lang.Character, aloyslisp.core.types.tREADTABLE)
	 */
	public tFUNCTION_DESIGNATOR GET_DISPATCH_MACRO_CHARACTER(Character disp,
			Character sub, tREADTABLE readtable)
	{
		tLIST res = map.get("" + disp + sub);
		if (res == null)
			return NIL;
		return (tFUNCTION_DESIGNATOR) res.CAR();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tREADTABLE#SET_MACRO_CHARACTER(java.lang.Character,
	 * aloyslisp.core.types.tFUNCTION_DESIGNATOR, java.lang.Boolean,
	 * aloyslisp.core.types.tREADTABLE)
	 */
	public tT SET_MACRO_CHARACTER(Character c, tFUNCTION_DESIGNATOR func,
			Boolean nonTerm, tREADTABLE readtable)
	{
		map.put("" + c, cons(func, nonTerm ? T : NIL));
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tREADTABLE#GET_MACRO_CHARACTER(java.lang.Character,
	 * aloyslisp.core.types.tREADTABLE)
	 */
	public tT[] GET_MACRO_CHARACTER(Character c, tREADTABLE readtable)
	{
		tLIST res = map.get("" + c);
		if (res == null)
			return new tT[]
			{ NIL, NIL };

		return new tT[]
		{ res.CAR(), res.CDR() };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tREADTABLE#READTABLE_CASE(aloyslisp.core.types.
	 * tREADTABLE)
	 */
	public tSYMBOL READTABLE_CASE(tREADTABLE tablereader)
	{
		return key(caseVal.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tREADTABLE#SET_READTABLE_CASE(aloyslisp.core.types
	 * .tREADTABLE, aloyslisp.core.types.tSYMBOL)
	 */
	public tSYMBOL SET_READTABLE_CASE(tREADTABLE readtable, tSYMBOL c)
	{
		// caseVal = Enum.valueOf(CaseType, c.getName().toUpperCase());
		String name = c.SYMBOL_NAME();
		if (name.equalsIgnoreCase("downcase"))
		{
			caseVal = CaseType.DOWNCASE;
		}
		else if (name.equalsIgnoreCase("upcase"))
		{
			caseVal = CaseType.UPCASE;
		}
		else if (name.equalsIgnoreCase("invert"))
		{
			caseVal = CaseType.INVERT;
		}
		else if (name.equalsIgnoreCase("preserve"))
		{
			caseVal = CaseType.PRESERVE;
		}
		else
		{
			throw new LispException(
					"Invalid table case ahould be :upcase, :downcase, :preserve or :invert : "
							+ c);
		}
		return c;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tREADTABLE#changeCase(java.lang.Character)
	 */
	public Character changeCase(Character c)
	{
		switch (caseVal)
		{
			case DOWNCASE:
				return Character.toLowerCase(c);

			case UPCASE:
				return Character.toUpperCase(c);

			case INVERT:
				return Character.isLowerCase(c) ? Character.toUpperCase(c)
						: Character.toLowerCase(c);

			case PRESERVE:
				return c;

			default:
				throw new LispException("bad tableReader case : " + caseVal);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tREADTABLE#SET_SYNTAX_FROM_CHAR(java.lang.Character,
	 * java.lang.Character, aloyslisp.core.types.tREADTABLE,
	 * aloyslisp.core.types.tREADTABLE)
	 */
	@Override
	public void SET_SYNTAX_FROM_CHAR(Character fromChar, Character toChar,
			tREADTABLE toReadtable, tREADTABLE fromReadtable)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tREADTABLE#COPY_READTABLE(aloyslisp.core.types.
	 * tREADTABLE, aloyslisp.core.types.tREADTABLE)
	 */
	@Override
	public tREADTABLE COPY_READTABLE(tREADTABLE fromReadtable, tT toReadtable)
	{
		// TODO Auto-generated method stub
		return null;
	}

	// ***************************************************************************
	private static final tSYMBOL	BACKQUOTE	= sym("sys::%backquote");

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "sys::%backquote-reader", doc = "02_df")
	public static tT BACKQUOTE_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		try
		{
			return list(BACKQUOTE, in.READ(in, false, NIL, true));
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
	@Static(name = "sys::%character-reader", doc = "02_dha")
	public static tT CHARACTER_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		try
		{
			// read atom (for example newline) if single char first char is
			// escaped to keep case
			return new cCHARACTER(in.readAtom(false, NIL, true));
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
	@Static(name = "%comment-reader", doc = "02_dd")
	public static tT COMMENT_READER( //
			@Arg(name = "stream") tINPUT_STREAM in, //
			@Arg(name = "char") Character car, //
			@Rest(name = "args") tT... args)
	{
		Character curr;

		try
		{
			// TODO COMMENT_READER should test on eol
			while ((curr = in.READ_CHAR(in, false, NIL, true)) != '\n'
					&& curr != '\r')
				;

			// we should reverse the list
			return in.READ(in, false, NIL, true);
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
	@Static(name = "sys::%close-parent-reader", doc = "02_db")
	public static tT CLOSE_PARENT_READER( //
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
	@Static(name = "sys::%function-reader", doc = "02_dhb")
	public static tT FUNCTION_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		try
		{
			return list(FUNCTION, in.READ(in, false, NIL, true));
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
	@Static(name = "sys::%parent-reader", doc = "02_da")
	public static tT PARENT_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		tLIST res = NIL;
		try
		{
			tT curr = in.READ(in, false, NIL, true);
			while (!(curr instanceof tSYMBOL)
					|| !((tSYMBOL) curr).SYMBOL_NAME().equals(")"))
			{
				// manage dotted lists
				if (curr instanceof tSYMBOL
						&& ((tSYMBOL) curr).SYMBOL_NAME().equals("."))
				{
					curr = in.READ(in, false, NIL, true);
					res = (tLIST) ((tLIST) res.REVERSE()).APPEND(curr);
					curr = in.READ(in, false, NIL, true);

					// dotted pair should end
					if (curr instanceof tSYMBOL
							&& ((tSYMBOL) curr).SYMBOL_NAME().equals(")"))
						return res;

					throw new LispException("Dotted list bad end");
				}

				// else append element of list
				res = cons(curr, res);
				curr = in.READ(in, false, NIL, true);
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
	@Static(name = "sys::%quote-reader", doc = "02_dc")
	public static tT QUOTE_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		try
		{
			return quote(in.READ(in, false, NIL, true));
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
	@Static(name = "sys::%string-reader", doc = "02_de")
	public static tT STRING_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		StringBuilder str = new StringBuilder();
		Character curr;

		try
		{
			while ((curr = in.READ_CHAR(in, false, NIL, true)) != '"')
			{
				if (curr == '\\')
				{
					curr = in.READ_CHAR(in, false, NIL, true);
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
	@Static(name = "sys::%unintern-reader", doc = "02_dhe")
	public static tT UNINTERN_READER( //
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
			return new cSYMBOL(varName, null);
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
	@Static(name = "sys::%unquote-reader", doc = "02_df")
	public static tT UNQUOTE_READER( //
			@Arg(name = "sys::stream") tINPUT_STREAM in, //
			@Arg(name = "sys::char") Character car, //
			@Rest(name = "sys::args") tT... args)
	{
		Character sup = ' ';
		try
		{
			sup = in.PEEK_CHAR(NIL, in, false, NIL, true);
			switch (sup)
			{
				case '@':
					in.READ_CHAR(in, false, NIL, true);
					return list(SPLICE, in.READ(in, false, NIL, true));

				case '.':
					in.READ_CHAR(in, false, NIL, true);
					return list(NSPLICE, in.READ(in, false, NIL, true));

				default:
					return list(UNQUOTE, in.READ(in, false, NIL, true));
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
	@Static(name = "sys::%backquote", doc = "02_df")
	@SpecialOp
	@Mac(prefix = "`")
	public static tT BACKQUOTE( //
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
	public static tT walk(tT cons)
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
	@Static(name = "sys::%nsplice", doc = "02_df")
	@Mac(prefix = ",.")
	public static tT NSPLICE( //
			@Rest(name = "sys::obj") tT... obj)
	{
		throw new LispException(",. without `");
	}

	/***************************************************************************
	 * @param obj
	 * @return
	 */
	@Static(name = "sys::%splice", doc = "02_df")
	@Mac(prefix = ",@")
	public static tT SPLICE( //
			@Rest(name = "sys::obj") tT... obj)
	{
		throw new LispException(",@ without `");
	}

	/***************************************************************************
	 * @param obj
	 * @return
	 */
	@Static(name = "sys::%unquote", doc = "02_df")
	@Mac(prefix = ",")
	public static tT UNQUOTE( //
			@Rest(name = "sys::obj") tT... obj)
	{
		throw new LispException(", without `");
	}

}
