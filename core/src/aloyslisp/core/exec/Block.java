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

package aloyslisp.core.exec;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.math.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.sequences.tCONS;
import aloyslisp.core.sequences.tLIST;

/**
 * Block
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class Block extends PEnviron
{
	/**
	 * Code starting point
	 */
	tSYMBOL	name;

	/**
	 * Code starting point
	 */
	tLIST	code;

	/**
	 * Current interpretation pointer
	 */
	tLIST	ip;

	/**
	 * External block
	 */
	Block	previous;

	/**
	 * There is tags in this block
	 */
	boolean	tagBody	= false;

	/**
	 * Last instruction execution result
	 * Used for returns values of blocks
	 */
	tT[]	res		= new CELL[]
					{ NIL };

	/**
	 * Block constructor
	 * 
	 * @param name
	 * @param block
	 * @param previous
	 */
	public Block(tSYMBOL name, tLIST block, Block previous)
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

		Block walk = this;
		while (walk != null)
		{
			for (tT step : walk.code)
			{
				tCONS instr = (tCONS) step;
				if (label == instr.CAR())
				{
					// Block to use for action
					e.goBlock = walk;

					// last instruction of block so return
					if (instr.CDR() instanceof NIL)
						return;

					// next instruction is the goto place
					e.goIP = (tCONS) instr.CDR();
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

		Block walk = this;
		while (walk != null)
		{
			if (walk.name == label)
			{
				// Block to use for action
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
	 * @return Block variable
	 */
	public Symbol arg(tSYMBOL name)
	{
		return get(name);
	}

	/**
	 * Read only non compiled blocks
	 * 
	 * @param name
	 * @return Block variable
	 */
	public Symbol read(tSYMBOL name)
	{
		Symbol res = null;

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
	 * @return Block variable
	 */
	public Symbol sRead(tSYMBOL name)
	{
		Symbol res = null;

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

		res += "Block " + getName() + "\n" + super.dump();

		return res;
	}

}
