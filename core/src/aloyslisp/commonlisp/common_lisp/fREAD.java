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
// IP 14 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;

import java.io.EOFException;

import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * fREAD
 * 
 * <p>
 * [Function]
 * <p>
 * read &optional input-stream eof-error-p eof-value recursive-p
 * 
 * <p>
 * read reads in the printed representation of a Lisp object from input-stream,
 * builds a corresponding Lisp object, and returns the object. *
 * 
 * @see <a href='http://clm.aloys.li/node195.html'>22.2.1. Input from Character
 *      Streams</a>
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class fREAD extends SYSTEM_FUNCTION
{
	// private static final Integer inputStream = 0;
	// private static final Integer eofErrorP = 0;
	// private static final Integer eofValue = 0;
	// private static final Integer recursiveP = 0;

	/**
	 * @param eval
	 */
	public fREAD()
	{
		super(
				decl("&optional", //
						decl("input-stream", standardInput), //
						decl("eof-error-p", true), //
						"eof-value", //
						"recursive-p"), //
				"(fREAD &optional inputStream eof-error-p eof-value recursive-p)", //
				NIL);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.FUNCTION#impl(aloyslisp.core.plugs.CELL)
	 */
	public tT[] impl()
	{
		tT[] res = new tT[1];
		tINPUT_STREAM inputStream = (tINPUT_STREAM) arg(0);
		tT eofErrorP = arg(1);
		tT eofValue = arg(2);
		tT recursiveP = arg(3);

		// System.out.println(currPackage().describe());

		try
		{
			res[0] = inputStream.READ(eofErrorP, eofValue, recursiveP);
		}
		catch (EOFException e)
		{
			if (eofErrorP != NIL)
			{
				throw new LispException("Unexpected EOF");
			}
			res[0] = eofValue;
		}
		// System.out.println("(read)='" + res[0] + "'");
		return res;
	}

}
