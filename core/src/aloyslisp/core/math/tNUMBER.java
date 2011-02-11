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
public interface tNUMBER extends tBUILD_IN_CLASS
{
	/* *******************************************************************
	 * CONVERTERS
	 */
	/**
	 * Convert to ratio
	 * 
	 * @return
	 */
	abstract cRATIO getRatioValue();

	/**
	 * Convert to complex
	 * 
	 * @return
	 */
	abstract cCOMPLEX getComplexValue();

	/**
	 * Convert to integer
	 * 
	 * @return
	 */
	abstract cBIGNUM getIntegerValue();

	/**
	 * Convert to float
	 * 
	 * @return
	 */
	abstract cSINGLE_FLOAT getFloatValue();

	/**
	 * Convert to double
	 * 
	 * @return
	 */
	abstract cDOUBLE_FLOAT getDoubleValue();

	/**
	 * Convert to short
	 * 
	 * @return
	 */
	abstract cSHORT_FLOAT getShortValue();

	/**
	 * @param var
	 * @return
	 */
	abstract public cNUMBER coerce(tNUMBER var);

	/****************************************************************
	 * LISP FUNCTIONS
	 */

	/* *******************************************************************
	 * OPERATORS
	 */
	/**
	 * Test equality a == b
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "=", doc = "f_eq_sle")
	public boolean EQUALNUM( //
			@Rest(name = "op") tT op);

	/**
	 * Add a + b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@Function(name = "+", doc = "f_pl")
	public tNUMBER ADD( //
			@Rest(name = "op") tT op);

	/**
	 * Substract a - b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@Function(name = "-", doc = "f__")
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
	@Function(name = "%inversion")
	public tNUMBER INVERSION();

	/**
	 * Multiply a * b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@Function(name = "*", doc = "f_st")
	public tNUMBER MULTIPLY( //
			@Rest(name = "op") tT op);

	/**
	 * Division a / b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@Function(name = "/", doc = "f_sl")
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
	@Function(name = "realpart", doc = "f_realpa")
	public tNUMBER REALPART();

	/**
	 * Imaginary part (for complex) a + bi -> b
	 * 
	 * @param a
	 * @return
	 */
	@Function(name = "imagpart", doc = "f_realpa")
	public tNUMBER IMAGPART();

	/* *******************************************************************
	 * FUNCTIONS cCOMPLEX
	 */
	/**
	 * cCOMPLEX conjugate a + bi -> a - bi
	 * 
	 * @param a
	 * @return
	 */
	@Function(name = "conjugate", doc = "f_conjug")
	public tNUMBER CONJUGATE();

	/**
	 * Polar value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	@Function(name = "phase", doc = "f_phase")
	public tNUMBER PHASE();

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Absolute value ||a||, mod for complex
	 * 
	 * @return
	 */
	@Function(name = "abs", doc = "f_abs")
	public tREAL ABS();

	/**
	 * @return
	 */
	@Function(name = "zerop", doc = "f_zerop")
	public boolean ZEROP();

	/**
	 * @return
	 */
	@Function(name = "sin", doc = "f_sin_c")
	public tNUMBER SIN();

	/**
	 * @return
	 */
	@Function(name = "cos", doc = "f_sin_c")
	public tNUMBER COS();

	/**
	 * @return
	 */
	@Function(name = "tan", doc = "f_sin_c")
	public tNUMBER TAN();

	/**
	 * @return
	 */
	@Function(name = "asin", doc = "f_asin_")
	public tNUMBER ASIN();

	/**
	 * @return
	 */
	@Function(name = "acos", doc = "f_asin_")
	public tNUMBER ACOS();

	/**
	 * @param opt
	 * @return
	 */
	@Function(name = "atan", doc = "f_asin_")
	public tNUMBER ATAN( //
			@Opt(name = "opt") tT opt);

	/**
	 * @return
	 */
	@Function(name = "sinh", doc = "f_sinh_")
	public tNUMBER SINH();

	/**
	 * @return
	 */
	@Function(name = "cosh", doc = "f_sinh_")
	public tNUMBER COSH();

	/**
	 * @return
	 */
	@Function(name = "tanh", doc = "f_sinh_")
	public tNUMBER TANH();

	/**
	 * @return
	 */
	@Function(name = "asinh", doc = "f_sinh_")
	public tNUMBER ASINH();

	/**
	 * @return
	 */
	@Function(name = "acosh", doc = "f_sinh_")
	public tNUMBER ACOSH();

	/**
	 * @return
	 */
	@Function(name = "atanh", doc = "f_sinh_")
	public tNUMBER ATANH();

	/**
	 * @return
	 */
	@Function(name = "log", doc = "f_log")
	public tNUMBER LOG();

	/**
	 * @return
	 */
	@Function(name = "sqrt", doc = "f_sqrt_")
	public tNUMBER SQRT();

	/**
	 * @return
	 */
	@Function(name = "exp", doc = "f_exp_e")
	public tNUMBER EXP();

	/**
	 * @param power
	 * @return
	 */
	@Function(name = "expt", doc = "f_exp_e")
	public tNUMBER EXPT( //
			@Arg(name = "power") tNUMBER power);

}
