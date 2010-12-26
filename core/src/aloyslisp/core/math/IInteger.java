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
// IP 23 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

/**
 * IInteger
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface IInteger extends IRational
{
	/* *******************************************************************
	 * FACTORY
	 */

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Get string representation of number with base radix (for integers)
	 * 
	 * @param radix
	 * @return
	 */
	public String toBase(Integer radix);

	/**
	 * Less common multiplicand
	 * 
	 * @param b
	 * @return
	 */
	public IInteger lcm(IInteger b);

	/**
	 * Greather common denumerator
	 * 
	 * @param b
	 * @return
	 */
	public IInteger gcd(IInteger b);

	/**
	 * Arithmetic AND
	 * 
	 * @param b
	 * @return
	 */
	public IInteger logand(IInteger b);

	/**
	 * Arithmetic NAND
	 * 
	 * @param b
	 * @return
	 */
	public IInteger lognand(IInteger b);

	/**
	 * Arithmetic NAND COMPLEMENT 1
	 * 
	 * @param b
	 * @return
	 */
	public IInteger logandc1(IInteger b);

	/**
	 * Arithmetic AND COMPLEMENT 2
	 * 
	 * @param b
	 * @return
	 */
	public IInteger logandc2(IInteger b);

	/**
	 * Arithmetic OR
	 * 
	 * @param b
	 * @return
	 */
	public IInteger logior(IInteger b);

	/**
	 * Arithmetic OR COMPLEMENT 1
	 * 
	 * @param b
	 * @return
	 */
	public IInteger logorc1(IInteger b);

	/**
	 * Arithmetic OR COMPLEMENT 2
	 * 
	 * @param b
	 * @return
	 */
	public IInteger logorc2(IInteger b);

	/**
	 * Arithmetic XOR
	 * 
	 * @param b
	 * @return
	 */
	public IInteger logxor(IInteger b);

	/**
	 * Arithmetic EQUIVATEN
	 * 
	 * @param b
	 * @return
	 */
	public IInteger logeqv(IInteger b);

	/**
	 * Arithmetic NOT (complement to 0)
	 * 
	 * @param b
	 * @return
	 */
	public IInteger lognot();

}
