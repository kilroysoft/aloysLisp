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
// IP 15 oct. 2010-2011 Creation
// BUG Math functions doesn't work
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import aloyslisp.annotations.aBuiltIn;
import aloyslisp.core.conditions.*;
import static aloyslisp.core.L.*;

/**
 * cRATIO
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aBuiltIn(lispClass = "ratio", doc = "t_ratio")
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
		tINTEGER pgcd = num.GCD(den).COERCE_TO_INTEGER();
		this.num = num.DIVISION(pgcd).COERCE_TO_INTEGER();
		this.den = den.DIVISION(pgcd).COERCE_TO_INTEGER();
	}

	/**
	 * @param num
	 * @param den
	 */
	public cRATIO(tINTEGER num)
	{
		this.num = num;
		this.den = new cINTEGER(1);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#TO_STRING()
	 */
	public String TO_STRING()
	{
		return "" + num + "/" + den;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cREAL#COERCE_TO_COMPLEX()
	 */
	public cCOMPLEX COERCE_TO_COMPLEX()
	{
		return new cCOMPLEX(this, ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_DOUBLE_FLOAT()
	 */
	public tDOUBLE_FLOAT COERCE_TO_DOUBLE_FLOAT()
	{
		return num.COERCE_TO_DOUBLE_FLOAT().DIVISION(den)
				.COERCE_TO_DOUBLE_FLOAT();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_INTEGER()
	 */
	public tINTEGER COERCE_TO_INTEGER()
	{
		if (!den.EQ(ONE))
			throw new ARITHMETIC_ERROR(this, sym("integer"));

		return num.COERCE_TO_INTEGER();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_RATIO()
	 */
	public cRATIO COERCE_TO_RATIO()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_SHORT_FLOAT()
	 */
	public tSHORT_FLOAT COERCE_TO_SHORT_FLOAT()
	{
		return num.COERCE_TO_SHORT_FLOAT().DIVISION(den)
				.COERCE_TO_SHORT_FLOAT();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_SINGLE_FLOAT()
	 */
	public tSINGLE_FLOAT COERCE_TO_SINGLE_FLOAT()
	{
		return num.COERCE_TO_SINGLE_FLOAT().DIVISION(den)
				.COERCE_TO_SINGLE_FLOAT();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#coerce(aloyslisp.core.math.tNUMBER)
	 */
	public tNUMBER COERCE_TO_NUMBER(tNUMBER var)
	{
		if (var instanceof cSINGLE_FLOAT)
			return COERCE_TO_SINGLE_FLOAT();
	
		if (var instanceof cDOUBLE_FLOAT)
			return COERCE_TO_DOUBLE_FLOAT();
	
		if (var instanceof cSHORT_FLOAT)
			return COERCE_TO_SHORT_FLOAT();
	
		if (var instanceof cCOMPLEX)
			return COERCE_TO_COMPLEX();
	
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cRATIONAL#rationalizeValue()
	 */
	public tRATIONAL RATIONALIZE_VALUE()
	{
		if (den.EQUALNUM(ONE))
			return num;
	
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#division(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_DIVISION(tNUMBER op)
	{
		return MULTIPLY(op.INVERSION());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_ADD(tNUMBER op)
	{
		tRATIO op2 = op.COERCE_TO_RATIO();
		return new cRATIO((tINTEGER) num.MULTIPLY(op2.DENOMINATOR()).ADD(
				den.MULTIPLY(op2.NUMERATOR())), (tINTEGER) den.MULTIPLY(op2
				.DENOMINATOR())).RATIONALIZE_VALUE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#equal(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public boolean SINGLE_EQUALNUM(tNUMBER op)
	{
		tRATIO op2 = op.COERCE_TO_RATIO();
		return ((tINTEGER) num.MULTIPLY(op2.DENOMINATOR())).EQUALNUM(den
				.MULTIPLY(op2.NUMERATOR()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#greater(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public boolean SINGLE_GREATER(tREAL op)
	{
		tRATIO op2 = op.COERCE_TO_RATIO();
		return ((tINTEGER) num.MULTIPLY(op2.DENOMINATOR())).GREATER(den
				.MULTIPLY(op2.NUMERATOR()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#lower(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public boolean SINGLE_LOWER(tREAL op)
	{
		tRATIO op2 = op.COERCE_TO_RATIO();
		return ((tINTEGER) num.MULTIPLY(op2.DENOMINATOR())).LOWER(den
				.MULTIPLY(op2.NUMERATOR()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_MULTIPLY(tNUMBER op)
	{
		tRATIO op2 = op.COERCE_TO_RATIO();
		return new cRATIO((tINTEGER) num.MULTIPLY(op2.NUMERATOR()),
				(tINTEGER) den.MULTIPLY(op2.DENOMINATOR())).RATIONALIZE_VALUE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_SUBSTRACT(tNUMBER op)
	{
		return ADD(op.MINUS());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#denominator()
	 */
	@Override
	public tINTEGER DENOMINATOR()
	{
		return den;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#inversion()
	 */
	@Override
	public tNUMBER INVERSION()
	{
		return new cRATIO(den, num).RATIONALIZE_VALUE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#minus()
	 */
	@Override
	public tNUMBER MINUS()
	{
		return new cRATIO((tINTEGER) num.MINUS(), den).RATIONALIZE_VALUE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#numerator()
	 */
	@Override
	public tINTEGER NUMERATOR()
	{
		return num;
	}

}
