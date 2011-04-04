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
// IP 23 déc. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import java.math.BigInteger;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.clos.tBUILT_IN_CLASS;

/**
 * tNUMBER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "number", doc = "t_number", typep = "numberp")
public interface tNUMBER extends tBUILT_IN_CLASS
{
	/****************************************************************
	 * LISP FUNCTIONS
	 */

	/* *******************************************************************
	 * CONVERTERS
	 */
	/**
	 * Convert to ratio
	 * 
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "coerce-to-ratio")
	public tRATIO COERCE_TO_RATIO();

	/**
	 * Convert to integer
	 * 
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "coerce-to-integer")
	public tINTEGER COERCE_TO_INTEGER();

	/**
	 * Convert to float
	 * 
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "coerce-to-single-float")
	public tSINGLE_FLOAT COERCE_TO_SINGLE_FLOAT();

	/**
	 * Convert to double
	 * 
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "coerce-to-double-float")
	public tDOUBLE_FLOAT COERCE_TO_DOUBLE_FLOAT();

	/**
	 * Convert to short
	 * 
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "coerce-to-short-float")
	public tSHORT_FLOAT COERCE_TO_SHORT_FLOAT();

	/**
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "coerce-to-complex")
	public tNUMBER COERCE_TO_COMPLEX();

	/**
	 * @param var
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "coerce-to-number")
	public tNUMBER COERCE_TO_NUMBER( //
			@aArg(name = "var") tNUMBER var);

	/**
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "bigint-value")
	public BigInteger BIGINT_VALUE();

	/**
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "integer-value")
	public Integer INTEGER_VALUE();

	/**
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "long-value")
	public Long LONG_VALUE();

	/**
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "double-value")
	public Double DOUBLE_VALUE();

	/**
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "float-value")
	public Float FLOAT_VALUE();

	/**
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "rationalize-value")
	public tRATIONAL RATIONALIZE_VALUE();

	/**
	 * Add a + b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-add")
	public tNUMBER SINGLE_ADD( //
			@aArg(name = "op") tNUMBER op);

	/**
	 * Division a / b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-divition")
	public tNUMBER SINGLE_DIVISION( //
			@aArg(name = "op") tNUMBER op);

	/**
	 * Test equality a == b
	 * 
	 * @param op
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-equalnum", doc = "f_eq_sle")
	public boolean SINGLE_EQUALNUM( //
			@aArg(name = "op") tNUMBER op);

	/**
	 * Multiply a * b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-multiply")
	public abstract tNUMBER SINGLE_MULTIPLY( //
			@aArg(name = "op") tNUMBER op);

	/**
	 * Substract a - b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-substract")
	public tNUMBER SINGLE_SUBSTRACT( //
			@aArg(name = "op") tNUMBER op);

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Absolute value ||a||, mod for complex
	 * 
	 * @return
	 */
	@aFunction(name = "abs", doc = "f_abs")
	public tREAL ABS();

	/**
	 * @return
	 */
	@aFunction(name = "acos", doc = "f_asin_")
	public tNUMBER ACOS();

	/**
	 * @return
	 */
	@aFunction(name = "acosh", doc = "f_sinh_")
	public tNUMBER ACOSH();

	/**
	 * Add a + b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@aFunction(name = "+", doc = "f_pl")
	public tNUMBER ADD( //
			@aRest(name = "op") tT op);

	/**
	 * @return
	 */
	@aFunction(name = "asin", doc = "f_asin_")
	public tNUMBER ASIN();

	/**
	 * @return
	 */
	@aFunction(name = "asinh", doc = "f_sinh_")
	public tNUMBER ASINH();

	/**
	 * @param opt
	 * @return
	 */
	@aFunction(name = "atan", doc = "f_asin_")
	public tNUMBER ATAN( //
			@aOpt(name = "opt") tT opt);

	/**
	 * @return
	 */
	@aFunction(name = "atanh", doc = "f_sinh_")
	public tNUMBER ATANH();

	/* *******************************************************************
	 * FUNCTIONS cCOMPLEX
	 */
	/**
	 * cCOMPLEX conjugate a + bi -> a - bi
	 * 
	 * @param a
	 * @return
	 */
	@aFunction(name = "conjugate", doc = "f_conjug")
	public tNUMBER CONJUGATE();

	/**
	 * @return
	 */
	@aFunction(name = "cos", doc = "f_sin_c")
	public tNUMBER COS();

	/**
	 * @return
	 */
	@aFunction(name = "cosh", doc = "f_sinh_")
	public tNUMBER COSH();

	/**
	 * Division a / b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@aFunction(name = "/", doc = "f_sl")
	public tNUMBER DIVISION( //
			@aRest(name = "op") tT op);

	/**
	 * Test equality a == b
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "=", doc = "f_eq_sle")
	public boolean EQUALNUM( //
			@aRest(name = "op") tT op);

	/**
	 * @return
	 */
	@aFunction(name = "exp", doc = "f_exp_e")
	public tNUMBER EXP();

	/**
	 * @param power
	 * @return
	 */
	@aFunction(name = "expt", doc = "f_exp_e")
	public tNUMBER EXPT( //
			@aArg(name = "power") tNUMBER power);

	/**
	 * Imaginary part (for complex) a + bi -> b
	 * 
	 * @param a
	 * @return
	 */
	@aFunction(name = "imagpart", doc = "f_realpa")
	public tNUMBER IMAGPART();

	/**
	 * Inversion 1/a
	 * 
	 * @param a
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "%inversion")
	public tNUMBER INVERSION();

	/**
	 * @return
	 */
	@aFunction(name = "log", doc = "f_log")
	public tNUMBER LOG();

	/**
	 * log base n
	 * 
	 * @param base
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "log-base")
	tNUMBER LOG_BASE( //
			@aArg(name = "base") tREAL base);

	/**
	 * Minus -a
	 * 
	 * @param a
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "%minus")
	public tNUMBER MINUS();

	/**
	 * Multiply a * b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@aFunction(name = "*", doc = "f_st")
	public tNUMBER MULTIPLY( //
			@aRest(name = "op") tT op);

	/**
	 * Polar value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	@aFunction(name = "phase", doc = "f_phase")
	public tNUMBER PHASE();

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
	@aFunction(name = "realpart", doc = "f_realpa")
	public tNUMBER REALPART();

	/**
	 * @return
	 */
	@aFunction(name = "sin", doc = "f_sin_c")
	public tNUMBER SIN();

	/**
	 * @return
	 */
	@aFunction(name = "sinh", doc = "f_sinh_")
	public tNUMBER SINH();

	/**
	 * @return
	 */
	@aFunction(name = "sqrt", doc = "f_sqrt_")
	public tNUMBER SQRT();

	/**
	 * Substract a - b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@aFunction(name = "-", doc = "f__")
	public tNUMBER SUBSTRACT( //
			@aRest(name = "op") tT op);

	/**
	 * @return
	 */
	@aFunction(name = "tan", doc = "f_sin_c")
	public tNUMBER TAN();

	/**
	 * @return
	 */
	@aFunction(name = "tanh", doc = "f_sinh_")
	public tNUMBER TANH();

	/**
	 * @return
	 */
	@aFunction(name = "zerop", doc = "f_zerop")
	public boolean ZEROP();

}
