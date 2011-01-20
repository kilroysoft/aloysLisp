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
// IP 16 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.conditions.*;

/**
 * COMPLEX
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class COMPLEX extends NUMBER implements tCOMPLEX
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5745108518875400131L;

	/**
	 * 
	 */
	public tREAL				real;

	/**
	 * 
	 */
	public tREAL				imag;

	/**
	 * 
	 */
	/**
	 * @param real
	 * @param imag
	 */
	public COMPLEX(tREAL real, tREAL imag)
	{
		this.real = real;
		this.imag = imag;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "#C(" + real + " " + imag + ")";
	}

	/**
	 * @return
	 */
	public RATIO getRatioValue()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to ratio");
		}
		return realpart().getRatioValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getComplexValue()
	 */
	public COMPLEX getComplexValue()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getIntegerValue()
	 */
	public BIGNUM getIntegerValue()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to integer");
		}
		return realpart().getIntegerValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getFloatValue()
	 */
	public SINGLE_FLOAT getFloatValue()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to float");
		}
		return realpart().getFloatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getDoubleValue()
	 */
	public DOUBLE_FLOAT getDoubleValue()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to double");
		}
		return realpart().getDoubleValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#getShortValue()
	 */
	public SHORT_FLOAT getShortValue()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to short");
		}
		return realpart().getShortValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.NUMBER#complexifyValue()
	 */
	@Override
	public tNUMBER complexifyValue()
	{
		if (imag instanceof INTEGER && imag.EQUALNUM(ZERO))
			return real;

		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#coerce(aloyslisp.core.math.tNUMBER)
	 */
	public NUMBER coerce(tNUMBER var)
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#equalnum(aloyslisp.core.math.tNUMBER)
	 */
	boolean equalnum(tNUMBER op)
	{
		COMPLEX op2 = op.getComplexValue();
		return real.EQUALNUM(op2.real) && imag.EQUALNUM(op2.imag);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER add(tNUMBER op)
	{
		COMPLEX op2 = op.getComplexValue();
		return new COMPLEX((tREAL) real.ADD(op2.real),
				(tREAL) imag.ADD(op2.imag)).complexifyValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER substract(tNUMBER op)
	{
		COMPLEX op2 = op.getComplexValue();
		return new COMPLEX((tREAL) real.SUBSTRACT(op2.real),
				(tREAL) imag.SUBSTRACT(op2.imag)).complexifyValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#minus()
	 */
	@Override
	tNUMBER minus()
	{
		return new COMPLEX((tREAL) real.MINUS(), (tREAL) imag.MINUS())
				.complexifyValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#inversion()
	 */
	@Override
	tNUMBER inversion()
	{
		tNUMBER phase = (tREAL) PHASE().MINUS();
		tNUMBER mod = ABS();
		return new COMPLEX((tREAL) mod.MULTIPLY(phase.COS()),
				(tREAL) mod.MULTIPLY(phase.SIN())).complexifyValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER multiply(tNUMBER op)
	{
		tNUMBER phase = (tREAL) PHASE().MINUS();
		tNUMBER mod = ABS();
		COMPLEX op2 = (COMPLEX) op.coerce(this);
		phase = (tREAL) phase.ADD(op2.PHASE().MINUS());
		mod = mod.MULTIPLY(op2.ABS());
		return new COMPLEX((tREAL) mod.MULTIPLY(phase.COS()),
				(tREAL) mod.MULTIPLY(phase.SIN())).complexifyValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#division(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER division(tNUMBER op)
	{
		tNUMBER phase = (tREAL) PHASE().MINUS();
		tNUMBER mod = ABS();
		COMPLEX op2 = (COMPLEX) op.coerce(this);
		phase = (tREAL) phase.SUBSTRACT(op2.PHASE().MINUS());
		mod = mod.DIVISION(op2.ABS());
		return new COMPLEX((tREAL) mod.MULTIPLY(phase.COS()),
				(tREAL) mod.MULTIPLY(phase.SIN())).complexifyValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#realpart()
	 */
	@Override
	tNUMBER realpart()
	{
		return real;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#imagpart()
	 */
	@Override
	tNUMBER imagpart()
	{
		return imag;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#conjugate()
	 */
	@Override
	tNUMBER conjugate()
	{
		return new COMPLEX(real, (tREAL) imag.MINUS()).complexifyValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#phase()
	 */
	@Override
	tNUMBER phase()
	{
		return real.ATAN(imag);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#abs()
	 */
	@Override
	tREAL abs()
	{
		return (tREAL) real.EXPT(TWO).ADD(imag.EXPT(TWO)).SQRT();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#zerop()
	 */
	@Override
	boolean zerop()
	{
		return real.EQUALNUM(ZERO) && imag.EQUALNUM(ZERO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sin()
	 */
	@Override
	tNUMBER sin()
	{
		tNUMBER eix = MULTIPLY(I).EXP();
		return eix.SUBSTRACT(eix.INVERSION()).DIVISION(TWO.MULTIPLY(I));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#cos()
	 */
	@Override
	tNUMBER cos()
	{
		tNUMBER eix = MULTIPLY(I).EXP();
		return eix.ADD(eix.INVERSION()).DIVISION(TWO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#tan()
	 */
	@Override
	tNUMBER tan()
	{
		tNUMBER eix = MULTIPLY(I).EXP();
		tNUMBER e_ix = eix.INVERSION();
		return eix.SUBSTRACT(e_ix).DIVISION(eix.ADD(e_ix).DIVISION(I));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#asin()
	 */
	@Override
	tNUMBER asin()
	{
		return MULTIPLY(this).MINUS().SQRT().ADD(this.MULTIPLY(I)).LOG()
				.MULTIPLY(I).MINUS();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#acos()
	 */
	@Override
	tNUMBER acos()
	{
		return PI.DIVISION(TWO).SUBSTRACT(ASIN());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#atan()
	 */
	@Override
	tNUMBER atan()
	{
		tNUMBER iy = MULTIPLY(I);
		return ONE.ADD(iy).SUBSTRACT(ONE.SUBSTRACT(iy)).DIVISION(TWO)
				.MULTIPLY(I);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#atan(aloyslisp.core.math.tREAL)
	 */
	@Override
	tNUMBER atan(tREAL opt)
	{
		throw new LispException("ATAN optional arg should be REAL");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#log()
	 */
	@Override
	tNUMBER log()
	{
		return new COMPLEX((tREAL) real.ABS().LOG(), (tREAL) PHASE())
				.complexifyValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sqrt()
	 */
	@Override
	tNUMBER sqrt()
	{
		return new COMPLEX((tREAL) realpart().ADD(ABS()).SQRT()
				.MULTIPLY(TWO.INVERSION()).MULTIPLY(TWO.SQRT()),
				(tREAL) imagpart().SUBSTRACT(ABS()).SQRT()
						.MULTIPLY(TWO.INVERSION()).MULTIPLY(TWO.SQRT()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#exp()
	 */
	@Override
	tNUMBER exp()
	{
		return E.EXPT(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#expt(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	tNUMBER expt(tNUMBER power)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
