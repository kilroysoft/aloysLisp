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
// IP 9 oct. 2010 Creation
// IMPLEMENTS Tout à faire pour HashTable
// --------------------------------------------------------------------------

package aloyslisp.core.sequences;

import java.util.*;

import aloyslisp.core.*;
import static aloyslisp.core.engine.L.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.math.*;

/**
 * cHASH_TABLE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cHASH_TABLE extends cCELL implements tHASH_TABLE
{
	private LinkedHashMap<tT, tT>	table		= new LinkedHashMap<tT, tT>();

	private tFUNCTION				test		= null;

	private tINTEGER				size		= null;

	private tFLOAT					thresHold	= null;

	/**
	 * @param test
	 * @param size
	 * @param load
	 */
	public cHASH_TABLE(tFUNCTION test, tINTEGER size, tFLOAT load)
	{
		table = new LinkedHashMap<tT, tT>(((cINTEGER) size).integerValue(),
				((cSINGLE_FLOAT) ((cFLOAT) load).getFloatValue()).value);
		this.test = test;
		this.size = size;
		thresHold = load;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.sequences.tHASH_TABLE#MAKE_HASH_TABLE(aloyslisp.core.functions
	 * .tFUNCTION, aloyslisp.core.math.tINTEGER, aloyslisp.core.math.tINTEGER,
	 * aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tHASH_TABLE MAKE_HASH_TABLE(tT test, tINTEGER size,
			tINTEGER rehashSize, tFLOAT rehashThreshold)
	{
		if (!(test instanceof tFUNCTION))
			test = null;
		return new cHASH_TABLE((tFUNCTION) test, size, rehashThreshold);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#HASH_TABLE_COUNT()
	 */
	@Override
	public tINTEGER HASH_TABLE_COUNT()
	{
		return nInt(table.size());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#HASH_TABLE_REHASH_SIZE()
	 */
	@Override
	public tINTEGER HASH_TABLE_REHASH_SIZE()
	{
		return cBIGNUM.ZERO;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#HASH_TABLE_REHASH_THRESHOLD()
	 */
	@Override
	public tFLOAT HASH_TABLE_REHASH_THRESHOLD()
	{
		return thresHold;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#HASH_TABLE_SIZE()
	 */
	@Override
	public tINTEGER HASH_TABLE_SIZE()
	{
		return size;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#HASH_TABLE_TEST()
	 */
	@Override
	public tFUNCTION HASH_TABLE_TEST()
	{
		if (test == null)
			return (sym("eql").SYMBOL_FUNCTION());
		return test;

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#GETHASH(aloyslisp.core.tT,
	 * aloyslisp.core.sequences.tHASH_TABLE,
	 * aloyslisp.core.sequences.tHASH_TABLE)
	 */
	@Override
	public tT[] GETHASH(tT key, tHASH_TABLE hashTable, tT def)
	{
		currTest = test;
		tT res = table.get(key);
		if (res == null)
			return new tT[]
			{ def, NIL };
		else
			return new tT[]
			{ res, T };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#SET_GETHASH(aloyslisp.core.tT,
	 * aloyslisp.core.tT, aloyslisp.core.sequences.tHASH_TABLE,
	 * aloyslisp.core.sequences.tHASH_TABLE)
	 */
	@Override
	public tT SET_GETHASH(tT value, tT key, tHASH_TABLE hashTable, tT def)
	{
		currTest = test;
		return table.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#REMHASH(aloyslisp.core.tT,
	 * aloyslisp.core.sequences.tHASH_TABLE)
	 */
	@Override
	public tT REMHASH(tT key, tHASH_TABLE hashTable)
	{
		currTest = test;
		return table.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.sequences.tHASH_TABLE#MAPHASH(aloyslisp.core.functions
	 * .tFUNCTION, aloyslisp.core.sequences.tHASH_TABLE)
	 */
	@Override
	public tT MAPHASH(tFUNCTION func, tHASH_TABLE hashTable)
	{
		Set<tT> set = table.keySet();

		for (tT elem : set)
		{
			func.e(elem, GETHASH(elem, null, NIL));
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#CLRHASH()
	 */
	@Override
	public tT CLRHASH()
	{
		table.clear();
		return NIL;
	}
}
