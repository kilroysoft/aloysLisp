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
// IP 16 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * sSETF
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class sSETF extends SPECIAL_OPERATOR
{

	/**
	 * @param args
	 * @param doc
	 * @param declare
	 */
	public sSETF()
	{
		super(decl("place", "value"), "(SETF place value)", NIL);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tFUNCTION#impl()
	 */
	@Override
	public tT[] impl()
	{
		return new tT[]
		{ arg(0), arg(1) };
	}

	private static final tSYMBOL	SETQ	= sym("lisp::setq");

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tSPECIAL_OPERATOR#implSpecial(aloyslisp.core.types
	 * .tT[])
	 */
	@Override
	public tT[] implSpecial(tT[] args)
	{
		tT place = args[0];
		tT value = args[1].EVAL()[0];

		// if symbol it's a SETQ
		if (place instanceof tSYMBOL)
		{
			return SETQ.SYMBOL_FUNCTION().e(place, value);
		}

		// test type validity for place
		if (!(place instanceof tCONS))
		{
			ERROR("FSET : Bad type for place : ~s", place);
			return null;
		}

		// get place function
		tT func = place.CAR();
		if (!(func instanceof tSYMBOL))
		{
			ERROR("FSET : Bad function for place : ~s", func);
			return null;
		}

		// get setf function
		tT newFunc = ((tSYMBOL) func).GET(setfKey, NIL);
		if (newFunc instanceof tNULL)
		{
			ERROR("FSET : Function for place has no FSET definition: ~s", func);
			return null;
		}

		// write to place
		tLIST test = cons(newFunc,
				((tCONS) ((tLIST) place.CDR().copy()).APPEND(list(value))));
		return test.EVAL();
	}
}
