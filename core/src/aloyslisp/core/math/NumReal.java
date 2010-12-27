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
// IP 26 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import aloyslisp.core.numbers.*;

/**
 * NumReal
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class NumReal extends NumNumber implements IReal
{

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#mod(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal mod(IReal op)
	{
		return getDoubleValue().mod(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#rem(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal rem(IReal op)
	{
		return getDoubleValue().rem(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#floor(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] floor()
	{
		return getDoubleValue().floor();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#floor(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] floor(IReal op)
	{
		return getDoubleValue().floor(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#ceiling(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] ceiling()
	{
		return getDoubleValue().ceiling();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#ceiling(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] ceiling(IReal op)
	{
		return getDoubleValue().ceiling(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#truncate(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] truncate()
	{
		return getDoubleValue().truncate();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#truncate(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] truncate(IReal op)
	{
		return getDoubleValue().truncate(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#round(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] round()
	{
		return getDoubleValue().round();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#round(aloyslisp.core.math.IReal)
	 */
	@Override
	public IReal[] round(IReal op)
	{
		return getDoubleValue().round(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#abs()
	 */
	public IReal abs()
	{
		return lower(ZERO) ? (IReal) minus() : this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#realpart()
	 */
	@Override
	public INumber realpart()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#imagpart()
	 */
	@Override
	public INumber imagpart()
	{
		return ZERO;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#conjugate()
	 */
	@Override
	public INumber conjugate()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#phase()
	 */
	@Override
	public INumber phase()
	{
		INumber res = ONE.coerce(this);
		return minusp() ? res.minus() : res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#zerop()
	 */
	@Override
	public boolean zerop()
	{
		return equal(ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#sin()
	 */
	@Override
	public INumber cis()
	{
		return getDoubleValue().cis();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#sin()
	 */
	@Override
	public INumber sin()
	{
		return getDoubleValue().sin();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#cos()
	 */
	@Override
	public INumber cos()
	{
		return getDoubleValue().cos();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#tan()
	 */
	@Override
	public INumber tan()
	{
		return getDoubleValue().tan();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#asin()
	 */
	@Override
	public INumber asin()
	{
		return getDoubleValue().asin();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#acos()
	 */
	@Override
	public INumber acos()
	{
		return getDoubleValue().acos();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#atan()
	 */
	@Override
	public INumber atan()
	{
		return getDoubleValue().atan();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#atan(aloyslisp.core.math.INumber)
	 */
	@Override
	public INumber atan(IReal opt)
	{
		return getDoubleValue().atan(opt);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#sinh()
	 */
	@Override
	public INumber sinh()
	{
		return getDoubleValue().sinh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#cosh()
	 */
	@Override
	public INumber cosh()
	{
		return getDoubleValue().cosh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#tanh()
	 */
	@Override
	public INumber tanh()
	{
		return getDoubleValue().tanh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#asinh()
	 */
	@Override
	public INumber asinh()
	{
		return getDoubleValue().asinh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#acosh()
	 */
	@Override
	public INumber acosh()
	{
		return getDoubleValue().acosh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#atanh()
	 */
	@Override
	public INumber atanh()
	{
		return getDoubleValue().atanh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#log()
	 */
	@Override
	public INumber log()
	{
		return getDoubleValue().log();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#sqrt()
	 */
	@Override
	public INumber sqrt()
	{
		return getDoubleValue().sqrt();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#exp()
	 */
	@Override
	public INumber exp()
	{
		return getDoubleValue().exp();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#expt(aloyslisp.core.math.INumber)
	 */
	@Override
	public INumber expt(INumber power)
	{
		return getDoubleValue().expt(power);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.INumber#random()
	 */
	@Override
	public INumber random()
	{
		return getDoubleValue().random();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.INumber#random(aloyslisp.core.numbers.tRANDOM_STATE)
	 */
	@Override
	public INumber random(tRANDOM_STATE st)
	{
		return getDoubleValue().random(st);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#minusp()
	 */
	@Override
	public boolean minusp()
	{
		return lower(ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.IReal#plusp()
	 */
	@Override
	public boolean plusp()
	{
		return greater(ZERO);
	}

}
