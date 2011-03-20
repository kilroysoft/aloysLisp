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
// IP 25 oct. 2010 Creation
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
@Type(name = "list", doc = "t_list", typep = "listp")
public interface tLIST extends tSEQUENCE
{
	/**
	 * Get car
	 * 
	 * @return fCAR of cons
	 */
	@Function(name = "car", doc = "f_car_c")
	@SetF(name = "setf-car")
	public tT CAR();

	/**
	 * Get cdr
	 * 
	 * @return fCDR of cons
	 */
	@Function(name = "cdr", doc = "f_car_c")
	@SetF(name = "setf-cdr")
	public tT CDR();

	/**
	 * Put cell in car
	 * 
	 * @param newCell
	 */
	@Function(name = "setf-car")
	@BaseArg(name = "list", pos = 1)
	public tLIST SET_CAR( //
			@Arg(name = "val") tT val //
	);

	/**
	 * Put cell in cdr
	 * 
	 * @param newCell
	 */
	@Function(name = "setf-cdr")
	@BaseArg(name = "list", pos = 1)
	public tLIST SET_CDR( //
			@Arg(name = "val") tT val //
	);

	/**
	 * Test if cons is the last
	 * 
	 * @return
	 */
	@Function(name = "endp", doc = "f_endp")
	public boolean ENDP();

	/**
	 * Get last place
	 * 
	 * @return
	 */
	@Function(name = "last", doc = "f_last")
	public tLIST LAST();

	/**
	 * @param value
	 * @return
	 */
	@Function(name = "setf-last")
	public tLIST SET_LAST( //
			@Arg(name = "value") tT value);

	/**
	 * Append : write to the cdr of last cons (append to cNULL -> item)
	 * 
	 * @return
	 */
	@Function(name = "append", doc = "f_append")
	public tT APPEND( //
			@Arg(name = "item") tT item);

}
