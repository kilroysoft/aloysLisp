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
// IP 22 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.flowcontrol;

import aloyslisp.core.*;

/**
 * THROW_CONDITION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class THROW_CONDITION extends LispFlowControl
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4189491198644557637L;

	private tT					catchTag			= null;

	private tT[]				catchValue			= new tT[]
													{ cLISP.NIL };

	/**
	 * 
	 */
	public THROW_CONDITION(tT catchTag, tT[] catchValue)
	{
		super();
		this.catchTag = catchTag;
		this.catchValue = catchValue;
	}

	/**
	 * @param tag
	 * @return
	 */
	public Boolean TST_CATCH(tT tag)
	{
		return catchTag.EQL(tag);
	}

	/**
	 * @return
	 */
	public tT[] CATCH_VALUE()
	{
		return catchValue;
	}

}
