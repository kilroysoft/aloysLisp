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

import aloyslisp.core.plugs.*;

/**
 * NUMBER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class NUMBER extends CELL implements tNUMBER
{

	public static final BIGNUM	ONE		= new BIGNUM(1);

	public static final BIGNUM	ZERO	= new BIGNUM(0);

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ADD(aloyslisp.core.plugs.tT)
	 */
	@Override
	public tNUMBER ADD(tT op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#SUBSTRACT(aloyslisp.core.plugs.tT)
	 */
	@Override
	public tNUMBER SUBSTRACT(tT op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#MINUS()
	 */
	@Override
	public tNUMBER MINUS()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#INVERSION()
	 */
	@Override
	public tNUMBER INVERSION()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#MULTIPLY(aloyslisp.core.plugs.tT)
	 */
	@Override
	public tNUMBER MULTIPLY(tT op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#DIVISION(aloyslisp.core.plugs.tT)
	 */
	@Override
	public tNUMBER DIVISION(tT op)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#REALPART()
	 */
	@Override
	public tNUMBER REALPART()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#IMAGPART()
	 */
	@Override
	public tNUMBER IMAGPART()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#CONJUGATE()
	 */
	@Override
	public tNUMBER CONJUGATE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#PHASE()
	 */
	@Override
	public tNUMBER PHASE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ABS()
	 */
	@Override
	public tREAL ABS()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ZEROP()
	 */
	@Override
	public boolean ZEROP()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#CIS()
	 */
	@Override
	public tNUMBER CIS()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#SIN()
	 */
	@Override
	public tNUMBER SIN()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COS()
	 */
	@Override
	public tNUMBER COS()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#TAN()
	 */
	@Override
	public tNUMBER TAN()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ASIN()
	 */
	@Override
	public tNUMBER ASIN()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ACOS()
	 */
	@Override
	public tNUMBER ACOS()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ATAN()
	 */
	@Override
	public tNUMBER ATAN()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ATAN(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tNUMBER ATAN(tREAL opt)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#SINH()
	 */
	@Override
	public tNUMBER SINH()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COSH()
	 */
	@Override
	public tNUMBER COSH()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#TANH()
	 */
	@Override
	public tNUMBER TANH()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ASINH()
	 */
	@Override
	public tNUMBER ASINH()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ACOSH()
	 */
	@Override
	public tNUMBER ACOSH()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ATANH()
	 */
	@Override
	public tNUMBER ATANH()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#LOG()
	 */
	@Override
	public tNUMBER LOG()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#SQRT()
	 */
	@Override
	public tNUMBER SQRT()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#EXP()
	 */
	@Override
	public tNUMBER EXP()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#EXPT(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER EXPT(tNUMBER power)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#RANDOM()
	 */
	@Override
	public tNUMBER RANDOM()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.tNUMBER#RANDOM(aloyslisp.core.numbers.tRANDOM_STATE)
	 */
	@Override
	public tNUMBER RANDOM(tRANDOM_STATE st)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
