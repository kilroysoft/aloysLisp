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
	 * OPERATOR
	 */
	/**
	 * Less common multiplicand
	 * 
	 * @param op
	 * @return
	 */
	public IInteger lcm(IInteger op);

	/**
	 * Greather common denumerator
	 * 
	 * @param op
	 * @return
	 */
	public IInteger gcd(IInteger op);

	/**
	 * Arithmetic AND
	 * 
	 * @param op
	 * @return
	 */
	public IInteger logand(IInteger op);

	/**
	 * Arithmetic NAND
	 * 
	 * @param op
	 * @return
	 */
	public IInteger lognand(IInteger op);

	/**
	 * Arithmetic NAND COMPLEMENT 1
	 * 
	 * @param op
	 * @return
	 */
	public IInteger logandc1(IInteger op);

	/**
	 * Arithmetic AND COMPLEMENT 2
	 * 
	 * @param op
	 * @return
	 */
	public IInteger logandc2(IInteger op);

	/**
	 * Arithmetic OR
	 * 
	 * @param op
	 * @return
	 */
	public IInteger logior(IInteger op);

	/**
	 * Arithmetic OR COMPLEMENT 1
	 * 
	 * @param op
	 * @return
	 */
	public IInteger logorc1(IInteger op);

	/**
	 * Arithmetic OR COMPLEMENT 2
	 * 
	 * @param op
	 * @return
	 */
	public IInteger logorc2(IInteger op);

	/**
	 * Arithmetic XOR
	 * 
	 * @param op
	 * @return
	 */
	public IInteger logxor(IInteger op);

	/**
	 * Arithmetic EQUIVALENCE
	 * 
	 * @param op
	 * @return
	 */
	public IInteger logeqv(IInteger op);

	/**
	 * Arithmetic NOT (complement to 0)
	 * 
	 * @return
	 */
	public IInteger lognot();

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
	 * Arithmetic bit pattern test
	 * 
	 * @param op
	 * @return
	 */
	public IInteger logtest(IInteger op);

	/**
	 * Arithmetic bit count
	 * 
	 * @param op
	 * @return
	 */
	public IInteger logcount(IInteger op);

	/**
	 * Arithmetic bit test
	 * 
	 * @param op
	 * @return
	 */
	public IInteger logbitp(IInteger op);

	/**
	 * Arithmetic bit shift
	 * 
	 * @param count
	 * @return
	 */
	public IInteger ash(IInteger count);

	/**
	 * @return
	 */
	public boolean evenp();

	/**
	 * @return
	 */
	public boolean oddp();

	/**
	 * @return
	 */
	public IInteger isqrt();

	/**
	 * @return
	 */
	public IInteger integer_length();

	
}
