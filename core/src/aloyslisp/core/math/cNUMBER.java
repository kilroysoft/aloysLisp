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

	/**
	 * Constant 0
	 */
	public static final cBIGNUM			ZERO	= new cBIGNUM(0);

	/**
	 * Constant 1
	 */
	public static final cBIGNUM			ONE		= new cBIGNUM(1);

	/**
	 * Constant 2
	 */
	public static final cBIGNUM			TWO		= new cBIGNUM(2);

	/**
	 * Constant 10
	 */
	public static final cBIGNUM			TEN		= new cBIGNUM(10);

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

	/**
	 * @return
	 */
	abstract tNUMBER complexifyValue();

	/* *******************************************************************
	 * OPERATORS
	 */
	/**
	 * Test equality a == b
	 * 
	 * @param op
	 * @return
	 */
	abstract boolean equalnum(tNUMBER op);

	/**
	 * Add a + b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	abstract tNUMBER add(tNUMBER op);

	/**
	 * Substract a - b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	abstract tNUMBER substract(tNUMBER op);

	/**
	 * Minus -a
	 * 
	 * @param a
	 * @return
	 */
	abstract tNUMBER minus();

	/**
	 * Inversion 1/a
	 * 
	 * @param a
	 * @return
	 */
	abstract tNUMBER inversion();

	/**
	 * Multiply a * b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	abstract tNUMBER multiply(tNUMBER op);

	/**
	 * Division a / b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	abstract tNUMBER division(tNUMBER op);

	/* *******************************************************************
	 * ACCESSORS
	 */
	/**
	 * /**
	 * Real value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	abstract tNUMBER realpart();

	/**
	 * Imaginary part (for complex) a + bi -> b
	 * 
	 * @param a
	 * @return
	 */
	abstract tNUMBER imagpart();

	/* *******************************************************************
	 * FUNCTIONS cCOMPLEX
	 */
	/**
	 * cCOMPLEX conjugate a + bi -> a - bi
	 * 
	 * @param a
	 * @return
	 */
	abstract tNUMBER conjugate();

	/**
	 * Polar value (for complex)
	 * 
	 * @param a
	 * @return
	 */
	abstract tNUMBER phase();

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Absolute value ||a||, mod for complex
	 * 
	 * @return
	 */
	abstract tREAL abs();

	/**
	 * @return
	 */
	abstract boolean zerop();

	/**
	 * @return
	 */
	abstract tNUMBER sin();

	/**
	 * @return
	 */
	abstract tNUMBER cos();

	/**
	 * @return
	 */
	abstract tNUMBER tan();

	/**
	 * @return
	 */
	abstract tNUMBER asin();

	/**
	 * @return
	 */
	abstract tNUMBER acos();

	/**
	 * @return
	 */
	abstract tNUMBER atan();

	/**
	 * @param opt
	 * @return
	 */
	abstract tNUMBER atan(tREAL opt);

	/**
	 * @return
	 */
	abstract tNUMBER log();

	/**
	 * @return
	 */
	tNUMBER log(tREAL base)
	{
		if (base.ZEROP())
			return ZERO;
		return LOG().DIVISION(base.LOG());
	}

	/**
	 * @return
	 */
	abstract tNUMBER sqrt();

	/**
	 * @return
	 */
	abstract tNUMBER exp();

	/**
	 * @param power
	 * @return
	 */
	abstract tNUMBER expt(tNUMBER power);

	/**
	 * @param number
	 * @return
	 */
	public static tREAL create(String nb)
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

		int base = ((tNUMBER) readBase.SYMBOL_VALUE()).getIntegerValue()
				.integerValue();
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
			return new cRATIO(new cBIGNUM(rat[0], base), new cBIGNUM(rat[1],
					base)).rationalizeValue();
		}

		// Integer
		if (nb.matches("^[-\\+]?" + strBase + "+$"))
		{
			return new cBIGNUM(nb, base);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#EQUALNUM(aloyslisp.core.tT)
	 */
	public boolean EQUALNUM(tT op)
	{
		if (op instanceof tNUMBER)
		{
			return ((cNUMBER) coerce((cNUMBER) op)).equalnum((tNUMBER) op);
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
			return ((cNUMBER) coerce((cNUMBER) op)).add((tNUMBER) op);
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
			return ((cNUMBER) coerce((cNUMBER) op)).substract((tNUMBER) op);
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
	 * @see aloyslisp.core.math.tNUMBER#MINUS()
	 */
	@Override
	public tNUMBER MINUS()
	{
		return minus();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#INVERSION()
	 */
	@Override
	public tNUMBER INVERSION()
	{
		return inversion();
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
			return ((cNUMBER) coerce((cNUMBER) op)).multiply((tNUMBER) op);
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
			return ((cNUMBER) coerce((cNUMBER) op)).division((tNUMBER) op);
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
	 * @see aloyslisp.core.math.tNUMBER#REALPART()
	 */
	@Override
	public tNUMBER REALPART()
	{
		return realpart();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#IMAGPART()
	 */
	@Override
	public tNUMBER IMAGPART()
	{
		return imagpart();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#CONJUGATE()
	 */
	@Override
	public tNUMBER CONJUGATE()
	{
		return conjugate();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#PHASE()
	 */
	@Override
	public tNUMBER PHASE()
	{
		return phase();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ABS()
	 */
	@Override
	public tREAL ABS()
	{
		return abs();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ZEROP()
	 */
	@Override
	public boolean ZEROP()
	{
		return zerop();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#SIN()
	 */
	@Override
	public tNUMBER SIN()
	{
		return sin();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COS()
	 */
	@Override
	public tNUMBER COS()
	{
		return cos();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#TAN()
	 */
	@Override
	public tNUMBER TAN()
	{
		return tan();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ASIN()
	 */
	@Override
	public tNUMBER ASIN()
	{
		return asin();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ACOS()
	 */
	@Override
	public tNUMBER ACOS()
	{
		return acos();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#ATAN(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tNUMBER ATAN(tT opt)
	{
		if (opt == NIL)
		{
			return atan();
		}
		else if (opt instanceof tREAL)
		{
			return atan((tREAL) opt);
		}

		throw new TYPE_ERROR(opt, sym("real"));
	}

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
	 * @see aloyslisp.core.math.tNUMBER#COSH()
	 */
	@Override
	public tNUMBER COSH()
	{
		tNUMBER xexp = this.EXP();
		return xexp.ADD(xexp.MINUS()).DIVISION(TWO);
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
	 * @see aloyslisp.core.math.tNUMBER#ASINH()
	 */
	@Override
	public tNUMBER ASINH()
	{
		return this.ADD(ONE.ADD(this.EXPT(TWO))).LOG();
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
	 * @see aloyslisp.core.math.tNUMBER#ATANH()
	 */
	@Override
	public tNUMBER ATANH()
	{
		return ADD(ONE).LOG().SUBSTRACT(ONE.SUBSTRACT(this).LOG())
				.DIVISION(TWO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#LOG()
	 */
	@Override
	public tNUMBER LOG()
	{
		return log();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#SQRT()
	 */
	@Override
	public tNUMBER SQRT()
	{
		return sqrt();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#EXP()
	 */
	@Override
	public tNUMBER EXP()
	{
		return exp();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#EXPT(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER EXPT(tNUMBER power)
	{
		return expt(power);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public int hashCode()
	{
		if (ZEROP())
			return 0;
		return getDoubleValue().hashCode();
	}

}
