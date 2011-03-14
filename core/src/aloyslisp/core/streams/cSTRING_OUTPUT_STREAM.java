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

import aloyslisp.core.tT;
import static aloyslisp.core.L.*;

/**
 * cSTRING_OUTPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cSTRING_OUTPUT_STREAM extends cOUTPUT_STREAM implements
		tSTRING_OUTPUT_STREAM
{

	/**
	 * Output buffer
	 */
	protected StringBuffer	buffer	= null;

	/**
	 * Open state
	 */
	protected Boolean		opened	= false;

	/**
	 * 
	 */
	public cSTRING_OUTPUT_STREAM()
	{
		buffer = new StringBuffer();
		opened = true;
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
		buffer.append(character);
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
	 * @see aloyslisp.core.streams.tSTRING_STREAM#getString()
	 */
	@Override
	public String getString()
	{
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tSTRING_STREAM#setString(java.lang.String)
	 */
	@Override
	public void setString(String str)
	{
		buffer = new StringBuffer(str);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.streams.tSTRING_OUTPUT_STREAM#GET_OUTPUT_STREAM_STRING()
	 */
	@Override
	public String GET_OUTPUT_STREAM_STRING()
	{
		return getString();
	}

}
