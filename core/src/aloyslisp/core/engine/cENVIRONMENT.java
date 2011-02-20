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
// IP 21 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.engine;

import static aloyslisp.core.engine.L.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import aloyslisp.iterators.LISTIterator;

/**
 * cENVIRONMENT
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cENVIRONMENT extends PEnviron
{
	/**
	 * Code starting point
	 */
	tSYMBOL			name;

	/**
	 * Code starting point
	 */
	tLIST			code;

	/**
	 * Current interpretation pointer
	 */
	tLIST			ip;

	/**
	 * External block
	 */
	cENVIRONMENT	previous;

	/**
	 * There is tags in this block
	 */
	boolean			tagBody	= false;

	/**
	 * Last instruction execution result
	 * Used for returns values of blocks
	 */
	tT[]			res		= new cCELL[]
							{ NIL };

	/**
	 * cENVIRONMENT constructor
	 * 
	 * @param name
	 * @param block
	 * @param previous
	 */
	public cENVIRONMENT(tSYMBOL name, tLIST block, cENVIRONMENT previous)
	{
		this.code = this.ip = block;
		this.name = name;
		this.previous = previous;
	}

	/**
	 * Execute block
	 * 
	 * @return last function result
	 */
	public tT[] exec()
	{
		ip = code;
		while (ip instanceof tCONS)
		{
			// System.out.println("Execution step : " + ip.car());
			if (!tagBody || ip.CAR() instanceof tCONS)
				res = ip.CAR().EVAL();

			if (e.ret != null)
			{
				// not in this block
				if (e.goBlock != this)
					return null;

				// proceed with go
				if (e.goIP != null)
				{
					e.ret = null;
					e.goBlock = null;
					ip = e.goIP;
					e.goIP = null;

					// proceed with next
					continue;
				}

				// proceed with return from
				res = e.ret;
				e.ret = null;
				e.goBlock = null;
				return res;
			}

			// if we go to a label we go to the label, so next instruction is
			// the right first next instruction...
			ip = (tLIST) ip.CDR();
		}

		return res;
	}

	/**
	 * Goto label
	 * 
	 * @param label
	 */
	public void go(tT label)
	{
		e.ret = new tT[]
		{ NIL };

		cENVIRONMENT walk = this;
		while (walk != null)
		{
			LISTIterator iter = (LISTIterator) walk.code.iterator();
			while (iter.hasNext())
			{
				tT step = iter.next();
				if (label == step)
				{
					// cENVIRONMENT to use for action
					e.goBlock = walk;

					// last instruction of block so return
					if (!iter.hasNext())
						return;

					// next instruction is the goto place
					iter.next();
					e.goIP = (tCONS) iter.getNode();
					return;
				}
			}
			walk = walk.previous;
		}
		throw new LispException("Go : no tag " + label + " found");
	}

	/**
	 * Return from label
	 * 
	 * @param label
	 */
	public void returnFrom(tT label, tT[] value)
	{
		e.ret = value;

		cENVIRONMENT walk = this;
		while (walk != null)
		{
			if (walk.name == label)
			{
				// cENVIRONMENT to use for action
				e.goBlock = walk;
				return;
			}
			walk = walk.previous;
		}
		throw new LispException("RETURN-FROM : no block " + label + " found");
	}

	/**
	 * Read arguments in curent block
	 * 
	 * @param name
	 * @return cENVIRONMENT variable
	 */
	public cDYN_SYMBOL arg(tSYMBOL name)
	{
		return get(name);
	}

	/**
	 * Read only non compiled blocks
	 * 
	 * @param name
	 * @return cENVIRONMENT variable
	 */
	public cDYN_SYMBOL read(tSYMBOL name)
	{
		cDYN_SYMBOL res = null;

		res = get(name);

		if (res == null)
		{
			if (previous == null)
				return null;
			return previous.read(name);
		}

		return res;
	}

	/**
	 * Read in all environments
	 * 
	 * @param name
	 * @return cENVIRONMENT variable
	 */
	public cDYN_SYMBOL sRead(tSYMBOL name)
	{
		cDYN_SYMBOL res = null;

		res = get(name);

		if (res == null)
		{
			if (previous == null)
				return null;
			return previous.sRead(name);
		}

		return res;
	}

	/**
	 * Gives a trace of current closure variables
	 * 
	 * @return Trace string
	 */
	public String trace(boolean all)
	{
		String res = "";

		if (all && previous != null)
			res += previous.trace(all) + "\n";

		res += "cENVIRONMENT " + getName() + "\n" + super.dump();

		return res;
	}

}
