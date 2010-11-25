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
// IP 3 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.system;

import static aloyslisp.commonlisp.L.*;

import java.io.EOFException;

import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * fpPARENT_READER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class fpPARENT_READER extends SYSTEM_FUNCTION
{

	/**
	 * @param def
	 */
	public fpPARENT_READER()
	{
		super(list("stream", "key", "&rest", "args"), //
				"(%parent-reader stream key &rest args)", //
				NIL);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.IFunc#impl(aloyslisp.core.plugs.collections
	 * .IList)
	 */
	@Override
	public tT[] impl()
	{
		tINPUT_STREAM in = (tINPUT_STREAM) arg(0);

		tLIST res = NIL;
		try
		{
			tT curr = in.READ(NIL, NIL, T);
			while (!(curr instanceof tSYMBOL)
					|| !((tSYMBOL) curr).SYMBOL_NAME().equals(")"))
			{
				// manage dotted lists
				if (curr instanceof tSYMBOL
						&& ((tSYMBOL) curr).SYMBOL_NAME().equals("."))
				{
					curr = in.READ(NIL, NIL, T);
					res = (tLIST) ((tLIST) res.REVERSE()).APPEND(curr);
					curr = in.READ(NIL, NIL, T);

					// dotted pair should end
					if (curr instanceof tSYMBOL
							&& ((tSYMBOL) curr).SYMBOL_NAME().equals(")"))
						return new tT[]
						{ res };

					throw new LispException("Dotted list bad end");
				}

				// else append element of list
				res = cons(curr, res);
				curr = in.READ(NIL, NIL, T);
			}

			// we should reverse the list
			return new tT[]
			{ res.REVERSE() };
		}
		catch (EOFException e)
		{
			throw new LispException("Error in reading a list : " + res);
		}
	}
}
