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

import aloyslisp.core.math.*;
import aloyslisp.core.annotations.*;

/**
 * IValue
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tNUMBER extends tATOM, INumber
{
	/**
	 * Get the numeric object
	 * 
	 * @return Value of PValue
	 */
	public INumber getValue();

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
	public abstract NumInteger integerValue();

	/**
	 * Returns the value of the specified number as a <code>float</code>.
	 * This may involve rounding.
	 * 
	 * @return the numeric value represented by this object after conversion
	 *         to type <code>float</code>.
	 */
	public abstract NumFloat floatValue();

	/**
	 * Returns the value of the specified number as a <code>double</code>.
	 * This may involve rounding.
	 * 
	 * @return the numeric value represented by this object after conversion
	 *         to type <code>double</code>.
	 */
	public abstract NumDouble doubleValue();

	/**
	 * Returns the value of the specified number as a <code>short</code>.
	 * This may involve rounding or truncation.
	 * 
	 * @return the numeric value represented by this object after conversion
	 *         to type <code>short</code>.
	 * @since JDK1.1
	 */
	public NumShort shortValue();

	/**
	 * Arc Cosine
	 * 
	 * @return
	 */
	@Function(name = "acos")
	public tNUMBER ACOS();

	/**
	 * Arc Sine
	 * 
	 * @return
	 */
	@Function(name = "asin")
	public tNUMBER ASIN();

	/**
	 * Arc Tangent
	 * 
	 * @return
	 */
	public tNUMBER ATAN();

	/**
	 * Arc Tangent
	 * 
	 * @param num2
	 * @return
	 */
	@Function(name = "atan")
	public tNUMBER ATAN( //
			@Opt(name = "number2") tNUMBER num2);

	/**
	 * Cosine
	 * 
	 * @return
	 */
	@Function(name = "cos")
	public tNUMBER COS();

	/**
	 * Complex value of argument part modulo 1 #C((cos val) (sin val))
	 * 
	 * @return
	 */
	@Function(name = "cis")
	public tNUMBER CIS();

	/**
	 * Exponential base e
	 * 
	 * @return
	 */
	@Function(name = "exp")
	public tNUMBER EXP();

	/**
	 * Logarithm
	 * 
	 * @return
	 *         TODO add optional base
	 */
	@Function(name = "log")
	public tNUMBER LOG();

	/**
	 * Power
	 * 
	 * @param b
	 * @return
	 */
	@Function(name = "expt")
	public tNUMBER EXPT( //
			@Arg(name = "power-number") tNUMBER power);

	/**
	 * Random value
	 * 
	 * @param randomState
	 * @return
	 */
	@Function(name = "random")
	public tNUMBER RANDOM( //
			@Opt(name = "random-state") tT randomState);

	/**
	 * Sine
	 * 
	 * @return
	 */
	@Function(name = "sin")
	public tNUMBER SIN();

	/**
	 * Square root
	 * 
	 * @return
	 */
	@Function(name = "sqrt")
	public tNUMBER SQRT();

	/**
	 * Tangent
	 * 
	 * @return
	 */
	@Function(name = "tan")
	public tNUMBER TAN();

	public tNUMBER toDegrees();

	public tNUMBER toRadians();

	/**
	 * Addition
	 * 
	 * @param a
	 * @return
	 */
	@Function(name = "+")
	public tNUMBER ADD( //
			@Rest(name = "operands") tT operands);

	/**
	 * Substraction
	 * 
	 * @param operands
	 * @return
	 */
	@Function(name = "-")
	public tNUMBER SUBSTRACT( //
			@Rest(name = "operands") tT operands);

	public tNUMBER minus();

	public tNUMBER inversion();

	/**
	 * Multiply
	 * 
	 * @param a
	 * @return
	 */
	@Function(name = "*")
	public tNUMBER MULTIPLY( //
			@Rest(name = "operands") tT operands);

	/**
	 * Division
	 * 
	 * @param operands
	 * @return
	 */
	@Function(name = "/")
	public tNUMBER DIVISION( //
			@Rest(name = "operands") tT operands);

	public tNUMBER divide(tT a);

	/**
	 * Modulus
	 * 
	 * @param operands
	 * @return
	 */
	@Function(name = "mod")
	public tNUMBER MOD( //
			@Rest(name = "operands") tT operands);

	public tNUMBER IEEEremainder(tNUMBER b);

	/**
	 * Conjugate
	 * 
	 * @return
	 */
	@Function(name = "conjugate")
	public tNUMBER CONJUGATE();

	/**
	 * Real part
	 * 
	 * @return
	 */
	@Function(name = "realpart")
	public tNUMBER REALPART();

	/**
	 * Phase of complex
	 * 
	 * @return
	 */
	@Function(name = "phase")
	public tNUMBER PHASE();

	/**
	 * Imaginary part
	 * 
	 * @return
	 */
	@Function(name = "imagpart")
	public tNUMBER IMAGPART();

	/**
	 * Numerator of rational value
	 * 
	 * @return
	 */
	@Function(name = "numerator")
	public tNUMBER NUMERATOR();

	/**
	 * Denominator
	 * 
	 * @return
	 */
	@Function(name = "denominator")
	public tNUMBER DENOMINATOR();

	/**
	 * Least common multiple
	 * 
	 * @param a
	 * @return
	 */
	@Function(name = "lcm")
	public tNUMBER LCM( //
			@Rest(name = "integers") tT integers);

	/**
	 * Greatest common denominator
	 * 
	 * @param integers
	 * @return
	 */
	@Function(name = "gcd")
	public tNUMBER GCD( //
			@Rest(name = "integers") tT integers);

	/**
	 * Logical and
	 * 
	 * @param integers
	 * @return
	 */
	@Function(name = "logand")
	public tNUMBER LOGAND( //
			@Rest(name = "integers") tT integers);

	/**
	 * Logical or
	 * 
	 * @param integers
	 * @return
	 */
	@Function(name = "logior")
	public tNUMBER LOGIOR( //
			@Rest(name = "integers") tT integers);

	/**
	 * Logical xor
	 * 
	 * @param integers
	 * @return
	 */
	@Function(name = "logxor")
	public tNUMBER LOGXOR( //
			@Rest(name = "integers") tT integers);

	/**
	 * Logical not
	 * 
	 * @return
	 */
	@Function(name = "lognot")
	public tNUMBER LOGNOT();

	public String toBase(int base);

	public tNUMBER fromBase(String a, Integer base);

	/**
	 * Equal numeric
	 * 
	 * @param integers
	 * @return
	 */
	@Function(name = "=")
	public boolean EQN( //
			@Rest(name = "ops") tT ops);

	/**
	 * Greater than
	 * 
	 * @param ops
	 * @return
	 */
	@Function(name = ">")
	public boolean GT( //
			@Rest(name = "ops") tT ops);

	/**
	 * Greater or equal
	 * 
	 * @param ops
	 * @return
	 */
	@Function(name = ">=")
	public boolean GE( //
			@Rest(name = "ops") tT ops);

	/**
	 * Lower
	 * 
	 * @param ops
	 * @return
	 */
	@Function(name = "<")
	public boolean LT( //
			@Rest(name = "ops") tT ops);

	/**
	 * Lower or equal
	 * 
	 * @param ops
	 * @return
	 */
	@Function(name = "<=")
	public boolean LE( //
			@Rest(name = "ops") tT ops);

	/**
	 * Absolute value
	 * 
	 * @param number
	 * @return
	 */
	@Function(name = "abs")
	public tNUMBER ABS();

	/**
	 * Maximum
	 * 
	 * @param ops
	 * @return
	 */
	@Function(name = "max")
	public tNUMBER MAX( //
			@Rest(name = "ops") tT ops);

	/**
	 * Minimum
	 * 
	 * @param ops
	 * @return
	 */
	@Function(name = "min")
	public tNUMBER MIN( //
			@Rest(name = "ops") tT ops);

	/********************************************************************************************
	 * TO BE IMPLEMENTED :
	 * 
	 * <= lisp
	 * >= lisp
	 * /= lisp
	 * minusp lisp
	 * plusp lisp
	 * zerop lisp
	 * ffloor lisp
	 * fceiling lisp
	 * ftruncate lisp
	 * fround lisp
	 * sinh
	 * cosh
	 * tanh
	 * asinh
	 * acosh
	 * atanh
	 * 1+ lisp
	 * 1- lisp
	 * evenp ->integer
	 * oddp ->integer
	 * incf macro
	 * decf macro
	 * rem
	 * signum rational, float, complex
	 * isqrt positive integer
	 * ash
	 * integer-length
	 * parse-integer
	 * boole
	 * logcount
	 * logbitp
	 * logtest
	 * byte
	 * byte-size
	 * byte-position
	 * deposit-field
	 * dpb
	 * ldb
	 * ldb-test
	 * mask-field
	 * 
	 * float
	 * rational
	 * rationalize
	 * complex
	 * 
	 * 
	 */

	/**
	 * Ceiling
	 * 
	 * @return
	 */
	@Function(name = "ceiling")
	public tNUMBER CEILING();

	/**
	 * Truncate
	 * 
	 * @return
	 */
	@Function(name = "truncate")
	public tNUMBER TRUNCATE();

	/**
	 * Round
	 * 
	 * @param number
	 * @return
	 */
	@Function(name = "round")
	public tNUMBER ROUND();

	/**
	 * Floor
	 * 
	 * @return
	 */
	@Function(name = "floor")
	public tNUMBER FLOOR();

}
