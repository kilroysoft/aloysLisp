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
// IP 15 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.types;

/**
 * tSEQUENCE
 * 
 * @see http://www-prod-gif.supelec.fr/docs/cltl/clm/node141.html#
 *      SECTION001800000000000000000
 * @see http://www.slac.stanford.edu/comp/unix/gnu-info/elisp_7.html
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tSEQUENCE extends tT, Iterable<tT>
{
	/**
	 * @return
	 */
	public Integer LENGTH();

	/**
	 * @param pos
	 * @return
	 */
	public tT ELT(Integer pos);

	/**
	 * @param pos
	 * @param value
	 * @return
	 */
	public tSEQUENCE SET_ELT(Integer pos, tT value);

	/**
	 * @param start
	 * @param end
	 * @return
	 */
	public tSEQUENCE SUBSEQ(Integer start, Integer end);

	/**
	 * @param start
	 * @param end
	 * @param value
	 * @return
	 */
	public tSEQUENCE SET_SUBSEQ(Integer start, Integer end, tT value);

	/**
	 * Reverse the list.
	 * 
	 * @param list
	 * @return
	 */
	public tSEQUENCE REVERSE();

	/**
	 * Reverse the list.
	 * 
	 * @return
	 */
	public tSEQUENCE NREVERSE();

	/**
	 * Return elements of sequence in an array
	 * 
	 * @return
	 */
	public tT[] getArray();

}
