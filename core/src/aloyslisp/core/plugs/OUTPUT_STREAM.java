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
import aloyslisp.core.types.*;

/**
 * OUTPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class OUTPUT_STREAM extends STREAM implements tOUTPUT_STREAM
{
	protected boolean		lineBegin	= true;
	protected PrintStream	writer;

	/**
	 * For file output
	 * 
	 * @param file
	 *            new FileWriter("datafile")
	 * @throws FileNotFoundException
	 */
	public OUTPUT_STREAM(File file) throws FileNotFoundException
	{
		writer = new PrintStream(file);
	}

	/**
	 * For standard output file
	 * 
	 * @param file
	 *            System.out
	 */
	public OUTPUT_STREAM(PrintStream file)
	{
		writer = file;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#WRITE(aloyslisp.core.types.tT)
	 */
	@Function(name = "sys::write")
	public tT WRITE( //
			@Arg(name = "obj") tT obj)
	{
		return WRITE_STRING(str(obj.printable()), NIL, NIL);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.streams.IOutputStream#writeChar(java.lang.Character)
	 */
	@Override
	public Character WRITE_CHAR(Character car)
	{
		lineBegin = (car == '\n' || car == '\r');
		putc(car);
		return car;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.streams.IOutputStream#writeString(java.lang.String)
	 */
	@Override
	public tT WRITE_STRING(tT str, tT start, tT end)
	{
		// TODO manage start and end in WRITE_STRING
		for (tT car : (tSTRING) str)
		{
			WRITE_CHAR(((tCHARACTER) car).getChar());
		}
		return str;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IOutputStream#terpri()
	 */
	public tNULL TERPRI()
	{
		WRITE_CHAR('\n');
		return NIL;

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IOutputStream#freshLine()
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
	 * @see aloyslisp.core.plugs.streams.IStream#isOpen()
	 */
	@Override
	public boolean OPEN_STREAM_P()
	{
		return writer != null;
	}

	public tT CLOSE(tOUTPUT_STREAM stream)
	{
		writer.close();
		writer = null;
		return T;
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
		return "<#OUTPUT " + this.getClass().getSimpleName() + ">";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IOutputStream#putc(java.lang.Character)
	 */
	@Override
	public void putc(Character car)
	{
		writer.print(car);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IOutputStream#finishOutput()
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
	 * @see aloyslisp.core.plugs.streams.IOutputStream#forceOutput()
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
	 * @see aloyslisp.core.plugs.streams.IOutputStream#clearOutput()
	 */
	@Override
	public tT CLEAR_OUTPUT()
	{
		// TODO verify output
		writer.flush();
		return T;
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
	@Override
	public tT STREAM_ELEMENT_TYPE()
	{
		// IMPLEMENT return value
		return null;
	}

}
