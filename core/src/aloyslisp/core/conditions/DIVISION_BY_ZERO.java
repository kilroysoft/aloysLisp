/**
 * aloysLisp.
 * <p>
 * A LISP interpreter, compiler and library.
 * <p>
 * Copyright (C) 2010-2011 kilroySoft <aloyslisp@kilroysoft.ch>
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
// IP 29 d�c. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.conditions;

import aloyslisp.annotations.*;
import aloyslisp.core.tT;

/**
 * DIVISION_BY_ZERO
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "division-by-zero", doc = "e_divisi")
@aBuiltIn(lispClass = "division-by-zero", doc = "e_divisi")
public class DIVISION_BY_ZERO extends ARITHMETIC_ERROR
{
	private static final long	serialVersionUID	= 1L;

	/**
	 * @param operation
	 * @param operands
	 */
	public DIVISION_BY_ZERO(tT operation, tT operands)
	{
		super(operation, operands);
		message += " Division by zero.";
	}

}
