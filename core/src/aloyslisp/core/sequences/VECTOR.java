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
// IP 12 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.sequences;

import java.util.*;

import aloyslisp.core.conditions.LispException;
import aloyslisp.core.plugs.CELL;
import aloyslisp.core.plugs.tT;

/**
 * VECTOR
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class VECTOR extends CELL implements tVECTOR
{
	private ArrayList<tT>	array;

	private tT				type;

	private int				base	= 0;

	private int				size	= -1;

	/**
	 * Create emplty array
	 */
	public VECTOR()
	{
		array = new ArrayList<tT>();
	}

	/**
	 * Add an array as array values
	 * 
	 * @param arr
	 *            array to copy
	 */
	public VECTOR(tT[] arr)
	{
		array = new ArrayList<tT>();
		for (tT walk : arr)
		{
			array.add(walk);
		}
	}

	/**
	 * Add a list as array
	 * 
	 * @param arr
	 */
	public VECTOR(tLIST arr)
	{
		array = new ArrayList<tT>();
		for (tT walk : arr)
		{
			array.add(walk);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#copy()
	 */
	public tT copy()
	{
		// return new VECTOR(new CONS(array.toArray()));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.IArray#get()
	 */
	public tT[] get()
	{
		return array.toArray(new CELL[0]);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.collections.IArray#getType()
	 */
	public tT getType()
	{
		return type;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.collections.IArray#setType(aloyslisp.core.plugs.
	 * CELL)
	 */
	public tARRAY setType(tT type)
	{
		this.type = type;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<tT> iterator()
	{
		return array.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#printable()
	 */
	@Override
	public String printable()
	{
		String res = "#(";
		String sep = "";

		for (tT walk : array)
		{
			res += sep;
			sep = " ";
			res += walk.printable();
		}

		res += ")";

		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.collections.ISequence#length()
	 */
	public Integer LENGTH()
	{
		return array.size();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.collections.ISequence#elt(java.lang.Integer)
	 */
	public tT ELT(Integer pos)
	{
		if (!testLimits(pos))
			return null;

		return array.get(pos);
	}

	public tSEQUENCE SET_ELT(Integer pos, tT value)
	{
		if (!testLimits(pos))
			return null;

		// throw new LispException("sElt(" + pos + ", " + value.printable() +
		// ")");
		array.add(value);
		// array.set(pos, value);
		return (tSEQUENCE) this;
	}

	/**
	 * Verifiy index.
	 * 
	 * @param pos
	 * @return
	 */
	private boolean testLimits(int pos)
	{
		if (pos < base)
		{
			throw new LispException("Index (" + pos + ") < base (" + base
					+ ") of array");
		}

		if (pos - base >= size && size > 0)
		{
			throw new LispException("Index (" + pos + ") > size (" + base
					+ ") of array with base = (" + base + ")");
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.collections.ISequence#subseq(java.lang.Integer,
	 * java.lang.Integer)
	 */
	public tSEQUENCE SUBSEQ(Integer start, Integer end)
	{
		// TODO Subsequence
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.collections.ISequence#reverse()
	 */
	public tSEQUENCE REVERSE()
	{
		// TODO Reverse
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.collections.ISequence#nreverse()
	 */
	public tSEQUENCE NREVERSE()
	{
		// TODO Nreverse
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#getArray()
	 */
	public tT[] getArray()
	{
		tT[] res = new tT[LENGTH()];
		int i = 0;
		for (tT walk : this)
		{
			res[i++] = walk;
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#equal(aloyslisp.core.types.tT)
	 */
	public boolean EQUAL(tT cell)
	{
		return EQ(cell);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#SET_SUBSEQ(java.lang.Integer,
	 * java.lang.Integer, aloyslisp.core.types.tT)
	 */
	@Override
	public tSEQUENCE SET_SUBSEQ(Integer start, Integer end, tT value)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
