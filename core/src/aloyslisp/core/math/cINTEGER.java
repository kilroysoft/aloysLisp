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
// IP 23 déc. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import java.math.BigInteger;

import aloyslisp.annotations.aBuiltIn;
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
@aBuiltIn(lispClass = "integer", doc = "t_integer")
public class cINTEGER extends cRATIONAL implements tINTEGER
{
	public BigInteger	val;

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

	/**
	 * @param init
	 */
	public cINTEGER(BigInteger init)
	{
		val = new BigInteger(init.toString());
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
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_RATIO()
	 */
	public cRATIO COERCE_TO_RATIO()
	{
		return new cRATIO(this);
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
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_INTEGER()
	 */
	public cINTEGER COERCE_TO_INTEGER()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_SINGLE_FLOAT()
	 */
	public cSINGLE_FLOAT COERCE_TO_SINGLE_FLOAT()
	{
		return new cSINGLE_FLOAT(val.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_DOUBLE_FLOAT()
	 */
	public cDOUBLE_FLOAT COERCE_TO_DOUBLE_FLOAT()
	{
		return new cDOUBLE_FLOAT(val.doubleValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_SHORT_FLOAT()
	 */
	public cSHORT_FLOAT COERCE_TO_SHORT_FLOAT()
	{
		return new cSHORT_FLOAT(val.shortValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#coerce(aloyslisp.core.math.tNUMBER)
	 */
	public cNUMBER COERCE_TO_NUMBER(tNUMBER var)
	{
		if (var instanceof cRATIO)
			return COERCE_TO_RATIO();

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
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#TO_STRING()
	 */
	public String TO_STRING()
	{
		tT base = printBase.SYMBOL_VALUE();
		int numBase = 10;
		if (base instanceof tINTEGER)
			numBase = ((cINTEGER) base).INTEGER_VALUE();
		return val.toString(numBase);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_ADD(tNUMBER op)
	{
		return new cINTEGER(val.add(op.BIGINT_VALUE()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#division(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_DIVISION(tNUMBER op)
	{
		if (op.COERCE_TO_INTEGER().EQUALNUM(ONE))
			return this;
		if (MOD(op.COERCE_TO_INTEGER()).EQUALNUM(ZERO))
			return new cINTEGER(val.divide(op.BIGINT_VALUE()));
		else
			return new cRATIO(this, op.COERCE_TO_INTEGER()).RATIONALIZE_VALUE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#equalnum(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public boolean SINGLE_EQUALNUM(tNUMBER op)
	{
		return val.compareTo(op.BIGINT_VALUE()) == 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#gcd(aloyslisp.core.math.tINTEGER)
	 */
	public tINTEGER SINGLE_GCD(tINTEGER op)
	{
		return new cINTEGER(val.gcd(((cINTEGER) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cREAL#greater(aloyslisp.core.math.tREAL)
	 */
	@Override
	public boolean SINGLE_GREATER(tREAL op)
	{
		return val.compareTo(op.BIGINT_VALUE()) > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#lcm(aloyslisp.core.math.tINTEGER)
	 */
	public tINTEGER SINGLE_LCM(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logand(aloyslisp.core.math.tINTEGER)
	 */
	public tINTEGER SINGLE_LOGAND(tINTEGER op)
	{
		return new cINTEGER(val.and(((cINTEGER) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logior(aloyslisp.core.math.tINTEGER)
	 */
	public tINTEGER SINGLE_LOGIOR(tINTEGER op)
	{
		return new cINTEGER(val.or(((cINTEGER) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#logxor(aloyslisp.core.math.tINTEGER)
	 */
	public tINTEGER SINGLE_LOGXOR(tINTEGER op)
	{
		return new cINTEGER(val.xor(((cINTEGER) op).val));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cREAL#lower(aloyslisp.core.math.tREAL)
	 */
	@Override
	public boolean SINGLE_LOWER(tREAL op)
	{
		return val.compareTo(op.BIGINT_VALUE()) < 0;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_MULTIPLY(tNUMBER op)
	{
		return new cINTEGER(val.multiply(op.BIGINT_VALUE()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_SUBSTRACT(tNUMBER op)
	{
		return new cINTEGER(val.subtract(op.BIGINT_VALUE()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ASH(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER ASH(tINTEGER count)
	{
		if (count.MINUSP())
			return new cINTEGER(val.shiftRight(count.MINUS().INTEGER_VALUE()));
		else
			return new cINTEGER(val.shiftLeft(count.INTEGER_VALUE()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#denominator()
	 */
	@Override
	public tINTEGER DENOMINATOR()
	{
		return ONE;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#EVENP()
	 */
	@Override
	public boolean EVENP()
	{
		return val.mod(new BigInteger("2")).intValue() == 0;
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
			return ((cINTEGER) COERCE_TO_NUMBER((cINTEGER) op))
					.SINGLE_GCD((tINTEGER) op);
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
	 * @see aloyslisp.core.math.tINTEGER#INTEGER_LENGTH()
	 */
	@Override
	public tINTEGER INTEGER_LENGTH()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#inversion()
	 */
	@Override
	public tNUMBER INVERSION()
	{
		return new cRATIO(ONE, this).RATIONALIZE_VALUE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ISQRT()
	 */
	@Override
	public tINTEGER ISQRT()
	{
		// TODO Auto-generated method stub
		return null;
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
			return ((cINTEGER) COERCE_TO_NUMBER((cINTEGER) op))
					.SINGLE_LCM((tINTEGER) op);
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
	 * @see aloyslisp.core.math.tINTEGER#LOGAND(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER LOGAND(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) COERCE_TO_NUMBER((cINTEGER) op))
					.SINGLE_LOGAND((tINTEGER) op);
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
		return ((cINTEGER) COERCE_TO_NUMBER((cINTEGER) op)).LOGAND(op).LOGNOT();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGANDC1(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGANDC1(tINTEGER op)
	{
		return ((cINTEGER) COERCE_TO_NUMBER((cINTEGER) op)).LOGNOT().LOGAND(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGANDC2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGANDC2(tINTEGER op)
	{
		return ((cINTEGER) COERCE_TO_NUMBER((cINTEGER) op)).LOGAND(op.LOGNOT());
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
			return ((cINTEGER) COERCE_TO_NUMBER((cINTEGER) op))
					.SINGLE_LOGIOR((tINTEGER) op);
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
		return ((cINTEGER) COERCE_TO_NUMBER((cINTEGER) op)).LOGNOT().LOGIOR(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGORC2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGORC2(tINTEGER op)
	{
		return ((cINTEGER) COERCE_TO_NUMBER((cINTEGER) op)).LOGIOR(op.LOGNOT());
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
			return ((cINTEGER) COERCE_TO_NUMBER((cINTEGER) op))
					.SINGLE_LOGXOR((tINTEGER) op);
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
			return ((cINTEGER) COERCE_TO_NUMBER((cINTEGER) op)).LOGXOR(
					(tINTEGER) op).LOGNOT();
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
		return new cINTEGER(val.not());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGCOUNT(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGCOUNT()
	{
		return new cINTEGER(val.bitCount());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGTEST(aloyslisp.core.math.tINTEGER)
	 */
	public Boolean LOGTEST(tINTEGER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGBITP(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public Boolean LOGBITP(tINTEGER op)
	{
		return val.testBit(op.INTEGER_VALUE());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#minus()
	 */
	@Override
	public tNUMBER MINUS()
	{
		return new cINTEGER(val.negate());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tRATIONAL#numerator()
	 */
	@Override
	public tINTEGER NUMERATOR()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ODDP()
	 */
	@Override
	public boolean ODDP()
	{
		return val.mod(new BigInteger("2")).intValue() != 0;
	}

}
