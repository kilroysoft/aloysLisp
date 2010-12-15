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
import aloyslisp.core.common.*;
import aloyslisp.core.types.*;

/**
 * INPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class INPUT_STREAM extends STREAM implements tINPUT_STREAM
{

	public PushbackReader	reader;

	/**
	 * Use standard input
	 * 
	 * @param file
	 *            System.in
	 */
	public INPUT_STREAM(InputStream file)
	{
		reader = new PushbackReader(new InputStreamReader(file));
	}

	/**
	 * Use a file
	 * 
	 * @param file
	 *            new FileReader("filename")
	 */
	public INPUT_STREAM(FileReader file)
	{
		reader = new PushbackReader(file);
	}

	/**
	 * Use a string
	 * 
	 * @param str
	 *            string to be used
	 */
	public INPUT_STREAM(String str)
	{
		reader = new PushbackReader(new StringReader(str));
	}

	/**
	 * @param stream
	 * @return
	 */
	public static Boolean CLOSE(tINPUT_STREAM stream)
	{
		try
		{
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return true;
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
		return "<#INPUT " + this.getClass().getSimpleName() + ">";
	}

	/**
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public static Character READ_CHAR(tINPUT_STREAM stream, Boolean eofErrorP,
			tT eofValue, Boolean recursiveP) throws EOFException
	{
		Character res = null;
		try
		{
			int car = stream.read();
			if (car == -1)
			{
				throw new EOFException("");
			}

			res = new Character((char) car);
			// System.out.println("char : '" + res + "' (" + res.hashCode() +
			// ")");
		}
		catch (EOFException e)
		{
			// retrow EOF
			throw new EOFException("");
		}
		catch (IOException e)
		{
			throw new LispException("Error on reading stream. "
					+ e.getLocalizedMessage());
		}

		return res;
	}

	/**
	 * @param car
	 * @return
	 */
	public static Character UNREAD_CHAR(tINPUT_STREAM stream, Character car)
	{
		try
		{
			stream.unread(car);
		}
		catch (IOException e)
		{
			throw new LispException("Error unbuffering " + car + " "
					+ e.getLocalizedMessage());
		}
		return car;
	}

	public static Character PEEK_CHAR(tINPUT_STREAM stream, tT car,
			Boolean eofErrorP, tT eofValue, Boolean recursiveP)
			throws EOFException
	{
		Character walk = READ_CHAR(stream, eofErrorP, eofValue, recursiveP);
		boolean space = car == T;
		boolean carTest = car instanceof tCHARACTER;
		Character test = '\uffff';
		if (carTest)
			test = ((tCHARACTER) car).getChar();

		while (walk != null
		// test for space char or for terminal char
				&& ((space && Character.isSpaceChar(walk)) || (carTest && walk != test)))
		{
			walk = READ_CHAR(stream, eofErrorP, eofValue, recursiveP);
		}

		if (walk != null)
			UNREAD_CHAR(stream, walk);

		return walk;
	}

	/**
	 * @return
	 */
	public static boolean LISTEN(tINPUT_STREAM stream)
	{
		try
		{
			return stream.ready();
		}
		catch (IOException e)
		{
			throw new LispException("Can't listen to the file. "
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public static Character READ_CHAR_NO_HANG(tINPUT_STREAM stream,
			Boolean eofErrorP, tT eofValue, Boolean recursiveP)
			throws EOFException
	{
		if (!LISTEN(stream))
			return null;
		return READ_CHAR(stream, eofErrorP, eofValue, recursiveP);
	}

	/**
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public static tT READ(tINPUT_STREAM stream, Boolean eofErrorP, tT eofValue,
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
		Character curr = PEEK_CHAR(this, NIL, eofErrorP, eofValue, recursiveP);
		READTABLE table = (READTABLE) readTable.SYMBOL_VALUE();

		if (!table.isConstituent(curr = PEEK_CHAR(this, NIL, eofErrorP,
				eofValue, recursiveP)))
		{
			curr = READ_CHAR(this, eofErrorP, eofValue, recursiveP);

			// test macrochar
			tLIST charMacro = table.getMacroCharacter(curr);
			if (charMacro == null)
				return null;

			// test macrochar extension
			if (charMacro.CDR() == NIL)
			{
				Character curr2 = READ_CHAR(this, eofErrorP, eofValue,
						recursiveP);
				charMacro = table.getDispatchMacroCharacter(curr, curr2);
				if (charMacro == null)
				{
					throw new LispException("Macro character " + curr + curr2
							+ " not defined");
				}

				curr = curr2;
			}

			// verify function
			tFUNCTION function = ((tFUNCTION) charMacro.CAR());
			if (function == null)
			{
				throw new LispException("Function not defined for macrochar "
						+ curr);
			}

			// Call macro function
			tT read = function.e(this, c(curr), NIL)[0];
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
		Character curr = PEEK_CHAR(this, NIL, eofErrorP, eofValue, recursiveP);
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
		UNREAD_CHAR(this, curr);

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

	@Override
	public boolean OPEN_STREAM_P()
	{
		// TODO Auto-generated method stub
		return reader != null;
	}

	@Override
	public int read() throws IOException
	{
		return reader.read();
	}

	@Override
	public boolean ready() throws IOException
	{
		return reader.ready();
	}

	@Override
	public void unread(Character car) throws IOException
	{
		// TODO Auto-generated method stub
		reader.unread(car);
	}

	@Override
	public void close() throws IOException
	{
		// TODO Auto-generated method stub
		reader.close();
	}

}