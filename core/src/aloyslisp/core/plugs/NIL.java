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
// IP 13 sept. 2010 Creation
// IP UB19 Modify commentaries
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import java.util.Iterator;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.common.*;
import aloyslisp.core.types.*;

/**
 * NIL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class NIL extends SYMBOL implements tNULL
{
	/**
	 * Constructor
	 */
	public NIL()
	{
		super("NIL");
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#CAR()
	 */
	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	public tT CAR()
	{
		return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#CDR()
	 */
	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	public tT CDR()
	{
		return NIL;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#SETF_CAR(aloyslisp.core.types.tT)
	 */
	public tLIST SETF_CAR(tT newCell)
	{
		throw new LispErrorFunctionCannotApplyOn("rplaca", this);
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#SETF_CDR(aloyslisp.core.types.tT)
	 */
	public tLIST SETF_CDR(tT newCell)
	{
		throw new LispErrorFunctionCannotApplyOn("rplacd", this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<tT> iterator()
	{
		return new NilIterator();
	}

	/**
	 * NilIterator
	 * 
	 * @author Ivan Pierre {ivan@kilroysoft.ch}
	 * @author George Kilroy {george@kilroysoft.ch}
	 * 
	 */
	private class NilIterator implements Iterator<tT>
	{

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext()
		{
			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public tT next()
		{
			return NIL;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove()
		{
			// no signification
		}

	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#REVERSE()
	 */
	public tLIST REVERSE()
	{
		return NIL;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#NREVERSE()
	 */
	public tLIST NREVERSE()
	{
		return NIL;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#LAST()
	 */
	public tLIST LAST()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#printable()
	 */
	public String printable()
	{
		return "nil";
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#ENDP()
	 */
	public boolean ENDP()
	{
		return true;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#LENGTH()
	 */
	public Integer LENGTH()
	{
		return 0;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#ELT(java.lang.Integer)
	 */
	public tT ELT(Integer pos)
	{
		throw new LispErrorFunctionCannotApplyOn("elt", this);
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#SET_ELT(java.lang.Integer,
	 * aloyslisp.core.types.tT)
	 */
	public tLIST SETF_ELT(Integer pos, tT value)
	{
		throw new LispErrorFunctionCannotApplyOn("selt", this);
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#SUBSEQ(java.lang.Integer,
	 * java.lang.Integer)
	 */
	public tLIST SUBSEQ(Integer start, Integer end)
	{
		return NIL;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#PUSH(aloyslisp.core.types.tT)
	 */
	public tLIST PUSH(tT item)
	{
		return new CONS(item, NIL);
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#POP()
	 */
	public tLIST POP()
	{
		throw new LispErrorFunctionCannotApplyOn("pop", this);
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#APPEND(aloyslisp.core.types.tT)
	 */
	public tT APPEND(tT item)
	{
		return item;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#COMPILE()
	 */
	public String COMPILE()
	{
		return "NIL";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#copy()
	 */
	public SYMBOL copy()
	{
		return NIL;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.atoms.SYMBOL#SETF_SYMBOL_PACKAGE(aloyslisp.core.
	 * types.tT)
	 */
	public tSYMBOL SETF_SYMBOL_PACKAGE(tT dictionary)
	{
		return this;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#UNINTERN()
	 */
	public tSYMBOL UNINTERN()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#setExported(boolean)
	 */
	public tSYMBOL setExported(boolean exported)
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#isExported()
	 */
	public boolean isExported()
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#setSpecial(boolean)
	 */
	public tSYMBOL setSpecial(boolean special)
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#isSpecial()
	 */
	public boolean isSpecial()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.atoms.SYMBOL#setDeclare(aloyslisp.core.types.tLIST)
	 */
	public tSYMBOL setDeclare(tLIST declare)
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#getDeclare()
	 */
	public tLIST getDeclare()
	{
		return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#setConstant(boolean)
	 */
	public tSYMBOL setConstant(boolean constant)
	{
		return this;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#CONSTANTP()
	 */
	public boolean CONSTANTP()
	{
		return true;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.atoms.SYMBOL#SETF_SYMBOL_VALUE(aloyslisp.core.types
	 * .tT)
	 */
	public tSYMBOL SETF_SYMBOL_VALUE(tT value)
	{
		return this;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.atoms.SYMBOL#SETF_SYMBOL_FUNCTION(aloyslisp.core
	 * .types.tFUNCTION)
	 */
	public tSYMBOL SETF_SYMBOL_FUNCTION(tFUNCTION func)
	{
		return this;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#SYMBOL_VALUE()
	 */
	public tT SYMBOL_VALUE()
	{
		return NIL;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#SYMBOL_FUNCTION()
	 */
	public tFUNCTION SYMBOL_FUNCTION()
	{
		return null;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#unset()
	 */
	public tSYMBOL unset()
	{
		return this;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#fUnset()
	 */
	public tSYMBOL fUnset()
	{
		return this;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#BOUNDP()
	 */
	public boolean BOUNDP()
	{
		return true;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.atoms.SYMBOL#FBOUNDP()
	 */
	public boolean FBOUNDP()
	{
		return false;
	}

	/*
	 * // defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	 * public tLIST LIST()
	 * {
	 * return NIL;
	 * }
	 * /*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#getArray()
	 */
	public tT[] getArray()
	{
		return new tT[] {};
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#SET_LAST(aloyslisp.core.types.tT)
	 */
	@Override
	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	public tLIST SETF_LAST(tT value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	// defined elsewhere (NIL is just a gost of a CONS or SYMBOL)
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#SET_SUBSEQ(java.lang.Integer,
	 * java.lang.Integer, aloyslisp.core.types.tT)
	 */
	public tSEQUENCE SETF_SUBSEQ(Integer start, Integer end, tT value)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
