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

import java.math.BigInteger;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.sequences.*;
import aloyslisp.core.conditions.*;
import static aloyslisp.core.L.*;

/**
 * cNUMBER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class cNUMBER extends cCELL implements tNUMBER
{

	/* *******************************************************************
	 * CONSTANT
	 */
	/**
	 * Constant 0
	 */
	public static final cINTEGER		ZERO	= new cINTEGER(0);

	/**
	 * Constant 1
	 */
	public static final cINTEGER		ONE		= new cINTEGER(1);

	/**
	 * Constant 2
	 */
	public static final cINTEGER		TWO		= new cINTEGER(2);

	/**
	 * Constant 10
	 */
	public static final cINTEGER		TEN		= new cINTEGER(10);

	/**
	 * Constant PI
	 */
	public static final cDOUBLE_FLOAT	PI		= new cDOUBLE_FLOAT(Math.PI);

	/**
	 * Constant E
	 */
	public static final cDOUBLE_FLOAT	E		= new cDOUBLE_FLOAT(Math.E);

	/**
	 * Constant I
	 */
	public static final cCOMPLEX		I		= new cCOMPLEX(ZERO, ONE);

	/* *******************************************************************
	 * STATIC
	 */
	/**
	 * @param number
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "make-number")
	public static tREAL MAKE_NUMBER( //
			@aArg(name = "str") String nb)
	{
		// floating point
		if (nb.matches("^(-|\\+)?\\d*\\.\\d+([esfdlESFDL](-|\\+)?\\d+)?$")
				|| nb.matches("^(-|\\+)?\\d+(\\.\\d*)?[esfdlESFDL](-|\\+)?\\d+$"))
		{
			// double
			if (nb.matches(".+[lL].+"))
			{
				nb = nb.replaceAll("[lL]", "e") + "d";
				return new cLONG_FLOAT(Double.valueOf(nb));
			}

			if (nb.matches(".+[ldLD].+"))
			{
				nb = nb.replaceAll("[dD]", "e") + "d";
				return new cDOUBLE_FLOAT(Double.valueOf(nb));
			}

			// short
			if (nb.matches(".+[sS].+"))
			{
				nb = nb.replaceAll("[sS]", "e") + "f";
				return new cSHORT_FLOAT(Float.valueOf(nb));
			}
			// float
			if (nb.matches(".+[efEF].+"))
			{
				nb = nb.replaceAll("[efEF]", "e") + "f";
			}

			// by default single float
			return new cSINGLE_FLOAT(Float.valueOf(nb));
		}

		int base = ((tNUMBER) readBase.SYMBOL_VALUE()).COERCE_TO_INTEGER()
				.INTEGER_VALUE();
		if (base < 2 || base > 37)
			base = 10;
		String strBase = "[0-";
		if (base <= 10)
			strBase += "" + (base - 1) + "]";
		else
		{
			strBase += "9A-" + ('A' + (base - 11)) + "]";
		}
		// ratio
		// System.out.println("Match ..." + nb + ":" + "^[-\\+]?" + strBase +
		// "+/"
		// + strBase + "+$");
		if (nb.matches("^[-\\+]?" + strBase + "+/" + strBase + "+"))
		{
			String[] rat = nb.split("/");
			// System.out.println("Match ..." + rat[0] + " / " + rat[1]);
			return new cRATIO(new cINTEGER(rat[0], base), new cINTEGER(rat[1],
					base)).RATIONALIZE_VALUE();
		}

		// Integer
		if (nb.matches("^[-\\+]?" + strBase + "+$"))
		{
			return new cINTEGER(nb, base);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public Integer SXHASH()
	{
		if (ZEROP())
			return 0;
		return COERCE_TO_DOUBLE_FLOAT().SXHASH();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#integerValue()
	 */
	public Integer INTEGER_VALUE()
	{
		return ((cINTEGER) COERCE_TO_INTEGER()).val.intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#longValue()
	 */
	public Long LONG_VALUE()
	{
		return ((cINTEGER) COERCE_TO_INTEGER()).val.longValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#longValue()
	 */
	public BigInteger BIGINT_VALUE()
	{
		return ((cINTEGER) COERCE_TO_INTEGER()).val;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#doubleValue()
	 */
	public Double DOUBLE_VALUE()
	{
		return ((cDOUBLE_FLOAT) COERCE_TO_DOUBLE_FLOAT()).value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tREAL#floatValue()
	 */
	public Float FLOAT_VALUE()
	{
		return ((cSINGLE_FLOAT) COERCE_TO_DOUBLE_FLOAT()).value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#rationalizeValue()
	 */
	public tRATIONAL RATIONALIZE_VALUE()
	{
		throw new LispException("Can't rationalize " + TO_STRING());
	}

	/* *******************************************************************
	 * OPERATORS
	 */
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#EQUALNUM(aloyslisp.core.tT)
	 */
	public boolean EQUALNUM(tT op)
	{
		if (op instanceof tNUMBER)
		{
			return COERCE_TO_NUMBER((cNUMBER) op).SINGLE_EQUALNUM((tNUMBER) op);
		}
		else if (op == NIL)
		{
			return true;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (!EQUALNUM(list.CAR()))
				return false;
			return EQUALNUM(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("number"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ADD(aloyslisp.core.tT)
	 */
	@Override
	public tNUMBER ADD(tT op)
	{
		if (op instanceof tNUMBER)
		{
			return COERCE_TO_NUMBER((tNUMBER) op).SINGLE_ADD((tNUMBER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.ADD((tNUMBER) list.CAR());
			else
				return this.ADD((tNUMBER) list.CAR()).ADD(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("number"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#SUBSTRACT(aloyslisp.core.tT)
	 */
	@Override
	public tNUMBER SUBSTRACT(tT op)
	{
		if (op instanceof tNUMBER)
		{
			return ((cNUMBER) COERCE_TO_NUMBER((cNUMBER) op))
					.SINGLE_SUBSTRACT((tNUMBER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.SUBSTRACT((tNUMBER) list.CAR());
			else
				return this.SUBSTRACT((tNUMBER) list.CAR()).SUBSTRACT(
						list.CDR());
		}

		throw new TYPE_ERROR(op, sym("number"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#MULTIPLY(aloyslisp.core.tT)
	 */
	@Override
	public tNUMBER MULTIPLY(tT op)
	{
		if (op instanceof tNUMBER)
		{
			return COERCE_TO_NUMBER((tNUMBER) op).SINGLE_MULTIPLY((tNUMBER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.MULTIPLY((tNUMBER) list.CAR());
			else
				return this.MULTIPLY((tNUMBER) list.CAR()).MULTIPLY(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("number"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#DIVISION(aloyslisp.core.tT)
	 */
	@Override
	public tNUMBER DIVISION(tT op)
	{
		if (op instanceof tNUMBER)
		{
			return COERCE_TO_NUMBER((cNUMBER) op).SINGLE_DIVISION((tNUMBER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.DIVISION((tNUMBER) list.CAR());
			else
				return this.DIVISION((tNUMBER) list.CAR()).DIVISION(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("number"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ACOSH()
	 */
	@Override
	public tNUMBER ACOSH()
	{
		return ADD(ONE).DIVISION(TWO).SQRT()
				.ADD(SUBSTRACT(ONE).DIVISION(TWO).SQRT()).LOG().MULTIPLY(TWO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ASINH()
	 */
	@Override
	public tNUMBER ASINH()
	{
		return this.ADD(ONE.ADD(this.EXPT(TWO))).LOG();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COSH()
	 */
	@Override
	public tNUMBER COSH()
	{
		tNUMBER xexp = this.EXP();
		return xexp.ADD(xexp.MINUS()).DIVISION(TWO);
	}

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#SINH()
	 */
	@Override
	public tNUMBER SINH()
	{
		tNUMBER xexp = this.EXP();
		return xexp.SUBSTRACT(xexp.MINUS()).DIVISION(TWO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#TANH()
	 */
	@Override
	public tNUMBER TANH()
	{
		tNUMBER xexp = this.EXP();
		return xexp.SUBSTRACT(xexp.MINUS()).DIVISION(xexp.ADD(xexp.MINUS()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ATANH()
	 */
	@Override
	public tNUMBER ATANH()
	{
		return ADD(ONE).LOG().SUBSTRACT(ONE.SUBSTRACT(this).LOG())
				.DIVISION(TWO);
	}

	/**
	 * @return
	 */
	public tNUMBER LOG_BASE(tREAL base)
	{
		if (base.ZEROP())
			return ZERO;
		return LOG().DIVISION(base.LOG());
	}
}
