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
// IP 28 déc. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.conditions;

import aloyslisp.annotations.aBuiltIn;
import aloyslisp.annotations.aType;
import aloyslisp.core.tT;

/**
 * UNBOUND_SLOT
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "unbound-slot", doc = "e_unboun")
@aBuiltIn(lispClass = "unbound-slot", doc = "e_unboun")
public class UNBOUND_SLOT extends CELL_ERROR
{
	private static final long	serialVersionUID	= 1L;

	/**
	 * 
	 */
	public UNBOUND_SLOT(tT cell)
	{
		super(cell);
		message += " Unbound slot.";
	}

}
