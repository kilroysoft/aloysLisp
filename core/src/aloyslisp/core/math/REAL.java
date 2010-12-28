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

import aloyslisp.core.plugs.tT;

/**
 * REAL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class REAL extends NUMBER implements tREAL
{

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#mod(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL mod(tREAL op)
	{
		return getDoubleValue().mod(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#rem(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL rem(tREAL op)
	{
		return getDoubleValue().rem(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#floor(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] floor()
	{
		return getDoubleValue().floor();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#floor(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] floor(tREAL op)
	{
		return getDoubleValue().floor(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#ceiling(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] ceiling()
	{
		return getDoubleValue().ceiling();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#ceiling(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] ceiling(tREAL op)
	{
		return getDoubleValue().ceiling(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#truncate(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] truncate()
	{
		return getDoubleValue().truncate();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#truncate(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] truncate(tREAL op)
	{
		return getDoubleValue().truncate(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#round(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] round()
	{
		return getDoubleValue().round();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#round(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] round(tREAL op)
	{
		return getDoubleValue().round(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#abs()
	 */
	public tREAL abs()
	{
		return lower(ZERO) ? (tREAL) minus() : this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#realpart()
	 */
	@Override
	public tNUMBER realpart()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#imagpart()
	 */
	@Override
	public tNUMBER imagpart()
	{
		return ZERO;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#conjugate()
	 */
	@Override
	public tNUMBER conjugate()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#phase()
	 */
	@Override
	public tNUMBER phase()
	{
		tNUMBER res = ONE.coerce(this);
		return minusp() ? res.minus() : res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#zerop()
	 */
	@Override
	public boolean zerop()
	{
		return equal(ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sin()
	 */
	@Override
	public tNUMBER cis()
	{
		return getDoubleValue().cis();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sin()
	 */
	@Override
	public tNUMBER sin()
	{
		return getDoubleValue().sin();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#cos()
	 */
	@Override
	public tNUMBER cos()
	{
		return getDoubleValue().cos();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#tan()
	 */
	@Override
	public tNUMBER tan()
	{
		return getDoubleValue().tan();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#asin()
	 */
	@Override
	public tNUMBER asin()
	{
		return getDoubleValue().asin();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#acos()
	 */
	@Override
	public tNUMBER acos()
	{
		return getDoubleValue().acos();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#atan()
	 */
	@Override
	public tNUMBER atan()
	{
		return getDoubleValue().atan();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#atan(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER atan(tREAL opt)
	{
		return getDoubleValue().atan(opt);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sinh()
	 */
	@Override
	public tNUMBER sinh()
	{
		return getDoubleValue().sinh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#cosh()
	 */
	@Override
	public tNUMBER cosh()
	{
		return getDoubleValue().cosh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#tanh()
	 */
	@Override
	public tNUMBER tanh()
	{
		return getDoubleValue().tanh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#asinh()
	 */
	@Override
	public tNUMBER asinh()
	{
		return getDoubleValue().asinh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#acosh()
	 */
	@Override
	public tNUMBER acosh()
	{
		return getDoubleValue().acosh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#atanh()
	 */
	@Override
	public tNUMBER atanh()
	{
		return getDoubleValue().atanh();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#log()
	 */
	@Override
	public tNUMBER log()
	{
		return getDoubleValue().log();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sqrt()
	 */
	@Override
	public tNUMBER sqrt()
	{
		return getDoubleValue().sqrt();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#exp()
	 */
	@Override
	public tNUMBER exp()
	{
		return getDoubleValue().exp();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#expt(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER expt(tNUMBER power)
	{
		return getDoubleValue().expt(power);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#random()
	 */
	@Override
	public tNUMBER random()
	{
		return getDoubleValue().random();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.tNUMBER#random(aloyslisp.core.numbers.tRANDOM_STATE)
	 */
	@Override
	public tNUMBER random(tRANDOM_STATE st)
	{
		return getDoubleValue().random(st);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#minusp()
	 */
	@Override
	public boolean minusp()
	{
		return lower(ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#plusp()
	 */
	@Override
	public boolean plusp()
	{
		return greater(ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#GREATER(aloyslisp.core.plugs.tT)
	 */
	@Override
	public boolean GREATER(tT op)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#LOWER(aloyslisp.core.plugs.tT)
	 */
	@Override
	public boolean LOWER(tT op)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#MINUSP()
	 */
	@Override
	public boolean MINUSP()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#PLUSP()
	 */
	@Override
	public boolean PLUSP()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#RATIONAL()
	 */
	@Override
	public tRATIONAL RATIONAL()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#RATIONALIZE()
	 */
	@Override
	public tRATIONAL RATIONALIZE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#MOD(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL MOD(tREAL op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#REM(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL REM(tREAL op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#FLOOR()
	 */
	@Override
	public tREAL[] FLOOR()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#FLOOR(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] FLOOR(tREAL op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#CEILING()
	 */
	@Override
	public tREAL[] CEILING()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#CEILING(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] CEILING(tREAL op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#TRUNCATE()
	 */
	@Override
	public tREAL[] TRUNCATE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#TRUNCATE(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] TRUNCATE(tREAL op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#ROUND()
	 */
	@Override
	public tREAL[] ROUND()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#ROUND(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] ROUND(tREAL op)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
