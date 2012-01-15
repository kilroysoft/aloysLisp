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
// IP 15 sept. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.sequences;

import aloyslisp.annotations.*;
import aloyslisp.core.tT;
import aloyslisp.internal.iterators.SEQUENCEIterator;

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
@aType(name = "sequencs", doc = "t_seq", typep = "sequence-p")
public interface tSEQUENCE extends tT, Iterable<tT>
{

	/**
	 * Return iterator over sequence.
	 * 
	 * @param destructive
	 * @return
	 */
	@aJavaInternal
	public SEQUENCEIterator iterator(boolean destructive);

	// ////////////////////////////////////////////////////////
	// LISP Methods
	/**
	 * @return
	 */
	@aFunction(name = "length", doc = "f_length")
	public Integer LENGTH();

	/**
	 * @param pos
	 * @return
	 */
	@aFunction(name = "elt", doc = "f_elt")
	public tT ELT( //
			@aArg(name = "pos") Integer pos);

	/**
	 * @param pos
	 * @param value
	 * @return
	 */
	@aFunction(name = "set-elt")
	public tT SET_ELT( //
			@aArg(name = "value") tT value, //
			@aArg(name = "pos") Integer pos //
	);

	/**
	 * @param start
	 * @param end
	 * @return
	 */
	@aFunction(name = "subseq", doc = "f_subseq")
	public tSEQUENCE SUBSEQ( //
			@aArg(name = "start") Integer start, //
			@aOpt(name = "end") Integer end);

	/**
	 * @param start
	 * @param end
	 * @param value
	 * @return
	 */
	@aFunction(name = "set-subseq")
	public tSEQUENCE SET_SUBSEQ( //
			@aArg(name = "value") tT value, //
			@aArg(name = "start") Integer start, //
			@aArg(name = "end") Integer end //
	);

	/**
	 * Reverse the list.
	 * 
	 * @param list
	 * @return
	 */
	@aFunction(name = "reverse", doc = "f_revers")
	public tSEQUENCE REVERSE();

	/**
	 * Reverse the list.
	 * 
	 * @return
	 */
	@aFunction(name = "nreverse", doc = "f_revers")
	public tSEQUENCE NREVERSE();

	/**
	 * Return elements of sequence in an array
	 * 
	 * @return
	 */
	@aFunction(name = "values-list", doc = "f_vals")
	public tT[] VALUES_LIST();

	/**
	 * search an element in a sequence
	 * 
	 * @return
	 */
	@aFunction(name = "find", doc = "f_find_")
	@aKey(keys = "(from-end test test-not start end key)")
	@aBaseArg()
	public tT FIND( //
			@aArg(name = "item") tT item//
	);

}
