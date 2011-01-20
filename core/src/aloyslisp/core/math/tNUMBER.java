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
	 * CONVERTERS
	 */
	/**
	 * Convert to ratio
	 * 
	 * @return
	 */
	abstract RATIO getRatioValue();

	/**
	 * Convert to complex
	 * 
	 * @return
	 */
	abstract COMPLEX getComplexValue();

	/**
	 * Convert to integer
	 * 
	 * @return
	 */
	abstract BIGNUM getIntegerValue();

	/**
	 * Convert to float
	 * 
	 * @return
	 */
	abstract SINGLE_FLOAT getFloatValue();

	/**
	 * Convert to double
	 * 
	 * @return
	 */
	abstract DOUBLE_FLOAT getDoubleValue();

	/**
	 * Convert to short
	 * 
	 * @return
	 */
	abstract SHORT_FLOAT getShortValue();

	/**
	 * @param var
	 * @return
	 */
	abstract public NUMBER coerce(tNUMBER var);

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
	@Function(name = "=")
	public boolean EQUALNUM( //
			@Rest(name = "op") tT op);

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

}
