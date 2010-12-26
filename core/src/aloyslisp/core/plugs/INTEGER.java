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
// IP 12 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import static aloyslisp.commonlisp.L.nInt;
import aloyslisp.core.math.*;
import aloyslisp.core.types.*;

/**
 * INTEGER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class INTEGER extends NumInteger implements tFIXNUM, tBIGNUM
{
	/**
	 * @param val
	 */
	public INTEGER(int val)
	{
		super(new NumInteger(val));
	}

	/**
	 * @param val
	 */
	public INTEGER(long val)
	{
		super(new NumInteger(val));
	}

	/**
	 * @param val
	 */
	public INTEGER(INumber val)
	{
		super(val);
	}

	/**
	 * Coerce to int
	 * 
	 * @param val
	 * @return
	 */
	public static INTEGER make(INumber val)
	{
		return new INTEGER(val.getIntegerValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#add(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER add(tNUMBER a, tNUMBER b)
	{
		return a.getValue().add(b.getValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#substract(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER substract(tNUMBER a, tNUMBER b)
	{
		return make(a.getValue().getIntegerValue() - b.getValue().getIntegerValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#minus(aloyslisp.core.types.tNUMBER
	 * )
	 */
	public tNUMBER minus(tNUMBER a)
	{
		return make(-a.getValue().getIntegerValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#inversion(aloyslisp.core.types
	 * .tNUMBER)
	 */
	public tNUMBER inversion(tNUMBER a)
	{
		return RATIO.make(nInt(1), a);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#multiply(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER multiply(tNUMBER a, tNUMBER b)
	{
		return make(a.getValue().getIntegerValue() * b.getValue().getIntegerValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#division(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER division(tNUMBER a, tNUMBER b)
	{
		return new RATIO(a, b);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#divide(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER divide(tNUMBER a, tNUMBER b)
	{
		return make(a.getValue().getIntegerValue()
				/ (Long) b.getValue().getIntegerValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#mod(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER mod(tNUMBER a, tNUMBER b)
	{
		return make(a.getValue().getIntegerValue() % b.getValue().getIntegerValue());
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
		return make(0);
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
		return make(1);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#logand(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER logand(tNUMBER a, tNUMBER b)
	{
		return make(a.getValue().getIntegerValue() & b.getValue().getIntegerValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#logior(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER logior(tNUMBER a, tNUMBER b)
	{
		return make(a.getValue().getIntegerValue() | b.getValue().getIntegerValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#logxor(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER logxor(tNUMBER a, tNUMBER b)
	{
		return make(a.getValue().getIntegerValue() ^ b.getValue().getIntegerValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#complement(aloyslisp.core.types
	 * .tNUMBER)
	 */
	public tNUMBER lognot(tNUMBER a)
	{
		return make(~a.getValue().getIntegerValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#toBase(aloyslisp.core.types.tNUMBER
	 * , java.lang.Integer)
	 */
	public String toBase(tNUMBER a, Integer base)
	{
		// TODO toBase
		return a.printable();
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
		return a.getValue().getIntegerValue() > b.getValue().getIntegerValue();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#lower(aloyslisp.core.types.tNUMBER
	 * , aloyslisp.core.types.tNUMBER)
	 */
	public boolean lower(tNUMBER a, tNUMBER b)
	{
		return a.getValue().getIntegerValue() < b.getValue().getIntegerValue();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#abs(aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER abs(tNUMBER a)
	{
		return make(Math.abs(a.getValue().getIntegerValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#max(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER max(tNUMBER a, tNUMBER b)
	{
		return make(Math.max(a.getValue().getIntegerValue(), b.getValue()
				.getIntegerValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#min(aloyslisp.core.types.tNUMBER,
	 * aloyslisp.core.types.tNUMBER)
	 */
	public tNUMBER min(tNUMBER a, tNUMBER b)
	{
		return make(Math.min(a.getValue().getIntegerValue(), b.getValue()
				.getIntegerValue()));
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
