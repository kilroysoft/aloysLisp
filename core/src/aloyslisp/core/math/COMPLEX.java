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

/**
 * COMPLEX
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class COMPLEX extends NUMBER implements tCOMPLEX
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5745108518875400131L;

	/**
	 * 
	 */
	public tREAL				real;

	/**
	 * 
	 */
	public tREAL				imag;

	/**
	 * 
	 */
	/**
	 * @param real
	 * @param imag
	 */
	public COMPLEX(tREAL real, tREAL imag)
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
	public RATIO getRatioValue()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to ratio");
		}
		return realpart().getRatioValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getComplexValue()
	 */
	public COMPLEX getComplexValue()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getIntegerValue()
	 */
	public BIGNUM getIntegerValue()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to integer");
		}
		return realpart().getIntegerValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getFloatValue()
	 */
	public SINGLE_FLOAT getFloatValue()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to float");
		}
		return realpart().getFloatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getDoubleValue()
	 */
	public DOUBLE_FLOAT getDoubleValue()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to double");
		}
		return realpart().getDoubleValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getShortValue()
	 */
	public SHORT_FLOAT getShortValue()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to short");
		}
		return realpart().getShortValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#coerce(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER coerce(tNUMBER var)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER add(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER substract(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#minus()
	 */
	@Override
	public tNUMBER minus()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#inversion()
	 */
	@Override
	public tNUMBER inversion()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER multiply(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#division(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER division(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#realpart()
	 */
	@Override
	public tNUMBER realpart()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#imagpart()
	 */
	@Override
	public tNUMBER imagpart()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#conjugate()
	 */
	@Override
	public tNUMBER conjugate()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#phase()
	 */
	@Override
	public tNUMBER phase()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#abs()
	 */
	@Override
	public tREAL abs()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#zerop()
	 */
	@Override
	public boolean zerop()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#cis()
	 */
	@Override
	public tNUMBER cis()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sin()
	 */
	@Override
	public tNUMBER sin()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#cos()
	 */
	@Override
	public tNUMBER cos()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#tan()
	 */
	@Override
	public tNUMBER tan()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#asin()
	 */
	@Override
	public tNUMBER asin()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#acos()
	 */
	@Override
	public tNUMBER acos()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#atan()
	 */
	@Override
	public tNUMBER atan()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#atan(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tNUMBER atan(tREAL opt)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sinh()
	 */
	@Override
	public tNUMBER sinh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#cosh()
	 */
	@Override
	public tNUMBER cosh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#tanh()
	 */
	@Override
	public tNUMBER tanh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#asinh()
	 */
	@Override
	public tNUMBER asinh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#acosh()
	 */
	@Override
	public tNUMBER acosh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#atanh()
	 */
	@Override
	public tNUMBER atanh()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#log()
	 */
	@Override
	public tNUMBER log()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sqrt()
	 */
	@Override
	public tNUMBER sqrt()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#exp()
	 */
	@Override
	public tNUMBER exp()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#expt(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER expt(tNUMBER power)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#random()
	 */
	@Override
	public tNUMBER random()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.tNUMBER#random(aloyslisp.core.numbers.tRANDOM_STATE)
	 */
	@Override
	public tNUMBER random(tRANDOM_STATE st)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
