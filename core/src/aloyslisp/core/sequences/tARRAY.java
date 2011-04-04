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
// IP 18 sept. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.sequences;

import aloyslisp.annotations.aType;
import aloyslisp.core.*;
import aloyslisp.core.clos.tBUILT_IN_CLASS;

/**
 * tARRAY
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "array", doc = "t_array", typep = "arrayp")
public interface tARRAY extends tBUILT_IN_CLASS
{
	/**
	 * @return
	 */
	public tT[] get();

	/**
	 * Return the type as a cons (int) (int 0 5) (complex (int) (float))
	 * 
	 * @return aType of array cell
	 */
	public tT getType();

	/**
	 * The the array type
	 * 
	 * @param type
	 */
	public tARRAY setType(tT type);

}
