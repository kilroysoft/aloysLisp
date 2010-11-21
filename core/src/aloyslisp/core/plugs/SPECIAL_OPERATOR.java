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
// IP 26 oct. 2010 Creation
// TODO Rethink about Special form as a 2 way function and macro
// transformations.
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.types.*;

/**
 * SPECIAL_OPERATOR
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class SPECIAL_OPERATOR extends FUNCTION implements
		tSPECIAL_OPERATOR, tCOMPILED_FUNCTION
{

	/**
	 * @param def
	 */
	public SPECIAL_OPERATOR(tLIST args, String doc, tLIST declare)
	{
		super(null, args, list(str(doc), declare));
		this.setFuncName(sym(currPackage(), compiledName()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.functions.FUNCTION#printableStruct()
	 */
	protected String printableStruct()
	{
		return "SPECIAL " + compiledName() + " " + intern.getArgs() + " "
				+ intern.commentary() + " " + intern.declare();
	}

}
