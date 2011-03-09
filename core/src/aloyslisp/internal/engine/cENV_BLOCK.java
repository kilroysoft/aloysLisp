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
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.flowcontrol.*;

/**
 * cENV_BLOCK
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cENV_BLOCK extends cENV_PROGN
{
	/**
	 * Name of block
	 */
	private tSYMBOL	name	= null;

	/**
	 * @param name
	 * @param block
	 * @param previous
	 */
	public cENV_BLOCK(tSYMBOL name, tT... blocks)
	{
		super(blocks);
		this.name = name;
	}

	/**
	 * @param name
	 * @param blocks
	 */
	public cENV_BLOCK(tSYMBOL name, tLIST blocks)
	{
		super(blocks);
		this.name = name;
	}

	/**
	 * @return
	 */
	public tSYMBOL ENV_BLOCK_NAME()
	{
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#EVAL()
	 */
	public tT[] EVAL()
	{
		tT[] res = new tT[]
		{ NIL };

		while (ip != NIL)
		{
			try
			{
				res = ENV_STEP();
			}
			catch (RETURN_CONDITION e)
			{
				if (e.TST_RETURN(name))
				{
					return e.RETURN_VALUE();
				}

				// Pass exception to next level
				throw e;

			}
			catch (RuntimeException e)
			{
				// Pass exception to next level
				throw e;
			}

			ip = ENV_NEXT_STEP();
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tENV#ENV_BLOCK_TST(aloyslisp.core.packages.
	 * tSYMBOL)
	 */
	@Override
	public cENV_BLOCK ENV_BLOCK_TST(tSYMBOL name)
	{
		if (this.name.EQL(name))
			return this;

		if (previous == null)
			return null;

		return previous.ENV_BLOCK_TST(name);
	}

}
