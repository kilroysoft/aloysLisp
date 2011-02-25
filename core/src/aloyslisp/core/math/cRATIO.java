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
// IP 15 oct. 2010 Creation
// BUG Math functions doesn't work
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import static aloyslisp.internal.engine.L.sym;
import aloyslisp.core.conditions.*;

/**
 * cRATIO
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cRATIO extends cRATIONAL implements tRATIO
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1362599486477845228L;

	/**
	 * 
	 */
	public tINTEGER				num;

	/**
	 * 
	 */
	public tINTEGER				den;

	/**
	 * @param num
	 * @param den
	 */
	public cRATIO(tINTEGER num, tINTEGER den)
	{
		tINTEGER pgcd = num.GCD(den).getIntegerValue();
		this.num = num.DIVISION(pgcd).getIntegerValue();
		this.den = den.DIVISION(pgcd).getIntegerValue();
	}

	/**
	 * @param num
	 * @param den
	 */
	public cRATIO(tINTEGER num)
	{
		this.num = num;
		this.den = new cBIGNUM(1);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "" + num + "/" + den;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#integerValue()
	 */
	public cBIGNUM getIntegerValue()
	{
		if (!den.EQ(ONE))
			throw new ARITHMETIC_ERROR(this, sym("integer"));

		return num.getIntegerValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#floatValue()
	 */
	public cSINGLE_FLOAT getFloatValue()
	{
		return num.getFloatValue().DIVISION(den).getFloatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#doubleValue()
	 */
	public cDOUBLE_FLOAT getDoubleValue()
	{
		return num.getDoubleValue().DIVISION(den).getDoubleValue();
	}

	/**
	 * @return
	 */
	public cRATIO getRatioValue()
	{
		return this;
	}

	/**
	 * @return
	 */
	public cCOMPLEX getComplexValue()
	{
		return new cCOMPLEX(this, ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#shortValue()
	 */
	@Override
	public cSHORT_FLOAT getShortValue()
	{
		return num.getShortValue().DIVISION(den).getShortValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cRATIONAL#getRationalValue()
	 */
	@Override
	public tRATIONAL rationalizeValue()
	{
		if (den.EQUALNUM(ONE))
			return num;

		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#coerce(aloyslisp.core.math.tNUMBER)
	 */
	public cNUMBER coerce(tNUMBER var)
	{
		if (var instanceof cSINGLE_FLOAT)
			return getFloatValue();

		if (var instanceof cDOUBLE_FLOAT)
			return getDoubleValue();

		if (var instanceof cSHORT_FLOAT)
			return getShortValue();

		if (var instanceof cCOMPLEX)
			return getComplexValue();

		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#equal(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	boolean equalnum(tNUMBER op)
	{
		cRATIO op2 = op.getRatioValue();
		return ((tINTEGER) num.MULTIPLY(op2.den)).EQUALNUM(den
				.MULTIPLY(op2.num));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#greater(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	boolean greater(tREAL op)
	{
		cRATIO op2 = op.getRatioValue();
		return ((tINTEGER) num.MULTIPLY(op2.den))
				.GREATER(den.MULTIPLY(op2.num));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#lower(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	boolean lower(tREAL op)
	{
		cRATIO op2 = op.getRatioValue();
		return ((tINTEGER) num.MULTIPLY(op2.den)).LOWER(den.MULTIPLY(op2.num));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER add(tNUMBER op)
	{
		cRATIO op2 = op.getRatioValue();
		return new cRATIO((tINTEGER) num.MULTIPLY(op2.den).ADD(
				den.MULTIPLY(op2.num)), (tINTEGER) den.MULTIPLY(op2.den))
				.rationalizeValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER substract(tNUMBER op)
	{
		return ADD(op.MINUS());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#minus()
	 */
	@Override
	tNUMBER minus()
	{
		return new cRATIO((tINTEGER) num.MINUS(), den).rationalizeValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#inversion()
	 */
	@Override
	tNUMBER inversion()
	{
		return new cRATIO(den, num).rationalizeValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER multiply(tNUMBER op)
	{
		cRATIO op2 = op.getRatioValue();
		return new cRATIO((tINTEGER) num.MULTIPLY(op2.num),
				(tINTEGER) den.MULTIPLY(op2.den)).rationalizeValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#division(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER division(tNUMBER op)
	{
		return MULTIPLY(op.INVERSION());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#numerator()
	 */
	@Override
	tINTEGER numerator()
	{
		return num;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#denominator()
	 */
	@Override
	tINTEGER denominator()
	{
		return den;
	}

}
