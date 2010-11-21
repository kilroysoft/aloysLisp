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
// IP 8 sept. 2010 Creation
// IMPLEMENT complete interface for numerical model
// --------------------------------------------------------------------------

package aloyslisp.core.types;

import aloyslisp.core.common.NumComplex;
import aloyslisp.core.common.NumRatio;

/**
 * IValue
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tNUMBER extends tATOM
{
	/**
	 * Get the numeric object
	 * 
	 * @return Value of PValue
	 */
	public Number getValue();

	/**
	 * @return
	 */
	public tNUMBER clone();

	/**
	 * Get the weight of the value Class
	 * 
	 * @return
	 */
	public Integer getWeight();

	/**
	 * Returns the value of the specified number as an <code>NumComplex</code>.
	 * This may involve rounding or truncation.
	 * 
	 * @return the numeric value represented by this object after conversion
	 *         to type <code>NumComplex</code>.
	 */
	public abstract NumComplex complexValue();

	/**
	 * Returns the value of the specified number as a <code>NumRatio</code>.
	 * This may involve rounding or truncation.
	 * 
	 * @return the numeric value represented by this object after conversion
	 *         to type <code>NumRatio</code>.
	 */
	public abstract NumRatio ratioValue();

	/**
	 * Returns the value of the specified number as an <code>int</code>.
	 * This may involve rounding or truncation.
	 * 
	 * @return the numeric value represented by this object after conversion
	 *         to type <code>int</code>.
	 */
	public abstract int intValue();

	/**
	 * Returns the value of the specified number as a <code>long</code>.
	 * This may involve rounding or truncation.
	 * 
	 * @return the numeric value represented by this object after conversion
	 *         to type <code>long</code>.
	 */
	public abstract long longValue();

	/**
	 * Returns the value of the specified number as a <code>float</code>.
	 * This may involve rounding.
	 * 
	 * @return the numeric value represented by this object after conversion
	 *         to type <code>float</code>.
	 */
	public abstract float floatValue();

	/**
	 * Returns the value of the specified number as a <code>double</code>.
	 * This may involve rounding.
	 * 
	 * @return the numeric value represented by this object after conversion
	 *         to type <code>double</code>.
	 */
	public abstract double doubleValue();

	/**
	 * Returns the value of the specified number as a <code>byte</code>.
	 * This may involve rounding or truncation.
	 * 
	 * @return the numeric value represented by this object after conversion
	 *         to type <code>byte</code>.
	 * @since JDK1.1
	 */
	public byte byteValue();

	/**
	 * Returns the value of the specified number as a <code>short</code>.
	 * This may involve rounding or truncation.
	 * 
	 * @return the numeric value represented by this object after conversion
	 *         to type <code>short</code>.
	 * @since JDK1.1
	 */
	public short shortValue();

	public tNUMBER acos();

	public tNUMBER asin();

	public tNUMBER atan();

	public tNUMBER atan(tNUMBER b);

	public tNUMBER ceiling();

	public tNUMBER truncate();

	public tNUMBER cos();

	public tNUMBER cis();

	public tNUMBER exp();

	public tNUMBER floor();

	public tNUMBER IEEEremainder(tNUMBER b);

	public tNUMBER log();

	public tNUMBER expt(tNUMBER b);

	public tNUMBER random();

	public tNUMBER sin();

	public tNUMBER sqrt();

	public tNUMBER tan();

	public tNUMBER toDegrees();

	public tNUMBER toRadians();

	public tNUMBER add(tT a);

	public tNUMBER substract(tT a);

	public tNUMBER minus();

	public tNUMBER inversion();

	public tNUMBER multiply(tT a);

	public tNUMBER division(tT a);

	public tNUMBER divide(tT a);

	public tNUMBER mod(tT a);

	public tNUMBER conjugate();

	public tNUMBER real();

	public tNUMBER phase();

	public tNUMBER imag();

	public tNUMBER numerator();

	public tNUMBER denominator();

	public tNUMBER lcm(tT a);

	public tNUMBER gcd(tT a);

	public tNUMBER logand(tT a);

	public tNUMBER logior(tT a);

	public tNUMBER logxor(tT a);

	public tNUMBER lognot();

	public String toBase(int base);

	public tNUMBER fromBase(String a, Integer base);

	public boolean equality(tNUMBER a);

	public boolean greather(tNUMBER a);

	public boolean greatherEqual(tNUMBER a);

	public boolean lower(tNUMBER a);

	public boolean lowerEqual(tNUMBER a);

	public tNUMBER abs();

	public tNUMBER max(tT a);

	public tNUMBER min(tT a);

	public tNUMBER round();

}
