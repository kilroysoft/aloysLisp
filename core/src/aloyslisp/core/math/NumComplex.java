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
// IP 16 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * NumComplex
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class NumComplex implements IComplex
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5745108518875400131L;

	/**
	 * 
	 */
	public tNUMBER				real;

	/**
	 * 
	 */
	public tNUMBER				imag;

	/**
	 * 
	 */
	public NumComplex(tNUMBER real, tNUMBER imag)
	{
		this.real = real;
		this.imag = imag;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "#C(" + real + " " + imag + ")";
	}

	/**
	 * @return
	 */
	public tNUMBER getReal()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to real");
		}
		return real;
	}

	/**
	 * @return
	 */
	public NumRatio getRatioValue()
	{
		return getReal().ratioValue();
	}

	/**
	 * @return
	 */
	public NumComplex getComplexValue()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#integerValue()
	 */
	public NumInteger getIntegerValue()
	{
		return getReal().getValue().getIntegerValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#floatValue()
	 */
	public NumFloat getFloatValue()
	{
		return getReal().getValue().getFloatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Number#doubleValue()
	 */
	public NumDouble getDoubleValue()
	{
		return getReal().getValue().getDoubleValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#shortValue()
	 */
	@Override
	public NumShort getShortValue()
	{
		return getReal().getValue().getShortValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#make()
	 */
	@Override
	public tNUMBER make()
	{
		return new COMPLEX(this);
	}

}
