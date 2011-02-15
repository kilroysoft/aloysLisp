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
import aloyslisp.core.functions.tFUNCTION;
import aloyslisp.core.math.tFLOAT;
import aloyslisp.core.math.tINTEGER;

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

	private static tFUNCTION		currTest	= null;

	public cHASH_TABLE(tFUNCTION test, int init, float load)
	{
		table = new LinkedHashMap<tT, tT>(init, load);
		this.test = test;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.sequences.tHASH_TABLE#MAKE_HASH_TABLE(aloyslisp.core.functions
	 * .tFUNCTION, aloyslisp.core.math.tINTEGER, aloyslisp.core.math.tINTEGER,
	 * aloyslisp.core.math.tINTEGER)
	 */
	@Override
	public tHASH_TABLE MAKE_HASH_TABLE(tFUNCTION test, tINTEGER size,
			tINTEGER rehashSize, tINTEGER rehashThreshold)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#HASH_TABLE_COUNT()
	 */
	@Override
	public tHASH_TABLE HASH_TABLE_COUNT()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#HASH_TABLE_REHASH_SIZE()
	 */
	@Override
	public tINTEGER HASH_TABLE_REHASH_SIZE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#HASH_TABLE_REHASH_THRESHOLD()
	 */
	@Override
	public tFLOAT HASH_TABLE_REHASH_THRESHOLD()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#HASH_TABLE_SIZE()
	 */
	@Override
	public tINTEGER HASH_TABLE_SIZE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#HASH_TABLE_TEST()
	 */
	@Override
	public tFUNCTION HASH_TABLE_TEST()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#GETHASH(aloyslisp.core.tT,
	 * aloyslisp.core.sequences.tHASH_TABLE,
	 * aloyslisp.core.sequences.tHASH_TABLE)
	 */
	@Override
	public tT[] GETHASH(tT key, tHASH_TABLE hashTable, tHASH_TABLE def)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#SET_GETHASH(aloyslisp.core.tT,
	 * aloyslisp.core.tT, aloyslisp.core.sequences.tHASH_TABLE,
	 * aloyslisp.core.sequences.tHASH_TABLE)
	 */
	@Override
	public tT SET_GETHASH(tT value, tT key, tHASH_TABLE hashTable,
			tHASH_TABLE def)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#REMHASH(aloyslisp.core.tT,
	 * aloyslisp.core.sequences.tHASH_TABLE)
	 */
	@Override
	public tT REMHASH(tT key, tHASH_TABLE hashTable)
	{
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tHASH_TABLE#CLRHASH()
	 */
	@Override
	public tT CLRHASH()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
