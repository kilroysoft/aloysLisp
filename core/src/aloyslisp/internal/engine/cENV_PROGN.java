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
import static aloyslisp.internal.engine.L.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.sequences.*;

/**
 * cENV_PROGN
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cENV_PROGN extends cENV
{
	protected tLIST	blocks	= NIL;

	protected tLIST	ip		= NIL;

	/**
	 * @param blocks
	 */
	public cENV_PROGN(tLIST blocks)
	{
		super();
		this.blocks = blocks;
		ip = this.blocks;
	}

	public cENV_PROGN(tT... blocks)
	{
		super();
		this.blocks = list((Object[]) blocks);
		ip = this.blocks;
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
			res = ENV_STEP();
		}
		return res;
	}

	/**
	 * Execute next instruction
	 * 
	 * @return
	 */
	public tT[] ENV_STEP()
	{
		tT[] res = null;
		System.out.println("exec : " + ip);
		res = ip.CAR().EVAL();
		ip = ENV_NEXT_STEP();
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tENV#ENV_NEXT_STEP()
	 */
	protected tLIST ENV_NEXT_STEP()
	{
		if (!(ip.CDR() instanceof tLIST))
			throw new LispException("BLOCK code is not a list : " + blocks);

		return ip = (tLIST) ip.CDR();
	}

}
