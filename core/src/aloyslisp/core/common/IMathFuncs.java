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
// IP 12 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.common;

import aloyslisp.core.types.*;

/**
 * IMathFuncs
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface IMathFuncs
{
	/**
	 * Add a + b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER add(tNUMBER a, tNUMBER b);

	/**
	 * Substract a - b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER substract(tNUMBER a, tNUMBER b);

	/**
	 * Minus -a
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER minus(tNUMBER a);

	/**
	 * Inversion 1/a
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER inversion(tNUMBER a);

	/**
	 * Multiply a * b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER multiply(tNUMBER a, tNUMBER b);

	/**
	 * Division a / b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER division(tNUMBER a, tNUMBER b);

	/**
	 * Integer division truncate(a / b)
	 * Semms not to be used in LISP standard. Use ceil, floor, truncate, round
	 * instead
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER divide(tNUMBER a, tNUMBER b);

	/**
	 * Modulo a mod b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER mod(tNUMBER a, tNUMBER b);

	/**
	 * NumComplex conjugate a + bi -> a - bi
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER conjugate(tNUMBER a);

	/**
	 * Real value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER realpart(tNUMBER a);

	/**
	 * Polar value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER phase(tNUMBER a);

	/**
	 * Imaginary part (for complex) a + bi -> b
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER imagpart(tNUMBER a);

	/**
	 * Numerator (for ratio) a/b -> a
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER numerator(tNUMBER a);

	/**
	 * Denumerator (for ratio) a/b -> b
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER denominator(tNUMBER a);

	/**
	 * Less common multiplicand
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER lcm(tNUMBER a, tNUMBER b);

	/**
	 * Greather common denumerator
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER gcd(tNUMBER a, tNUMBER b);

	/**
	 * Arithmetic AND
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER logand(tNUMBER a, tNUMBER b);

	/**
	 * Arithmetic OR
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER logior(tNUMBER a, tNUMBER b);

	/**
	 * Arithmetic XOR
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER logxor(tNUMBER a, tNUMBER b);

	/**
	 * Arithmetic NOT (complement to 0)
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER lognot(tNUMBER a);

	/**
	 * Get string representation of number with base radix (for integers)
	 * 
	 * @param a
	 * @param base
	 * @return
	 */
	public String toBase(tNUMBER a, Integer base);

	/**
	 * Get number from a string representation in base radix
	 * 
	 * @param a
	 * @param base
	 * @return
	 */
	public tNUMBER fromBase(String a, Integer base);

	/**
	 * Test equality a == b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean equal(tNUMBER a, tNUMBER b);

	/**
	 * Test greater a > b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean greather(tNUMBER a, tNUMBER b);

	/**
	 * Test greater or equal a >= b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean greatherEqual(tNUMBER a, tNUMBER b);

	/**
	 * Test lower a < b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean lower(tNUMBER a, tNUMBER b);

	/**
	 * Test lower or equal a <= b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean lowerEqual(tNUMBER a, tNUMBER b);

	/**
	 * Absolute value ||a||, mod for complex
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER abs(tNUMBER a);

	/**
	 * Maximum value
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER max(tNUMBER a, tNUMBER b);

	/**
	 * Minimum value
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER min(tNUMBER a, tNUMBER b);

	/**
	 * Round
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER round(tNUMBER a);

}
