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
// IP 11 nov. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import aloyslisp.annotations.*;

/**
 * tSTRING_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tSTRING_STREAM extends tSTREAM
{
	/**
	 * Initialize string state
	 * 
	 * @param str
	 */
	@aNonStandard
	@aWriter(accessor = "get-output-stream-string")
	@aFunction(name = "set-output-stream-string", doc = "f_get_ou")
	public void SET_OUPUT_STREAM_STRING( //
			@aArg(name = "new-file") String str //
	);

	@aFunction(name = "get-output-stream-string", doc = "f_get_ou")
	public String GET_OUTPUT_STREAM_STRING( //
	);
}
