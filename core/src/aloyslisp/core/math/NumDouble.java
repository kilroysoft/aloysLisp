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

import aloyslisp.core.numbers.*;

/**
 * NumDouble
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class NumDouble extends NumReal implements IFloat
{
	public double	value;

	/**
	 * @param init
	 */
	public NumDouble(double init)
	{
		value = init;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#ratioValue()
	 */
	@Override
	public NumRatio getRatioValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#complexValue()
	 */
	@Override
	public NumComplex getComplexValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#integerValue()
	 */
	@Override
	public NumInteger getIntegerValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#floatValue()
	 */
	@Override
	public NumFloat getFloatValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#doubleValue()
	 */
	@Override
	public NumDouble getDoubleValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#shortValue()
	 */
	@Override
	public NumShort getShortValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#make()
	 */
	@Override
	public DOUBLE make()
	{
		return new DOUBLE(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#mod(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal mod(IReal op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#rem(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal rem(IReal op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#floor(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] floor()
	{
		return floor(ONE);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#floor(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] floor(IReal op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#ceiling(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] ceiling()
	{
		return ceiling(ONE);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#ceiling(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] ceiling(IReal op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#truncate(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] truncate()
	{
		return truncate(ONE);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#truncate(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] truncate(IReal op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#round(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] round()
	{
		return round(ONE);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#round(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] round(IReal op)
	{
		// TODO Auto-generated method stub
		return null;
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
	 * @see aloyslisp.core.math.IReal#minusp()
	 */
	@Override
	public boolean minusp()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#plusp()
	 */
	@Override
	public boolean plusp()
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
	 * @see aloyslisp.core.math.INumber#realpart()
	 */
	@Override
	public INumber realpart()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#imagpart()
	 */
	@Override
	public INumber imagpart()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#conjugate()
	 */
	@Override
	public INumber conjugate()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#phase()
	 */
	@Override
	public INumber phase()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#abs()
	 */
	@Override
	public IReal abs()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#zerop()
	 */
	@Override
	public boolean zerop()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#cis()
	 */
	@Override
	public INumber cis()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#sin()
	 */
	@Override
	public INumber sin()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#cos()
	 */
	@Override
	public INumber cos()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#tan()
	 */
	@Override
	public INumber tan()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#asin()
	 */
	@Override
	public INumber asin()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#acos()
	 */
	@Override
	public INumber acos()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#atan()
	 */
	@Override
	public INumber atan()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#atan(aloyslisp.core.math.IReal)
	 */
	@Override
	public INumber atan(IReal opt)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#sinh()
	 */
	@Override
	public INumber sinh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#cosh()
	 */
	@Override
	public INumber cosh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#tanh()
	 */
	@Override
	public INumber tanh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#asinh()
	 */
	@Override
	public INumber asinh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#acosh()
	 */
	@Override
	public INumber acosh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#atanh()
	 */
	@Override
	public INumber atanh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#log()
	 */
	@Override
	public INumber log()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#sqrt()
	 */
	@Override
	public INumber sqrt()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#exp()
	 */
	@Override
	public INumber exp()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#expt(aloyslisp.core.math.INumber)
	 */
	@Override
	public INumber expt(INumber power)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#random()
	 */
	@Override
	public INumber random()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.INumber#random(aloyslisp.core.numbers.tRANDOM_STATE)
	 */
	@Override
	public INumber random(tRANDOM_STATE st)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IFloat#decode_float(aloyslisp.core.math.IFloat)
	 */
	@Override
	public IFloat[] decode_float(IFloat f)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.IFloat#integer_decode_float(aloyslisp.core.math.IFloat
	 * )
	 */
	@Override
	public IFloat[] integer_decode_float(IFloat f)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IFloat#scale_float(aloyslisp.core.math.IFloat,
	 * aloyslisp.core.math.IInteger)
	 */
	@Override
	public IFloat scale_float(IFloat f, IInteger scale)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IFloat#float_radix(aloyslisp.core.math.IFloat)
	 */
	@Override
	public IFloat float_radix(IFloat f)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IFloat#float_sign(aloyslisp.core.math.IFloat,
	 * aloyslisp.core.math.IFloat)
	 */
	@Override
	public IFloat float_sign(IFloat f, IFloat f2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IFloat#float_digits(aloyslisp.core.math.IFloat)
	 */
	@Override
	public IInteger float_digits(IFloat f)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.IFloat#float_precision(aloyslisp.core.math.IFloat)
	 */
	@Override
	public IInteger float_precision(IFloat f)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
