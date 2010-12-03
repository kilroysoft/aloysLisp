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
import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * sBLOCK
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class sBLOCK extends SPECIAL_OPERATOR
{

	/**
	 * 
	 */
	public sBLOCK()
	{
		super(decl("name", "&rest", "blocks"), //
				"(sBLOCK name &rest)", //
				NIL);
	}

	/**
	 * @return
	 */
	public tT[] IMPL(tSYMBOL name, tT... block)
	{
		return new tT[]
		{ name, list((Object[]) block) };
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.SPECIAL_OPERATOR#implSpecial(aloyslisp.core
	 * .plugs.Cell[])
	 */
	public tT[] implSpecial(tT[] rest)
	{
		tT name = rest[0];
		if (!(name instanceof tSYMBOL))
		{
			throw new LispException("Block name is not a symbol :" + name);
		}

		tT func = rest[1];
		if (!(func instanceof tLIST))
		{
			throw new LispException("Function definition is not a list :"
					+ func);
		}

		// sPROGN with name
		newBlock((tSYMBOL) name, NIL, (tLIST) func, NIL);

		tT res[] = e.exec();
		e.popBlock();

		return res;
	}

}
