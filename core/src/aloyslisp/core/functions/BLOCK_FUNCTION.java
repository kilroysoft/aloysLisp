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
// IP 29 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import aloyslisp.core.plugs.tSYMBOL;
import aloyslisp.core.sequences.tLIST;

/**
 * BLOCK_FUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class BLOCK_FUNCTION extends LAMBDA_FUNCTION implements tBLOCK_FUNCTION
{

	/**
	 * @param def
	 */
	public BLOCK_FUNCTION(tSYMBOL name, tLIST args, tLIST func)
	{
		super(name, args, func);
	}

	protected String printableStruct()
	{
		return "FUNCTION " + intern.getName() + " " + intern.getArgs() + " "
				+ intern.commentary() + " " + intern.declare() + " "
				+ intern.func();
	}

}
