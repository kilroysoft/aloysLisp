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

import aloyslisp.core.plugs.tT;

/**
 * tNUMBER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tNUMBER
{
	/* *******************************************************************
	 * CONVERTORS
	 */

	/**
	 * Convert to variable type if of higher weight
	 * 
	 * @param var
	 * @return
	 */
	public tNUMBER coerce(tNUMBER var);

	/**
	 * Convert to ratio
	 * 
	 * @return
	 */
	public RATIO getRatioValue();

	/**
	 * Convert to complex
	 * 
	 * @return
	 */
	public COMPLEX getComplexValue();

	/**
	 * Convert to integer
	 * 
	 * @return
	 */
	public BIGNUM getIntegerValue();

	/**
	 * Convert to float
	 * 
	 * @return
	 */
	public SINGLE_FLOAT getFloatValue();

	/**
	 * Convert to double
	 * 
	 * @return
	 */
	public DOUBLE_FLOAT getDoubleValue();

	/**
	 * Convert to short
	 * 
	 * @return
	 */
	public SHORT_FLOAT getShortValue();

	/* *******************************************************************
	 * OPERATORS
	 */
	/**
	 * Add a + b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER add(tNUMBER op);

	/**
	 * Substract a - b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER substract(tNUMBER op);

	/**
	 * Minus -a
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER minus();

	/**
	 * Inversion 1/a
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER inversion();

	/**
	 * Multiply a * b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER multiply(tNUMBER op);

	/**
	 * Division a / b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER division(tNUMBER op);

	/* *******************************************************************
	 * ACCESSORS
	 */
	/**
	 * /**
	 * Real value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER realpart();

	/**
	 * Imaginary part (for complex) a + bi -> b
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER imagpart();

	/* *******************************************************************
	 * FUNCTIONS COMPLEX
	 */
	/**
	 * COMPLEX conjugate a + bi -> a - bi
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER conjugate();

	/**
	 * Polar value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER phase();

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Absolute value ||a||, mod for complex
	 * 
	 * @return
	 */
	public tREAL abs();

	/**
	 * @return
	 */
	public boolean zerop();

	/**
	 * @return
	 */
	public tNUMBER cis();

	/**
	 * @return
	 */
	public tNUMBER sin();

	/**
	 * @return
	 */
	public tNUMBER cos();

	/**
	 * @return
	 */
	public tNUMBER tan();

	/**
	 * @return
	 */
	public tNUMBER asin();

	/**
	 * @return
	 */
	public tNUMBER acos();

	/**
	 * @return
	 */
	public tNUMBER atan();

	/**
	 * @param opt
	 * @return
	 */
	public tNUMBER atan(tREAL opt);

	/**
	 * @return
	 */
	public tNUMBER sinh();

	/**
	 * @return
	 */
	public tNUMBER cosh();

	/**
	 * @return
	 */
	public tNUMBER tanh();

	/**
	 * @return
	 */
	public tNUMBER asinh();

	/**
	 * @return
	 */
	public tNUMBER acosh();

	/**
	 * @return
	 */
	public tNUMBER atanh();

	/**
	 * @return
	 */
	public tNUMBER log();

	/**
	 * @return
	 */
	public tNUMBER sqrt();

	/**
	 * @return
	 */
	public tNUMBER exp();

	/**
	 * @param power
	 * @return
	 */
	public tNUMBER expt(tNUMBER power);

	/**
	 * @return
	 */
	public tNUMBER random();

	/**
	 * @return
	 */
	public tNUMBER random(tRANDOM_STATE st);

	/****************************************************************
	 * LISP FUNCTIONS
	 */

	/* *******************************************************************
	 * OPERATORS
	 */
	/**
	 * Add a + b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER ADD(tT op);

	/**
	 * Substract a - b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER SUBSTRACT(tT op);

	/**
	 * Minus -a
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER MINUS();

	/**
	 * Inversion 1/a
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER INVERSION();

	/**
	 * Multiply a * b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER MULTIPLY(tT op);

	/**
	 * Division a / b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public tNUMBER DIVISION(tT op);

	/* *******************************************************************
	 * ACCESSORS
	 */
	/**
	 * /**
	 * Real value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER REALPART();

	/**
	 * Imaginary part (for complex) a + bi -> b
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER IMAGPART();

	/* *******************************************************************
	 * FUNCTIONS COMPLEX
	 */
	/**
	 * COMPLEX conjugate a + bi -> a - bi
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER CONJUGATE();

	/**
	 * Polar value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER PHASE();

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Absolute value ||a||, mod for complex
	 * 
	 * @return
	 */
	public tREAL ABS();

	/**
	 * @return
	 */
	public boolean ZEROP();

	/**
	 * @return
	 */
	public tNUMBER CIS();

	/**
	 * @return
	 */
	public tNUMBER SIN();

	/**
	 * @return
	 */
	public tNUMBER COS();

	/**
	 * @return
	 */
	public tNUMBER TAN();

	/**
	 * @return
	 */
	public tNUMBER ASIN();

	/**
	 * @return
	 */
	public tNUMBER ACOS();

	/**
	 * @return
	 */
	public tNUMBER ATAN();

	/**
	 * @param opt
	 * @return
	 */
	public tNUMBER ATAN(tREAL opt);

	/**
	 * @return
	 */
	public tNUMBER SINH();

	/**
	 * @return
	 */
	public tNUMBER COSH();

	/**
	 * @return
	 */
	public tNUMBER TANH();

	/**
	 * @return
	 */
	public tNUMBER ASINH();

	/**
	 * @return
	 */
	public tNUMBER ACOSH();

	/**
	 * @return
	 */
	public tNUMBER ATANH();

	/**
	 * @return
	 */
	public tNUMBER LOG();

	/**
	 * @return
	 */
	public tNUMBER SQRT();

	/**
	 * @return
	 */
	public tNUMBER EXP();

	/**
	 * @param power
	 * @return
	 */
	public tNUMBER EXPT(tNUMBER power);

	/**
	 * @return
	 */
	public tNUMBER RANDOM();

	/**
	 * @return
	 */
	public tNUMBER RANDOM(tRANDOM_STATE st);

}
