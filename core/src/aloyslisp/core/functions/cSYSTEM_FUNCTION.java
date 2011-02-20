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
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import static aloyslisp.core.engine.L.*;
import aloyslisp.core.sequences.tLIST;

/**
 * cSYSTEM_FUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class cSYSTEM_FUNCTION extends cFUNCTION implements
		tCOMPILED_FUNCTION
{
	/**
	 * @param def
	 */
	public cSYSTEM_FUNCTION(Class<?> cls, String name, tLIST args, String doc,
			tLIST declare)
	{
		super(true, cls, sym(name), args, list(str(doc), declare));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.functions.FUNCTION#printableStruct()
	 */
	protected String printableStruct()
	{
		return "SYSTEM " + getFuncName() + " " + api.getArgs() + " "
				+ api.commentary() + " " + api.declare();
	}

}
