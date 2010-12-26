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
// IP 11 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import static aloyslisp.commonlisp.L.*;
import java.util.*;

import aloyslisp.core.conditions.*;
import aloyslisp.core.math.*;
import aloyslisp.core.types.*;

/**
 * NUMBER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class NUMBER extends CELL implements tNUMBER
{
	/**
	 * Table of operator according to weight
	 */
	protected static INTEGER				zero	= new INTEGER(0);

	/**
	 * 
	 */
	protected static INTEGER				one		= new INTEGER(1);

	/**
	 * 
	 */
	static final IMathFuncs[]				oper	=
													{//
													one, // byte
			one, // short
			one, // int
			new INTEGER(0L), // long
			new RATIO(), // fractional
			new FLOAT((float) 0), // float
			new DOUBLE((double) 0), // double
			new COMPLEX(one, one), // complex
													};
	/**
	 * Types of numbers by wheight
	 */
	static final HashMap<String, Integer>	types	= new HashMap<String, Integer>();

	static
	{
		types.put("Byte", 0);
		types.put("Short", 1);
		types.put("Integer", 2);
		types.put("Long", 3);
		types.put("NumRatio", 4);
		types.put("Float", 5);
		types.put("Double", 6);
		types.put("NumComplex", 7);
	}

	/**
	 * Numeric value, can't be changed
	 */
	public INumber							value;

	/**
	 * Constructor
	 * 
	 * @param val
	 */
	public NUMBER(INumber val)
	{
		value = val;
	}

	/**
	 * Constructor
	 * 
	 * @param val
	 */
	public NUMBER(int val)
	{
		value = new NumInteger(val);
	}

	/**
	 * @param val
	 */
	public NUMBER(long val)
	{
		value = new NumInteger(val);
	}

	/**
	 * @param val
	 */
	public NUMBER(float val)
	{
		value = new NumFloat(val);
	}

	/**
	 * @param val
	 */
	public NUMBER(short val)
	{
		value = new NumShort(val);
	}

	/**
	 * @param val
	 */
	public NUMBER(double val)
	{
		value = new NumDouble(val);
	}

	/**
	 * @param nb
	 * @return
	 */
	public static tNUMBER create(String nb)
	{
		// floating point
		if (nb.matches("^(-|\\+)?\\d*\\.\\d+([esfdlESFDL](-|\\+)?\\d+)?$")
				|| nb.matches("^(-|\\+)?\\d+(\\.\\d*)?[esfdlESFDL](-|\\+)?\\d+$"))
		{
			System.out.println("nb = " + nb);
			// double
			if (nb.matches("^[^ldLD]*[ldLD].*$"))
			{
				System.out.println("nb = " + nb);
				nb = nb.replaceFirst("[ldLD]", "e") + "d";
				System.out.println("nb = " + nb);
				return new DOUBLE(Double.valueOf(nb));
			}

			// float
			if (nb.matches("^[^esfESF]*[esfESF].*$"))
			{
				System.out.println("nb = " + nb);
				nb = nb.replaceFirst("[esfESF]", "e") + "f";
				System.out.println("nb = " + nb);
			}
			return new FLOAT(Float.valueOf(nb));
		}

		int base = ((tNUMBER) readBase.SYMBOL_VALUE()).getValue().getIntegerValue();
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
		if (nb.matches("^(-|\\+)?" + strBase + "+/" + strBase + "+$"))
		{
			String[] rat = nb.split("/");
			return new RATIO(create(rat[0]), create(rat[1]));
		}

		// Integer
		if (nb.matches("^(-|\\+)?" + strBase + "+$"))
		{
			try
			{
				return new INTEGER(Integer.parseInt(nb, base));
			}
			catch (NumberFormatException e)
			{
				return new INTEGER(Long.parseLong(nb, base));
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public tNUMBER clone()
	{
		throw new LispErrorFunctionCannotApplyOn("clone", this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#getValue()
	 */
	public INumber getValue()
	{
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#compile()
	 */
	public String COMPILE()
	{
		return "new " + getClass().getCanonicalName() + "(" + value + ")";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#getWeight()
	 */
	public Integer getWeight()
	{
		return types.get(value.getClass().getSimpleName());
	}

	/**
	 * @param weight
	 * @return
	 */
	static IMathFuncs oper(Integer weight)
	{
		return oper[weight];
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#integerValue()
	 */
	public NumInteger integerValue()
	{
		return ((INumber) value).getIntegerValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#floatValue()
	 */
	public NumFloat floatValue()
	{
		return value.getFloatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#doubleValue()
	 */
	public NumDouble doubleValue()
	{
		return ((INumber) value).getDoubleValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#shortValue()
	 */
	public NumShort shortValue()
	{
		return ((INumber) value).getShortValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#ratioValue()
	 */
	public NumRatio ratioValue()
	{
		if (value instanceof NumComplex)
			return new NumRatio(((NumComplex) value).getReal().make(), nInt(1));
		else if (value instanceof NumRatio)
			return (NumRatio) value;
		else
			return new NumRatio(nLong(((INumber) value).getIntegerValue()), nInt(1));

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#complexValue()
	 */
	public NumComplex complexValue()
	{
		if (value instanceof NumRatio)
			return new NumComplex(nDouble(((INumber) value).getDoubleValue()),
					nInt(0));
		else if (value instanceof NumComplex)
			return (NumComplex) value;
		else
			return new NumComplex(nDouble(((INumber) value).getDoubleValue()),
					nInt(0));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#printable()
	 */
	public String printable()
	{
		return value.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#copy()
	 */
	public tT copy()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#acos()
	 */
	public tNUMBER ACOS()
	{
		return new DOUBLE(Math.acos(getValue().getDoubleValue().value));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#asin()
	 */
	public tNUMBER ASIN()
	{
		return new DOUBLE(Math.asin(getValue().getDoubleValue().value));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#atan()
	 */
	public tNUMBER ATAN()
	{
		return new DOUBLE(Math.atan(getValue().getDoubleValue().value));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#atan(aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER ATAN(tNUMBER b)
	{
		return new DOUBLE(Math.atan2(getValue().getDoubleValue().value, b
				.getValue().getDoubleValue().value));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#ceiling()
	 */
	public tNUMBER CEILING()
	{
		return DOUBLE.make(Math.ceil(getValue().getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#cos()
	 */
	public tNUMBER COS()
	{
		return DOUBLE.make(Math.cos(getValue().getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#cos()
	 */
	public tNUMBER CIS()
	{
		return COMPLEX.make(Math.cos(getValue().getDoubleValue()),
				Math.sin(getValue().getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#exp()
	 */
	public tNUMBER EXP()
	{
		return DOUBLE.make(Math.exp(getValue().getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#floor()
	 */
	public tNUMBER FLOOR()
	{
		return DOUBLE.make(Math.floor(getValue().getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tNUMBER#IEEEremainder(aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER IEEEremainder(tNUMBER b)
	{
		return DOUBLE.make(Math.IEEEremainder(getValue().getDoubleValue(), b
				.getValue().getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#log()
	 */
	public tNUMBER LOG()
	{
		return DOUBLE.make(Math.log(getValue().getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#expt(aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER EXPT(tNUMBER b)
	{
		return DOUBLE.make(Math.pow(getValue().getDoubleValue(), b.getValue()
				.getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#random()
	 */
	public tNUMBER RANDOM(tT randomState)
	{
		tNUMBER res = DOUBLE.make(Math.random()).MULTIPLY(this);
		if (this instanceof tINTEGER)
		{
			res = res.FLOOR();
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#sin()
	 */
	public tNUMBER SIN()
	{
		return DOUBLE.make(Math.sin(getValue().getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#sqrt()
	 */
	public tNUMBER SQRT()
	{
		return DOUBLE.make(Math.sqrt(getValue().getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#tan()
	 */
	public tNUMBER TAN()
	{
		return DOUBLE.make(Math.tan(getValue().getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#toDegrees()
	 */
	public tNUMBER toDegrees()
	{
		return DOUBLE.make(Math.toDegrees(getValue().getDoubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#toRadians()
	 */
	public tNUMBER toRadians()
	{
		return DOUBLE.make(Math.toRadians(getValue().getDoubleValue()));
	}

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	private IMathFuncs getOper(tNUMBER a, tNUMBER b)
	{
		return oper(Math.max(a.getWeight(), b.getWeight()));
	}

	/**
	 * @param a
	 * @return
	 */
	private IMathFuncs getOper(tNUMBER a)
	{
		return oper(a.getWeight());
	}

	/**
	 * Just an operator to proceed
	 */
	static final IMathFuncs	funcs	= new INTEGER(0);

	/**
	 * @return
	 */
	private IMathFuncs getOper()
	{
		return funcs;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#add(aloyslisp.core.types.tT)
	 */
	public tNUMBER ADD(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.add(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			// loop
			return ADD(a.CAR()).ADD(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("+", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#substract(aloyslisp.core.types.tT)
	 */
	public tNUMBER SUBSTRACT(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.substract(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// No argument return the opposite
			return this.minus();
		}
		if (a instanceof tCONS)
		{
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return SUBSTRACT(a.CAR());
			else
				// Recurse
				return SUBSTRACT(a.CAR()).SUBSTRACT(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("-", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#minus()
	 */
	public tNUMBER minus()
	{
		IMathFuncs oper = getOper(this);
		return oper.minus(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#inversion()
	 */
	public tNUMBER inversion()
	{
		IMathFuncs oper = getOper(this);
		return oper.inversion(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#multiply(aloyslisp.core.types.tT)
	 */
	public tNUMBER MULTIPLY(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.multiply(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			// loop
			return MULTIPLY(a.CAR()).MULTIPLY(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("*", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#division(aloyslisp.core.types.tT)
	 */
	public tNUMBER DIVISION(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.division(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// No argument return the opposite
			return this.inversion();
		}
		if (a instanceof tCONS)
		{
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return DIVISION(a.CAR());
			else
				// Recurse
				return DIVISION(a.CAR()).DIVISION(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("/", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#divide(aloyslisp.core.types.tT)
	 */
	public tNUMBER divide(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.divide(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// No argument return the opposite
			return this.inversion();
		}
		if (a instanceof tCONS)
		{
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return divide(a.CAR());
			else
				// Recurse
				return divide(a.CAR()).divide(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("divide", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#mod(aloyslisp.core.types.tT)
	 */
	public tNUMBER MOD(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.mod(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			// loop
			return MOD(a.CAR()).MOD(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("-", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#lcm(aloyslisp.core.types.tT)
	 */
	public tNUMBER LCM(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.lcm(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return LCM(a.CAR());
			else
				// Recurse
				return LCM(a.CAR()).LCM(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("lcm", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#gcd(aloyslisp.core.types.tT)
	 */
	public tNUMBER GCD(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.gcd(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return GCD(a.CAR());
			else
				// Recurse
				return GCD(a.CAR()).GCD(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("gcd", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#conjugate()
	 */
	public tNUMBER CONJUGATE()
	{
		IMathFuncs oper = getOper(this);
		return oper.conjugate(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#real()
	 */
	public tNUMBER REALPART()
	{
		IMathFuncs oper = getOper(this);
		return oper.realpart(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#imag()
	 */
	public tNUMBER IMAGPART()
	{
		IMathFuncs oper = getOper(this);
		return oper.imagpart(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#phase()
	 */
	public tNUMBER PHASE()
	{
		IMathFuncs oper = getOper(this);
		return oper.phase(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#numerator()
	 */
	public tNUMBER NUMERATOR()
	{
		IMathFuncs oper = getOper(this);
		return oper.numerator(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#denominator()
	 */
	public tNUMBER DENOMINATOR()
	{
		IMathFuncs oper = getOper(this);
		return oper.denominator(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#logand(aloyslisp.core.types.tT)
	 */
	public tNUMBER LOGAND(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.logand(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return LOGAND(a.CAR());
			else
				// Recurse
				return LOGAND(a.CAR()).LOGAND(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("logand", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#logior(aloyslisp.core.types.tT)
	 */
	public tNUMBER LOGIOR(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.logior(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return LOGIOR(a.CAR());
			else
				// Recurse
				return LOGIOR(a.CAR()).LOGIOR(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("logior", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#logxor(aloyslisp.core.types.tT)
	 */
	public tNUMBER LOGXOR(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.logxor(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return LOGXOR(a.CAR());
			else
				// Recurse
				return LOGXOR(a.CAR()).LOGXOR(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("logxor", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#complement()
	 */
	public tNUMBER LOGNOT()
	{
		IMathFuncs oper = getOper(this);
		return oper.lognot(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#toBase(int)
	 */
	public String toBase(int base)
	{
		IMathFuncs oper = getOper(this);
		return oper.toBase(this, base);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#fromBase(java.lang.String,
	 * java.lang.Integer)
	 */
	public tNUMBER fromBase(String a, Integer base)
	{
		IMathFuncs oper = getOper();
		return oper.fromBase(a, base);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#equality(aloyslisp.core.types.tNUMBER)
	 */
	public boolean EQN(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.equal(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return true;
		}
		if (a instanceof tCONS)
		{
			tNUMBER first = (tNUMBER) a.CAR();
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return EQN(first);
			else
				// Recurse
				return EQN(a.CAR()) && first.EQN(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("=", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#greather(aloyslisp.core.types.tNUMBER)
	 */
	public boolean GT(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.greather(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return true;
		}
		if (a instanceof tCONS)
		{
			tNUMBER first = (tNUMBER) a.CAR();
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return GT(first);
			else
				// Recurse
				return GT(a.CAR()) && first.GT(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType(">", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tNUMBER#greatherEqual(aloyslisp.core.types.tNUMBER)
	 */
	public boolean GE(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.greatherEqual(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return true;
		}
		if (a instanceof tCONS)
		{
			tNUMBER first = (tNUMBER) a.CAR();
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return GE(first);
			else
				// Recurse
				return GE(a.CAR()) && first.GE(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType(">=", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#lower(aloyslisp.core.types.tNUMBER)
	 */
	public boolean LT(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.lower(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return true;
		}
		if (a instanceof tCONS)
		{
			tNUMBER first = (tNUMBER) a.CAR();
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return LT(first);
			else
				// Recurse
				return LT(a.CAR()) && first.LT(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("<", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tNUMBER#lowerEqual(aloyslisp.core.types.tNUMBER)
	 */
	public boolean LE(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.lowerEqual(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return true;
		}
		if (a instanceof tCONS)
		{
			tNUMBER first = (tNUMBER) a.CAR();
			if (((tCONS) a).LENGTH() == 1)
				// Last operand
				return LE(first);
			else
				// Recurse
				return LE(a.CAR()) && first.LE(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("<=", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#abs()
	 */
	public tNUMBER ABS()
	{
		IMathFuncs oper = getOper(this);
		return oper.abs(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#max(aloyslisp.core.types.tT)
	 */
	public tNUMBER MAX(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.max(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			// loop
			return MAX(a.CAR()).MAX(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("max", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#min(aloyslisp.core.types.tT)
	 */
	public tNUMBER MIN(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.min(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			// loop
			return MIN(a.CAR()).MIN(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("min", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#round()
	 */
	public tNUMBER ROUND()
	{
		IMathFuncs oper = getOper(this);
		return oper.round(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#truncate()
	 */
	public tNUMBER TRUNCATE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#eql(aloyslisp.core.types.tT)
	 */
	public boolean EQL(tT cell)
	{
		if (this.getClass() != cell.getClass())
			return false;
		return this.EQN((NUMBER) cell);
	}

}
