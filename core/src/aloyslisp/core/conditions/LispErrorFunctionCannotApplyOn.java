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
// IP 12 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.conditions;

import aloyslisp.core.types.tT;

/**
 * LispErrorFunctionCannotApplyOn
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class LispErrorFunctionCannotApplyOn extends LispException
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5746282197320687493L;

	/**
	 * @param message
	 */
	public LispErrorFunctionCannotApplyOn(String function, tT object)
	{
		super(function
				+ " cannot apply on "
				+ ((object == null) ? "null" : object.getClass()
						.getSimpleName()) + " : " + object);
	}

}
