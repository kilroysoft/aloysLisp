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
// IP 25 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.numbers;

import aloyslisp.core.conditions.*;
import aloyslisp.core.math.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.sequences.*;

/**
 * REAL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class REAL extends NUMBER implements tREAL
{
	/**
	 * @param val
	 */
	public REAL(INumber val)
	{
		super(val);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#max(aloyslisp.core.types.tT)
	 */
	public tREAL MAX(tT a)
	{
		if (a instanceof tREAL)
		{
			return GT(a) ? this : (tREAL) a;
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
		throw new LispErrorBadArgumentType("max", this, "REAL");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#min(aloyslisp.core.types.tT)
	 */
	public tREAL MIN(tT a)
	{
		if (a instanceof tREAL)
		{
			return LT(a) ? this : (tREAL) a;
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
		throw new LispErrorBadArgumentType("min", this, "REAL");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#ceiling()
	 */
	public tNUMBER[] FLOOR(tREAL div)
	{
		INumber[] res = ((IReal) value).floor((IReal) div.getValue());
		return new tNUMBER[]
		{ res[0].make(), res[1].make() };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#ceiling()
	 */
	public tNUMBER[] CEILING(tREAL div)
	{
		INumber[] res = ((IReal) value).ceiling((IReal) div.getValue());
		return new tNUMBER[]
		{ res[0].make(), res[1].make() };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#ceiling()
	 */
	public tNUMBER[] ROUND(tREAL div)
	{
		INumber[] res = ((IReal) value).round((IReal) div.getValue());
		return new tNUMBER[]
		{ res[0].make(), res[1].make() };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#ceiling()
	 */
	public tNUMBER[] TRUNCATE(tREAL div)
	{
		INumber[] res = ((IReal) value).truncate((IReal) div.getValue());
		return new tNUMBER[]
		{ res[0].make(), res[1].make() };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tNUMBER#mod(aloyslisp.core.types.tT)
	 */
	public tREAL MOD(tT a)
	{
		if (a instanceof REAL)
		{
			// normal addition
			IReal oper = (IReal) value.coerce(((tREAL) a).getValue());
			return (tREAL) oper.mod((IReal) ((tREAL) a).getValue()).make();
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

}
