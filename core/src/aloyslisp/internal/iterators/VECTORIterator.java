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
// IP 13 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.iterators;

import aloyslisp.core.tT;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.sequences.*;

/**
 * VECTORIterator
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class VECTORIterator extends SEQUENCEIterator
{
	/**
	 * Vector access
	 */
	private tVECTOR	vector	= null;

	/**
	 * @param vector
	 */
	public VECTORIterator(tVECTOR vector)
	{
		super(false);
		this.vector = vector;
		rewind();
	}

	/**
	 * @param vector
	 * @param destructive
	 */
	public VECTORIterator(tVECTOR vector, boolean destructive)
	{
		super(destructive);
		this.vector = vector;
		rewind();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext()
	{
		// System.out.println("length " + vector.LENGTH() + " index = " +
		// index);
		return vector.LENGTH() > index + 1;
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

		return ((cVECTOR) vector).array.get(++index);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove()
	{
		if (index >= 0 && vector.LENGTH() >= index)
		{
			throw new LispException("Iterator no item to remove : remove");
		}

		save();

		((cVECTOR) vector).array.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#add(aloyslisp.core.tT)
	 */
	@Override
	public tT add(tT obj)
	{
		if (vector.LENGTH() > index)
		{
			throw new LispException("Iterator no item to remove : remove");
		}

		if (vector.LENGTH() == index)
		{
			((cVECTOR) vector).array.add(obj);
			return obj;
		}

		save();

		((cVECTOR) vector).array.add(index + 1, obj);
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#set(aloyslisp.core.tT)
	 */
	@Override
	public tT set(tT obj)
	{
		if (index >= 0 && vector.LENGTH() >= index)
		{
			throw new LispException("Iterator no item to set : set");
		}

		save();

		((cVECTOR) vector).array.set(index, obj);
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#go(int)
	 */
	@Override
	public tT go(int index)
	{
		if (vector.LENGTH() > index)
		{
			throw new LispException("Go out of bound");
		}

		if (index < 0)
		{
			rewind();
			return null;
		}

		this.index = index;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#rewind()
	 */
	@Override
	public void rewind()
	{
		index = -1;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#toEnd()
	 */
	@Override
	public tT toEnd()
	{
		index = ((cVECTOR) vector).array.size() - 1;
		return ((cVECTOR) vector).array.get(index);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#copy()
	 */
	@Override
	protected void copy()
	{
		vector = (cVECTOR) vector.COPY_CELL();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.iterators.SEQUENCEIterator#getFinal()
	 */
	@Override
	public tSEQUENCE getFinal()
	{
		return vector;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.iterators.SEQUENCEIterator#append(aloyslisp.core.tT)
	 */
	@Override
	public tT append(tT obj)
	{
		toEnd();
		return add(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.iterators.SEQUENCEIterator#setCDR(aloyslisp.core.tT)
	 */
	@Override
	public tT setCDR(tT obj)
	{
		// TODO setCDR not implanted
		return null;
	}

}
