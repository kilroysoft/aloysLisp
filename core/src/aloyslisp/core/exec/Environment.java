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

import aloyslisp.core.conditions.LispException;
import aloyslisp.core.math.*;
import aloyslisp.core.types.*;

/**
 * Environment
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class Environment
{
	/**
	 * Closure stack pointer
	 */
	Closure			topClosure;

	/**
	 * Return value
	 */
	public tT[]		ret		= null;

	/**
	 * Block to go (return-from) ou (go)
	 */
	public Block	goBlock	= null;

	/**
	 * New IP for go
	 */
	public tCONS	goIP	= null;

	/**
	 * Wrapper to open a new closure
	 */
	public void newClosure()
	{
		topClosure = new Closure(topClosure);
	}

	/**
	 * Init environment
	 */
	public void init()
	{
		ret = null;
		goBlock = null;
		goIP = null;
		topClosure = null;
	}

	/**
	 * Wrapper to open a new block
	 * 
	 * @param name
	 * @param args
	 * @param block
	 * @param vals
	 * @return If a new closure has been created
	 */
	public boolean newBlock(tSYMBOL name, tLIST block)
	{
		if (topClosure == null)
		{
			newClosure();
		}

		topClosure.newBlock(name, block);
		return false;
	}

	/**
	 * Wrapper to execute block
	 * 
	 * @return
	 */
	public tT[] exec()
	{
		return topClosure.exec();
	}

	/**
	 * Pop closure, done by pop block
	 */
	private void PopClosure()
	{
		topClosure = topClosure.previous;
	}

	/**
	 * Pop block
	 */
	public void popBlock()
	{
		topClosure.PopBlock();
		if (topClosure.topBlock == null)
			PopClosure();
	}

	/**
	 * Return
	 * 
	 * @param name
	 * @return
	 */
	public Symbol arg(tSYMBOL name)
	{
		if (topClosure != null)
			return topClosure.arg(name);

		return null;
	}

	/**
	 * @param name
	 * @return
	 */
	public Symbol read(tSYMBOL name)
	{
		if (topClosure != null)
			return topClosure.read(name);

		return null;
	}

	/**
	 * @param name
	 * @return
	 */
	public tT readVal(tSYMBOL name)
	{
		Symbol res = read(name);

		if (res == null)
			return null;

		return res.SYMBOL_VALUE();
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public tSYMBOL writeVal(tSYMBOL name, tT value)
	{
		Symbol atom = read(name);
		if (atom == null)
			return null;
		return atom.SET_SYMBOL_VALUE(value);
	}

	/**
	 * Get special atom. We walk through all closure that could define the
	 * special variable.
	 * 
	 * @param name
	 * @return
	 */
	public Symbol sRead(tSYMBOL name)
	{
		return topClosure.sRead(name);
	}

	/**
	 * @param name
	 * @param val
	 * @return
	 */
	public Symbol write(tSYMBOL name, tT val)
	{
		if (topClosure == null)
			return null;
		return topClosure.write(name, val);
	}

	/**
	 * @param name
	 * @return
	 */
	public Symbol intern(tSYMBOL atom)
	{
		return topClosure.intern(atom);
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public Symbol intern(tSYMBOL atom, tT value)
	{
		return topClosure.intern(atom, value);
	}

	/**
	 * @return
	 */
	public String trace()
	{
		return trace(false);
	}

	/**
	 * @return
	 */
	public String trace(boolean all)
	{
		if (topClosure != null)
			return topClosure.trace(all);
		return "Top level closure";
	}

	/**
	 * Prepare environment for a go special form
	 * 
	 * @param label
	 */
	public void go(tT label)
	{
		if (topClosure == null)
		{
			throw new LispException("sGO : invalid in top closure");
		}
		this.topClosure.topBlock.go(label);
	}

	/**
	 * @param label
	 * @param value
	 */
	public void returnFrom(tT label, tT[] value)
	{
		if (topClosure == null)
		{
			throw new LispException("RETURN-FROM : invalid in top closure");
		}
		this.topClosure.topBlock.returnFrom(label, value);
	}

	/**
	 * Set block as tagbody
	 */
	public void tagBody()
	{
		this.topClosure.topBlock.tagBody = true;
	}

}
