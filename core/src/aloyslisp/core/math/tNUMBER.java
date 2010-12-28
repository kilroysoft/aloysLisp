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

import aloyslisp.core.annotations.*;
import aloyslisp.core.plugs.*;

/**
 * tNUMBER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tNUMBER extends tATOM
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
	@Function(name = "+")
	public tNUMBER ADD( //
			@Rest(name = "op") tT op);

	/**
	 * Substract a - b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@Function(name = "-")
	public tNUMBER SUBSTRACT( //
			@Rest(name = "op") tT op);

	/**
	 * Minus -a
	 * 
	 * @param a
	 * @return
	 */
	@Function(name = "%minus")
	public tNUMBER MINUS();

	/**
	 * Inversion 1/a
	 * 
	 * @param a
	 * @return
	 */
	@Function(name = "inversion")
	public tNUMBER INVERSION();

	/**
	 * Multiply a * b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@Function(name = "*")
	public tNUMBER MULTIPLY( //
			@Rest(name = "op") tT op);

	/**
	 * Division a / b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@Function(name = "/")
	public tNUMBER DIVISION( //
			@Rest(name = "op") tT op);

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
	@Function(name = "realpart")
	public tNUMBER REALPART();

	/**
	 * Imaginary part (for complex) a + bi -> b
	 * 
	 * @param a
	 * @return
	 */
	@Function(name = "imagpart")
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
	@Function(name = "conjugate")
	public tNUMBER CONJUGATE();

	/**
	 * Polar value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	@Function(name = "phase")
	public tNUMBER PHASE();

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Absolute value ||a||, mod for complex
	 * 
	 * @return
	 */
	@Function(name = "abs")
	public tREAL ABS();

	/**
	 * @return
	 */
	@Function(name = "zerop")
	public boolean ZEROP();

	/**
	 * @return
	 */
	@Function(name = "cis")
	public tNUMBER CIS();

	/**
	 * @return
	 */
	@Function(name = "sin")
	public tNUMBER SIN();

	/**
	 * @return
	 */
	@Function(name = "cos")
	public tNUMBER COS();

	/**
	 * @return
	 */
	@Function(name = "tan")
	public tNUMBER TAN();

	/**
	 * @return
	 */
	@Function(name = "asin")
	public tNUMBER ASIN();

	/**
	 * @return
	 */
	@Function(name = "acos")
	public tNUMBER ACOS();

	/**
	 * @param opt
	 * @return
	 */
	@Function(name = "atan")
	public tNUMBER ATAN( //
			@Opt(name = "opt") tT opt);

	/**
	 * @return
	 */
	@Function(name = "sinh")
	public tNUMBER SINH();

	/**
	 * @return
	 */
	@Function(name = "cosh")
	public tNUMBER COSH();

	/**
	 * @return
	 */
	@Function(name = "tanh")
	public tNUMBER TANH();

	/**
	 * @return
	 */
	@Function(name = "asinh")
	public tNUMBER ASINH();

	/**
	 * @return
	 */
	@Function(name = "acosh")
	public tNUMBER ACOSH();

	/**
	 * @return
	 */
	@Function(name = "atanh")
	public tNUMBER ATANH();

	/**
	 * @return
	 */
	@Function(name = "log")
	public tNUMBER LOG();

	/**
	 * @return
	 */
	@Function(name = "sqrt")
	public tNUMBER SQRT();

	/**
	 * @return
	 */
	@Function(name = "exp")
	public tNUMBER EXP();

	/**
	 * @param power
	 * @return
	 */
	@Function(name = "expt")
	public tNUMBER EXPT( //
			@Arg(name = "power") tNUMBER power);

	/**
	 * @return
	 */
	@Function(name = "random")
	public tNUMBER RANDOM( //
			@Opt(name = "state") tT st);

}
