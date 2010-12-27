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

package aloyslisp.core.numbers;

import aloyslisp.core.math.*;

/**
 * RATIO
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class RATIO extends REAL implements tRATIO
{

	/**
	 * @param num
	 * @param den
	 */
	public RATIO(tINTEGER num, tINTEGER den)
	{
		super(new NumRatio(num.getValue().getIntegerValue(), den.getValue()
				.getIntegerValue()));
	}

	/**
	 * @param num
	 * @param den
	 */
	public RATIO(IInteger num, IInteger den)
	{
		super(new NumRatio(num, den));
	}

	/**
	 * @param num
	 * @param den
	 */
	public RATIO(tNUMBER val)
	{
		super(val.getValue().getRatioValue());
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

}
