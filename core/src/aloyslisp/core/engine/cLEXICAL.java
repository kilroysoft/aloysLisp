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

import aloyslisp.core.tT;
import aloyslisp.core.packages.tSYMBOL;
import aloyslisp.core.sequences.tLIST;

/**
 * cLEXICAL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cLEXICAL
{
	/**
	 * cENVIRONMENT stack pointer
	 */
	cENVIRONMENT	topBlock	= null;

	/**
	 * cLEXICAL stack previous element
	 */
	cLEXICAL	previous;

	/**
	 * Create a new closure
	 * 
	 * @param name
	 * @param args
	 * @param block
	 * @param vals
	 * @param top
	 */
	public cLEXICAL(cLEXICAL top)
	{
		previous = top;
	}

	/**
	 * Create a new environment
	 * 
	 * @param name
	 * @param args
	 * @param block
	 * @param vals
	 * @param compiled
	 */
	public void newBlock(tSYMBOL name, tLIST block)
	{
		topBlock = new cENVIRONMENT(name, block, topBlock);
	}

	/**
	 * Wrapper for block execution
	 * 
	 * @return
	 */
	public tT[] exec()
	{
		if (topBlock == null)
			return null;

		return topBlock.exec();
	}

	/**
	 * Pop current env
	 */
	public void PopBlock()
	{
		topBlock = topBlock.previous;
	}

	/**
	 * Get value atom for compiled functions
	 * 
	 * @param name
	 * @return
	 */
	public cDYN_SYMBOL arg(tSYMBOL name)
	{
		if (topBlock == null)
			return null;

		return topBlock.arg(name);
	}

	/**
	 * Get value atom for non compiled blocks
	 * 
	 * @param name
	 * @return
	 */
	public cDYN_SYMBOL read(tSYMBOL name)
	{
		if (topBlock == null)
			return null;

		return topBlock.read(name);
	}

	/**
	 * Write value to an existing variable not for compiled Blocks should user
	 * api()
	 * 
	 * @param Name
	 * @param val
	 * @return
	 */
	public cDYN_SYMBOL write(tSYMBOL name, tT val)
	{
		cDYN_SYMBOL atom = read(name);
		if (atom != null)
		{
			atom.SET_SYMBOL_VALUE(val);
		}
		return atom;
	}

	/**
	 * Get special atom. We walk through all closure that could define the
	 * special variable.
	 * 
	 * @param name
	 * @return
	 */
	public cDYN_SYMBOL sRead(tSYMBOL name)
	{
		cDYN_SYMBOL res = null;

		if (topBlock != null)
			res = topBlock.sRead(name);

		if (res == null && previous != null)
		{
			res = previous.sRead(name);
		}

		return res;
	}

	/**
	 * Intern a variable into the environment return the created or existing one
	 * 
	 * @param name
	 * @return
	 */
	public cDYN_SYMBOL intern(tSYMBOL atom)
	{
		return topBlock.intern(atom);
	}

	/**
	 * Set value of variable, api variable if not exists
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public cDYN_SYMBOL intern(tSYMBOL atom, tT value)
	{
		return topBlock.intern(atom, value);
	}

	/**
	 * Wrapper for cENVIRONMENT Trace
	 * 
	 * @return
	 */
	public String trace(boolean all)
	{
		String res = "";

		// if (all && previous != null)
		// {
		// res += previous.trace(all);
		// }

		res += topBlock.trace(all);

		return res;
	}
}
