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
// IP 26 déc. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.sequences.*;
import static aloyslisp.core.L.*;

/**
 * cREAL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class cREAL extends cNUMBER implements tREAL
{

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_COMPLEX()
	 */
	public cNUMBER COERCE_TO_COMPLEX()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#abs()
	 */
	public tREAL ABS()
	{
		return SINGLE_LOWER(ZERO) ? (tREAL) MINUS() : this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#acos()
	 */
	@Override
	public tNUMBER ACOS()
	{
		Double res = Math.acos(COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#asin()
	 */
	@Override
	public tNUMBER ASIN()
	{
		Double res = Math.asin(COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#atan(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER ATAN(tT opt)
	{
		// TODO crectly write the atan
		Double res = Math.atan(COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#CEILING(aloyslisp.core.tT)
	 */
	public tREAL[] CEILING(tT op)
	{
		if (op == NIL)
			op = ONE;
		else if (op instanceof tREAL)
			;
		else
			throw new TYPE_ERROR(op, sym("real"));
		tINTEGER quotient = new cINTEGER(((Double) Math.ceil(DIVISION(
				((tREAL) op).COERCE_TO_DOUBLE_FLOAT()).COERCE_TO_DOUBLE_FLOAT()
				.DOUBLE_VALUE())).toString());
		return new tREAL[]
		{ quotient, (tREAL) this.SUBSTRACT(quotient.MULTIPLY(op)) };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#CIS()
	 */
	public tCOMPLEX CIS()
	{
		return new cCOMPLEX((tREAL) COS(), (tREAL) SIN());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#conjugate()
	 */
	public tNUMBER CONJUGATE()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#cos()
	 */
	@Override
	public tNUMBER COS()
	{
		Double res = Math.cos(COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#exp()
	 */
	@Override
	public tNUMBER EXP()
	{
		Double res = Math.exp(COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#expt(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER EXPT(tNUMBER power)
	{
		// TODO add power as integer
		Double res = Math.pow(DOUBLE_VALUE(), power.DOUBLE_VALUE());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#FLOOR(aloyslisp.core.tT)
	 */
	public tREAL[] FLOOR(tT op)
	{
		if (op == NIL)
			op = ONE;
		else if (op instanceof tREAL)
			;
		else
			throw new TYPE_ERROR(op, sym("real"));
		tINTEGER quotient = new cINTEGER(((Double) Math.floor(DIVISION(
				((tREAL) op).COERCE_TO_DOUBLE_FLOAT()).COERCE_TO_DOUBLE_FLOAT()
				.DOUBLE_VALUE())).toString());
		return new tREAL[]
		{ quotient, (tREAL) this.SUBSTRACT(quotient.MULTIPLY(op)) };
	}

	/* *******************************************************************
	 * COMPARATORS
	 */
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#GREATER(aloyslisp.core.tT)
	 */
	@Override
	public boolean GREATER(tT op)
	{
		if (op instanceof tREAL)
		{
			return ((cREAL) COERCE_TO_NUMBER((cREAL) op))
					.SINGLE_GREATER((tREAL) op);
		}
		else if (op == NIL)
		{
			return true;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (!GREATER(list.CAR()))
				return false;
			return GREATER(list.CDR());
		}
	
		throw new TYPE_ERROR(op, sym("real"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#imagpart()
	 */
	public tNUMBER IMAGPART()
	{
		return ZERO;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#log()
	 */
	@Override
	public tNUMBER LOG()
	{
		Double res = Math.log(COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#LOWER(aloyslisp.core.tT)
	 */
	@Override
	public boolean LOWER(tT op)
	{
		if (op instanceof tREAL)
		{
			return ((cREAL) COERCE_TO_NUMBER((cREAL) op))
					.SINGLE_LOWER((tREAL) op);
		}
		else if (op == NIL)
		{
			return true;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (!GREATER(list.CAR()))
				return false;
			return GREATER(list.CDR());
		}
	
		throw new TYPE_ERROR(op, sym("real"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#MINUSP()
	 */
	@Override
	public boolean MINUSP()
	{
		return LOWER(ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#MOD(aloyslisp.core.math.tREAL)
	 */
	public tREAL MOD(tREAL op)
	{
		tREAL[] rest = FLOOR(op);
		return rest[1];
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#phase()
	 */
	public tNUMBER PHASE()
	{
		tNUMBER res = ONE.COERCE_TO_NUMBER(this);
		return MINUSP() ? res.MINUS() : res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#PLUSP()
	 */
	@Override
	public boolean PLUSP()
	{
		return GREATER(ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.math.tNUMBER#RANDOM(aloyslisp.core.numbers.tRANDOM_STATE)
	 */
	@Override
	public tNUMBER RANDOM(tT st)
	{
		if (st == NIL)
			return MULTIPLY(new cDOUBLE_FLOAT(Math.random())).COERCE_TO_NUMBER(
					this);
		else if (st instanceof tRANDOM_STATE)
			return MULTIPLY(new cDOUBLE_FLOAT(Math.random())).COERCE_TO_NUMBER(
					this);
	
		throw new TYPE_ERROR(st, sym("random-state"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#realpart()
	 */
	public tNUMBER REALPART()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#REM(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL REM(tREAL op)
	{
		tREAL[] rest = TRUNCATE(op);
		return rest[1];
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#ROUND(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] ROUND(tT op)
	{
		if (op == NIL)
			op = ONE;
		else if (op instanceof tREAL)
			;
		else
			throw new TYPE_ERROR(op, sym("real"));
		tINTEGER quotient = new cINTEGER(Math.round(DIVISION(
				((tREAL) op).COERCE_TO_DOUBLE_FLOAT()).COERCE_TO_DOUBLE_FLOAT()
				.DOUBLE_VALUE()));
		return new tREAL[]
		{ quotient, (tREAL) this.SUBSTRACT(quotient.MULTIPLY(op)) };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sin()
	 */
	@Override
	public tNUMBER SIN()
	{
		Double res = Math.sin(COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sqrt()
	 */
	@Override
	public tNUMBER SQRT()
	{
		Double res = Math.sqrt(COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#tan()
	 */
	@Override
	public tNUMBER TAN()
	{
		Double res = Math.tan(COERCE_TO_DOUBLE_FLOAT().DOUBLE_VALUE());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#TRUNCATE(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] TRUNCATE(tT op)
	{
		if (op == NIL)
			op = ONE;
		else if (op instanceof tREAL)
			;
		else
			throw new TYPE_ERROR(op, sym("real"));
		tINTEGER quotient = DIVISION(((tREAL) op).COERCE_TO_DOUBLE_FLOAT())
				.COERCE_TO_INTEGER();
		return new tREAL[]
		{ quotient, (tREAL) this.SUBSTRACT(quotient.MULTIPLY(op)) };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#zerop()
	 */
	public boolean ZEROP()
	{
		return EQUALNUM(ZERO);
	}

}
