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

package aloyslisp.core.common;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.plugs.INTEGER;
import aloyslisp.core.plugs.LONG;
import aloyslisp.core.types.*;

/**
 * NumRatio
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class NumRatio extends Number
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1362599486477845228L;

	/**
	 * 
	 */
	public tNUMBER				num;

	/**
	 * 
	 */
	public tNUMBER				den;

	/**
	 * 
	 */
	public NumRatio()
	{
		this.num = new INTEGER(1);
		this.den = new INTEGER(1);
	}

	/**
	 * @param num
	 * @param den
	 */
	public NumRatio(tNUMBER num, tNUMBER den)
	{
		tNUMBER pgcd = PGCD(num, den);
		num = num.divide(pgcd);
		den = den.divide(pgcd);
		this.num = num;
		this.den = den;
	}

	/**
	 * @param num
	 * @param den
	 */
	public NumRatio(Number num, Number den)
	{
		this.num = LONG.make(num.longValue());
		this.den = LONG.make(den.longValue());
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
	 * @see java.lang.Number#intValue()
	 */
	public int intValue()
	{
		return ratio().getValue().intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#longValue()
	 */
	public long longValue()
	{
		return ratio().getValue().longValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#floatValue()
	 */
	public float floatValue()
	{
		return ratio().getValue().floatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#doubleValue()
	 */
	public double doubleValue()
	{
		return ratio().getValue().doubleValue();
	}

	/**
	 * @return
	 */
	public NumRatio ratioValue()
	{
		return this;
	}

	/**
	 * @return
	 */
	public NumComplex complexValue()
	{
		return new NumComplex(this.ratio(), nInt(0));
	}

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	private tNUMBER PGCD(tNUMBER a, tNUMBER b)
	{
		tNUMBER k;

		while (!(b.EQUAL(new INTEGER(0))))
		{
			// System.out.println("a = " + a + ", b= " + b);
			k = a.mod(b);
			a = b;
			b = k;
		}

		return a;
	}

}
