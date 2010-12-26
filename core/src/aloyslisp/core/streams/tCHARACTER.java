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
// IP 20 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import aloyslisp.core.annotations.*;
import aloyslisp.core.plugs.tATOM;
import aloyslisp.core.plugs.tT;

/**
 * tCHARACTER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tCHARACTER extends tATOM
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
	@Function(name = "char-upcase")
	public tCHARACTER CHAR_UPCASE();

	/**
	 * @return
	 */
	@Function(name = "char-downcase")
	public tCHARACTER CHAR_DOWNCASE();

	/**
	 * @return
	 */
	@Function(name = "char-name")
	public tCHARACTER CHAR_NAME();

	/**
	 * @return
	 */
	@Function(name = "char-int")
	public tT CHAR_INT();

}
