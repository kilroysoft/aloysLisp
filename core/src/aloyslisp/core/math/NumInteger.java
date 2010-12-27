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

import aloyslisp.core.numbers.*;

/**
 * NumInteger
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class NumInteger extends NumReal implements IInteger
{
	private BigInteger	val;

	/**
	 * @param init
	 */
	public NumInteger(BigInteger init)
	{
		val = new BigInteger(init.toString());
	}

	/**
	 * @param init
	 */
	public NumInteger(Integer init)
	{
		val = new BigInteger(init.toString());
	}

	/**
	 * @param init
	 */
	public NumInteger(Long init)
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
	public static NumInteger parse_integer(String val, Integer start,
			Integer end, Integer radix, Boolean junk)
	{
		return new NumInteger(new BigInteger(val, radix));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#ratioValue()
	 */
	@Override
	public NumRatio getRatioValue()
	{
		return new NumRatio(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#complexValue()
	 */
	@Override
	public NumComplex getComplexValue()
	{
		return new NumComplex(new INTEGER(this), new INTEGER(0));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#integerValue()
	 */
	@Override
	public NumInteger getIntegerValue()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#floatValue()
	 */
	@Override
	public NumFloat getFloatValue()
	{
		return new NumFloat(val.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#doubleValue()
	 */
	@Override
	public NumDouble getDoubleValue()
	{
		return new NumDouble(val.doubleValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#shortValue()
	 */
	@Override
	public NumShort getShortValue()
	{
		return new NumShort(val.shortValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#make()
	 */
	@Override
	public INTEGER make()
	{
		return new INTEGER(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#equal(aloyslisp.core.math.INumber)
	 */
	@Override
	public boolean equal(INumber op)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#greater(aloyslisp.core.math.INumber)
	 */
	@Override
	public boolean greater(INumber op)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#lower(aloyslisp.core.math.INumber)
	 */
	@Override
	public boolean lower(INumber op)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#rational()
	 */
	@Override
	public IRational rational()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#rationalize()
	 */
	@Override
	public IRational rationalize()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#add(aloyslisp.core.math.INumber)
	 */
	@Override
	public INumber add(INumber op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#substract(aloyslisp.core.math.INumber)
	 */
	@Override
	public INumber substract(INumber op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#minus()
	 */
	@Override
	public INumber minus()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#inversion()
	 */
	@Override
	public INumber inversion()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#multiply(aloyslisp.core.math.INumber)
	 */
	@Override
	public INumber multiply(INumber op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#division(aloyslisp.core.math.INumber)
	 */
	@Override
	public INumber division(INumber op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#lcm(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger lcm(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#gcd(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger gcd(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#logand(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger logand(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#lognand(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger lognand(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#logandc1(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger logandc1(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#logandc2(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger logandc2(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#logior(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger logior(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#logorc1(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger logorc1(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#logorc2(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger logorc2(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#logxor(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger logxor(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#logeqv(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger logeqv(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#lognot()
	 */
	@Override
	public IInteger lognot()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#toBase(java.lang.Integer)
	 */
	@Override
	public String toBase(Integer radix)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#logtest(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger logtest(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#logcount(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger logcount(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#logbitp(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger logbitp(IInteger op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#ash(aloyslisp.core.math.IInteger)
	 */
	@Override
	public IInteger ash(IInteger count)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#evenp()
	 */
	@Override
	public boolean evenp()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#oddp()
	 */
	@Override
	public boolean oddp()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#isqrt()
	 */
	@Override
	public IInteger isqrt()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IInteger#integer_length()
	 */
	@Override
	public IInteger integer_length()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see aloyslisp.core.math.IRational#numerator()
	 */
	@Override
	public NumInteger numerator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see aloyslisp.core.math.IRational#denominator()
	 */
	@Override
	public NumInteger denominator()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
