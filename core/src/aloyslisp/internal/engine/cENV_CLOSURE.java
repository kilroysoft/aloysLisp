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
// IP 21 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.core.*;

/**
 * cENV_CLOSURE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cENV_CLOSURE extends cENV
{
	private tENV	topClosure	= null;

	/**
	 * This closure is for a new environment
	 */
	public cENV_CLOSURE()
	{
		super();
	}

	/**
	 * This closure get back to the lambda environment
	 */
	public cENV_CLOSURE(tENV closure)
	{
		super();
		topClosure = closure;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.cENV#ENV_STOP()
	 */
	public tENV ENV_POP()
	{
		topClosure = null;
		return super.ENV_POP();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_PREVIOUS_LEXICAL()
	 */
	@Override
	public tT[] ENV_PREVIOUS_LEXICAL()
	{
		if (topClosure == null)
			return new tT[]
			{ NIL, NIL };
		else
			return new tT[]
			{ topClosure, T };
	}

}
