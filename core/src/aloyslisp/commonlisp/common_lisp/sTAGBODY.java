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
// IP 8 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * sTAGBODY
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class sTAGBODY extends SPECIAL_OPERATOR
{

	/**
	 * @param args
	 * @param doc
	 * @param declare
	 */
	public sTAGBODY()
	{
		super(decl("&rest", "blocks"), //
				"(sTAGBODY &rest)", //
				NIL);
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
	 * aloyslisp.core.plugs.functions.ISpecialForm#implSpecial(aloyslisp.core
	 * .plugs.Cell[])
	 */
	@Override
	public tT[] implSpecial(tT[] args)
	{
		// anonymous code w/o args
		newBlock(null, NIL, (tLIST) args[0], NIL);
		e.tagBody();

		// Execute progn
		tT res[] = e.exec();

		e.popBlock();
		return res;
	}

}
