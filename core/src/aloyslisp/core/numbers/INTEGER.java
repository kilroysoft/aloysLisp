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

package aloyslisp.core.numbers;

import aloyslisp.core.conditions.*;
import aloyslisp.core.math.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.sequences.*;

/**
 * INTEGER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class INTEGER extends REAL implements tFIXNUM, tBIGNUM
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
	 * @see aloyslisp.core.types.tNUMBER#lcm(aloyslisp.core.types.tT)
	 */
	public tINTEGER LCM(tT a)
	{
		if (a instanceof INTEGER)
		{
			// normal addition
			IInteger oper = (IInteger) value.coerce(((tINTEGER) a).getValue());
			return (tINTEGER) oper.lcm((IInteger) ((tINTEGER) a).getValue())
					.make();
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
	public tINTEGER GCD(tT a)
	{
		if (a instanceof NUMBER)
		{
			// normal addition
			IInteger oper = (IInteger) value.coerce(((tINTEGER) a).getValue());
			return (tINTEGER) oper.gcd((IInteger) ((tINTEGER) a).getValue())
					.make();
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

}
