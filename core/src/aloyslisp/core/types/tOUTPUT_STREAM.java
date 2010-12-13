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
// IP 16 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.types;

/**
 * IOutput
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tOUTPUT_STREAM extends tSTREAM
{
	/**
	 * Write a char
	 * 
	 * @param obj
	 */
	public void putc(Character car);

	/**
	 * Write a char
	 * 
	 * @param car
	 * @return
	 */
	public Character WRITE_CHAR(Character car);

	/**
	 * Write an object
	 * 
	 * @param obj
	 * @return
	 */
	public tT WRITE(tT obj);

	/**
	 * @param str
	 */
	public tT WRITE_STRING(tT str, tT start, tT end);

	/**
	 * 
	 */
	public tNULL TERPRI();

	/**
	 * 
	 */
	public tT FRESH_LINE();

	/**
	 * 
	 */
	public tT FINISH_OUTPUT();

	/**
	 * 
	 */
	public tT FORCE_OUTPUT();

	/**
	 * 
	 */
	public tT CLEAR_OUTPUT();
}
