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
// IP 12 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.iterators;

import java.util.*;

import aloyslisp.core.*;
import aloyslisp.core.sequences.*;

/**
 * SEQUENCEIterator
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class SEQUENCEIterator implements Iterator<tT>
{
	/**
	 * Index of current item in the sequence
	 */
	protected int		index		= -1;

	/**
	 * Is the iterator destructive ?
	 */
	protected boolean	destructive	= false;

	/**
	 * Data has been copied ?
	 */
	protected boolean	copied		= false;

	/**
	 * @param destructive
	 */
	public SEQUENCEIterator(boolean destructive)
	{
		this.destructive = destructive;
		copied = false;
		index = -1;
	}

	/**
	 * Next index
	 * 
	 * @return
	 */
	public int nextIndex()
	{
		return index;
	}

	/**
	 * Insert an element before the next element.
	 * 
	 * @param obj
	 * @return
	 */
	public abstract tT add(tT obj);

	/**
	 * Write object in the current position.
	 * <p>
	 * Error in case the current position is not set (No first getNext()).
	 * 
	 * @param obj
	 * @return
	 */
	public abstract tT set(tT obj);

	/**
	 * Place cursors to index
	 * <p>
	 * Index 0 put on the first item (as elt). Index < 0, make a rewind.
	 * 
	 * @param index
	 */
	public abstract tT go(int index);

	/**
	 * @return
	 */
	public abstract void rewind();

	/**
	 * @return
	 */
	public abstract tT toEnd();

	/**
	 * Save non-destructive data
	 */
	protected void save()
	{
		if (destructive || copied)
			return;

		int index = this.index;
		copy();
		go(index);
		copied = true;
	}

	/**
	 * Copy sequence
	 */
	protected abstract void copy();

	/**
	 * Return the head of the modified or not list.
	 * 
	 * @return
	 */
	public abstract tSEQUENCE getFinal();

}
