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
// IP 12 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.sequences;


/**
 * tSTRING
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tSTRING extends tVECTOR
{
	/**
	 * @return string value
	 */
	public String getString();

	/**
	 * @return
	 */
	public tSTRING STRING_UPCASE();

	/**
	 * @return
	 */
	public tSTRING STRING_DOWNCASE();

	/**
	 * @return
	 */
	public tSTRING STRING_CAPITALIZE();

	/**
	 * @return
	 */
	public tSTRING STRING_TRIM();

	/**
	 * @return
	 */
	public tSTRING STRING_LEFT_TRIM();

	/**
	 * @return
	 */
	public tSTRING STRING_RIGHT_TRIM();

}
