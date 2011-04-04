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
// IP 20 sept. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.clos.tBUILT_IN_CLASS;
import aloyslisp.core.designators.tSTRING_DESIGNATOR;
import aloyslisp.core.sequences.*;

/**
 * tCHARACTER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tCHARACTER extends tBUILT_IN_CLASS, tSTRING_DESIGNATOR
{
	/**
	 * Get char value
	 * 
	 * @return
	 */
	public Character getChar();

	/**
	 * Get char as a lisp formated string
	 * 
	 * @return
	 */
	public String getFormated();

	/**
	 * @return
	 */
	@aFunction(name = "char-upcase", doc = "f_char_u")
	public tCHARACTER CHAR_UPCASE();

	/**
	 * @return
	 */
	@aFunction(name = "char-downcase", doc = "f_char_u")
	public tCHARACTER CHAR_DOWNCASE();

	/**
	 * @return
	 */
	@aFunction(name = "char-name", doc = "f_char_n")
	public tCHARACTER CHAR_NAME();

	/**
	 * @return
	 */
	@aFunction(name = "char-int", doc = "f_char_i")
	public tT CHAR_INT();

}
