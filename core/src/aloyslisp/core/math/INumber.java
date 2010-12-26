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
 * INumber
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface INumber
{
	/* *******************************************************************
	 * CONVERTORS
	 */
	/**
	 * Convert to ratio
	 * 
	 * @return
	 */
	public NumRatio getRatioValue();

	/**
	 * Convert to complex
	 * 
	 * @return
	 */
	public NumComplex getComplexValue();

	/**
	 * Convert to integer
	 * 
	 * @return
	 */
	public NumInteger getIntegerValue();

	/**
	 * Convert to float
	 * 
	 * @return
	 */
	public NumFloat getFloatValue();

	/**
	 * Convert to double
	 * 
	 * @return
	 */
	public NumDouble getDoubleValue();

	/**
	 * Convert to short
	 * 
	 * @return
	 */
	public NumShort getShortValue();

	/* *******************************************************************
	 * FACTORY
	 */
	/**
	 * Create a tNUMBER object from a INumber
	 * 
	 * @return
	 */
	public tNUMBER make();

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
	public INumber add(INumber op);

	/**
	 * Substract a - b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public INumber substract(INumber op);

	/**
	 * Minus -a
	 * 
	 * @param a
	 * @return
	 */
	public INumber minus();

	/**
	 * Inversion 1/a
	 * 
	 * @param a
	 * @return
	 */
	public INumber inversion();

	/**
	 * Multiply a * b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public INumber multiply(INumber op);

	/**
	 * Division a / b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public INumber division(INumber op);

	/**
	 * Integer division truncate(a / b)
	 * Semms not to be used in LISP standard. Use ceil, floor, truncate, round
	 * instead
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public INumber divide(INumber op);

	/**
	 * Modulo a mod b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public INumber mod(INumber op);

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
	public INumber realpart();

	/**
	 * Imaginary part (for complex) a + bi -> b
	 * 
	 * @param a
	 * @return
	 */
	public INumber imagpart();

	/* *******************************************************************
	 * COMPARATORS
	 */
	/**
	 * Test equality a == b
	 * 
	 * @param op
	 * @return
	 */
	public boolean equal(INumber op);

	/**
	 * Test greater a > b
	 * 
	 * @param op
	 * @return
	 */
	public boolean greather(INumber op);

	/**
	 * Test lower a < b
	 * 
	 * @param op
	 * @return
	 */
	public boolean lower(INumber op);

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Absolute value ||a||, mod for complex
	 * 
	 * @param a
	 * @return
	 */
	public tNUMBER abs(INumber a);

	/* *******************************************************************
	 * FUNCTIONS COMPLEX
	 */
	/**
	 * NumComplex conjugate a + bi -> a - bi
	 * 
	 * @param a
	 * @return
	 */
	public INumber conjugate();

	/**
	 * Polar value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	public IFloat phase();

}
