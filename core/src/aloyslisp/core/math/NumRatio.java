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

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * NumRatio
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class NumRatio implements IRational
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1362599486477845228L;

	/**
	 * 
	 */
	public NumInteger			num;

	/**
	 * 
	 */
	public NumInteger			den;

	/**
	 * 
	 */
	public NumRatio()
	{
		this.num = new NumInteger(1);
		this.den = new NumInteger(1);
	}

	/**
	 * @param num
	 * @param den
	 */
	public NumRatio(NumInteger num, NumInteger den)
	{
		NumInteger pgcd = new NumInteger(num.gcd(den));
		this.num = new NumInteger(num.divide(pgcd));
		this.den = new NumInteger(den.divide(pgcd));
	}

	/**
	 * @param num
	 * @param den
	 */
	public NumRatio(NumInteger num)
	{
		this.num = num;
		this.den = new NumInteger(1);
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
		return num.make().DIVISION(den.make());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#integerValue()
	 */
	public NumInteger getIntegerValue()
	{
		return ratio().getValue().getIntegerValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#floatValue()
	 */
	public NumFloat getFloatValue()
	{
		return ratio().getValue().getFloatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#doubleValue()
	 */
	public NumDouble getDoubleValue()
	{
		return ratio().getValue().getDoubleValue();
	}

	/**
	 * @return
	 */
	public NumRatio getRatioValue()
	{
		return this;
	}

	/**
	 * @return
	 */
	public NumComplex getComplexValue()
	{
		return new NumComplex(this.ratio(), nInt(0));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#shortValue()
	 */
	@Override
	public NumShort getShortValue()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
