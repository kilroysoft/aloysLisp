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

import aloyslisp.core.plugs.*;
import aloyslisp.core.types.tNUMBER;

/**
 * NumInteger
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class NumInteger implements IInteger
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
	 * @param radix
	 * @return
	 */
	public static NumInteger makeRadix(String val, Integer radix)
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
	public tNUMBER make()
	{
		return new INTEGER(this);
	}

}
