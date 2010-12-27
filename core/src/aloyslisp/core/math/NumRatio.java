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
import aloyslisp.core.numbers.*;

/**
 * NumRatio
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class NumRatio extends NumReal implements IRational
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1362599486477845228L;

	/**
	 * 
	 */
	public IInteger				num;

	/**
	 * 
	 */
	public IInteger				den;

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
	public NumRatio(IInteger num, IInteger den)
	{
		IInteger pgcd = num.gcd(den).getIntegerValue();
		this.num = num.division(pgcd).getIntegerValue();
		this.den = den.division(pgcd).getIntegerValue();
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
		return new NumShort(0);
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
	 * @see aloyslisp.core.math.INumber#make()
	 */
	@Override
	public RATIO make()
	{
		return new RATIO(this);
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
	 * @see aloyslisp.core.math.IRational#numerator()
	 */
	@Override
	public NumInteger numerator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IRational#denominator()
	 */
	@Override
	public NumInteger denominator()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
