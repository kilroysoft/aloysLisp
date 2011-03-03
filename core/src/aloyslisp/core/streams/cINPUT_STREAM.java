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
// IP 5 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import static aloyslisp.internal.engine.L.*;
import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.math.*;
import aloyslisp.core.packages.tSYMBOL;
import aloyslisp.core.sequences.tSEQUENCE;

/**
 * cINPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class cINPUT_STREAM extends cSTREAM implements tINPUT_STREAM
{

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
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
				res = cNUMBER.create(atom);
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
		return "<#INPUT-cSTREAM " + this.getClass().getSimpleName() + ">";
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
	public tT readMacroChar(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
			throws END_OF_FILE
	{
		Character curr = PEEK_CHAR(NIL, eofErrorP, eofValue, recursiveP);
		cREADTABLE table = (cREADTABLE) readTable.SYMBOL_VALUE();

		if (!table.isConstituent(curr = PEEK_CHAR(NIL, eofErrorP, eofValue,
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
					throw new LispException("Macro character " + curr + curr2
							+ " not defined");
				}

				curr = curr2;
			}

			// verify function
			tT function = charMacro[0];
			if (function instanceof tSYMBOL)
			{
				trace("cDYN_SYMBOL macrochar = " + function + " "
						+ function.DESCRIBE());
				function = ((tSYMBOL) function).SYMBOL_FUNCTION();
			}
			if (function == null)
			{
				throw new LispException("Function not defined for macrochar "
						+ charMacro[0]);
			}

			// Call macro function
			tT read = ((tFUNCTION) function).e(this, c(curr), NIL)[0];
			return read;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.IInputStream#readAtom()
	 */
	public String readAtom(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
			throws END_OF_FILE
	{
		return readAtom(false, eofErrorP, eofValue, recursiveP);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.IInputStream#readAtom(boolean)
	 */
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
			while (table.isConstituent(curr = READ_CHAR(eofErrorP, eofValue,
					recursiveP)) || singleEscaped || multiEscaped)
			{
				// switched on | ;-)
				multiEscaped ^= (curr == '|');

				res.append(singleEscaped || multiEscaped ? curr : table
						.changeCase(curr));

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
	 * @see aloyslisp.core.types.tSTREAM#STREAM_ELEMENT_TYPE()
	 */
	public tT STREAM_ELEMENT_TYPE()
	{
		// TODO Auto-generated method stub
		return null;
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
	@Static(name = "load", doc = "f_load")
	public static tT[] LOAD( //
			@Arg(name = "file") tPATHNAME_DESIGNATOR file, //
			@Opt(name = "verbose", def = "t") Boolean verbose, //
			@Opt(name = "print", def = "t") Boolean print, //
			@Opt(name = "not-exists", def = "nil") Boolean notExists)
	{
		tINPUT_STREAM in;

		if (file instanceof tINPUT_STREAM)
		{
			in = (tINPUT_STREAM) file;
		}
		else
		{
			try
			{
				in = new cFILE_INPUT_STREAM(file);
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
			System.out.println("; Loading contents of file " + file);
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
					System.out.println("; lisp>" + res[0]);
				}

				// and evaluate it
				// System.out.println("eval : " + res[0]);
				res = res[0].EVAL();

				if (print)
					for (tT cell : res)
					{
						System.out.println("; " + cell);
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
			throw new LispException(e.getLocalizedMessage());
		}

		if (verbose)
		{
			System.out.println("; Finished loading " + file);
		}
		return new tT[]
		{ T };
	}

}
