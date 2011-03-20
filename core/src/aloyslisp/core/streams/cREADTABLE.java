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

import java.util.*;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.engine.Library;
import static aloyslisp.core.L.*;

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
		SET_MACRO_CHARACTER('"', sym("%string-reader"), false);
		SET_MACRO_CHARACTER('\'', sym("%quote-reader"), false);
		SET_MACRO_CHARACTER('(', sym("%parent-reader"), false);
		SET_MACRO_CHARACTER(')', sym("%close-parent-reader"), false);
		SET_MACRO_CHARACTER(',', sym("%unquote-reader"), false);
		SET_MACRO_CHARACTER(';', sym("%comment-reader"), false);
		SET_MACRO_CHARACTER('`', sym("%backquote-reader"), false);

		// as standard only # as dispatch macro
		MAKE_DISPATCH_MACRO_CHARACTER('#', true);

		// reserved
		SET_DISPATCH_MACRO_CHRACTER('#', '!', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '?', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '[', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', ']', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '{', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '}', NIL);

		// signal error
		SET_DISPATCH_MACRO_CHRACTER('#', '<', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '\b', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '\t', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '\r', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '\n', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '\f', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', ' ', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', ')', NIL);

		// undefined
		SET_DISPATCH_MACRO_CHRACTER('#', '"', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '$', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '%', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '&', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', ';', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '>', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '@', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '^', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '_', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '`', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '~', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '/', NIL);

		// macro char
		SET_DISPATCH_MACRO_CHRACTER('#', '#', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '\'', sym("%function-reader"));
		SET_DISPATCH_MACRO_CHRACTER('#', '(', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '*', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', ',', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', ':', sym("%unintern-reader"));
		SET_DISPATCH_MACRO_CHRACTER('#', '=', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '\\', sym("%character-reader"));
		SET_DISPATCH_MACRO_CHRACTER('#', '|', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '+', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '-', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', '.', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', 'A', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', 'B', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', 'C', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', 'O', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', 'P', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', 'R', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', 'S', NIL);
		SET_DISPATCH_MACRO_CHRACTER('#', 'X', NIL);
	}

	/**
	 * @return
	 */
	public tREADTABLE init()
	{
		// validate standard readtable functions
		Library.INSTANTIATE(this.getClass().getCanonicalName());
		return this;
	}

	/**
	 * @param character
	 * @param stream
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@Static(name = "read-delimited-list", doc = "f_rd_del")
	public static tT READ_DELIMITED_LIST(
			@Arg(name = "character") Character character, //
			@Opt(name = "input-stream", def = "*standard-input*") tSTREAM stream, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE
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
	public tT COPY_CELL()
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
	public tT MAKE_DISPATCH_MACRO_CHARACTER(Character c, Boolean nonTerm)
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
			tFUNCTION_DESIGNATOR func)
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
			Character sub)
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
			Boolean nonTerm)
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
	public tT[] GET_MACRO_CHARACTER(Character c)
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
	public tSYMBOL READTABLE_CASE()
	{
		return key(caseVal.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tREADTABLE#SET_READTABLE_CASE(aloyslisp.core.types
	 * .tREADTABLE, aloyslisp.core.types.tSYMBOL)
	 */
	public tSYMBOL SET_READTABLE_CASE(tSYMBOL c)
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
			tREADTABLE toReadtable)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tREADTABLE#COPY_READTABLE(aloyslisp.core.types.
	 * tREADTABLE, aloyslisp.core.types.tREADTABLE)
	 */
	@Override
	public tREADTABLE COPY_READTABLE(tT toReadtable)
	{
		// TODO Auto-generated method stub
		return null;
	}

	// ***************************************************************************
	private static final tSYMBOL	BACKQUOTE	= sym("%backquote");

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "%backquote-reader", doc = "02_df")
	public static tT BACKQUOTE_READER( //
			@Arg(name = "stream") tSTREAM in, //
			@Arg(name = "char") Character car, //
			@Rest(name = "args") tT... args)
	{
		try
		{
			return list(BACKQUOTE, in.READ(false, NIL, true));
		}
		catch (END_OF_FILE e)
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
	@Static(name = "%character-reader", doc = "02_dha")
	public static tT CHARACTER_READER( //
			@Arg(name = "stream") tSTREAM in, //
			@Arg(name = "char") Character car, //
			@Rest(name = "args") tT... args)
	{
		try
		{
			// read atom (for example newline) if single char first char is
			// escaped to keep case
			return new cCHARACTER(in.readAtom(false, NIL, true));
		}
		catch (END_OF_FILE e)
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
			@Arg(name = "stream") tSTREAM in, //
			@Arg(name = "char") Character car, //
			@Rest(name = "args") tT... args)
	{
		Character curr;

		try
		{
			// TODO COMMENT_READER should test on eol
			while ((curr = in.READ_CHAR(false, NIL, true)) != '\n'
					&& curr != '\r')
				;

			// we should reverse the list
			return in.READ(false, NIL, true);
		}
		catch (END_OF_FILE e)
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
	@Static(name = "%close-parent-reader", doc = "02_db")
	public static tT CLOSE_PARENT_READER( //
			@Arg(name = "stream") tSTREAM in, //
			@Arg(name = "char") Character car, //
			@Rest(name = "args") tT... args)
	{
		return sym(")");
	}

	// ***************************************************************************
	private static final tSYMBOL	FUNCTION	= sym("function");

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "%function-reader", doc = "02_dhb")
	public static tT FUNCTION_READER( //
			@Arg(name = "stream") tSTREAM in, //
			@Arg(name = "char") Character car, //
			@Rest(name = "args") tT... args)
	{
		try
		{
			return list(FUNCTION, in.READ(false, NIL, true));
		}
		catch (END_OF_FILE e)
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
	@Static(name = "%parent-reader", doc = "02_da")
	public static tT PARENT_READER( //
			@Arg(name = "stream") tSTREAM in, //
			@Arg(name = "char") Character car, //
			@Rest(name = "args") tT... args)
	{
		tLIST res = NIL;
		try
		{
			tT curr = in.READ(false, NIL, true);
			while (!(curr instanceof tSYMBOL)
					|| !((tSYMBOL) curr).SYMBOL_NAME().equals(")"))
			{
				// manage dotted lists
				if (curr instanceof tSYMBOL
						&& ((tSYMBOL) curr).SYMBOL_NAME().equals("."))
				{
					curr = in.READ(false, NIL, true);
					res = (tLIST) ((tLIST) res.REVERSE()).APPEND(curr);
					curr = in.READ(false, NIL, true);

					// dotted pair should end
					if (curr instanceof tSYMBOL
							&& ((tSYMBOL) curr).SYMBOL_NAME().equals(")"))
						return res;

					throw new LispException("Dotted list bad end");
				}

				// else append element of list
				res = cons(curr, res);
				curr = in.READ(false, NIL, true);
			}

			// we should reverse the list
			return res.REVERSE();
		}
		catch (END_OF_FILE e)
		{
			throw new LispException("Error in reading a list : " + res);
		}
	}

	// ***************************************************************************
	private static final tSYMBOL	QUOTE	= sym("quote");

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "%quote-reader", doc = "02_dc")
	public static tT QUOTE_READER( //
			@Arg(name = "stream") tSTREAM in, //
			@Arg(name = "char") Character car, //
			@Rest(name = "args") tT... args)
	{
		try
		{
			return quote(in.READ(false, NIL, true));
		}
		catch (END_OF_FILE e)
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
	@Static(name = "%string-reader", doc = "02_de")
	public static tT STRING_READER( //
			@Arg(name = "stream") tSTREAM in, //
			@Arg(name = "char") Character car, //
			@Rest(name = "args") tT... args)
	{
		StringBuilder str = new StringBuilder();
		Character curr;

		try
		{
			while ((curr = in.READ_CHAR(false, NIL, true)) != '"')
			{
				if (curr == '\\')
				{
					curr = in.READ_CHAR(false, NIL, true);
				}

				str.append(curr);
			}

			// we should return as LISP String
			return str(str.toString());
		}
		catch (END_OF_FILE e)
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
	@Static(name = "%unintern-reader", doc = "02_dhe")
	public static tT UNINTERN_READER( //
			@Arg(name = "stream") tSTREAM in, //
			@Arg(name = "char") Character car, //
			@Rest(name = "args") tT... args)
	{
		try
		{
			String varName = in.readAtom(false, NIL, true);
			if (varName.equals(""))
				throw new LispException(
						"#: should be followed by an symbol name");
			return new cSYMBOL(varName, null);
		}
		catch (END_OF_FILE e)
		{
			throw new LispException("Error in reading an uninterned variable");
		}
	}

	// ***************************************************************************
	private static final tSYMBOL	SPLICE	= sym("%splice");
	private static final tSYMBOL	NSPLICE	= sym("%nsplice");
	private static final tSYMBOL	UNQUOTE	= sym("%unquote");

	/***************************************************************************
	 * @param in
	 * @param car
	 * @param args
	 * @return
	 */
	@Static(name = "%unquote-reader", doc = "02_df")
	public static tT UNQUOTE_READER( //
			@Arg(name = "stream") tSTREAM in, //
			@Arg(name = "char") Character car, //
			@Rest(name = "args") tT... args)
	{
		Character sup = ' ';
		try
		{
			sup = in.PEEK_CHAR(NIL, false, NIL, true);
			switch (sup)
			{
				case '@':
					in.READ_CHAR(false, NIL, true);
					return list(SPLICE, in.READ(false, NIL, true));

				case '.':
					in.READ_CHAR(false, NIL, true);
					return list(NSPLICE, in.READ(false, NIL, true));

				default:
					return list(UNQUOTE, in.READ(false, NIL, true));
			}
		}
		catch (END_OF_FILE e)
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
	@Static(name = "%backquote", doc = "02_df")
	@SpecialOp
	@Mac(prefix = "`")
	public static tT BACKQUOTE( //
			@Arg(name = "obj") tT obj)
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
			return list(sym("list")).APPEND(cons);
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
	@Static(name = "%nsplice", doc = "02_df")
	@Mac(prefix = ",.")
	public static tT NSPLICE( //
			@Rest(name = "obj") tT... obj)
	{
		throw new LispException(",. without `");
	}

	/***************************************************************************
	 * @param obj
	 * @return
	 */
	@Static(name = "%splice", doc = "02_df")
	@Mac(prefix = ",@")
	public static tT SPLICE( //
			@Rest(name = "obj") tT... obj)
	{
		throw new LispException(",@ without `");
	}

	/***************************************************************************
	 * @param obj
	 * @return
	 */
	@Static(name = "%unquote", doc = "02_df")
	@Mac(prefix = ",")
	public static tT UNQUOTE( //
			@Rest(name = "obj") tT... obj)
	{
		throw new LispException(", without `");
	}

}
