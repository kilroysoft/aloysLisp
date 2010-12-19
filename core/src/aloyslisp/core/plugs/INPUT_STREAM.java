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

package aloyslisp.core.plugs;

import java.io.*;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.annotations.*;
import aloyslisp.core.common.*;
import aloyslisp.core.types.*;

/**
 * INPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class INPUT_STREAM extends STREAM implements tINPUT_STREAM
{

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public tT READ(tINPUT_STREAM stream, Boolean eofErrorP, tT eofValue,
			Boolean recursiveP) throws EOFException
	{
		// loop until something found or EOF
		for (;;)
		{
			// Test macro char
			tT res = stream.readMacroChar(eofErrorP, eofValue, recursiveP);
			if (res != null)
				return res;

			// It's a constituent... so atom
			String atom = stream.readAtom(eofErrorP, eofValue, recursiveP);
			if (atom != null)
			{
				// test if numeric
				res = NUMBER.create(atom);
				if (res != null)
					return res;

				// else it's a symbol
				return sym(atom);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#copy()
	 */
	@Override
	public tT copy()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#printable()
	 */
	@Override
	public String printable()
	{
		return "<#INPUT-STREAM " + this.getClass().getSimpleName() + ">";
	}

	public Character PEEK_CHAR(tT peekType, //
			tINPUT_STREAM stream, //
			Boolean eofErrorP, //
			tT eofValue, //
			Boolean recursiveP) throws EOFException

	{
		Character walk = READ_CHAR(stream, eofErrorP, eofValue, recursiveP);
		boolean space = peekType == T;
		boolean carTest = peekType instanceof tCHARACTER;
		Character test = '\uffff';
		if (carTest)
			test = ((tCHARACTER) peekType).getChar();

		while (walk != null
		// test for space char or for terminal char
				&& ((space && Character.isSpaceChar(walk)) || (carTest && walk != test)))
		{
			walk = READ_CHAR(stream, eofErrorP, eofValue, recursiveP);
		}

		if (walk != null)
			UNREAD_CHAR(walk, stream);

		return walk;
	}

	/**
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public Character READ_CHAR_NO_HANG(tINPUT_STREAM stream, Boolean eofErrorP,
			tT eofValue, Boolean recursiveP) throws EOFException
	{
		if (!LISTEN(stream))
			return null;
		return READ_CHAR(stream, eofErrorP, eofValue, recursiveP);
	}

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public tT readMacroChar(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
			throws EOFException
	{
		Character curr = PEEK_CHAR(NIL, this, eofErrorP, eofValue, recursiveP);
		READTABLE table = (READTABLE) readTable.SYMBOL_VALUE();

		if (!table.isConstituent(curr = PEEK_CHAR(NIL, this, eofErrorP,
				eofValue, recursiveP)))
		{
			curr = READ_CHAR(this, eofErrorP, eofValue, recursiveP);

			// test macrochar
			tT[] charMacro = table.GET_MACRO_CHARACTER(curr, table);
			if (charMacro[0] == NIL)
				return null;

			// test macrochar extension
			if (charMacro[1] != NIL)
			{
				Character curr2 = READ_CHAR(this, eofErrorP, eofValue,
						recursiveP);
				charMacro[0] = table.GET_DISPATCH_MACRO_CHARACTER(curr, curr2,
						table);
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
				trace = true;
				trace("Symbol macrochar = " + function + " "
						+ function.DESCRIBE());
				function = ((tSYMBOL) function).SYMBOL_FUNCTION();
			}
			if (function == null)
			{
				throw new LispException("Function not defined for macrochar "
						+ charMacro);
			}

			// Call macro function
			tT read = ((tFUNCTION) function).e(this, c(curr), NIL)[0];
			return read;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IInputStream#readAtom()
	 */
	public String readAtom(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
			throws EOFException
	{
		return readAtom(false, eofErrorP, eofValue, recursiveP);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IInputStream#readAtom(boolean)
	 */
	public String readAtom(Boolean firstEscaped, Boolean eofErrorP,
			tT eofValue, Boolean recursiveP) throws EOFException
	{
		Character curr = PEEK_CHAR(NIL, this, eofErrorP, eofValue, recursiveP);
		READTABLE table = (READTABLE) readTable.SYMBOL_VALUE();
		StringBuilder res = new StringBuilder("");
		boolean singleEscaped = firstEscaped;
		boolean multiEscaped = false;

		while (table.isConstituent(curr = READ_CHAR(this, eofErrorP, eofValue,
				recursiveP)) || singleEscaped || multiEscaped)
		{
			// switched on | ;-)
			multiEscaped ^= (curr == '|');

			res.append(singleEscaped || multiEscaped ? curr : table
					.changeCase(curr));

			// single escape
			singleEscaped = (curr == '\\') && !singleEscaped && !multiEscaped;
		}
		UNREAD_CHAR(curr, this);

		String atom = res.toString();
		if (atom.equals(""))
			return null;

		return atom;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#coerce(aloyslisp.core.types.tT)
	 */
	@Override
	public tT COERCE(tT type)
	{
		// IMPLEMENT Coerce
		return null;
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

	/**
	 * @return
	 */
	@Static(name = "load")
	public static tT[] LOAD( //
			@Arg(name = "file") tT file, //
			@Opt(name = "verbose", def = "t") Boolean verbose, //
			@Opt(name = "print", def = "t") Boolean print, //
			@Opt(name = "not-exists", def = "nil") Boolean notExists)
	{
		String name;
		tINPUT_STREAM in;

		if (file instanceof tINPUT_STREAM)
		{
			in = (tINPUT_STREAM) file;
			name = in.printable();
		}
		else
		{
			if (file instanceof tSTRING)
				name = ((tSTRING) file).getString();
			else if (file instanceof tSYMBOL)
				name = ((tSYMBOL) file).SYMBOL_NAME();
			else
			{
				throw new LispException(
						"Filename should be a string or an atom");
			}

			try
			{
				in = new FILE_INPUT_STREAM(new FileInputStream(name));
			}
			catch (FileNotFoundException e)
			{
				if (notExists)
				{
					return new tT[]
					{ NIL };
				}
				throw new LispException("Error opening " + name + " "
						+ e.getLocalizedMessage());
			}
		}

		if (verbose)
		{
			System.out.println("; Loading contents of file " + name);
		}

		// while there's something to read
		try
		{
			tT[] res;
			for (;;)
			{
				// read it
				res = new tT[]
				{ in.READ(in, false, NIL, false) };

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
		catch (EOFException e)
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
			System.out.println("; Finished loading " + name);
		}
		return new tT[]
		{ T };
	}

}
