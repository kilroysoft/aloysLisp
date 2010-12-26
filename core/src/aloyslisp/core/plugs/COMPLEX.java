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

package aloyslisp.core.plugs;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.conditions.LispErrorFunctionCannotApplyOn;
import aloyslisp.core.math.*;
import aloyslisp.core.types.*;

/**
 * COMPLEX
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class COMPLEX extends PMath<NumComplex> implements tCOMPLEX
{

	/**
	 * @param val
	 */
	public COMPLEX(tNUMBER real, tNUMBER imag)
	{
		super(new NumComplex(real, imag));
	}

	/**
	 * @param val
	 */
	public COMPLEX(INumber real, INumber imag)
	{
		super(new NumComplex(nDouble(real.getDoubleValue()),
				nDouble(imag.getDoubleValue())));
	}

	/**
	 * @param a
	 */
	public COMPLEX(INumber a)
	{
		super(a);
	}

	/**
	 * Create new complex
	 * 
	 * @param real
	 * @param imag
	 * @return
	 */
	public static COMPLEX make(tNUMBER real, tNUMBER imag)
	{
		return new COMPLEX(real, imag);
	}

	/**
	 * @param real
	 * @param imag
	 * @return
	 */
	public static COMPLEX make(Object real, Object imag)
	{
		return new COMPLEX((INumber) real, (INumber) imag);
	}

	/**
	 * Coerce to complex
	 * 
	 * @param val
	 * @return
	 */
	public static COMPLEX make(tNUMBER val)
	{
		return new COMPLEX(val.complexValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#add(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER add(tNUMBER a, tNUMBER b)
	{
		COMPLEX op1 = make(a);
		COMPLEX op2 = make(b);

		return make(op1.REALPART().ADD(op2.REALPART()),
				op1.IMAGPART().ADD(op2.IMAGPART()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#substract(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER substract(tNUMBER a, tNUMBER b)
	{
		COMPLEX op1 = make(a);
		COMPLEX op2 = make(b);

		return make(op1.REALPART().SUBSTRACT(op2.REALPART()), op1.IMAGPART()
				.SUBSTRACT(op2.IMAGPART()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#minus(aloyslisp.core.types.tNUMBER
	 * )
	 */
	public tNUMBER minus(tNUMBER a)
	{
		COMPLEX op1 = make(a);

		return make(op1.REALPART().minus(), op1.IMAGPART().minus());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#inversion(aloyslisp.core.types
	 * .tNUMBER)
	 */
	public tNUMBER inversion(tNUMBER a)
	{
		return make(one).DIVISION(make(a));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#multiply(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER multiply(tNUMBER a, tNUMBER b)
	{
		tNUMBER arg = a.PHASE().ADD(b.PHASE());
		tNUMBER abs = a.ABS().MULTIPLY(b.ABS());
		return make(abs.MULTIPLY(arg.SIN()), abs.MULTIPLY(arg.COS()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#division(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER division(tNUMBER a, tNUMBER b)
	{
		tNUMBER arg = a.PHASE().SUBSTRACT(b.PHASE());
		tNUMBER abs = a.ABS().DIVISION(b.ABS());
		return make(abs.MULTIPLY(arg.SIN()), abs.MULTIPLY(arg.COS()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#divide(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER divide(tNUMBER a, tNUMBER b)
	{
		throw new LispErrorFunctionCannotApplyOn("/", this);
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
		throw new LispErrorFunctionCannotApplyOn("conjugue", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#real(aloyslisp.core.types.tNUMBER
	 * )
	 */
	public tNUMBER realpart(tNUMBER a)
	{
		return ((NumComplex) a.getValue()).real;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#imag(aloyslisp.core.types.tNUMBER
	 * )
	 */
	public tNUMBER imagpart(tNUMBER a)
	{
		return ((NumComplex) a.getValue()).imag;
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
		return INTEGER.make(1);
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
		// TODO toBase conversion
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.numbers.NUMBER#fromBase(java.lang.String,
	 * java.lang.Integer)
	 */
	public tNUMBER fromBase(String a, Integer base)
	{
		// TODO fromBase conversion
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
		return a.getValue().getIntegerValue() == b.getValue().getIntegerValue();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#greather(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public boolean greather(tNUMBER a, tNUMBER b)
	{
		throw new LispErrorFunctionCannotApplyOn(">", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#lower(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public boolean lower(tNUMBER a, tNUMBER b)
	{
		throw new LispErrorFunctionCannotApplyOn("<", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#abs(aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER abs(tNUMBER a)
	{
		return make(a.REALPART().ABS(), a.IMAGPART().ABS());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#max(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER max(tNUMBER a, tNUMBER b)
	{
		throw new LispErrorFunctionCannotApplyOn("max", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#min(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER min(tNUMBER a, tNUMBER b)
	{
		throw new LispErrorFunctionCannotApplyOn("min", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#round(aloyslisp.core.types.tNUMBER
	 * )
	 */
	public tNUMBER round(tNUMBER a)
	{
		return a;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.PMath#phase(aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER phase(tNUMBER a)
	{
		NumComplex val = (NumComplex) a.getValue();
		Double real = val.real.doubleValue();
		Double imag = val.imag.doubleValue();
		return DOUBLE.make(Math.atan2(real, imag));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.numbers.NUMBER#compile()
	 */
	public String COMPILE()
	{
		return "new " + getClass().getCanonicalName() + "("
				+ ((NumComplex) value).real.COMPILE() + ","
				+ ((NumComplex) value).imag.COMPILE() + ")";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#coerce(aloyslisp.core.types.tT)
	 */
	public tT COERCE(tT type)
	{
		// IMPLEMENT Coerce
		if (!(this instanceof NUMBER))
			throw new LispErrorFunctionCannotApplyOn("coerce", this);
		return new COMPLEX(this.complexValue());
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
