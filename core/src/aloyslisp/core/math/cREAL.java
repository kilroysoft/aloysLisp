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

import static aloyslisp.core.engine.L.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.sequences.*;

/**
 * cREAL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class cREAL extends cNUMBER implements tREAL
{

	/* *******************************************************************
	 * COMPARATORS
	 */
	/**
	 * Test greater a > b
	 * 
	 * @param op
	 * @return
	 */
	abstract boolean greater(tREAL op);

	/**
	 * Test lower a < b
	 * 
	 * @param op
	 * @return
	 */
	abstract boolean lower(tREAL op);

	/* *******************************************************************
	 * cRATIONAL
	 */
	/**
	 * @return
	 */
	abstract tRATIONAL rational();

	/**
	 * @return
	 */
	abstract tRATIONAL rationalize();

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#doubleValue()
	 */
	public Double doubleValue()
	{
		return getDoubleValue().value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#complexifyValue()
	 */
	public tNUMBER complexifyValue()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#floatValue()
	 */
	public Float floatValue()
	{
		return getFloatValue().value;
	}

	/**
	 * @param op
	 * @return
	 */
	tREAL mod(tREAL op)
	{
		tREAL[] rest = FLOOR(op);
		return rest[1];
	}

	/**
	 * @param op
	 * @return
	 */
	tREAL rem(tREAL op)
	{
		tREAL[] rest = TRUNCATE(op);
		return rest[1];
	}

	tREAL[] floor()
	{
		return FLOOR(ONE);
	}

	tREAL[] floor(tREAL op)
	{
		tINTEGER quotient = new cBIGNUM(
				((Double) Math.floor(DIVISION(op.getDoubleValue())
						.getDoubleValue().doubleValue())).toString());
		return new tREAL[]
		{ quotient, (tREAL) this.SUBSTRACT(quotient.MULTIPLY(op)) };
	}

	tREAL[] ceiling()
	{
		return CEILING(ONE);
	}

	tREAL[] ceiling(tREAL op)
	{
		tINTEGER quotient = new cBIGNUM(
				((Double) Math.ceil(DIVISION(op.getDoubleValue())
						.getDoubleValue().doubleValue())).toString());
		return new tREAL[]
		{ quotient, (tREAL) this.SUBSTRACT(quotient.MULTIPLY(op)) };
	}

	tREAL[] truncate()
	{
		return TRUNCATE(ONE);
	}

	tREAL[] truncate(tREAL op)
	{
		tINTEGER quotient = DIVISION(op.getDoubleValue()).getIntegerValue();
		return new tREAL[]
		{ quotient, (tREAL) this.SUBSTRACT(quotient.MULTIPLY(op)) };
	}

	tREAL[] round()
	{
		return ROUND(ONE);
	}

	tREAL[] round(tREAL op)
	{
		tINTEGER quotient = new cBIGNUM(Math.round(DIVISION(op.getDoubleValue())
				.getDoubleValue().doubleValue()));
		return new tREAL[]
		{ quotient, (tREAL) this.SUBSTRACT(quotient.MULTIPLY(op)) };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#abs()
	 */
	tREAL abs()
	{
		return lower(ZERO) ? (tREAL) minus() : this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#realpart()
	 */
	tNUMBER realpart()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#imagpart()
	 */
	tNUMBER imagpart()
	{
		return ZERO;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#conjugate()
	 */
	tNUMBER conjugate()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#phase()
	 */
	tNUMBER phase()
	{
		tNUMBER res = ONE.coerce(this);
		return MINUSP() ? res.MINUS() : res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.cNUMBER#zerop()
	 */
	boolean zerop()
	{
		return EQUALNUM(ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sin()
	 */
	@Override
	tNUMBER sin()
	{
		Double res = Math.sin(getDoubleValue().doubleValue());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#cos()
	 */
	@Override
	tNUMBER cos()
	{
		Double res = Math.cos(getDoubleValue().doubleValue());
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
	tNUMBER tan()
	{
		Double res = Math.tan(getDoubleValue().doubleValue());
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
	tNUMBER asin()
	{
		Double res = Math.asin(getDoubleValue().doubleValue());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#acos()
	 */
	@Override
	tNUMBER acos()
	{
		Double res = Math.acos(getDoubleValue().doubleValue());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#atan()
	 */
	@Override
	tNUMBER atan()
	{
		Double res = Math.atan(getDoubleValue().doubleValue());
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
	tNUMBER atan(tREAL opt)
	{
		// TODO crectly write the atan
		Double res = Math.atan(getDoubleValue().doubleValue());
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#log()
	 */
	@Override
	tNUMBER log()
	{
		Double res = Math.log(getDoubleValue().doubleValue());
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
	tNUMBER sqrt()
	{
		Double res = Math.sqrt(getDoubleValue().doubleValue());
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
	tNUMBER exp()
	{
		Double res = Math.exp(getDoubleValue().doubleValue());
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
	tNUMBER expt(tNUMBER power)
	{
		// TODO add power as integer
		Double res = Math.pow(getDoubleValue().doubleValue(),
				power.getDoubleValue().value);
		if (this instanceof cDOUBLE_FLOAT)
			return new cDOUBLE_FLOAT(res);
		else
			return new cSINGLE_FLOAT(res.floatValue());
	}

	/**
	 * @return
	 */
	tNUMBER random()
	{
		return MULTIPLY(new cDOUBLE_FLOAT(Math.random())).coerce(this);
	}

	/**
	 * @param st
	 * @return
	 */
	tNUMBER random(tRANDOM_STATE st)
	{
		return MULTIPLY(new cDOUBLE_FLOAT(Math.random())).coerce(this);
	}

	/**
	 * @return
	 */
	boolean minusp()
	{
		return LOWER(ZERO);
	}

	/**
	 * @return
	 */
	boolean plusp()
	{
		return GREATER(ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#GREATER(aloyslisp.core.tT)
	 */
	@Override
	public boolean GREATER(tT op)
	{
		if (op instanceof tREAL)
		{
			return ((cREAL) coerce((cREAL) op)).greater((tREAL) op);
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
	 * @see aloyslisp.core.math.tREAL#LOWER(aloyslisp.core.tT)
	 */
	@Override
	public boolean LOWER(tT op)
	{
		if (op instanceof tREAL)
		{
			return ((cREAL) coerce((cREAL) op)).lower((tREAL) op);
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
		return minusp();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#PLUSP()
	 */
	@Override
	public boolean PLUSP()
	{
		return plusp();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#RATIONAL()
	 */
	@Override
	public tRATIONAL RATIONAL()
	{
		return rational();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#RATIONALIZE()
	 */
	@Override
	public tRATIONAL RATIONALIZE()
	{
		return rationalize();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#MOD(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL MOD(tREAL op)
	{
		return mod(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#REM(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL REM(tREAL op)
	{
		return rem(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#FLOOR(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] FLOOR(tT op)
	{
		if (op == NIL)
			return floor();
		else if (op instanceof tREAL)
			return floor((tREAL) op);

		throw new TYPE_ERROR(op, sym("real"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#CEILING(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] CEILING(tT op)
	{
		if (op == NIL)
			return ceiling();
		else if (op instanceof tREAL)
			return ceiling((tREAL) op);

		throw new TYPE_ERROR(op, sym("real"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#TRUNCATE(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] TRUNCATE(tT op)
	{
		if (op == NIL)
			return truncate();
		else if (op instanceof tREAL)
			return truncate((tREAL) op);

		throw new TYPE_ERROR(op, sym("real"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#ROUND(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tREAL[] ROUND(tT op)
	{
		if (op == NIL)
			return round();
		else if (op instanceof tREAL)
			return round((tREAL) op);

		throw new TYPE_ERROR(op, sym("real"));
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
	 * @see
	 * aloyslisp.core.math.tNUMBER#RANDOM(aloyslisp.core.numbers.tRANDOM_STATE)
	 */
	@Override
	public tNUMBER RANDOM(tT st)
	{
		if (st == NIL)
			return random();
		else if (st instanceof tRANDOM_STATE)
			return random((tRANDOM_STATE) st);

		throw new TYPE_ERROR(st, sym("random-state"));
	}

}
