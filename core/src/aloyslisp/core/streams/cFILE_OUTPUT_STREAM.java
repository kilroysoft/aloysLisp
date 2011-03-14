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
// IP 18 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import java.io.*;

import aloyslisp.core.tT;
import aloyslisp.core.conditions.LispException;
import static aloyslisp.core.L.*;

/**
 * cFILE_OUTPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cFILE_OUTPUT_STREAM extends cOUTPUT_STREAM implements
		tFILE_OUTPUT_STREAM
{

	private tPATHNAME		path	= null;
	protected PrintStream	writer;

	/**
	 * For file output
	 * 
	 * @param file
	 *            new FileWriter("datafile")
	 * @throws FileNotFoundException
	 */
	public cFILE_OUTPUT_STREAM(File file) throws FileNotFoundException
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
	public cFILE_OUTPUT_STREAM(tPATHNAME_DESIGNATOR file)
	{
		setPathname(file);
		try
		{
			writer = new PrintStream(new File(((cPATHNAME) path).getFile()));
		}
		catch (FileNotFoundException e)
		{
			throw new LispException("File not found " + e.getLocalizedMessage());
		}
	}

	/**
	 * For standard output file
	 * 
	 * @param file
	 *            System.out
	 */
	public cFILE_OUTPUT_STREAM(PrintStream file)
	{
		writer = file;
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
	 * @see aloyslisp.core.streams.IStream#isOpen()
	 */
	@Override
	public Boolean OPEN_STREAM_P()
	{
		return writer != null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTREAM#CLOSE(aloyslisp.core.types.tSTREAM,
	 * java.lang.Boolean)
	 */
	public Boolean CLOSE()
	{
		writer.close();
		writer = null;
		return true;
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

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return path.hashCode();
	}

}
