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
// IP 28 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import static aloyslisp.internal.engine.L.NIL;
import static aloyslisp.internal.engine.L.sym;
import aloyslisp.core.tT;
import aloyslisp.core.conditions.TYPE_ERROR;
import aloyslisp.core.sequences.tLIST;

/**
 * cINTEGER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class cINTEGER extends cRATIONAL implements tINTEGER
{
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#integerValue()
	 */
	public Integer integerValue()
	{
		return getIntegerValue().val.intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#longValue()
	 */
	public Long longValue()
	{
		return getIntegerValue().val.longValue();
	}

	/* *******************************************************************
	 * OPERATOR
	 */
	/**
	 * Less common multiplicand
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER lcm(tINTEGER op);

	/**
	 * Greather common denumerator
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER gcd(tINTEGER op);

	/**
	 * Arithmetic AND
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER logand(tINTEGER op);

	/**
	 * Arithmetic NAND
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER lognand(tINTEGER op);

	/**
	 * Arithmetic NAND COMPLEMENT 1
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER logandc1(tINTEGER op);

	/**
	 * Arithmetic AND COMPLEMENT 2
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER logandc2(tINTEGER op);

	/**
	 * Arithmetic OR
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER logior(tINTEGER op);

	/**
	 * Arithmetic OR COMPLEMENT 1
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER logorc1(tINTEGER op);

	/**
	 * Arithmetic OR COMPLEMENT 2
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER logorc2(tINTEGER op);

	/**
	 * Arithmetic XOR
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER logxor(tINTEGER op);

	/**
	 * Arithmetic EQUIVALENCE
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER logeqv(tINTEGER op);

	/**
	 * Arithmetic NOT (complement to 0)
	 * 
	 * @return
	 */
	abstract tINTEGER lognot();

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Get string representation of number with base radix (for integers)
	 * 
	 * @param radix
	 * @return
	 */
	abstract String toBase(Integer radix);

	/**
	 * Arithmetic bit pattern test
	 * 
	 * @param op
	 * @return
	 */
	abstract Boolean logtest(tINTEGER op);

	/**
	 * Arithmetic bit count
	 * 
	 * @param op
	 * @return
	 */
	abstract tINTEGER logcount();

	/**
	 * Arithmetic bit test
	 * 
	 * @param op
	 * @return
	 */
	abstract Boolean logbitp(tINTEGER op);

	/**
	 * Arithmetic bit shift
	 * 
	 * @param count
	 * @return
	 */
	abstract tINTEGER ash(tINTEGER count);

	/**
	 * @return
	 */
	abstract boolean evenp();

	/**
	 * @return
	 */
	abstract boolean oddp();

	/**
	 * @return
	 */
	abstract tINTEGER isqrt();

	/**
	 * @return
	 */
	abstract tINTEGER integer_length();

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LCM(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER LCM(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).lcm((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.LCM((tNUMBER) list.CAR());
			else
				return this.LCM((tNUMBER) list.CAR()).LCM(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#GCD(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER GCD(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).gcd((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.GCD((tNUMBER) list.CAR());
			else
				return this.GCD((tNUMBER) list.CAR()).GCD(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGAND(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER LOGAND(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).logand((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.LOGAND((tNUMBER) list.CAR());
			else
				return this.LOGAND((tNUMBER) list.CAR()).LOGAND(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGNAND(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGNAND(tINTEGER op)
	{
		return ((cINTEGER) coerce((cINTEGER) op)).lognand(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGANDC1(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGANDC1(tINTEGER op)
	{
		return ((cINTEGER) coerce((cINTEGER) op)).logandc1(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGANDC2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGANDC2(tINTEGER op)
	{
		return ((cINTEGER) coerce((cINTEGER) op)).logandc2(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGIOR(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER LOGIOR(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).logior((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.LOGIOR((tNUMBER) list.CAR());
			else
				return this.LOGIOR((tNUMBER) list.CAR()).LOGIOR(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGORC1(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGORC1(tINTEGER op)
	{
		return ((cINTEGER) coerce((cINTEGER) op)).logorc1(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGORC2(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGORC2(tINTEGER op)
	{
		return ((cINTEGER) coerce((cINTEGER) op)).logorc2(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGXOR(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER LOGXOR(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).logxor((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.LOGXOR((tNUMBER) list.CAR());
			else
				return this.LOGXOR((tNUMBER) list.CAR()).LOGXOR(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGEQV(aloyslisp.core.tT)
	 */
	@Override
	public tINTEGER LOGEQV(tT op)
	{
		if (op instanceof tINTEGER)
		{
			return ((cINTEGER) coerce((cINTEGER) op)).logeqv((tINTEGER) op);
		}
		else if (op == NIL)
		{
			return this;
		}
		else if (op instanceof tLIST)
		{
			tLIST list = (tLIST) op;
			if (list.LENGTH() == 1)
				return this.LOGEQV((tNUMBER) list.CAR());
			else
				return this.LOGEQV((tNUMBER) list.CAR()).LOGEQV(list.CDR());
		}

		throw new TYPE_ERROR(op, sym("integer"));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGNOT()
	 */
	@Override
	public tINTEGER LOGNOT()
	{
		return lognot();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGCOUNT(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER LOGCOUNT()
	{
		return logcount();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGTEST(aloyslisp.core.math.tINTEGER)
	 */
	public Boolean LOGTEST(tINTEGER op)
	{
		return logtest(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#LOGBITP(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public Boolean LOGBITP(tINTEGER op)
	{
		return logbitp(op);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ASH(aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tINTEGER ASH(tINTEGER count)
	{
		return ash(count);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#EVENP()
	 */
	@Override
	public boolean EVENP()
	{
		return evenp();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ODDP()
	 */
	@Override
	public boolean ODDP()
	{
		return oddp();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#ISQRT()
	 */
	@Override
	public tINTEGER ISQRT()
	{
		return isqrt();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.math.tINTEGER#INTEGER_LENGTH()
	 */
	@Override
	public tINTEGER INTEGER_LENGTH()
	{
		return integer_length();
	}

}
