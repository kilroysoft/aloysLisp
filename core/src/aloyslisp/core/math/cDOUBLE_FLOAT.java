/**
 * aloysLisp.
 * <p>
 * A LISP interpreter, compiler and library.
 * <p>
 * Copyright (C) 2010-2011 kilroySoft <aloyslisp@kilroysoft.ch>
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
// IP 23 déc. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import aloyslisp.annotations.aBuiltIn;
import aloyslisp.core.*;
import static aloyslisp.core.L.*;

/**
 * cDOUBLE_FLOAT
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aBuiltIn(lispClass = "float", lispType = "double-float", doc = "t_short_")
public class cDOUBLE_FLOAT extends cFLOAT implements tDOUBLE_FLOAT
{
	public Double	value;

	/**
	 * @param init
	 */
	public cDOUBLE_FLOAT(double init)
	{
		value = init;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ratioValue()
	 */
	@Override
	public cRATIO COERCE_TO_RATIO()
	{
		// TODO some to reflect about
		tRATIONAL rat = RATIONALIZE();
		if (rat instanceof tRATIO)
			return (cRATIO) rat;
		return new cRATIO((tINTEGER) rat, ONE);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#complexValue()
	 */
	@Override
	public cCOMPLEX COERCE_TO_COMPLEX()
	{
		return new cCOMPLEX(this, ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#integerValue()
	 */
	@Override
	public tINTEGER COERCE_TO_INTEGER()
	{
		// TODO to think about
		return FLOOR(ONE)[0].COERCE_TO_INTEGER();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#floatValue()
	 */
	@Override
	public cSINGLE_FLOAT COERCE_TO_SINGLE_FLOAT()
	{
		return new cSINGLE_FLOAT(value.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#doubleValue()
	 */
	@Override
	public cDOUBLE_FLOAT COERCE_TO_DOUBLE_FLOAT()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#shortValue()
	 */
	@Override
	public cSHORT_FLOAT COERCE_TO_SHORT_FLOAT()
	{
		return new cSHORT_FLOAT(value.shortValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#coerce(aloyslisp.core.math.tNUMBER)
	 */
	public cNUMBER COERCE_TO_NUMBER(tNUMBER var)
	{
		if (var instanceof cCOMPLEX)
			return COERCE_TO_COMPLEX();

		return this;
	}

	public Integer SXHASH()
	{
		return str(TO_STRING()).SXHASH();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#TO_STRING()
	 */
	public String TO_STRING()
	{
		return value.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#decode_float()
	 */
	@Override
	public tT[] DECODE_FLOAT()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#integer_decode_float()
	 */
	@Override
	public tT[] INTEGER_DECODE_FLOAT()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#scale_float(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tFLOAT SCALE_FLOAT(tINTEGER scale)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#float_radix()
	 */
	@Override
	public tFLOAT FLOAT_RADIX()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#float_sign(aloyslisp.core.math.tFLOAT)
	 */
	@Override
	public tFLOAT FLOAT_SIGN(tFLOAT f2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#float_digits()
	 */
	@Override
	public tINTEGER FLOAT_DIGITS()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tFLOAT#float_precision()
	 */
	@Override
	public tINTEGER FLOAT_PRECISION()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#greater(aloyslisp.core.math.tREAL)
	 */
	@Override
	public boolean SINGLE_GREATER(tREAL op)
	{
		return value > op.COERCE_TO_SINGLE_FLOAT().DOUBLE_VALUE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#lower(aloyslisp.core.math.tREAL)
	 */
	@Override
	public boolean SINGLE_LOWER(tREAL op)
	{
		return value < op.COERCE_TO_SINGLE_FLOAT().DOUBLE_VALUE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#rational()
	 */
	@Override
	public tRATIONAL RATIONAL()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#rationalize()
	 */
	@Override
	public tRATIONAL RATIONALIZE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#equalnum(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public boolean SINGLE_EQUALNUM(tNUMBER op)
	{
		return value == op.COERCE_TO_SINGLE_FLOAT().DOUBLE_VALUE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_ADD(tNUMBER op)
	{
		return new cDOUBLE_FLOAT(value
				+ op.COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_SUBSTRACT(tNUMBER op)
	{
		return new cDOUBLE_FLOAT(value
				- op.COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#minus()
	 */
	@Override
	public tNUMBER MINUS()
	{
		return new cDOUBLE_FLOAT(-value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#inversion()
	 */
	@Override
	public tNUMBER INVERSION()
	{
		return new cDOUBLE_FLOAT(1 / value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_MULTIPLY(tNUMBER op)
	{
		return new cDOUBLE_FLOAT(value
				* op.COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#division(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_DIVISION(tNUMBER op)
	{
		return new cDOUBLE_FLOAT(value
				/ op.COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
	}

}
