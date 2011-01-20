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
	 * @see aloyslisp.core.plugs.CELL#toString()
	 */
	public String toString()
	{
		return val.toString(10);
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
	 * @see aloyslisp.core.math.RATIONAL#getRationalValue()
	 */
	public tRATIONAL rationalizeValue()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#coerce(aloyslisp.core.math.tNUMBER)
	 */
	public NUMBER coerce(tNUMBER var)
	{
		if (var instanceof RATIO)
			return getRatioValue();

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
	 * @see aloyslisp.core.math.tINTEGER#lcm(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER lcm(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#gcd(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER gcd(tINTEGER op)
	{
		return new BIGNUM(val.gcd(((BIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logand(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logand(tINTEGER op)
	{
		return new BIGNUM(val.and(((BIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#lognand(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER lognand(tINTEGER op)
	{
		return new BIGNUM(val.and(((BIGNUM) op).val).not());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logandc1(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logandc1(tINTEGER op)
	{
		return new BIGNUM(val.not().and(((BIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logandc2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logandc2(tINTEGER op)
	{
		return new BIGNUM(val.and(((BIGNUM) op).val.not()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logior(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logior(tINTEGER op)
	{
		return new BIGNUM(val.or(((BIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logorc1(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logorc1(tINTEGER op)
	{
		return new BIGNUM(val.not().or(((BIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logorc2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logorc2(tINTEGER op)
	{
		return new BIGNUM(val.or(((BIGNUM) op).val.not()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logxor(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logxor(tINTEGER op)
	{
		return new BIGNUM(val.xor(((BIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logeqv(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logeqv(tINTEGER op)
	{
		return new BIGNUM(val.xor(((BIGNUM) op).val).not());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#lognot()
	 */
	@Override
	tINTEGER lognot()
	{
		return new BIGNUM(val.not());
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
	 * @see aloyslisp.core.math.tINTEGER#toBase(java.lang.Integer)
	 */
	@Override
	String toBase(Integer radix)
	{
		return val.toString(radix);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logtest(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	Boolean logtest(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logcount()
	 */
	@Override
	tINTEGER logcount()
	{
		return new BIGNUM(val.bitCount());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logbitp(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	Boolean logbitp(tINTEGER op)
	{
		return val.testBit(op.getIntegerValue().val.intValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ash(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER ash(tINTEGER count)
	{
		if (count.MINUSP())
			return new BIGNUM(
					val.shiftRight(count.MINUS().getIntegerValue().val
							.intValue()));
		else
			return new BIGNUM(val.shiftLeft(count.getIntegerValue().val
					.intValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#evenp()
	 */
	@Override
	boolean evenp()
	{
		return val.mod(new BigInteger("2")).intValue() == 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#oddp()
	 */
	@Override
	boolean oddp()
	{
		return val.mod(new BigInteger("2")).intValue() != 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#isqrt()
	 */
	@Override
	tINTEGER isqrt()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#integer_length()
	 */
	@Override
	tINTEGER integer_length()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#numerator()
	 */
	@Override
	tINTEGER numerator()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#denominator()
	 */
	@Override
	tINTEGER denominator()
	{
		return ONE;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.REAL#greater(aloyslisp.core.math.tREAL)
	 */
	@Override
	boolean greater(tREAL op)
	{
		return val.compareTo(op.getIntegerValue().val) > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.REAL#lower(aloyslisp.core.math.tREAL)
	 */
	@Override
	boolean lower(tREAL op)
	{
		return val.compareTo(op.getIntegerValue().val) < 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.NUMBER#equalnum(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	boolean equalnum(tNUMBER op)
	{
		return val.compareTo(op.getIntegerValue().val) == 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.NUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER add(tNUMBER op)
	{
		return new BIGNUM(val.add(op.getIntegerValue().val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.NUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER substract(tNUMBER op)
	{
		return new BIGNUM(val.subtract(op.getIntegerValue().val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.NUMBER#minus()
	 */
	@Override
	tNUMBER minus()
	{
		return new BIGNUM(val.negate());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.NUMBER#inversion()
	 */
	@Override
	tNUMBER inversion()
	{
		return new RATIO(ONE, this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.NUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER multiply(tNUMBER op)
	{
		return new BIGNUM(val.multiply(op.getIntegerValue().val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.NUMBER#division(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER division(tNUMBER op)
	{
		if (op.getIntegerValue().EQUALNUM(ONE))
			return this;
		if (MOD(op.getIntegerValue()).EQUALNUM(ZERO))
			return new BIGNUM(val.divide(op.getIntegerValue().val));
		else
			return new RATIO(this, op.getIntegerValue());
	}

}
