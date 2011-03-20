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

import aloyslisp.annotations.BuiltIn;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.sequences.*;
import static aloyslisp.core.L.*;

/**
 * cINTEGER
 * 
 * Implements all integers, so difference between bignums and fixnums will be
 * programmaticaly set (class, tests, etc)
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@BuiltIn(classOf = "integer", doc = "t_integer")
public class cINTEGER extends cRATIONAL implements tINTEGER
{
	public BigInteger	val;

	/**
	 * @param init
	 */
	public cINTEGER(BigInteger init)
	{
		val = new BigInteger(init.toString());
	}

	/**
	 * @param init
	 */
	public cINTEGER(String init)
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
	public cINTEGER(String init, Integer radix)
	{
		val = new BigInteger(init, radix);
	}

	/**
	 * @param init
	 */
	public cINTEGER(Integer init)
	{
		val = new BigInteger(init.toString());
	}

	/**
	 * @param init
	 */
	public cINTEGER(Long init)
	{
		val = new BigInteger(init.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#integerValue()
	 */
	public Integer integerValue()
	{
		return getIntegerValue().val.intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#longValue()
	 */
	public Long longValue()
	{
		return getIntegerValue().val.longValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#toString()
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
	public cINTEGER getIntegerValue()
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
	tINTEGER lcm(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#gcd(aloyslisp.core.math.tINTEGER)
	 */
	tINTEGER gcd(tINTEGER op)
	{
		return new cINTEGER(val.gcd(((cINTEGER) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logand(aloyslisp.core.math.tINTEGER)
	 */
	tINTEGER logand(tINTEGER op)
	{
		return new cINTEGER(val.and(((cINTEGER) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#lognand(aloyslisp.core.math.tINTEGER)
	 */
	tINTEGER lognand(tINTEGER op)
	{
		return new cINTEGER(val.and(((cINTEGER) op).val).not());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logandc1(aloyslisp.core.math.tINTEGER)
	 */
	tINTEGER logandc1(tINTEGER op)
	{
		return new cINTEGER(val.not().and(((cINTEGER) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logandc2(aloyslisp.core.math.tINTEGER)
	 */
	tINTEGER logandc2(tINTEGER op)
	{
		return new cINTEGER(val.and(((cINTEGER) op).val.not()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logior(aloyslisp.core.math.tINTEGER)
	 */
	tINTEGER logior(tINTEGER op)
	{
		return new cINTEGER(val.or(((cINTEGER) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logorc1(aloyslisp.core.math.tINTEGER)
	 */
	tINTEGER logorc1(tINTEGER op)
	{
		return new cINTEGER(val.not().or(((cINTEGER) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logorc2(aloyslisp.core.math.tINTEGER)
	 */
	tINTEGER logorc2(tINTEGER op)
	{
		return new cINTEGER(val.or(((cINTEGER) op).val.not()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logxor(aloyslisp.core.math.tINTEGER)
	 */
	tINTEGER logxor(tINTEGER op)
	{
		return new cINTEGER(val.xor(((cINTEGER) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logeqv(aloyslisp.core.math.tINTEGER)
	 */
	tINTEGER logeqv(tINTEGER op)
	{
		return new cINTEGER(val.xor(((cINTEGER) op).val).not());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#lognot()
	 */
	tINTEGER lognot()
	{
		return new cINTEGER(val.not());
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
	public static cINTEGER parse_integer(String val, Integer start,
			Integer end, Integer radix, Boolean junk)
	{
		return new cINTEGER(new BigInteger(val, radix));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#toBase(java.lang.Integer)
	 */
	String toBase(Integer radix)
	{
		return val.toString(radix);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logtest(aloyslisp.core.math.tINTEGER)
	 */
	Boolean logtest(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logcount()
	 */
	tINTEGER logcount()
	{
		return new cINTEGER(val.bitCount());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logbitp(aloyslisp.core.math.tINTEGER)
	 */
	Boolean logbitp(tINTEGER op)
	{
		return val.testBit(op.getIntegerValue().val.intValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ash(aloyslisp.core.math.tINTEGER)
	 */
	tINTEGER ash(tINTEGER count)
	{
		if (count.MINUSP())
			return new cINTEGER(
					val.shiftRight(count.MINUS().getIntegerValue().val
							.intValue()));
		else
			return new cINTEGER(val.shiftLeft(count.getIntegerValue().val
					.intValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#evenp()
	 */
	boolean evenp()
	{
		return val.mod(new BigInteger("2")).intValue() == 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#oddp()
	 */
	boolean oddp()
	{
		return val.mod(new BigInteger("2")).intValue() != 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#isqrt()
	 */
	tINTEGER isqrt()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#integer_length()
	 */
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
		return new cINTEGER(val.add(op.getIntegerValue().val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER substract(tNUMBER op)
	{
		return new cINTEGER(val.subtract(op.getIntegerValue().val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#minus()
	 */
	@Override
	tNUMBER minus()
	{
		return new cINTEGER(val.negate());
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
		return new cINTEGER(val.multiply(op.getIntegerValue().val));
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
			return new cINTEGER(val.divide(op.getIntegerValue().val));
		else
			return new cRATIO(this, op.getIntegerValue()).rationalizeValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LCM(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER LCM(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).lcm((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.LCM((tNUMBER) list.CAR());
			else
				return this.LCM((tNUMBER) list.CAR()).LCM(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#GCD(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER GCD(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).gcd((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.GCD((tNUMBER) list.CAR());
			else
				return this.GCD((tNUMBER) list.CAR()).GCD(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGAND(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER LOGAND(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).logand((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.LOGAND((tNUMBER) list.CAR());
			else
				return this.LOGAND((tNUMBER) list.CAR()).LOGAND(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGNAND(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGNAND(tINTEGER op)
	{
		return ((cINTEGER) coerce((cINTEGER) op)).lognand(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGANDC1(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGANDC1(tINTEGER op)
	{
		return ((cINTEGER) coerce((cINTEGER) op)).logandc1(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGANDC2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGANDC2(tINTEGER op)
	{
		return ((cINTEGER) coerce((cINTEGER) op)).logandc2(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGIOR(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER LOGIOR(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).logior((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.LOGIOR((tNUMBER) list.CAR());
			else
				return this.LOGIOR((tNUMBER) list.CAR()).LOGIOR(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGORC1(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGORC1(tINTEGER op)
	{
		return ((cINTEGER) coerce((cINTEGER) op)).logorc1(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGORC2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGORC2(tINTEGER op)
	{
		return ((cINTEGER) coerce((cINTEGER) op)).logorc2(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGXOR(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER LOGXOR(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).logxor((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.LOGXOR((tNUMBER) list.CAR());
			else
				return this.LOGXOR((tNUMBER) list.CAR()).LOGXOR(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGEQV(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER LOGEQV(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).logeqv((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.LOGEQV((tNUMBER) list.CAR());
			else
				return this.LOGEQV((tNUMBER) list.CAR()).LOGEQV(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGNOT()
	 */
	@Override
	public tINTEGER LOGNOT()
	{
		return lognot();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGCOUNT(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGCOUNT()
	{
		return logcount();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGTEST(aloyslisp.core.math.tINTEGER)
	 */
	public Boolean LOGTEST(tINTEGER op)
	{
		return logtest(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGBITP(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public Boolean LOGBITP(tINTEGER op)
	{
		return logbitp(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ASH(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER ASH(tINTEGER count)
	{
		return ash(count);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#EVENP()
	 */
	@Override
	public boolean EVENP()
	{
		return evenp();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ODDP()
	 */
	@Override
	public boolean ODDP()
	{
		return oddp();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ISQRT()
	 */
	@Override
	public tINTEGER ISQRT()
	{
		return isqrt();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#INTEGER_LENGTH()
	 */
	@Override
	public tINTEGER INTEGER_LENGTH()
	{
		return integer_length();
	}

}
