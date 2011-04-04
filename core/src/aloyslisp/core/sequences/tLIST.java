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
// IP 25 oct. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.sequences;

import aloyslisp.annotations.*;
import aloyslisp.core.tT;

;

/**
 * tLIST
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "list", doc = "t_list", typep = "listp")
public interface tLIST extends tSEQUENCE
{
	/**
	 * Put cell in car
	 * 
	 * @param newCell
	 */
	@aFunction(name = "setf-car")
	@aBaseArg(name = "list", pos = 1)
	public tLIST SET_CAR( //
			@aArg(name = "val") tT val //
	);

	/**
	 * Put cell in cdr
	 * 
	 * @param newCell
	 */
	@aFunction(name = "setf-cdr")
	@aBaseArg(name = "list", pos = 1)
	public tLIST SET_CDR( //
			@aArg(name = "val") tT val //
	);

	/**
	 * Test if cons is the last
	 * 
	 * @return
	 */
	@aFunction(name = "endp", doc = "f_endp")
	public boolean ENDP();

	/**
	 * Get last place
	 * 
	 * @return
	 */
	@aFunction(name = "last", doc = "f_last")
	public tLIST LAST();

	/**
	 * @param value
	 * @return
	 */
	@aFunction(name = "setf-last")
	public tLIST SET_LAST( //
			@aArg(name = "value") tT value);

	/**
	 * Append : write to the cdr of last cons (append to cNULL -> item)
	 * 
	 * @return
	 */
	@aFunction(name = "append", doc = "f_append")
	public tT APPEND( //
			@aArg(name = "item") tT item);

}
