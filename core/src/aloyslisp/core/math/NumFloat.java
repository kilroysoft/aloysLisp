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
 * NumFloat
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class NumFloat extends NumReal implements IFloat
{
	public float	value;

	/**
	 * @param init
	 */
	public NumFloat(float init)
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
	public FLOAT make()
	{
		return new FLOAT(this);
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
