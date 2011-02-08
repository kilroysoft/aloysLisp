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

import static aloyslisp.packages.L.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.plugs.*;

/**
 * cBIGNUM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cBIGNUM extends cINTEGER implements tBIG_INTEGER
{
	public BigInteger	val;

	/**
	 * @param init
	 */
	public cBIGNUM(BigInteger init)
	{
		val = new BigInteger(init.toString());
	}

	/**
	 * @param init
	 */
	public cBIGNUM(String init)
	{
		int pos = init.indexOf(".");
		try
		{
			if (pos >= 0)
				val = new BigInteger(init.substring(0, pos));
			else
				val = new BigInteger(init);
		}
		catch (Exception e)
		{
			throw new ARITHMETIC_ERROR(str(e.getLocalizedMessage()), str(init));
		}
	}

	/**
	 * @param init
	 */
	public cBIGNUM(String init, Integer radix)
	{
		val = new BigInteger(init, radix);
	}

	/**
	 * @param init
	 */
	public cBIGNUM(Integer init)
	{
		val = new BigInteger(init.toString());
	}

	/**
	 * @param init
	 */
	public cBIGNUM(Long init)
	{
		val = new BigInteger(init.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.cCELL#toString()
	 */
	public String toString()
	{
		tT base = printBase.SYMBOL_VALUE();
		int numBase = 10;
		if (base instanceof tINTEGER)
			numBase = ((cINTEGER) base).integerValue();
		return val.toString(numBase);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ratioValue()
	 */
	@Override
	public cRATIO getRatioValue()
	{
		return new cRATIO(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#complexValue()
	 */
	@Override
	public cCOMPLEX getComplexValue()
	{
		return new cCOMPLEX(this, ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#integerValue()
	 */
	@Override
	public cBIGNUM getIntegerValue()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#floatValue()
	 */
	@Override
	public cSINGLE_FLOAT getFloatValue()
	{
		return new cSINGLE_FLOAT(val.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#doubleValue()
	 */
	@Override
	public cDOUBLE_FLOAT getDoubleValue()
	{
		return new cDOUBLE_FLOAT(val.doubleValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#shortValue()
	 */
	@Override
	public cSHORT_FLOAT getShortValue()
	{
		return new cSHORT_FLOAT(val.shortValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cRATIONAL#getRationalValue()
	 */
	public tRATIONAL rationalizeValue()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#coerce(aloyslisp.core.math.tNUMBER)
	 */
	public cNUMBER coerce(tNUMBER var)
	{
		if (var instanceof cRATIO)
			return getRatioValue();

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
		return new cBIGNUM(val.gcd(((cBIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logand(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logand(tINTEGER op)
	{
		return new cBIGNUM(val.and(((cBIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#lognand(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER lognand(tINTEGER op)
	{
		return new cBIGNUM(val.and(((cBIGNUM) op).val).not());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logandc1(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logandc1(tINTEGER op)
	{
		return new cBIGNUM(val.not().and(((cBIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logandc2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logandc2(tINTEGER op)
	{
		return new cBIGNUM(val.and(((cBIGNUM) op).val.not()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logior(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logior(tINTEGER op)
	{
		return new cBIGNUM(val.or(((cBIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logorc1(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logorc1(tINTEGER op)
	{
		return new cBIGNUM(val.not().or(((cBIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logorc2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logorc2(tINTEGER op)
	{
		return new cBIGNUM(val.or(((cBIGNUM) op).val.not()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logxor(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logxor(tINTEGER op)
	{
		return new cBIGNUM(val.xor(((cBIGNUM) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logeqv(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	tINTEGER logeqv(tINTEGER op)
	{
		return new cBIGNUM(val.xor(((cBIGNUM) op).val).not());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#lognot()
	 */
	@Override
	tINTEGER lognot()
	{
		return new cBIGNUM(val.not());
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
	public static cBIGNUM parse_integer(String val, Integer start, Integer end,
			Integer radix, Boolean junk)
	{
		return new cBIGNUM(new BigInteger(val, radix));
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
		return new cBIGNUM(val.bitCount());
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
			return new cBIGNUM(
					val.shiftRight(count.MINUS().getIntegerValue().val
							.intValue()));
		else
			return new cBIGNUM(val.shiftLeft(count.getIntegerValue().val
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
	 * @see aloyslisp.core.math.cREAL#greater(aloyslisp.core.math.tREAL)
	 */
	@Override
	boolean greater(tREAL op)
	{
		return val.compareTo(op.getIntegerValue().val) > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cREAL#lower(aloyslisp.core.math.tREAL)
	 */
	@Override
	boolean lower(tREAL op)
	{
		return val.compareTo(op.getIntegerValue().val) < 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#equalnum(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	boolean equalnum(tNUMBER op)
	{
		return val.compareTo(op.getIntegerValue().val) == 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER add(tNUMBER op)
	{
		return new cBIGNUM(val.add(op.getIntegerValue().val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER substract(tNUMBER op)
	{
		return new cBIGNUM(val.subtract(op.getIntegerValue().val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#minus()
	 */
	@Override
	tNUMBER minus()
	{
		return new cBIGNUM(val.negate());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#inversion()
	 */
	@Override
	tNUMBER inversion()
	{
		return new cRATIO(ONE, this).rationalizeValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER multiply(tNUMBER op)
	{
		return new cBIGNUM(val.multiply(op.getIntegerValue().val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#division(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER division(tNUMBER op)
	{
		if (op.getIntegerValue().EQUALNUM(ONE))
			return this;
		if (MOD(op.getIntegerValue()).EQUALNUM(ZERO))
			return new cBIGNUM(val.divide(op.getIntegerValue().val));
		else
			return new cRATIO(this, op.getIntegerValue()).rationalizeValue();
	}

}
