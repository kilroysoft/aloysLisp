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

import static aloyslisp.internal.engine.L.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.sequences.*;

/**
 * Iterator on lists.
 * <p>
 * Iterate over the lists CARs (enable for loops). You can write car, insert or
 * remove nodes. If Iterator is non destructive, any modification of the linked
 * list will create a copy before any modification.
 * <p>
 * Creating a list from NIL, creating a NIL list, or changing the head of list
 * is not considered as list modification as original is not modified.
 * <p>
 * As SEQUENCEs are single linked list there is no possible backward move.
 * <p>
 * Different states
 * <li>list == NIL : empty list
 * <li>current = null : first next not done
 * <li>current = NIL : last item removed, at end of list
 * <li>previous = null : at beginning of list
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class LISTIterator extends SEQUENCEIterator
{
	/**
	 * Resulting list after all manipulations.
	 */
	protected tLIST	list		= null;

	/**
	 * Current node
	 */
	protected tLIST	current		= null;

	/**
	 * Previous node
	 */
	protected tLIST	previous	= null;

	/**
	 * Non destructive iterator (a copy is made in case of linked list
	 * modification)
	 */
	public LISTIterator(tLIST list)
	{
		super(false);
		this.list = list;
		rewind();
	}

	/**
	 * Destructive iterator (a copy is made in case of linked list modification)
	 */
	public LISTIterator(tLIST list, boolean destructive)
	{
		super(destructive);
		this.list = list;
		rewind();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext()
	{
		if (current == null && list != null)
			return true;

		if (current == NIL)
			return false;

		tT next = current.CDR();
		return next != NIL && next instanceof tLIST;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public tT next()
	{
		if (!hasNext())
		{
			throw new LispException("Iterator on list out of bound : next");
		}

		previous = current;
		if (current == null)
			current = list;
		else
			current = (tLIST) current.CDR();
		index++;
		return current.CAR();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove()
	{
		// there's nothing empty list or before or after the list
		if (list == NIL || current == null || current == NIL)
		{
			throw new LispException("Iterator no item to remove : remove");
		}

		// We are on the first item
		if (previous == null)
		{
			if (!hasNext())
			{
				list = NIL;
				current = null;
				index--;
				return;
			}

			list = current = (tLIST) current.CDR();
			return;
		}

		save();

		current = previous.SET_CDR(current.CDR(), null);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#nextIndex()
	 */
	@Override
	public int nextIndex()
	{
		return index;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#add(aloyslisp.core.tT)
	 */
	@Override
	public tT add(tT obj)
	{
		// at the beginning ?
		if (current == null)
		{
			if (list == null)
			{
				list = new cCONS(obj, NIL);
				return obj;
			}

			list = new cCONS(obj, list);
			return obj;
		}

		save();

		tLIST newCons = new cCONS(obj, current.CDR());
		current.SET_CDR(newCons, null);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#set(aloyslisp.core.tT)
	 */
	@Override
	public tT set(tT obj)
	{
		// empty list
		if (list == NIL || current == null)
		{
			throw new LispException("Iterator no item to set : set");
		}

		save();

		// last item deleted
		if (current == NIL)
		{
			current = new cCONS(obj, NIL);
			previous.SET_CDR(current, null);
			index++;
			return obj;
		}

		current.SET_CAR(obj, null);
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#getFinal()
	 */
	@Override
	public tSEQUENCE getFinal()
	{
		return list;
	}

	/**
	 * @return
	 */
	public tLIST getNode()
	{
		return current;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#rewind()
	 */
	@Override
	public void rewind()
	{
		current = previous = null;
		index = -1;
	}

	/**
	 * Place cursors to index
	 * <p>
	 * Index 0 put on the first item (as elt). Index < 0, make a rewind.
	 * 
	 * @param index
	 */
	public tT go(int index)
	{
		tT res = null;
		if (index < 0)
		{
			rewind();
			return res;
		}

		if (index < this.index || !hasNext())
		{
			rewind();
		}

		// <= as we have to execute the first next() for the first item
		while (this.index < index)
		{
			res = next();
		}

		return res;
	}

	/**
	 * @return
	 */
	public tT toEnd()
	{
		tT res = null;
		if (current == NIL)
		{
			rewind();
		}

		// return null if no item
		while (hasNext())
		{
			res = next();
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#copy()
	 */
	@Override
	protected void copy()
	{
		list = (tLIST) list.copy();
		rewind();
	}

}
