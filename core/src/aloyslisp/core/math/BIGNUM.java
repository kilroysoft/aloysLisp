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

import java.math.BigInteger;

/**
 * BIGNUM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class BIGNUM extends INTEGER implements tBIG_INTEGER
{
	public BigInteger	val;

	/**
	 * @param init
	 */
	public BIGNUM(BigInteger init)
	{
		val = new BigInteger(init.toString());
	}

	/**
	 * @param init
	 */
	public BIGNUM(String init)
	{
		val = new BigInteger(init);
	}

	/**
	 * @param init
	 */
	public BIGNUM(String init, Integer radix)
	{
		val = new BigInteger(init, radix);
	}

	/**
	 * @param init
	 */
	public BIGNUM(Integer init)
	{
		val = new BigInteger(init.toString());
	}

	/**
	 * @param init
	 */
	public BIGNUM(Long init)
	{
		val = new BigInteger(init.toString());
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

	/**
	 * Get number from a string representation in base radix
	 * 
	 * @param val
	 * @param start
	 * @param end
	 * @param radix
	 * @param junk
	 * @return
	 */
	public static BIGNUM parse_integer(String val, Integer start, Integer end,
			Integer radix, Boolean junk)
	{
		return new BIGNUM(new BigInteger(val, radix));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ratioValue()
	 */
	@Override
	public RATIO getRatioValue()
	{
		return new RATIO(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#complexValue()
	 */
	@Override
	public COMPLEX getComplexValue()
	{
		return new COMPLEX(this, ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#integerValue()
	 */
	@Override
	public BIGNUM getIntegerValue()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#floatValue()
	 */
	@Override
	public SINGLE_FLOAT getFloatValue()
	{
		return new SINGLE_FLOAT(val.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#doubleValue()
	 */
	@Override
	public DOUBLE_FLOAT getDoubleValue()
	{
		return new DOUBLE_FLOAT(val.doubleValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#shortValue()
	 */
	@Override
	public SHORT_FLOAT getShortValue()
	{
		return new SHORT_FLOAT(val.shortValue());
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
	 * @see aloyslisp.core.math.tINTEGER#lcm(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER lcm(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#gcd(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER gcd(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logand(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER logand(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#lognand(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER lognand(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logandc1(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER logandc1(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logandc2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER logandc2(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logior(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER logior(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logorc1(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER logorc1(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logorc2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER logorc2(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logxor(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER logxor(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logeqv(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER logeqv(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#lognot()
	 */
	@Override
	public tINTEGER lognot()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#toBase(java.lang.Integer)
	 */
	@Override
	public String toBase(Integer radix)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logtest(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER logtest(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logcount(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER logcount(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logbitp(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER logbitp(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ash(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER ash(tINTEGER count)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#evenp()
	 */
	@Override
	public boolean evenp()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#oddp()
	 */
	@Override
	public boolean oddp()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#isqrt()
	 */
	@Override
	public tINTEGER isqrt()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#integer_length()
	 */
	@Override
	public tINTEGER integer_length()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#numerator()
	 */
	@Override
	public tINTEGER numerator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#denominator()
	 */
	@Override
	public tINTEGER denominator()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
