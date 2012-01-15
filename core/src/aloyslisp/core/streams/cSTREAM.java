/**
 * aloysLisp.
 * <p>
 * A LISP interpreter, compiler and library.
 * <p>
 * Copyright (C) 2010-2011 kilroySoft <aloyslisp@kilroysoft.ch>
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
// IP 16 sept. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import static aloyslisp.core.L.*;
import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.designators.*;
import aloyslisp.core.functions.tFUNCTION;
import aloyslisp.core.math.cNUMBER;
import aloyslisp.core.packages.tNULL;
import aloyslisp.core.packages.tSYMBOL;
import aloyslisp.core.sequences.tLIST;
import aloyslisp.core.sequences.tSEQUENCE;
import aloyslisp.core.sequences.tSTRING;

/**
 * cSTREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * @see http://hyper.aloys.li/Body/t_stream.htm
 */
public abstract class cSTREAM extends cCELL implements tSTREAM
{
	protected boolean	lineBegin	= true;

	protected tT		elementType	= null;

	/**
	 * @param stream
	 * @param form
	 * @return
	 */
	@aSpecialOperator
	@aFunction(name = "with-open-stream", doc = "m_w_op_1")
	public static tT WITH_OPEN_STREAM( //
			@aArg(name = "stream") tLIST stream, //
			@aRest(name = "form") tT... form)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#STREAM_ELEMENT_TYPE()
	 */
	public tT STREAM_ELEMENT_TYPE()
	{
		return elementType;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tINPUT_STREAM#READ(java.lang.Boolean,
	 * aloyslisp.core.tT, java.lang.Boolean)
	 */
	public tT READ(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
	{
		// loop until something found or EOF
		for (;;)
		{
			// Test macro char
			tT res = readMacroChar(eofErrorP, eofValue, recursiveP);
			if (res != null)
				return res;

			// It's a constituent... so atom
			String atom = readAtom(eofErrorP, eofValue, recursiveP);
			if (atom != null)
			{
				// test if numeric
				res = cNUMBER.MAKE_NUMBER(atom);
				if (res != null)
					return res;

				// else it's a symbol
				return sym(atom);
			}
		}
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
	public String TO_STRING()
	{
		return "<#STREAM " + this.getClass().getSimpleName() + ">";
	}

	public Character PEEK_CHAR(tT peekType, //
			Boolean eofErrorP, //
			tT eofValue, //
			Boolean recursiveP)

	{
		Character walk = READ_CHAR(eofErrorP, eofValue, recursiveP);
		boolean space = peekType == T;
		boolean carTest = peekType instanceof tCHARACTER;
		Character test = '\uffff';
		if (carTest)
			test = ((tCHARACTER) peekType).getChar();

		while (walk != null
		// test for space char or for terminal char
				&& ((space && Character.isSpaceChar(walk)) || (carTest && walk != test)))
		{
			walk = READ_CHAR(eofErrorP, eofValue, recursiveP);
		}

		if (walk != null)
			UNREAD_CHAR(walk);

		return walk;
	}

	/**
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	public Character READ_CHAR_NO_HANG(Boolean eofErrorP, tT eofValue,
			Boolean recursiveP)
	{
		if (!LISTEN())
			return null;
		return READ_CHAR(eofErrorP, eofValue, recursiveP);
	}

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@aJavaInternal
	public tT readMacroChar(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
			throws END_OF_FILE
	{
		Character curr = PEEK_CHAR(NIL, eofErrorP, eofValue, recursiveP);
		cREADTABLE table = (cREADTABLE) readTable.SYMBOL_VALUE();

		if (!table.IS_CONSTITUENT(curr = PEEK_CHAR(NIL, eofErrorP, eofValue,
				recursiveP)))
		{
			curr = READ_CHAR(eofErrorP, eofValue, recursiveP);

			// test macrochar
			tT[] charMacro = table.GET_MACRO_CHARACTER(curr);
			if (charMacro[0] == NIL)
				return null;

			// test macrochar extension
			if (charMacro[1] != NIL)
			{
				Character curr2 = READ_CHAR(eofErrorP, eofValue, recursiveP);
				charMacro[0] = table.GET_DISPATCH_MACRO_CHARACTER(curr, curr2);
				if (charMacro[0] == NIL)
				{
					throw new LispException("aMacro character " + curr + curr2
							+ " not defined");
				}

				curr = curr2;
			}

			// verify function
			tT function = charMacro[0];
			if (function instanceof tSYMBOL)
			{
				// trace("cDYN_SYMBOL macrochar = " + function + " "
				// + function.DESCRIBE());
				function = ((tSYMBOL) function).SYMBOL_FUNCTION();
			}
			if (function == null)
			{
				throw new LispException("aFunction not defined for macrochar "
						+ charMacro[0]);
			}

			// Call macro function
			tT read = ((tFUNCTION) function).e(this, c(curr), this)[0];
			return read;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.IInputStream#readAtom()
	 */
	@aJavaInternal
	public String readAtom(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
			throws END_OF_FILE
	{
		return readAtom(false, eofErrorP, eofValue, recursiveP);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.IInputStream#readAtom(boolean)
	 */
	@aJavaInternal
	public String readAtom(Boolean firstEscaped, Boolean eofErrorP,
			tT eofValue, Boolean recursiveP)
	{
		Character curr = PEEK_CHAR(NIL, eofErrorP, eofValue, recursiveP);
		cREADTABLE table = (cREADTABLE) readTable.SYMBOL_VALUE();
		StringBuilder res = new StringBuilder("");
		boolean singleEscaped = firstEscaped;
		boolean multiEscaped = false;

		try
		{
			while (table.IS_CONSTITUENT(curr = READ_CHAR(eofErrorP, eofValue,
					recursiveP)) || singleEscaped || multiEscaped)
			{
				// switched on | ;-)
				multiEscaped ^= (curr == '|');

				res.append(singleEscaped || multiEscaped ? curr : table
						.CHANGE_CASE(curr));

				// single escape
				singleEscaped = (curr == '\\') && !singleEscaped
						&& !multiEscaped;
			}
			UNREAD_CHAR(curr);
		}
		catch (END_OF_FILE e)
		{

		}
		catch (Exception e)
		{
			throw new LispException("Error in read : " + res.toString());
		}

		String atom = res.toString();
		if (atom.equals(""))
			return null;

		return atom;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tINPUT_STREAM#READ_SEQUENCE(aloyslisp.core.types
	 * .tSEQUENCE, aloyslisp.core.types.tINPUT_STREAM, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public tT READ_SEQUENCE(tSEQUENCE sequence, Integer start, Integer end)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tINPUT_STREAM#READ_LINE(aloyslisp.core.types.
	 * tINPUT_STREAM, java.lang.Boolean, aloyslisp.core.types.tT,
	 * java.lang.Boolean)
	 */
	@Override
	public tT[] READ_LINE(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	@aFunction(name = "load", doc = "f_load")
	public static tT[] LOAD( //
			@aArg(name = "file") tPATHNAME_DESIGNATOR file, //
			@aOpt(name = "verbose", def = "nil") Boolean verbose, //
			@aOpt(name = "print", def = "nil") Boolean print, //
			@aOpt(name = "not-exists", def = "nil") Boolean notExists)
	{
		tSTREAM in;

		if (file instanceof tSTREAM)
		{
			in = (tSTREAM) file;
		}
		else
		{
			try
			{
				in = new cFILE_STREAM(true, file);
			}
			catch (FILE_ERROR e)
			{
				if (notExists)
				{
					return new tT[]
					{ NIL };
				}
				throw new LispException("Error opening " + file + " "
						+ e.getLocalizedMessage());
			}
		}

		if (verbose)
		{
			((tSTREAM) L.standardOutput.SYMBOL_VALUE())
					.PRINT(str("; Loading contents of file " + file));
		}

		// while there's something to read
		try
		{
			tT[] res;
			for (;;)
			{
				// read it
				res = new tT[]
				{ in.READ(false, NIL, false) };

				if (verbose)
				{
					((tSTREAM) L.standardOutput.SYMBOL_VALUE())
							.PRINT(str("; lisp>" + res[0]));
				}

				// and evaluate it
				// System.out.println("eval : " + res[0]);
				res = res[0].EVAL();

				if (print)
					for (tT cell : res)
					{
						((tSTREAM) L.standardOutput.SYMBOL_VALUE())
								.PRINT(str("; " + cell));
					}
			}
		}
		catch (END_OF_FILE e)
		{
			// End of file
		}
		catch (LispException e)
		{
			// Lisp error transfer
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new LispException("Error on load : "
					+ e.getLocalizedMessage());
		}

		if (verbose)
		{
			((tSTREAM) L.standardOutput.SYMBOL_VALUE())
					.PRINT(str("; Finished loading " + file));
		}
		return new tT[]
		{ T };
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.IOutputStream#writeString(java.lang.String)
	 */
	@Override
	public tT WRITE_STRING(tT str)
	{
		// TODO manage start and end in WRITE_STRING
		for (tT car : (tSTRING) str)
		{
			// System.out.println("(WRITE_CHAR " + ((tCHARACTER)
			// car).getChar());
			WRITE_CHAR(((tCHARACTER) car).getChar());
		}
		return str;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.IOutputStream#terpri()
	 */
	public tNULL TERPRI()
	{
		WRITE_CHAR('\n');
		lineBegin = true;
		return NIL;

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.IOutputStream#freshLine()
	 */
	@Override
	public tT FRESH_LINE()
	{
		if (!lineBegin)
			return TERPRI();
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#WRITE(aloyslisp.core.types.tT)
	 */
	public tT WRITE(tT obj)
	{
		// TODO manage &key arguments and generate Specials vars for printing
		// System.out.println("(WRITE " + str(obj.TO_STRING()));
		return WRITE_STRING(str(obj.TO_STRING()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#PRIN1(aloyslisp.core.types.tT,
	 * aloyslisp.core.types.tOUTPUT_STREAM)
	 */
	@Override
	public tT PRIN1(tT obj)
	{
		tT res = null;
		tT savEscape = printEscape.SYMBOL_VALUE();
		printEscape.SET_SYMBOL_VALUE(T);

		try
		{
			res = WRITE(obj);
		}
		finally
		{
			printEscape.SET_SYMBOL_VALUE(savEscape);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#PRINC(aloyslisp.core.types.tT,
	 * aloyslisp.core.types.tOUTPUT_STREAM)
	 */
	@Override
	public tT PRINC(tT obj)
	{
		tT res = null;
		tT savEscape = printEscape.SYMBOL_VALUE();
		printEscape.SET_SYMBOL_VALUE(NIL);
		tT savReadably = printReadably.SYMBOL_VALUE();
		printReadably.SET_SYMBOL_VALUE(NIL);

		try
		{
			res = WRITE(obj);
		}
		finally
		{
			printReadably.SET_SYMBOL_VALUE(savReadably);
			printEscape.SET_SYMBOL_VALUE(savEscape);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#PRINT(aloyslisp.core.types.tT,
	 * aloyslisp.core.types.tOUTPUT_STREAM)
	 */
	@Override
	public tT PRINT(tT obj)
	{
		FRESH_LINE();
		tT res = PRINC(obj);
		WRITE(c(' '));
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#OPEN_STREAM_P()
	 */
	@Override
	public abstract Boolean OPEN_STREAM_P();

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#CLOSE()
	 */
	@Override
	public abstract Boolean CLOSE();

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#READ_CHAR(java.lang.Boolean,
	 * aloyslisp.core.tT, java.lang.Boolean)
	 */
	@Override
	public abstract Character READ_CHAR(Boolean eofErrorP, tT eofValue,
			Boolean recursiveP) throws END_OF_FILE;

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#UNREAD_CHAR(java.lang.Character)
	 */
	@Override
	public abstract Character UNREAD_CHAR(Character character);

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#LISTEN()
	 */
	@Override
	public abstract boolean LISTEN();

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#CLEAR_INPUT()
	 */
	@Override
	public abstract tT CLEAR_INPUT();

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#READ_BYTE(java.lang.Boolean,
	 * aloyslisp.core.tT, java.lang.Boolean)
	 */
	@Override
	public abstract Integer READ_BYTE(Boolean eofErrorP, tT eofValue,
			Boolean recursiveP) throws END_OF_FILE;

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#WRITE_CHAR(java.lang.Character)
	 */
	@Override
	public abstract Character WRITE_CHAR(Character character);

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#WRITE_BYTE(java.lang.Integer)
	 */
	@Override
	public abstract Integer WRITE_BYTE(Integer val);

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#FINISH_OUTPUT()
	 */
	@Override
	public abstract tT FINISH_OUTPUT();

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#FORCE_OUTPUT()
	 */
	@Override
	public abstract tT FORCE_OUTPUT();

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#CLEAR_OUTPUT()
	 */
	@Override
	public abstract tT CLEAR_OUTPUT();

}
