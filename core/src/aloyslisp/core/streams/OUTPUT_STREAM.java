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

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.plugs.tNULL;
import aloyslisp.core.plugs.tT;
import aloyslisp.core.sequences.tSTRING;

/**
 * OUTPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class OUTPUT_STREAM extends STREAM implements tOUTPUT_STREAM
{
	protected boolean	lineBegin	= true;

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.streams.IOutputStream#writeString(java.lang.String)
	 */
	@Override
	public tT WRITE_STRING(tT str, tOUTPUT_STREAM stream, tT start, tT end)
	{
		// TODO manage start and end in WRITE_STRING
		for (tT car : (tSTRING) str)
		{
			WRITE_CHAR(((tCHARACTER) car).getChar(), stream);
		}
		return str;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IOutputStream#terpri()
	 */
	public tNULL TERPRI(tOUTPUT_STREAM stream)
	{
		WRITE_CHAR('\n', stream);
		lineBegin = true;
		return NIL;

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.streams.IOutputStream#freshLine()
	 */
	@Override
	public tT FRESH_LINE(tOUTPUT_STREAM stream)
	{
		if (!lineBegin)
			return TERPRI(stream);
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
	public String toString()
	{
		return "<#OUTPUT " + this.getClass().getSimpleName() + ">";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSTREAM#STREAM_ELEMENT_TYPE()
	 */
	@Override
	public tT STREAM_ELEMENT_TYPE(tSTREAM stream)
	{
		// IMPLEMENT return value
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#WRITE(aloyslisp.core.types.tT)
	 */
	public tT WRITE(tT obj, tOUTPUT_STREAM stream)
	{
		return WRITE_STRING(str(obj.toString()), stream, NIL, NIL);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#PRIN1(aloyslisp.core.types.tT,
	 * aloyslisp.core.types.tOUTPUT_STREAM)
	 */
	@Override
	public tT PRIN1(tT obj, tOUTPUT_STREAM stream)
	{
		tT res = null;
		tT savEscape = printEscape.SYMBOL_VALUE();
		printEscape.SET_SYMBOL_VALUE(T);

		try
		{
			res = WRITE(obj, this);
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
	public tT PRINC(tT obj, tOUTPUT_STREAM stream)
	{
		tT res = null;
		tT savEscape = printEscape.SYMBOL_VALUE();
		printEscape.SET_SYMBOL_VALUE(NIL);
		tT savReadably = printReadably.SYMBOL_VALUE();
		printReadably.SET_SYMBOL_VALUE(NIL);

		try
		{
			res = WRITE(obj, this);
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
	public tT PRINT(tT obj, tOUTPUT_STREAM stream)
	{
		TERPRI(stream);
		tT res = PRIN1(obj, stream);
		WRITE(c(' '), stream);
		return res;
	}

}
