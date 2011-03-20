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
// IP 28 d�c. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.conditions;

import aloyslisp.annotations.BuiltIn;
import aloyslisp.annotations.Type;
import aloyslisp.core.tT;

/**
 * CELL_ERROR
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@Type(name = "cell-error", doc = "e_cell_e")
@BuiltIn(classOf = "cell-error", doc = "e_cell_e")
public class CELL_ERROR extends ERROR
{
	private static final long	serialVersionUID	= 1L;

	protected tT				cell;

	/**
	 * 
	 */
	public CELL_ERROR(tT cell)
	{
		this.cell = cell;
		message += " Cell " + cell;
	}

}
