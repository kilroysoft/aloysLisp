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
// IP 16 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import java.io.*;

import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.sequences.*;

/**
 * cFILE_INPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cFILE_INPUT_STREAM extends cINPUT_STREAM implements
		tFILE_INPUT_STREAM
{
	private tPATHNAME		path	= null;
	private PushbackReader	reader	= null;

	// TODO private tPATHNAME path = null;

	/**
	 * Use standard input
	 * 
	 * @param file
	 *            System.in
	 */
	public cFILE_INPUT_STREAM(InputStream file)
	{
		reader = new PushbackReader(new InputStreamReader(file));
	}

	/**
	 * @param file
	 */
	public cFILE_INPUT_STREAM(tPATHNAME_DESIGNATOR file)
	{
		setPathname(file);
		try
		{
			reader = new PushbackReader(new InputStreamReader(
					new FileInputStream(((cPATHNAME) path).getFile())));
		}
		catch (FileNotFoundException e)
		{
			throw new FILE_ERROR(path);
		}
	}

	/**
	 * Use a file
	 * 
	 * @param file
	 *            new FileReader("filename")
	 */
	public cFILE_INPUT_STREAM(FileReader file)
	{
		reader = new PushbackReader(file);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tSTREAM#OPEN_STREAM_P(aloyslisp.core.types.tSTREAM)
	 */
	public Boolean OPEN_STREAM_P()
	{
		return reader != null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tINPUT_STREAM#READ_CHAR(aloyslisp.core.types.
	 * tINPUT_STREAM, java.lang.Boolean, aloyslisp.core.types.tT,
	 * java.lang.Boolean)
	 */
	public Character READ_CHAR(tINPUT_STREAM stream, Boolean eofErrorP,
			tT eofValue, Boolean recursiveP) throws EOFException
	{
		Character res = null;
		try
		{
			int car = reader.read();
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

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tINPUT_STREAM#UNREAD_CHAR(java.lang.Character,
	 * aloyslisp.core.types.tINPUT_STREAM)
	 */
	public Character UNREAD_CHAR(Character car, tINPUT_STREAM stream)
	{
		try
		{
			reader.unread(car);
		}
		catch (IOException e)
		{
			throw new LispException("Error unbuffering " + car + " "
					+ e.getLocalizedMessage());
		}
		return car;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tINPUT_STREAM#LISTEN(aloyslisp.core.types.tINPUT_STREAM
	 * )
	 */
	public boolean LISTEN(tINPUT_STREAM stream)
	{
		try
		{
			return reader.ready();
		}
		catch (IOException e)
		{
			throw new LispException("Can't listen to the file. "
					+ e.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTREAM#CLOSE(aloyslisp.core.types.tSTREAM,
	 * java.lang.Boolean)
	 */
	public Boolean CLOSE(Boolean abort)
	{
		try
		{
			reader.close();
			reader = null;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tSTREAM#STREAM_ELEMENT_TYPE(aloyslisp.core.types
	 * .tSTREAM)
	 */
	public tT STREAM_ELEMENT_TYPE(tSTREAM stream)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tINPUT_STREAM#CLEAR_INPUT(aloyslisp.core.types.
	 * tINPUT_STREAM)
	 */
	@Override
	public tT CLEAR_INPUT(tINPUT_STREAM stream)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tINPUT_STREAM#READ_BYTE(aloyslisp.core.types.
	 * tINPUT_STREAM, java.lang.Boolean, aloyslisp.core.types.tT,
	 * java.lang.Boolean)
	 */
	@Override
	public Integer READ_BYTE(tINPUT_STREAM stream, Boolean eofErrorP,
			tT eofValue, Boolean recursiveP) throws EOFException
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
	public tT READ_SEQUENCE(tSEQUENCE sequence, tINPUT_STREAM stream,
			Integer start, Integer end)
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
	public tT[] READ_LINE(tINPUT_STREAM stream, Boolean eofErrorP, tT eofValue,
			Boolean recursiveP) throws EOFException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tFILE_STREAM#setPathname(aloyslisp.core.streams
	 * .tPATHNAME_DESIGNATOR)
	 */
	@Override
	public tPATHNAME setPathname(tPATHNAME_DESIGNATOR path)
	{
		this.path = cPATHNAME.PATHNAME(path);
		return this.path;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tFILE_STREAM#getPathname()
	 */
	@Override
	public tPATHNAME getPathname()
	{
		return this.path;
	}

}
