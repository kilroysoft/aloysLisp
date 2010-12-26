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
// IP 15 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import aloyslisp.core.conditions.LispErrorFunctionCannotApplyOn;
import aloyslisp.core.math.*;
import aloyslisp.core.plugs.tT;
import aloyslisp.core.types.*;

/**
 * RATIO
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class RATIO extends NUMBER implements tRATIO
{

	/**
	 * @param val
	 */
	public RATIO(tNUMBER num, tNUMBER den)
	{
		super(new NumRatio(num, den));
	}

	/**
	 * @param num
	 * @param den
	 */
	public RATIO(tNUMBER val)
	{
		super(val.ratioValue());
	}

	/**
	 */
	public RATIO()
	{
		super(new NumRatio());
	}

	/**
	 * @param num
	 * @param den
	 */
	public RATIO(NumInteger num, NumInteger den)
	{
		super(new NumRatio(num, den));
	}

	/**
	 * @param val
	 */
	public RATIO(NumRatio val)
	{
		super(val);
	}

	/**
	 * Coerce to ratio
	 * 
	 * @param val
	 * @return
	 */
	public static RATIO make(tNUMBER val)
	{
		return new RATIO(val.ratioValue());
	}

	/**
	 * @param num
	 * @param den
	 * @return
	 */
	public static RATIO make(tNUMBER num, tNUMBER den)
	{
		return new RATIO(num.integerValue(), den.integerValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#add(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER add(tNUMBER a, tNUMBER b)
	{
		RATIO op1 = make(a);
		RATIO op2 = make(b);

		return make(
				op1.NUMERATOR().MULTIPLY(op2.DENOMINATOR())
						.ADD(op2.NUMERATOR().MULTIPLY(op1.DENOMINATOR())), op1
						.DENOMINATOR().MULTIPLY(op2.DENOMINATOR()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#substract(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER substract(tNUMBER a, tNUMBER b)
	{
		return add(a, b.minus());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#minus(aloyslisp.core.types.tNUMBER
	 * )
	 */
	public tNUMBER minus(tNUMBER a)
	{
		RATIO op1 = make(a);

		return make(op1.NUMERATOR().minus(), op1.DENOMINATOR());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#inversion(aloyslisp.core.types
	 * .tNUMBER)
	 */
	public tNUMBER inversion(tNUMBER a)
	{
		RATIO op1 = make(a);

		return make(op1.DENOMINATOR(), op1.NUMERATOR());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#multiply(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER multiply(tNUMBER a, tNUMBER b)
	{
		RATIO op1 = make(a);
		RATIO op2 = make(b);

		return make(op1.NUMERATOR().MULTIPLY(op2.NUMERATOR()), op1
				.DENOMINATOR().MULTIPLY(op2.DENOMINATOR()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#division(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER division(tNUMBER a, tNUMBER b)
	{
		return a.MULTIPLY(b.inversion());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#divide(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER divide(tNUMBER a, tNUMBER b)
	{
		throw new LispErrorFunctionCannotApplyOn("divide", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#mod(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER mod(tNUMBER a, tNUMBER b)
	{
		throw new LispErrorFunctionCannotApplyOn("modulo", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#conjugate(aloyslisp.core.types
	 * .tNUMBER)
	 */
	public tNUMBER conjugate(tNUMBER a)
	{
		return a;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#real(aloyslisp.core.types.tNUMBER
	 * )
	 */
	public tNUMBER realpart(tNUMBER a)
	{
		return a;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#imag(aloyslisp.core.types.tNUMBER
	 * )
	 */
	public tNUMBER imagpart(tNUMBER a)
	{
		return zero;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#numerator(aloyslisp.core.types
	 * .tNUMBER)
	 */
	public tNUMBER numerator(tNUMBER a)
	{
		return a;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#denominator(aloyslisp.core.types
	 * .tNUMBER)
	 */
	public tNUMBER denominator(tNUMBER a)
	{
		return one;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#logand(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER logand(tNUMBER a, tNUMBER b)
	{
		throw new LispErrorFunctionCannotApplyOn("and", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#logior(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER logior(tNUMBER a, tNUMBER b)
	{
		throw new LispErrorFunctionCannotApplyOn("or", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#logxor(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER logxor(tNUMBER a, tNUMBER b)
	{
		throw new LispErrorFunctionCannotApplyOn("xor", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#complement(aloyslisp.core.types
	 * .tNUMBER)
	 */
	public tNUMBER lognot(tNUMBER a)
	{
		throw new LispErrorFunctionCannotApplyOn("complement", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#toBase(aloyslisp.core.types.tNUMBER
	 * , java.lang.Integer)
	 */
	public String toBase(tNUMBER a, Integer base)
	{
		RATIO op1 = make(a);

		return op1.NUMERATOR().toBase(base) + "/"
				+ op1.DENOMINATOR().toBase(base);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.numbers.NUMBER#fromBase(java.lang.String,
	 * java.lang.Integer)
	 */
	public tNUMBER fromBase(String a, Integer base)
	{
		// TODO fromBase
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#equal(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public boolean equal(tNUMBER a, tNUMBER b)
	{
		return a.getValue().getDoubleValue() == b.getValue().getDoubleValue();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#greather(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public boolean greather(tNUMBER a, tNUMBER b)
	{
		return a.getValue().getDoubleValue() > b.getValue().getDoubleValue();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#lower(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public boolean lower(tNUMBER a, tNUMBER b)
	{
		return a.getValue().getDoubleValue() < b.getValue().getDoubleValue();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#abs(aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER abs(tNUMBER a)
	{
		RATIO op1 = make(a);

		return make(abs(op1.NUMERATOR()), op1.DENOMINATOR());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#max(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER max(tNUMBER a, tNUMBER b)
	{
		return a.GT(b) ? a : b;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#min(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER min(tNUMBER a, tNUMBER b)
	{
		return a.GT(b) ? b : a;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#round(aloyslisp.core.types.tNUMBER
	 * )
	 */
	public tNUMBER round(tNUMBER a)
	{
		return INTEGER.make(Math.round(a.doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.numbers.NUMBER#compile()
	 */
	public String COMPILE()
	{
		return "new " + getClass().getCanonicalName() + "("
				+ ((NumRatio) value).num.COMPILE() + ","
				+ ((NumRatio) value).den.COMPILE() + ")";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#coerce(aloyslisp.core.types.tT)
	 */
	public tT COERCE(tT type)
	{
		// IMPLEMENT Coerce
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#lcm(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER lcm(tNUMBER a, tNUMBER b)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#gcd(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER gcd(tNUMBER a, tNUMBER b)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
