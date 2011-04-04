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
// IP 19 mars 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import static aloyslisp.core.L.*;

import java.io.*;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.designators.tPATHNAME_DESIGNATOR;
import aloyslisp.core.packages.*;

/**
 * cFILE_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cFILE_STREAM extends cSTREAM implements tFILE_STREAM
{

	private tPATHNAME		path	= null;
	private PushbackReader	reader	= null;
	protected PrintStream	writer	= null;

	/**
	 * For file output
	 * 
	 * @param file
	 *            new FileWriter("datafile")
	 * @throws FileNotFoundException
	 */
	public cFILE_STREAM(File file) throws FileNotFoundException
	{
		try
		{
			setPathname(str(file.getCanonicalPath()));
		}
		catch (IOException e)
		{
			throw new LispException("I/O error : " + e.getLocalizedMessage());
		}

		writer = new PrintStream(file);
	}

	/**
	 * @param file
	 */
	public cFILE_STREAM(Boolean input, tPATHNAME_DESIGNATOR file)
	{
		setPathname(file);

		if (input)
		{
			try
			{
				reader = new PushbackReader(new InputStreamReader(
						new FileInputStream(((cPATHNAME) path).NAMESTRING())));
			}
			catch (FileNotFoundException e)
			{
				throw new FILE_ERROR(path);
			}
		}
		else
		{
			try
			{
				writer = new PrintStream(new File(
						((cPATHNAME) path).NAMESTRING()));
			}
			catch (FileNotFoundException e)
			{
				throw new LispException("File not found "
						+ e.getLocalizedMessage());
			}
		}
	}

	/**
	 * For standard output file
	 * 
	 * @param file
	 *            System.out
	 */
	public cFILE_STREAM(PrintStream file)
	{
		writer = file;
	}

	/**
	 * Use standard input
	 * 
	 * @param file
	 *            System.in
	 */
	public cFILE_STREAM(InputStream file)
	{
		reader = new PushbackReader(new InputStreamReader(file));
	}

	/**
	 * Use a file
	 * 
	 * @param file
	 *            new FileReader("filename")
	 */
	public cFILE_STREAM(FileReader file)
	{
		reader = new PushbackReader(file);
	}

	public static final tSYMBOL	INPUT				= key("input");
	public static final tSYMBOL	OUTPUT				= key("output");
	public static final tSYMBOL	IO					= key("io");
	public static final tSYMBOL	PROBE				= key("probe");
	public static final tSYMBOL	ERROR				= key("error");
	public static final tSYMBOL	NEW_VERSION			= key("new-version");
	public static final tSYMBOL	RENAME				= key("rename");
	public static final tSYMBOL	RENAME_AND_DELETE	= key("rename-and-delete");
	public static final tSYMBOL	OVERWRITE			= key("overwrite");
	public static final tSYMBOL	APPEND				= key("append");
	public static final tSYMBOL	SUPERSEDE			= key("supersede");
	public static final tSYMBOL	CREATE				= key("create");
	public static final tSYMBOL	DEFAULT				= key("default");

	/**
	 * @param fileSpec
	 * @return
	 */
	@aFunction(name = "open", doc = "f_open")
	@aKey(keys = "((direction :input) (element-type 'character) if-exists if-does-not-exist (external-format :default))")
	public static tT OPEN( //
			@aArg(name = "filespec") tPATHNAME_DESIGNATOR fileSpec //
	)
	{
		// L.e.ENV_DUMP();

		tSYMBOL direction = (tSYMBOL) arg("direction", tSYMBOL.class, INPUT);
		tT elementType = (tSYMBOL) arg("element-type", tT.class,
				sym("character"));
		tSYMBOL ifExists = (tSYMBOL) arg("if-exists", tSYMBOL.class, NIL);
		tSYMBOL ifNotExists = (tSYMBOL) arg("if-does-not-exist", NIL);
		tSYMBOL externalFormat = (tSYMBOL) arg("external-format",
				tSYMBOL.class, DEFAULT);
		tPATHNAME path = (tPATHNAME) fileSpec;

		File file = path.getFile();
		if (file.isFile())
		{

		}
		else
		{

		}

		boolean dir = direction == INPUT || direction == PROBE;
		cFILE_STREAM res = new cFILE_STREAM(dir, path);

		if (direction == PROBE)
			res.CLOSE();

		return res;
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
	public Character READ_CHAR(Boolean eofErrorP, tT eofValue,
			Boolean recursiveP)
	{
		Character res = null;
		try
		{
			int car = reader.read();
			if (car == -1)
			{
				throw new END_OF_FILE(this);
			}

			res = new Character((char) car);
			// System.out.println("char : '" + res + "' (" + res.hashCode() +
			// ")");
		}
		catch (END_OF_FILE e)
		{
			// retrow EOF
			throw new END_OF_FILE(this);
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
	public Character UNREAD_CHAR(Character car)
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
	public boolean LISTEN()
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
	public Boolean CLOSE()
	{
		try
		{
			if (reader != null)
			{
				reader.close();
				reader = null;
			}
			if (writer != null)
			{
				writer.close();
				writer = null;
			}
		}
		catch (IOException e)
		{
			throw new LispException("Error on close " + path + " : "
					+ e.getLocalizedMessage());
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tSTREAM#STREAM_ELEMENT_TYPE(aloyslisp.core.types
	 * .tSTREAM)
	 */
	public tT STREAM_ELEMENT_TYPE()
	{
		return L.sym("character");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tINPUT_STREAM#CLEAR_INPUT(aloyslisp.core.types.
	 * tINPUT_STREAM)
	 */
	@Override
	public tT CLEAR_INPUT()
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
	public Integer READ_BYTE(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
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
	protected tPATHNAME setPathname(tPATHNAME_DESIGNATOR path)
	{
		this.path = cPATHNAME.PATHNAME(path);
		return this.path;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tFILE_STREAM#getPathname()
	 */
	public tPATHNAME GET_PATHNAME()
	{
		return this.path;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#WRITE_CHAR(java.lang.Character)
	 */
	public Character WRITE_CHAR(Character car)
	{
		lineBegin = (car == '\n' || car == '\r');
		writer.print(car);
		return car;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.IOutputStream#forceOutput()
	 */
	@Override
	public tT FORCE_OUTPUT()
	{
		// TODO verify return
		writer.flush();
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.IOutputStream#clearOutput()
	 */
	@Override
	public tT CLEAR_OUTPUT()
	{
		// TODO verify output
		writer.flush();
		lineBegin = true;
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.IOutputStream#finishOutput()
	 */
	@Override
	public tT FINISH_OUTPUT()
	{
		// TODO verify return
		writer.flush();
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#WRITE_BYTE(java.lang.Integer,
	 * aloyslisp.core.types.tOUTPUT_STREAM)
	 */
	@Override
	public Integer WRITE_BYTE(Integer val)
	{
		writer.write(val);
		return val;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public Integer SXHASH()
	{
		return path.SXHASH();
	}

}
