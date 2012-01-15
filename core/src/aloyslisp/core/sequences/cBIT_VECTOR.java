/**
 * aloysLisp.
 * <p>
 * A LISP interpreter, compiler and library.
 * <p>
 * Copyright (C) 2010-2011 kilroySoft <aloyslisp@kilroysoft.ch>
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
// IP 19 mars 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.sequences;

import java.util.Iterator;

import aloyslisp.annotations.aBuiltIn;
import aloyslisp.annotations.aJavaInternal;
import aloyslisp.core.cCELL;
import aloyslisp.core.tT;
import aloyslisp.internal.iterators.SEQUENCEIterator;

/**
 * cBIT_VECTOR
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aBuiltIn(lispClass = "bit-vector", doc = "t_bt_vec")
public class cBIT_VECTOR extends cCELL implements tBIT_VECTOR
{

	/**
	 * 
	 */
	public cBIT_VECTOR()
	{
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tARRAY#getType()
	 */
	@Override
	public tT ARRAY_ELEMENT_TYPE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tARRAY#setType(aloyslisp.core.tT)
	 */
	@Override
	public tARRAY SET_ARRAY_ELEMENT_TYPE(tT type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSEQUENCE#iterator(boolean)
	 */
	@Override
	@aJavaInternal
	public SEQUENCEIterator iterator(boolean destructive)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSEQUENCE#LENGTH()
	 */
	@Override
	public Integer LENGTH()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSEQUENCE#ELT(java.lang.Integer)
	 */
	@Override
	public tT ELT(Integer pos)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSEQUENCE#SET_ELT(aloyslisp.core.tT,
	 * java.lang.Integer)
	 */
	@Override
	public tT SET_ELT(tT value, Integer pos)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSEQUENCE#SUBSEQ(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public tSEQUENCE SUBSEQ(Integer start, Integer end)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSEQUENCE#SET_SUBSEQ(aloyslisp.core.tT,
	 * java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public tSEQUENCE SET_SUBSEQ(tT value, Integer start, Integer end)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSEQUENCE#REVERSE()
	 */
	@Override
	public tSEQUENCE REVERSE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSEQUENCE#NREVERSE()
	 */
	@Override
	public tSEQUENCE NREVERSE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSEQUENCE#VALUES_LIST()
	 */
	@Override
	public tT[] VALUES_LIST()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSEQUENCE#FIND(aloyslisp.core.tT)
	 */
	@Override
	public tT FIND(tT item)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	@aJavaInternal
	public Iterator<tT> iterator()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
