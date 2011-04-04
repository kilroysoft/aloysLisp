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
// IP 12 sept. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.sequences;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.designators.tPACKAGE_DESIGNATOR;
import aloyslisp.core.designators.tPATHNAME_DESIGNATOR;
import aloyslisp.core.math.*;

/**
 * tSTRING
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "string", doc = "t_string", typep = "stringp")
public interface tSTRING extends tVECTOR, tPATHNAME_DESIGNATOR,
		tPACKAGE_DESIGNATOR
{
	/**
	 * @return string value
	 */
	public String getString();

	/**
	 * @return
	 */
	@aFunction(name = "string-upcase", doc = "f_stg_up")
	public tSTRING STRING_UPCASE();

	/**
	 * @return
	 */
	@aFunction(name = "string-downcase", doc = "f_stg_up")
	public tSTRING STRING_DOWNCASE();

	/**
	 * @return
	 */
	@aFunction(name = "string-capitalize", doc = "f_stg_up")
	public tSTRING STRING_CAPITALIZE();

	/**
	 * @return
	 */
	@aFunction(name = "string-trim", doc = "f_stg_tr")
	public tSTRING STRING_TRIM();

	/**
	 * @return
	 */
	@aFunction(name = "string-left-trim", doc = "f_stg_tr")
	public tSTRING STRING_LEFT_TRIM();

	/**
	 * @return
	 */
	@aFunction(name = "string-right-trim", doc = "f_stg_tr")
	public tSTRING STRING_RIGHT_TRIM();

	/**
	 * Get number from a string representation in base radix
	 * 
	 * @param val
	 * @param start
	 * @param end
	 * @param radix
	 * @param junk
	 * @return
	 */
	@aFunction(name = "parse-integer", doc = "f_parse_")
	public cINTEGER PARSE_INTEGER(@aOpt(name = "start", def = "0") Integer start, //
			@aOpt(name = "end", def = "nil") tT end, //
			@aOpt(name = "radix", def = "*read-base*") Integer radix, //
			@aOpt(name = "junk", def = "nil") Boolean junk);

}
