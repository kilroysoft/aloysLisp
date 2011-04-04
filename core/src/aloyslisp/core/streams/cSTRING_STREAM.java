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
import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.math.*;

/**
 * cSTRING_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cSTRING_STREAM extends cSTREAM implements tSTRING_STREAM
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
	public cSTRING_STREAM(String str, Integer start, Integer end)
	{
		if (end < 0)
			end = str.length();
		if (start < 0)
			start = 0;

		SET_OUPUT_STREAM_STRING(str);
		this.index = start;
		this.end = end;
		opened = true;
	}

	public cSTRING_STREAM()
	{
		buffer = "";
		opened = true;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTRING_STREAM#setString(java.lang.String)
	 */
	@Override
	public void SET_OUPUT_STREAM_STRING(String str)
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
	@aFunction(name = "make-string-input-stream", doc = "f_mk_s_1")
	public static tSTRING_STREAM MAKE_STRING_INPUT_STREAM( //
			@aArg(name = "string") String string, //
			@aOpt(name = "start") tT start, //
			@aOpt(name = "end") tT end //
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
			st = ((cINTEGER) start).INTEGER_VALUE();

		if (end != NIL)
			en = ((cINTEGER) end).INTEGER_VALUE();

		return new cSTRING_STREAM(string, st, en);
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
	public Character READ_CHAR(Boolean eofErrorP, tT eofValue,
			Boolean recursiveP)
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
	public Character UNREAD_CHAR(Character character)
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
	public boolean LISTEN()
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
	public tT CLEAR_INPUT()
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
	public Integer READ_BYTE(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tOUTPUT_STREAM#WRITE_CHAR(java.lang.Character,
	 * aloyslisp.core.streams.tOUTPUT_STREAM)
	 */
	@Override
	public Character WRITE_CHAR(Character character)
	{
		buffer += character;
		return character;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tOUTPUT_STREAM#WRITE_BYTE(java.lang.Integer,
	 * aloyslisp.core.streams.tOUTPUT_STREAM)
	 */
	@Override
	public Integer WRITE_BYTE(Integer val)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tOUTPUT_STREAM#FINISH_OUTPUT(aloyslisp.core.streams
	 * .tOUTPUT_STREAM)
	 */
	@Override
	public tT FINISH_OUTPUT()
	{
		return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tOUTPUT_STREAM#FORCE_OUTPUT(aloyslisp.core.streams
	 * .tOUTPUT_STREAM)
	 */
	@Override
	public tT FORCE_OUTPUT()
	{
		return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tOUTPUT_STREAM#CLEAR_OUTPUT(aloyslisp.core.streams
	 * .tOUTPUT_STREAM)
	 */
	@Override
	public tT CLEAR_OUTPUT()
	{
		return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tSTRING_OUTPUT_STREAM#GET_OUTPUT_STREAM_STRING()
	 */
	@Override
	public String GET_OUTPUT_STREAM_STRING()
	{
		// give back the rest of non read string
		return buffer.substring(index);
	}

}
