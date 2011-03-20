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

import aloyslisp.annotations.BuiltIn;
import aloyslisp.core.tT;

/**
 * cSINGLE_FLOAT
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@BuiltIn(classOf = "float", typeOf = "single-float", doc = "t_short_")
public class cSINGLE_FLOAT extends cFLOAT implements tSINGLE_FLOAT
{
	public Float	value;

	/**
	 * @param init
	 */
	public cSINGLE_FLOAT(float init)
	{
		value = init;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#toString()
	 */
	public String toString()
	{
		return value.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#complexValue()
	 */
	@Override
	public cCOMPLEX getComplexValue()
	{
		return new cCOMPLEX(this, ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#floatValue()
	 */
	@Override
	public cSINGLE_FLOAT getFloatValue()
	{
		return new cSINGLE_FLOAT(value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#doubleValue()
	 */
	@Override
	public cDOUBLE_FLOAT getDoubleValue()
	{
		return new cDOUBLE_FLOAT(value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#shortValue()
	 */
	@Override
	public cSHORT_FLOAT getShortValue()
	{
		return new cSHORT_FLOAT(value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#coerce(aloyslisp.core.math.tNUMBER)
	 */
	public cNUMBER coerce(tNUMBER var)
	{
		if (var instanceof cDOUBLE_FLOAT)
			return getDoubleValue();

		if (var instanceof cCOMPLEX)
			return getComplexValue();

		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#equal(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	boolean equalnum(tNUMBER op)
	{
		return value == op.getFloatValue().floatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#greater(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	boolean greater(tREAL op)
	{
		return value > op.getFloatValue().floatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#lower(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	boolean lower(tREAL op)
	{
		return value < op.getFloatValue().floatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#rational()
	 */
	@Override
	tRATIONAL rational()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#rationalize()
	 */
	@Override
	tRATIONAL rationalize()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER add(tNUMBER op)
	{
		return new cSINGLE_FLOAT(value + op.getFloatValue().floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER substract(tNUMBER op)
	{
		return new cSINGLE_FLOAT(value - op.getFloatValue().floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#minus()
	 */
	@Override
	tNUMBER minus()
	{
		return new cSINGLE_FLOAT(-value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#inversion()
	 */
	@Override
	tNUMBER inversion()
	{
		return new cSINGLE_FLOAT(1 / value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER multiply(tNUMBER op)
	{
		return new cSINGLE_FLOAT(value * op.getFloatValue().floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#division(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER division(tNUMBER op)
	{
		return new cSINGLE_FLOAT(value / op.getFloatValue().floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#decode_float(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	tT[] decode_float()
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
	tT[] integer_decode_float()
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
	tFLOAT scale_float(tINTEGER scale)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#float_radix(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	tFLOAT float_radix()
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
	tFLOAT float_sign(tFLOAT f2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#float_digits(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	tINTEGER float_digits()
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
	tINTEGER float_precision()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
