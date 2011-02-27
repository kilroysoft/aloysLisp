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
// IP 20 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import aloyslisp.core.*;
import static aloyslisp.internal.engine.L.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.math.*;
import aloyslisp.annotations.*;

/**
 * cSTRING_INPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cSTRING_INPUT_STREAM extends cINPUT_STREAM implements
		tSTRING_INPUT_STREAM
{
	/**
	 * String buffer
	 */
	protected String	buffer	= "";

	/**
	 * Current read index
	 */
	protected Integer	index	= 0;

	/**
	 * End of buffer
	 */
	protected Integer	end		= 0;

	/**
	 * Open state
	 */
	protected Boolean	opened	= false;

	/**
	 * 
	 */
	public cSTRING_INPUT_STREAM(String str, Integer start, Integer end)
	{
		if (end < 0)
			end = str.length();
		if (start < 0)
			start = 0;

		setString(str);
		this.index = start;
		this.end = end;
		opened = true;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTRING_STREAM#getString()
	 */
	@Override
	public String getString()
	{
		// give back the rest of non read string
		return buffer.substring(index);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTRING_STREAM#setString(java.lang.String)
	 */
	@Override
	public void setString(String str)
	{
		this.buffer = str;
		this.index = 0;
		this.end = str.length();
	}

	/**
	 * @param string
	 * @param start
	 * @param end
	 * @return
	 */
	@Static(name = "make-string-input-stream", doc = "f_mk_s_1")
	public static tSTRING_INPUT_STREAM MAKE_STRING_INPUT_STREAM( //
			@Arg(name = "string") String string, //
			@Opt(name = "start") tT start, //
			@Opt(name = "end") tT end //
	)
	{
		Integer st = 0;
		Integer en = string.length();

		if (start != NIL && !(start instanceof tINTEGER))
			throw new LispException(
					"make-string-input-stream : start should be an integer");

		if (end != NIL && !(end instanceof tINTEGER))
			throw new LispException(
					"make-string-input-stream : end should be an integer");

		if (start != NIL)
			st = ((cINTEGER) start).integerValue();

		if (end != NIL)
			en = ((cINTEGER) end).integerValue();

		return new cSTRING_INPUT_STREAM(string, st, en);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#OPEN_STREAM_P()
	 */
	@Override
	public Boolean OPEN_STREAM_P()
	{
		return opened;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTREAM#CLOSE(java.lang.Boolean)
	 */
	@Override
	public Boolean CLOSE()
	{
		Boolean was = opened;
		opened = false;
		return was;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tINPUT_STREAM#READ_CHAR(aloyslisp.core.streams
	 * .tINPUT_STREAM, java.lang.Boolean, aloyslisp.core.tT, java.lang.Boolean)
	 */
	@Override
	public Character READ_CHAR(tINPUT_STREAM stream, Boolean eofErrorP,
			tT eofValue, Boolean recursiveP)
	{
		if (index >= end)
			throw new END_OF_FILE(this);

		return buffer.charAt(index++);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tINPUT_STREAM#UNREAD_CHAR(java.lang.Character,
	 * aloyslisp.core.streams.tINPUT_STREAM)
	 */
	@Override
	public Character UNREAD_CHAR(Character character, tINPUT_STREAM stream)
	{
		if (index <= 0)
			throw new LispException("unread-char : can't unread char "
					+ character);

		// TODO temporary we don't unread a different char as the one read
		index--;
		return buffer.charAt(index);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tINPUT_STREAM#LISTEN(aloyslisp.core.streams.
	 * tINPUT_STREAM)
	 */
	@Override
	public boolean LISTEN(tINPUT_STREAM stream)
	{
		return index < end;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tINPUT_STREAM#CLEAR_INPUT(aloyslisp.core.streams
	 * .tINPUT_STREAM)
	 */
	@Override
	public tT CLEAR_INPUT(tINPUT_STREAM stream)
	{
		index = end;
		return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tINPUT_STREAM#READ_BYTE(aloyslisp.core.streams
	 * .tINPUT_STREAM, java.lang.Boolean, aloyslisp.core.tT, java.lang.Boolean)
	 */
	@Override
	public Integer READ_BYTE(tINPUT_STREAM stream, Boolean eofErrorP,
			tT eofValue, Boolean recursiveP)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
