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

import static aloyslisp.commonlisp.L.sym;
import aloyslisp.core.conditions.*;

/**
 * RATIO
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class RATIO extends RATIONAL implements tRATIO
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
	public RATIO(tINTEGER num, tINTEGER den)
	{
		tINTEGER pgcd = num.GCD(den).getIntegerValue();
		this.num = num.DIVISION(pgcd).getIntegerValue();
		this.den = den.DIVISION(pgcd).getIntegerValue();
	}

	/**
	 * @param num
	 * @param den
	 */
	public RATIO(tINTEGER num)
	{
		this.num = num;
		this.den = new BIGNUM(1);
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
	public BIGNUM getIntegerValue()
	{
		if (!den.EQ(ONE))
			throw new ARITHMETIC_ERROR(this, sym("integer"));

		return num.getIntegerValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#floatValue()
	 */
	public SINGLE_FLOAT getFloatValue()
	{
		return num.getFloatValue().DIVISION(den).getFloatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#doubleValue()
	 */
	public DOUBLE_FLOAT getDoubleValue()
	{
		return num.getDoubleValue().DIVISION(den).getDoubleValue();
	}

	/**
	 * @return
	 */
	public RATIO getRatioValue()
	{
		return this;
	}

	/**
	 * @return
	 */
	public COMPLEX getComplexValue()
	{
		return new COMPLEX(this, ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#shortValue()
	 */
	@Override
	public SHORT_FLOAT getShortValue()
	{
		return num.getShortValue().DIVISION(den).getShortValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.RATIONAL#getRationalValue()
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
	public NUMBER coerce(tNUMBER var)
	{
		if (var instanceof SINGLE_FLOAT)
			return getFloatValue();

		if (var instanceof DOUBLE_FLOAT)
			return getDoubleValue();

		if (var instanceof SHORT_FLOAT)
			return getShortValue();

		if (var instanceof COMPLEX)
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
		RATIO op2 = op.getRatioValue();
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
		RATIO op2 = op.getRatioValue();
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
		RATIO op2 = op.getRatioValue();
		return ((tINTEGER) num.MULTIPLY(op2.den)).LOWER(den.MULTIPLY(op2.num));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER add(tNUMBER op)
	{
		RATIO op2 = op.getRatioValue();
		return new RATIO((tINTEGER) num.MULTIPLY(op2.den).ADD(
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
		return new RATIO((tINTEGER) num.MINUS(), den).rationalizeValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#inversion()
	 */
	@Override
	tNUMBER inversion()
	{
		return new RATIO(den, num).rationalizeValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER multiply(tNUMBER op)
	{
		RATIO op2 = op.getRatioValue();
		return new RATIO((tINTEGER) num.MULTIPLY(op2.num),
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
