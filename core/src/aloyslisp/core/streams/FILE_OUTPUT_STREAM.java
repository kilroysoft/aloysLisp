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

import static aloyslisp.commonlisp.L.T;

import java.io.*;

import aloyslisp.core.plugs.tT;

/**
 * FILE_OUTPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class FILE_OUTPUT_STREAM extends OUTPUT_STREAM implements
		tFILE_OUTPUT_STREAM
{

	protected PrintStream	writer;

	/**
	 * For file output
	 * 
	 * @param file
	 *            new FileWriter("datafile")
	 * @throws FileNotFoundException
	 */
	public FILE_OUTPUT_STREAM(File file) throws FileNotFoundException
	{
		writer = new PrintStream(file);
	}

	/**
	 * For standard output file
	 * 
	 * @param file
	 *            System.out
	 */
	public FILE_OUTPUT_STREAM(PrintStream file)
	{
		writer = file;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#WRITE_CHAR(java.lang.Character)
	 */
	public Character WRITE_CHAR(Character car, tOUTPUT_STREAM stream)
	{
		lineBegin = (car == '\n' || car == '\r');
		writer.print(car);
		return car;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IOutputStream#forceOutput()
	 */
	@Override
	public tT FORCE_OUTPUT(tOUTPUT_STREAM stream)
	{
		// TODO verify return
		writer.flush();
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IOutputStream#clearOutput()
	 */
	@Override
	public tT CLEAR_OUTPUT(tOUTPUT_STREAM stream)
	{
		// TODO verify output
		writer.flush();
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IOutputStream#finishOutput()
	 */
	@Override
	public tT FINISH_OUTPUT(tOUTPUT_STREAM stream)
	{
		// TODO verify return
		writer.flush();
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IStream#isOpen()
	 */
	@Override
	public Boolean OPEN_STREAM_P(tSTREAM stream)
	{
		return writer != null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTREAM#CLOSE(aloyslisp.core.types.tSTREAM,
	 * java.lang.Boolean)
	 */
	public Boolean CLOSE(tSTREAM stream, Boolean abort)
	{
		writer.close();
		writer = null;
		return true;
	}

	/* (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#WRITE_BYTE(java.lang.Integer, aloyslisp.core.types.tOUTPUT_STREAM)
	 */
	@Override
	public Integer WRITE_BYTE(Integer val, tOUTPUT_STREAM stream)
	{
		writer.write(val);
		return val;
	}

}
