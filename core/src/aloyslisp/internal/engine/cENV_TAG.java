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

import static aloyslisp.internal.engine.L.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.flowcontrol.*;

/**
 * cENV_TAG
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cENV_TAG extends cENV_PROGN
{

	/**
	 * Local variables storage
	 */
	private tHASH_TABLE	labels	= null;

	/**
	 * @param blocks
	 */
	public cENV_TAG(tLIST blocks)
	{
		super(blocks);
		labels = new cHASH_TABLE(null, nInt(1), nFloat((float) 0.75));
	}

	/**
	 * @param blocks
	 */
	public cENV_TAG(tT... blocks)
	{
		super(blocks);
		labels = new cHASH_TABLE(null, nInt(1), nFloat((float) 0.75));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#EVAL()
	 */
	public tT[] EVAL()
	{
		tT[] res = new tT[]
		{ NIL };
		tT[] val = null;

		while (ip != NIL)
		{
			try
			{
				val = ENV_STEP();
			}
			catch (GOTO_CONDITION e)
			{
				tT newLabel = ENV_TST_TAG(((GOTO_CONDITION) e).GET_LABEL());
				if (newLabel != null)
				{
					if (!(newLabel instanceof tLIST))
					{
						throw new LispException(
								"TAGBODY : label place not and execution pointer : "
										+ newLabel);
					}

					// Move to new place
					ip = (tLIST) newLabel;
					continue;
				}

				// Pass exception to next level
				throw e;
			}
			catch (RuntimeException e)
			{
				// Pass exception to next level
				throw e;
			}

			if (val != null)
				res = val;

		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.cENV_PROGN#STEP()
	 */
	public tT[] ENV_STEP()
	{
		tT[] res = null;
		tT exec = ip.CAR();

		// Only execute list, not labels
		if (exec instanceof tLIST)
			res = exec.EVAL();

		if (!(ip.CDR() instanceof tLIST))
			throw new LispException("BLOCK code is not a list : " + blocks);

		ip = ENV_NEXT_STEP();

		return res;
	}

	public tT ENV_TST_TAG(tT label)
	{
		tT[] newIp = labels.GETHASH(label, null, null);

		if (newIp[1] == NIL)
			return null;
		else
			return newIp[0];
	}

}
