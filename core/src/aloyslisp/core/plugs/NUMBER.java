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

import aloyslisp.core.common.*;
import aloyslisp.core.types.*;

/**
 * Pvalue
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
/**
 * NUMBER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
/**
 * NUMBER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class NUMBER extends CELL implements tNUMBER, Cloneable
{
	/**
	 * Table of operator according to weight
	 */
	static INTEGER							zero	= new INTEGER(0);

	/**
	 * 
	 */
	static INTEGER							one		= new INTEGER(1);

	/**
	 * 
	 */
	static final IMathFuncs[]				oper	=
													{//
													one, // byte
			one, // short
			one, // int
			new LONG(0L), // long
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
	public Number							value;

	/**
	 * Constructor
	 * 
	 * @param val
	 */
	public NUMBER(Number val)
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
		value = val;
	}

	/**
	 * @param val
	 */
	public NUMBER(long val)
	{
		value = val;
	}

	/**
	 * @param nb
	 * @return
	 */
	public static tNUMBER create(tSTRING numStr)
	{
		return createStr(numStr.getString());
	}

	/**
	 * @param nb
	 * @return
	 */
	public static tNUMBER createStr(String nb)
	{
		// floating point
		if (nb.matches("^(-|\\+)?\\d*\\.\\d+([esfdlESFDL](-|\\+)?\\d+)?$")
				|| nb.matches("^(-|\\+)?\\d+(\\.\\d*)?[esfdlESFDL](-|\\+)?\\d+$"))
		{
			// double
			if (nb.matches("[ldLD]"))
			{
				nb = nb.replace("[ldLD]", "e") + "d";
				return new DOUBLE(Double.valueOf(nb));
			}

			// float
			if (nb.matches("[esfESF]"))
			{
				nb = nb.replace("[esfESF]", "e") + "f";
			}
			return new FLOAT(Float.valueOf(nb));
		}

		int base = ((tNUMBER) readBase.SYMBOL_VALUE()).getValue().intValue();
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
			return new RATIO(create(str(rat[0])), create(str(rat[1])));
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
				return new LONG(Long.parseLong(nb, base));
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
	public Number getValue()
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
	 * @see aloyslisp.core.types.tNUMBER#intValue()
	 */
	public int intValue()
	{
		return value.intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#longValue()
	 */
	public long longValue()
	{
		return value.longValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#floatValue()
	 */
	public float floatValue()
	{
		return value.floatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#doubleValue()
	 */
	public double doubleValue()
	{
		return value.doubleValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#byteValue()
	 */
	public byte byteValue()
	{
		return value.byteValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#shortValue()
	 */
	public short shortValue()
	{
		return value.shortValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#ratioValue()
	 */
	public NumRatio ratioValue()
	{
		if (value instanceof NumComplex)
			return new NumRatio(((NumComplex) value).getReal(), nInt(1));
		else if (value instanceof NumRatio)
			return (NumRatio) value;
		else
			return new NumRatio(nLong(value.longValue()), nInt(1));

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#complexValue()
	 */
	public NumComplex complexValue()
	{
		if (value instanceof NumRatio)
			return new NumComplex(nDouble(value.doubleValue()), nInt(0));
		else if (value instanceof NumComplex)
			return (NumComplex) value;
		else
			return new NumComplex(nDouble(value.doubleValue()), nInt(0));
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
	public tNUMBER acos()
	{
		return DOUBLE.make(Math.acos(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#asin()
	 */
	public tNUMBER asin()
	{
		return DOUBLE.make(Math.asin(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#atan()
	 */
	public tNUMBER atan()
	{
		return DOUBLE.make(Math.atan(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#atan(aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER atan(tNUMBER b)
	{
		return DOUBLE.make(Math.atan2(getValue().doubleValue(), b.getValue()
				.doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#ceiling()
	 */
	public tNUMBER ceiling()
	{
		return DOUBLE.make(Math.ceil(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#cos()
	 */
	public tNUMBER cos()
	{
		return DOUBLE.make(Math.cos(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#exp()
	 */
	public tNUMBER exp()
	{
		return DOUBLE.make(Math.exp(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#floor()
	 */
	public tNUMBER floor()
	{
		return DOUBLE.make(Math.floor(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tNUMBER#IEEEremainder(aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER IEEEremainder(tNUMBER b)
	{
		return DOUBLE.make(Math.IEEEremainder(getValue().doubleValue(), b
				.getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#log()
	 */
	public tNUMBER log()
	{
		return DOUBLE.make(Math.log(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#expt(aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER expt(tNUMBER b)
	{
		return DOUBLE.make(Math.pow(getValue().doubleValue(), b.getValue()
				.doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#random()
	 */
	public tNUMBER random()
	{
		return DOUBLE.make(Math.random());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#sin()
	 */
	public tNUMBER sin()
	{
		return DOUBLE.make(Math.sin(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#sqrt()
	 */
	public tNUMBER sqrt()
	{
		return DOUBLE.make(Math.sqrt(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#tan()
	 */
	public tNUMBER tan()
	{
		return DOUBLE.make(Math.tan(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#toDegrees()
	 */
	public tNUMBER toDegrees()
	{
		return DOUBLE.make(Math.toDegrees(getValue().doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#toRadians()
	 */
	public tNUMBER toRadians()
	{
		return DOUBLE.make(Math.toRadians(getValue().doubleValue()));
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
	public tNUMBER add(tT a)
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
			return add(a.CAR()).add(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("+", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#substract(aloyslisp.core.types.tT)
	 */
	public tNUMBER substract(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.substract(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			// loop
			return substract(a.CAR()).substract(a.CDR());
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
	public tNUMBER multiply(tT a)
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
			return multiply(a.CAR()).multiply(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("*", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#division(aloyslisp.core.types.tT)
	 */
	public tNUMBER division(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IMathFuncs oper = getOper(this, (tNUMBER) a);
			return oper.division(this, (tNUMBER) a);
		}
		if (a instanceof tNULL)
		{
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			// loop
			return division(a.CAR()).division(a.CDR());
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
			// end of process
			return this;
		}
		if (a instanceof tCONS)
		{
			// loop
			return divide(a.CAR()).divide(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("divide", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#mod(aloyslisp.core.types.tT)
	 */
	public tNUMBER mod(tT a)
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
			return mod(a.CAR()).mod(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("-", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#lcm(aloyslisp.core.types.tT)
	 */
	public tNUMBER lcm(tT a)
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
			// loop
			return lcm(a.CAR()).lcm(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("lcm", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#gcd(aloyslisp.core.types.tT)
	 */
	public tNUMBER gcd(tT a)
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
			// loop
			return gcd(a.CAR()).gcd(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("gcd", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#conjugate()
	 */
	public tNUMBER conjugate()
	{
		IMathFuncs oper = getOper(this);
		return oper.conjugate(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#real()
	 */
	public tNUMBER real()
	{
		IMathFuncs oper = getOper(this);
		return oper.realpart(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#imag()
	 */
	public tNUMBER imag()
	{
		IMathFuncs oper = getOper(this);
		return oper.imagpart(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#phase()
	 */
	public tNUMBER phase()
	{
		IMathFuncs oper = getOper(this);
		return oper.phase(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#numerator()
	 */
	public tNUMBER numerator()
	{
		IMathFuncs oper = getOper(this);
		return oper.numerator(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#denominator()
	 */
	public tNUMBER denominator()
	{
		IMathFuncs oper = getOper(this);
		return oper.denominator(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#logand(aloyslisp.core.types.tT)
	 */
	public tNUMBER logand(tT a)
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
			// loop
			return logand(a.CAR()).logand(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("logand", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#logior(aloyslisp.core.types.tT)
	 */
	public tNUMBER logior(tT a)
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
			// loop
			return logior(a.CAR()).logior(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("logior", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#logxor(aloyslisp.core.types.tT)
	 */
	public tNUMBER logxor(tT a)
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
			// loop
			return logxor(a.CAR()).logxor(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("logxor", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#complement()
	 */
	public tNUMBER lognot()
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
	public boolean equality(tNUMBER a)
	{
		// TODO list management and tT return value
		IMathFuncs oper = getOper(this, a);
		return oper.equal(this, a);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#greather(aloyslisp.core.types.tNUMBER)
	 */
	public boolean greather(tNUMBER a)
	{
		// TODO list management and tT return value
		IMathFuncs oper = getOper(this, a);
		return oper.greather(this, a);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tNUMBER#greatherEqual(aloyslisp.core.types.tNUMBER)
	 */
	public boolean greatherEqual(tNUMBER a)
	{
		// TODO list management and tT return value
		IMathFuncs oper = getOper(this, a);
		return oper.greatherEqual(this, a);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#lower(aloyslisp.core.types.tNUMBER)
	 */
	public boolean lower(tNUMBER a)
	{
		// TODO list management and tT return value
		IMathFuncs oper = getOper(this, a);
		return oper.lower(this, a);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.types.tNUMBER#lowerEqual(aloyslisp.core.types.tNUMBER)
	 */
	public boolean lowerEqual(tNUMBER a)
	{
		// TODO list management and tT return value
		IMathFuncs oper = getOper(this, a);
		return oper.lowerEqual(this, a);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#abs()
	 */
	public tNUMBER abs()
	{
		IMathFuncs oper = getOper(this);
		return oper.abs(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#max(aloyslisp.core.types.tT)
	 */
	public tNUMBER max(tT a)
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
			return max(a.CAR()).max(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("max", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#min(aloyslisp.core.types.tT)
	 */
	public tNUMBER min(tT a)
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
			return min(a.CAR()).min(a.CDR());
		}

		// error bad type
		throw new LispErrorBadArgumentType("min", this, "NUMBER");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#round()
	 */
	public tNUMBER round()
	{
		IMathFuncs oper = getOper(this);
		return oper.round(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#eql(aloyslisp.core.types.tT)
	 */
	public boolean EQL(tT cell)
	{
		if (this.getClass() != cell.getClass())
			return false;
		return this.equality((NUMBER) cell);
	}
}
