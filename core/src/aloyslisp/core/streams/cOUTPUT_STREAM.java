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

import aloyslisp.core.tT;
import aloyslisp.core.packages.tNULL;
import aloyslisp.core.sequences.tSTRING;

/**
 * cOUTPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class cOUTPUT_STREAM extends cSTREAM implements tOUTPUT_STREAM
{
	protected boolean	lineBegin	= true;

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
	public String toString()
	{
		return "<#OUTPUT " + this.getClass().getSimpleName() + ">";
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

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tOUTPUT_STREAM#WRITE(aloyslisp.core.types.tT)
	 */
	public tT WRITE(tT obj)
	{
		// TODO manage &key arguments and generate Specials vars for printing
		// System.out.println("(WRITE " + str(obj.toString()));
		return WRITE_STRING(str(obj.toString()));
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
		TERPRI();
		tT res = PRIN1(obj);
		WRITE(c(' '));
		return res;
	}

}
