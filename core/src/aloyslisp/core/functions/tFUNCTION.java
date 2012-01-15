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
// IP 13 sept. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.engine.*;

/**
 * tLAMBDA_FUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "function", doc = "03_", typep = "functionp")
public interface tFUNCTION extends tAPI
{
	/**
	 * Execute function with arguments as a list
	 * 
	 * @param args
	 *            List of arguments
	 * @return Evaluated results in ana array for eventual multiple values
	 */
	@aFunction(name = "funcall")
	public tT[] FUNCALL( //
			@aRest(name = "args") tLIST args //
	);

	/**
	 * Execute function with separate arguments
	 * 
	 * @param args
	 *            individual args
	 * @return Evaluated results in ana array for eventual multiple values
	 */
	@aJavaInternal
	public tT[] e(Object... args);

}
