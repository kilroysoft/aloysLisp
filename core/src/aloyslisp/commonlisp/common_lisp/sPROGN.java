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
// IP 6 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.exec.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * sPROGN
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class sPROGN extends SPECIAL_OPERATOR
{
	Arguments	block	= null;

	/**
	 * 
	 */
	public sPROGN()
	{
		super(decl("&rest", "blocks"), //
				"(sPROGN &rest blocks)", //
				NIL);
	}

	/**
	 * @param def
	 */
	public sPROGN(tLIST def, String doc, tLIST declare)
	{
		super(def, doc, declare);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.functions.IFunc#impl()
	 */
	@Override
	public tT[] impl()
	{
		return new tT[]
		{ arg() };
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.SPECIAL_OPERATOR#implSpecial(aloyslisp.core
	 * .plugs.Cell[])
	 */
	public tT[] implSpecial(tT[] rest)
	{
		// anonymous code w/o args
		newBlock(null, NIL, (tLIST) rest[0], NIL);

		// Execute progn
		tT res[] = e.exec();

		e.popBlock();
		return res;
	}

}
