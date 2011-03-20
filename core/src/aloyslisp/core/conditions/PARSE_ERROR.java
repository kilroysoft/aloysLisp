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
// IP 28 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.conditions;

import aloyslisp.annotations.BuiltIn;
import aloyslisp.annotations.Type;
import aloyslisp.core.streams.tPATHNAME_DESIGNATOR;

/**
 * PARSE_ERROR
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@Type(name = "parse-error", doc = "e_parse_")
@BuiltIn(classOf = "parse-error", doc = "e_parse_")
public class PARSE_ERROR extends ERROR
{
	private static final long		serialVersionUID	= 1L;
	protected tPATHNAME_DESIGNATOR	path;

	/**
	 * 
	 */
	public PARSE_ERROR(tPATHNAME_DESIGNATOR path)
	{
		this.path = path;
		message += "Parse error : " + path;
	}

}
