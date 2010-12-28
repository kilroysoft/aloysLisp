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
	 * 
	 */
	public RATIO()
	{
		this.num = new BIGNUM(1);
		this.den = new BIGNUM(1);
	}

	/**
	 * @param num
	 * @param den
	 */
	public RATIO(tINTEGER num, tINTEGER den)
	{
		tINTEGER pgcd = num.gcd(den).getIntegerValue();
		this.num = num.division(pgcd).getIntegerValue();
		this.den = den.division(pgcd).getIntegerValue();
	}

	/**
	 * @param num
	 * @param den
	 */
	public RATIO(BIGNUM num)
	{
		this.num = num;
		this.den = new BIGNUM(1);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#coerce(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER coerce(tNUMBER var)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return num + "/" + den;
	}

	/**
	 * @return
	 */
	public tNUMBER ratio()
	{
		return num.division(den);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#integerValue()
	 */
	public BIGNUM getIntegerValue()
	{
		return ratio().getIntegerValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#floatValue()
	 */
	public SINGLE_FLOAT getFloatValue()
	{
		return ratio().getFloatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#doubleValue()
	 */
	public DOUBLE_FLOAT getDoubleValue()
	{
		return ratio().getDoubleValue();
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
		return ratio().getShortValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#equal(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public boolean equal(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#greater(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public boolean greater(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#lower(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public boolean lower(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#rational()
	 */
	@Override
	public tRATIONAL rational()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#rationalize()
	 */
	@Override
	public tRATIONAL rationalize()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER add(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER substract(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#minus()
	 */
	@Override
	public tNUMBER minus()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#inversion()
	 */
	@Override
	public tNUMBER inversion()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER multiply(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#division(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER division(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#numerator()
	 */
	@Override
	public BIGNUM numerator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#denominator()
	 */
	@Override
	public BIGNUM denominator()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
