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
// IP 4 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.system;

import static aloyslisp.commonlisp.L.*;

import java.io.EOFException;

import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * fpQUOTE_READER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class fpQUOTE_READER extends SYSTEM_FUNCTION
{

	/**
	 * @param def
	 */
	public fpQUOTE_READER()
	{
		super(decl("stream", "key", "&rest", "args"), //
				"(%quote-reader stream key &rest args)", //
				NIL);

	}

	private static final tSYMBOL	QUOTE	= sym("lisp::quote");

	/**
	 * @return
	 */
	public tT[] IMPL()
	{
		tINPUT_STREAM in = (tINPUT_STREAM) arg(0);

		try
		{
			return new tT[]
			{ quote(in.READ(NIL, NIL, T)) };
		}
		catch (EOFException e)
		{
			throw new LispException("Error in reading a value after " + QUOTE);
		}
	}
}
