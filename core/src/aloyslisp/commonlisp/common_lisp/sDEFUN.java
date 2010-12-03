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
// IP 23 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * sDEFUN
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class sDEFUN extends SPECIAL_OPERATOR
{

	/**
	 * @param def
	 */
	public sDEFUN()
	{
		super(decl("name", "arglist", "&rest", "func"), //
				"(sDEFUN name args &rest func)", //
				NIL);
	}

	/**
	 * @return
	 */
	public tT[] IMPL()
	{
		return new tT[]
		{ arg(0), arg(1), arg() };
	}

	private static final tSYMBOL	SETF	= sym("lisp::setf");

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.ISpecialForm#implSpecial(aloyslisp.core
	 * .plugs.Cell[])
	 */
	@Override
	public tT[] implSpecial(tT[] args)
	{
		tT name = args[0];
		tT argList = args[1];
		tT func = args[2];

		if (name instanceof tCONS)
		{
			// (setf func) definition
			if (((tLIST) name).CAR() != SETF || ((tLIST) name).LENGTH() != 2
					|| !(((tLIST) name).CDR().CAR() instanceof tSYMBOL))
			{
				ERROR("DEFUN : Function name as list should have the form (SEFT name) : ~s",
						name);
			}

			// Setf func to manage
			tT newFunc = name.CDR().CAR();
			if (!(newFunc instanceof tSYMBOL))
			{
				ERROR("DEFUN : Function name as list should have the form (SEFT name) : ~s",
						name);
			}

			// setf writer symbol
			tSYMBOL setfFunc = sym(((tSYMBOL) newFunc).SYMBOL_PACKAGE(),
					"setf-" + ((tSYMBOL) newFunc).SYMBOL_NAME());

			// setf writer function
			tFUNCTION def = new DEFUN_FUNCTION(setfFunc, (tLIST) argList,
					(tLIST) func);

			((tSYMBOL) setfFunc).SET_SYMBOL_FUNCTION(def);

			//
			((tSYMBOL) newFunc).SET_GET(setfKey, setfFunc);

			return new tT[]
			{ newFunc };
		}

		if (!(name instanceof tSYMBOL))
		{
			throw new LispException("Function name not a symbol " + name);
		}

		if (!(argList instanceof tLIST))
		{
			throw new LispException("Function arguments not a list " + argList);
		}

		if (!(func instanceof tLIST))
		{
			throw new LispException("Function function definition not a list "
					+ func);
		}

		tFUNCTION def = new DEFUN_FUNCTION((tSYMBOL) name, (tLIST) argList,
				(tLIST) func);
		((tSYMBOL) name).SET_SYMBOL_FUNCTION(def);

		return new tT[]
		{ name };
	}

}
