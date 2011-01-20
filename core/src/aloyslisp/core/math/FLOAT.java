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
// IP 28 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import aloyslisp.core.conditions.LispException;
import aloyslisp.core.plugs.tT;

/**
 * FLOAT
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class FLOAT extends REAL implements tFLOAT
{
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ratioValue()
	 */
	@Override
	public RATIO getRatioValue()
	{
		throw new LispException("Cannot coerce FLOAT to RATIO");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ratioValue()
	 */
	@Override
	public BIGNUM getIntegerValue()
	{
		throw new LispException("Cannot coerce FLOAT to INTEGER");
	}

	/**
	 * @param f
	 * @return
	 */
	abstract tT[] decode_float();

	/**
	 * @param f
	 * @return
	 */
	abstract tT[] integer_decode_float();

	/**
	 * @param f
	 * @param scale
	 * @return
	 */
	abstract tFLOAT scale_float( //
			tINTEGER scale);

	/**
	 * @param f
	 * @return
	 */
	abstract tFLOAT float_radix();

	/**
	 * @param f
	 * @param f2
	 * @return
	 */
	abstract tFLOAT float_sign(tFLOAT f2);

	/**
	 * @param f
	 * @return
	 */
	abstract tINTEGER float_digits();

	/**
	 * @param f
	 * @return
	 */
	abstract tINTEGER float_precision();

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.tFLOAT#INTEGER_DECODE_FLOAT(aloyslisp.core.math.tFLOAT
	 * )
	 */
	@Override
	public tT[] INTEGER_DECODE_FLOAT()
	{
		return integer_decode_float();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#SCALE_FLOAT(aloyslisp.core.math.tFLOAT,
	 * aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tFLOAT SCALE_FLOAT(tINTEGER scale)
	{
		return scale_float(scale);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#FLOAT_RADIX(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tFLOAT FLOAT_RADIX()
	{
		return float_radix();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#FLOAT_SIGN(aloyslisp.core.math.tFLOAT,
	 * aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tFLOAT FLOAT_SIGN(tFLOAT f2)
	{
		return float_sign(f2);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#FLOAT_DIGITS(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tINTEGER FLOAT_DIGITS()
	{
		return float_digits();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.tFLOAT#FLOAT_PRECISION(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tINTEGER FLOAT_PRECISION()
	{
		return float_precision();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#DECODE_FLOAT()
	 */
	@Override
	public tT[] DECODE_FLOAT()
	{
		return decode_float();
	}

}
