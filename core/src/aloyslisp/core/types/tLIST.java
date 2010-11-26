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

package aloyslisp.core.types;

import aloyslisp.core.annotations.*;

;

/**
 * tLIST
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@Type(name = "list", test = "listp")
public interface tLIST extends tSEQUENCE
{
	/**
	 * Get car
	 * 
	 * @return fCAR of cons
	 */
	@Primitive(name = "car")
	public tT CAR();

	/**
	 * Get cdr
	 * 
	 * @return fCDR of cons
	 */
	@Primitive(name = "cdr")
	public tT CDR();

	/**
	 * Put cell in car
	 * 
	 * @param newCell
	 */
	@Primitive(name = "setf-car")
	public tLIST SET_CAR( //
			@Arg(name = "val") tT val);

	/**
	 * Put cell in cdr
	 * 
	 * @param newCell
	 */
	@Primitive(name = "setf-cdr")
	public tLIST SET_CDR( //
			@Arg(name = "val") tT val);

	/**
	 * Test if cons is the last
	 * 
	 * @return
	 */
	@Primitive(name = "endp")
	public boolean ENDP();

	/**
	 * Get last place
	 * 
	 * @return
	 */
	@Primitive(name = "last")
	public tLIST LAST();

	/**
	 * @param value
	 * @return
	 */
	@Primitive(name = "setf-last")
	public tLIST SET_LAST( //
			@Arg(name = "value") tT value);

	/**
	 * Append : write to the cdr of last cons (append to NIL -> item)
	 * 
	 * @return
	 */
	@Primitive(name = "append")
	public tT APPEND( //
			@Arg(name = "item") tT item);

}
