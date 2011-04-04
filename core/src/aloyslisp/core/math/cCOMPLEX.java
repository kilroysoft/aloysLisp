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
// IP 16 oct. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import aloyslisp.annotations.aBuiltIn;
import aloyslisp.core.tT;
import aloyslisp.core.conditions.*;
import static aloyslisp.core.L.*;

/**
 * cCOMPLEX
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aBuiltIn(lispClass = "complex", lispType = "complex", doc = "t_comple")
public class cCOMPLEX extends cNUMBER implements tCOMPLEX
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
	public cCOMPLEX(tREAL real, tREAL imag)
	{
		this.real = real;
		this.imag = imag;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#TO_STRING()
	 */
	public String TO_STRING()
	{
		return "#C(" + real + " " + imag + ")";
	}

	/**
	 * @return
	 */
	public tRATIO COERCE_TO_RATIO()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to ratio");
		}
		return REALPART().COERCE_TO_RATIO();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_COMPLEX()
	 */
	@Override
	public cNUMBER COERCE_TO_COMPLEX()
	{
		if (imag instanceof cINTEGER && imag.EQUALNUM(ZERO))
			return (cNUMBER) real;

		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_INTEGER()
	 */
	public tINTEGER COERCE_TO_INTEGER()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to integer");
		}
		return REALPART().COERCE_TO_INTEGER();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_SINGLE_FLOAT()
	 */
	public tSINGLE_FLOAT COERCE_TO_SINGLE_FLOAT()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to float");
		}
		return REALPART().COERCE_TO_SINGLE_FLOAT();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_DOUBLE_FLOAT()
	 */
	public tDOUBLE_FLOAT COERCE_TO_DOUBLE_FLOAT()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to double");
		}
		return REALPART().COERCE_TO_DOUBLE_FLOAT();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#COERCE_TO_SHORT_FLOAT()
	 */
	public tSHORT_FLOAT COERCE_TO_SHORT_FLOAT()
	{
		if (!imag.EQUAL(nInt(0)))
		{
			throw new LispException("Can't convert complex to short");
		}
		return REALPART().COERCE_TO_SHORT_FLOAT();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#coerce(aloyslisp.core.math.tNUMBER)
	 */
	public cNUMBER COERCE_TO_NUMBER(tNUMBER var)
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public Integer SXHASH()
	{
		return real.SXHASH() ^ imag.SXHASH();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#add(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_ADD(tNUMBER op)
	{
		return new cCOMPLEX((tREAL) real.ADD(op.REALPART()),
				(tREAL) imag.ADD(op.IMAGPART())).COERCE_TO_COMPLEX();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#division(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_DIVISION(tNUMBER op)
	{
		tNUMBER phase = (tREAL) PHASE().MINUS();
		tNUMBER mod = ABS();
		cCOMPLEX op2 = (cCOMPLEX) op.COERCE_TO_NUMBER(this);
		phase = (tREAL) phase.SUBSTRACT(op2.PHASE().MINUS());
		mod = mod.DIVISION(op2.ABS());
		return new cCOMPLEX((tREAL) mod.MULTIPLY(phase.COS()),
				(tREAL) mod.MULTIPLY(phase.SIN())).COERCE_TO_COMPLEX();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#equalnum(aloyslisp.core.math.tNUMBER)
	 */
	public boolean SINGLE_EQUALNUM(tNUMBER op)
	{
		return real.EQUALNUM(op.REALPART()) && imag.EQUALNUM(op.IMAGPART());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#multiply(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_MULTIPLY(tNUMBER op)
	{
		tNUMBER phase = (tREAL) PHASE().MINUS();
		tNUMBER mod = ABS();
		cCOMPLEX op2 = (cCOMPLEX) op.COERCE_TO_NUMBER(this);
		phase = (tREAL) phase.ADD(op2.PHASE().MINUS());
		mod = mod.MULTIPLY(op2.ABS());
		return new cCOMPLEX((tREAL) mod.MULTIPLY(phase.COS()),
				(tREAL) mod.MULTIPLY(phase.SIN())).COERCE_TO_COMPLEX();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#substract(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER SINGLE_SUBSTRACT(tNUMBER op)
	{
		return new cCOMPLEX((tREAL) real.SUBSTRACT(op.REALPART()),
				(tREAL) imag.SUBSTRACT(op.IMAGPART())).COERCE_TO_COMPLEX();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#abs()
	 */
	@Override
	public tREAL ABS()
	{
		return (tREAL) real.EXPT(TWO).ADD(imag.EXPT(TWO)).SQRT();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#acos()
	 */
	@Override
	public tNUMBER ACOS()
	{
		return PI.DIVISION(TWO).SUBSTRACT(ASIN());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#asin()
	 */
	@Override
	public tNUMBER ASIN()
	{
		return MULTIPLY(this).MINUS().SQRT().ADD(this.MULTIPLY(I)).LOG()
				.MULTIPLY(I).MINUS();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#atan(aloyslisp.core.math.tREAL)
	 */
	@Override
	public tNUMBER ATAN(tT opt)
	{
		tNUMBER iy = MULTIPLY(I);
		return ONE.ADD(iy).SUBSTRACT(ONE.SUBSTRACT(iy)).DIVISION(TWO)
				.MULTIPLY(I);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#conjugate()
	 */
	@Override
	public tNUMBER CONJUGATE()
	{
		return new cCOMPLEX(real, (tREAL) imag.MINUS()).COERCE_TO_COMPLEX();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#cos()
	 */
	@Override
	public tNUMBER COS()
	{
		tNUMBER eix = MULTIPLY(I).EXP();
		return eix.ADD(eix.INVERSION()).DIVISION(TWO);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#exp()
	 */
	@Override
	public tNUMBER EXP()
	{
		return E.EXPT(this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#expt(aloyslisp.core.math.tNUMBER)
	 */
	@Override
	public tNUMBER EXPT(tNUMBER power)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#imagpart()
	 */
	@Override
	public tNUMBER IMAGPART()
	{
		return imag;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#inversion()
	 */
	@Override
	public tNUMBER INVERSION()
	{
		tNUMBER phase = (tREAL) PHASE().MINUS();
		tNUMBER mod = ABS();
		return new cCOMPLEX((tREAL) mod.MULTIPLY(phase.COS()),
				(tREAL) mod.MULTIPLY(phase.SIN())).COERCE_TO_COMPLEX();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#log()
	 */
	@Override
	public tNUMBER LOG()
	{
		return new cCOMPLEX((tREAL) real.ABS().LOG(), (tREAL) PHASE())
				.COERCE_TO_COMPLEX();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#minus()
	 */
	@Override
	public tNUMBER MINUS()
	{
		return new cCOMPLEX((tREAL) real.MINUS(), (tREAL) imag.MINUS())
				.COERCE_TO_COMPLEX();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#phase()
	 */
	@Override
	public tNUMBER PHASE()
	{
		return real.ATAN(imag);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#realpart()
	 */
	@Override
	public tNUMBER REALPART()
	{
		return real;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sin()
	 */
	@Override
	public tNUMBER SIN()
	{
		tNUMBER eix = MULTIPLY(I).EXP();
		return eix.SUBSTRACT(eix.INVERSION()).DIVISION(TWO.MULTIPLY(I));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#sqrt()
	 */
	@Override
	public tNUMBER SQRT()
	{
		return new cCOMPLEX((tREAL) REALPART().ADD(ABS()).SQRT()
				.MULTIPLY(TWO.INVERSION()).MULTIPLY(TWO.SQRT()),
				(tREAL) IMAGPART().SUBSTRACT(ABS()).SQRT()
						.MULTIPLY(TWO.INVERSION()).MULTIPLY(TWO.SQRT()));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#tan()
	 */
	@Override
	public tNUMBER TAN()
	{
		tNUMBER eix = MULTIPLY(I).EXP();
		tNUMBER e_ix = eix.INVERSION();
		return eix.SUBSTRACT(e_ix).DIVISION(eix.ADD(e_ix).DIVISION(I));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tNUMBER#zerop()
	 */
	@Override
	public boolean ZEROP()
	{
		return real.EQUALNUM(ZERO) && imag.EQUALNUM(ZERO);
	}

}
