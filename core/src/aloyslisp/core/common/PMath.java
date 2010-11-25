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

package aloyslisp.core.common;

import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;
import static aloyslisp.commonlisp.L.*;

/**
 * PMath
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class PMath<Cast extends Number> extends NUMBER implements
		IMath<Cast>, IMathFuncs
{
	/**
	 * @param val
	 */
	public PMath(Number val)
	{
		super(val);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.numbers.NUMBER#clone()
	 */
	public tNUMBER clone()
	{
		throw new LispErrorFunctionCannotApplyOn("clone", this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#greatherEqual(aloyslisp.core.
	 * types.tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public boolean greatherEqual(tNUMBER a, tNUMBER b)
	{
		return !lower(a, b);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#lowerEqual(aloyslisp.core.types
	 * .tNUMBER, aloyslisp.core.types.tNUMBER)
	 */
	public boolean lowerEqual(tNUMBER a, tNUMBER b)
	{
		return !greather(a, b);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.numbers.IMathFuncs#phase(aloyslisp.core.types.tNUMBER
	 * )
	 */
	public tNUMBER phase(tNUMBER a)
	{
		double val = a.doubleValue();
		return val > 0 ? nInt(0) : nInt(180);

	}

}
