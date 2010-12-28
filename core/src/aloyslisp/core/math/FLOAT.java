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
	 * @see aloyslisp.core.math.tREAL#equal(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public boolean equal(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#greater(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public boolean greater(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#lower(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public boolean lower(tNUMBER op)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#rational()
	 */
	@Override
	public tRATIONAL rational()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#rationalize()
	 */
	@Override
	public tRATIONAL rationalize()
	{
		// TODO Auto-generated method stub
		return null;
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
	 * @see aloyslisp.core.math.tNUMBER#getRatioValue()
	 */
	@Override
	public RATIO getRatioValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getComplexValue()
	 */
	@Override
	public COMPLEX getComplexValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getIntegerValue()
	 */
	@Override
	public BIGNUM getIntegerValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getFloatValue()
	 */
	@Override
	public SINGLE_FLOAT getFloatValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getDoubleValue()
	 */
	@Override
	public DOUBLE_FLOAT getDoubleValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getShortValue()
	 */
	@Override
	public SHORT_FLOAT getShortValue()
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
	 * @see aloyslisp.core.math.tFLOAT#decode_float(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tFLOAT[] decode_float()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.tFLOAT#integer_decode_float(aloyslisp.core.math.tFLOAT
	 * )
	 */
	@Override
	public tFLOAT[] integer_decode_float()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#scale_float(aloyslisp.core.math.tFLOAT,
	 * aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tFLOAT scale_float(tINTEGER scale)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#float_radix(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tFLOAT float_radix()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#float_sign(aloyslisp.core.math.tFLOAT,
	 * aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tFLOAT float_sign(tFLOAT f2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#float_digits(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tINTEGER float_digits()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.tFLOAT#float_precision(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tINTEGER float_precision()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#DECODE_FLOAT(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tFLOAT[] DECODE_FLOAT()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.tFLOAT#INTEGER_DECODE_FLOAT(aloyslisp.core.math.tFLOAT
	 * )
	 */
	@Override
	public tFLOAT[] INTEGER_DECODE_FLOAT()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#SCALE_FLOAT(aloyslisp.core.math.tFLOAT,
	 * aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tFLOAT SCALE_FLOAT(tINTEGER scale)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#FLOAT_RADIX(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tFLOAT FLOAT_RADIX()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#FLOAT_SIGN(aloyslisp.core.math.tFLOAT,
	 * aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tFLOAT FLOAT_SIGN(tFLOAT f2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#FLOAT_DIGITS(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tINTEGER FLOAT_DIGITS()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.tFLOAT#FLOAT_PRECISION(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tINTEGER FLOAT_PRECISION()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
