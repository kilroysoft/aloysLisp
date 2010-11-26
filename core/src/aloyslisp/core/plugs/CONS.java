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
// IP 11 sept. 2010 Creation
// IP UB19 Update commentaries
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import java.util.*;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.annotations.*;
import aloyslisp.core.common.*;
import aloyslisp.core.types.*;

/**
 * CONS
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class CONS extends CELL implements tCONS
{
	/**
	 * 
	 */
	private tT	car;

	/**
	 * 
	 */
	private tT	cdr;

	/**
	 * Constructor
	 * 
	 * @param car
	 * @param cdr
	 */
	@Construct(name = "cons")
	public CONS(tT car, tT cdr)
	{
		SET_CAR(car);
		SET_CDR(cdr);
	}

	/**
	 * Create cons from am array
	 * 
	 * @param list
	 */
	@Construct(name = "list")
	public CONS(Object... list)
	{
		initCons(false, list);
	}

	/**
	 * Create cons from am array, for declaration
	 * 
	 * @param list
	 */
	public CONS(boolean decl, Object... list)
	{
		initCons(decl, list);
	}

	public void initCons(boolean decl, Object... list)
	{
		if (list.length == 0)
			throw new LispException(
					"Unable to init tCONS with empty array, use NIL");

		// We'll write in the cell first
		tLIST walk = NIL;
		for (Object cell : list)
		{
			// System.out.println("append : " + cell);
			tT newCell;
			if (cell == null)
				newCell = NIL;
			else if (cell instanceof tT)
				newCell = (tT) cell;
			else if (cell instanceof String)
				if (decl)
					newCell = sym((String) cell);
				else
					newCell = str((String) cell);
			else if (cell instanceof Boolean)
				newCell = bool((Boolean) cell);
			else if (cell instanceof Integer)
				newCell = nInt((Integer) cell);
			else if (cell instanceof Long)
				newCell = nLong((Long) cell);
			else if (cell instanceof Float)
				newCell = nFloat((Float) cell);
			else
			{
				throw new LispException("Bad type creating a list CONS() : "
						+ cell);
			}

			walk = new CONS(newCell, walk);
		}

		// System.out.println("old cons : " + walk);
		walk = (tLIST) walk.REVERSE();
		car = walk.CAR();
		cdr = walk.CDR();
		// System.out.println("new cons : " + this);
	}

	/**
	 * Make a new Sequence of values
	 * 
	 * @param cnt
	 * @param value
	 */
	public CONS(Integer cnt, tT value)
	{
		if (cnt < 1)
		{
			throw new LispException(
					"Can't make a sequence of less than 1 element");
		}

		tLIST res = NIL;
		for (Integer i = 0; i < cnt; i++)
		{
			res = new CONS(value, res);
		}

		SET_CAR(res.CAR());
		SET_CDR(res.CDR());
	}

	// ////////////////////////////////////////////////////////////////////////////
	// Lisp functions
	// ////////////////////////////////////////////////////////////////////////////
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#CAR()
	 */
	public tT CAR()
	{
		return car;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#CDR()
	 */
	public tT CDR()
	{
		return cdr;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#SET_CAR(aloyslisp.core.types.tT)
	 */
	public tLIST SET_CAR(tT val)
	{
		car = val;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#SET_CDR(aloyslisp.core.types.tT)
	 */
	public tLIST SET_CDR(tT val)
	{
		cdr = val;
		return this;
	}

	private static final tSYMBOL	FUNCTION	= sym("lisp::function");

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#EVAL()
	 */
	public tT[] EVAL()
	{
		tT func = FUNCTION.SYMBOL_FUNCTION().e(car)[0];
		// System.out.println("executing " + func);
		if (func instanceof tMACRO_FUNCTION)
		{
			// Expand macros
			return MACROEXPAND()[0].EVAL();
		}
		return ((tFUNCTION) func).exec((tLIST) CDR());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#MACROEXPAND1()
	 */
	public tT[] MACROEXPAND1()
	{
		tT func = FUNCTION.SYMBOL_FUNCTION().e(car)[0];
		// System.out.println("executing " + func);
		if (func instanceof tMACRO_FUNCTION)
		{
			// Expand macros
			tT res = ((tFUNCTION) func).exec((tLIST) CDR())[0];
			return new tT[]
			{ res, T };
		}
		return new tT[]
		{ this, NIL };
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<tT> iterator()
	{
		return new ConsIterator(this);
	}

	/**
	 * ConsIterator
	 * 
	 * @author Ivan Pierre {ivan@kilroysoft.ch}
	 * @author George Kilroy {george@kilroysoft.ch}
	 * 
	 */
	private class ConsIterator implements Iterator<tT>
	{
		tT		walk;
		boolean	first	= true;

		/**
		 * @param source
		 */
		public ConsIterator(tT source)
		{
			walk = source;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext()
		{
			if (first)
			{
				// We are in a cons the first is always true.
				first = false;
				return true;
			}

			// While cons in cdr, so only pure cons, not NIL
			return walk instanceof tCONS;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public tT next()
		{
			tT res = walk;
			walk = walk.CDR();
			return res;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove()
		{
			throw new LispException("can't remove in tCONS this way");
		}

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#REVERSE()
	 */
	public tLIST REVERSE()
	{
		tT res = NIL;

		for (tT walk : this)
		{
			res = new CONS(walk.CAR(), res);
		}

		return (tLIST) res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#NREVERSE()
	 */
	public tLIST NREVERSE()
	{
		tLIST newCons = REVERSE();
		SET_CAR(newCons.CAR());
		SET_CDR(newCons.CDR());
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#printable()
	 */
	public String printable()
	{
		tT func = CAR();
		String mac = null;
		if (func instanceof tSYMBOL)
		{
			func = ((tSYMBOL) func).SYMBOL_FUNCTION();
			if (func != null)
				mac = ((FUNCTION) func).mac;
		}
		String res = (mac == null) ? "(" : mac;
		String sep = "";

		tT walk = this;
		if (mac != null)
			walk = walk.CDR();

		while (walk instanceof tCONS)
		{
			res += sep;
			sep = " ";
			tT car = walk.CAR();
			res += car.printable();

			walk = walk.CDR();
		}

		// dotted pair
		if (!(walk instanceof NIL))
		{
			res += " . ";
			res += walk.printable();
		}

		if (mac == null)
			res += ")";

		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#LAST()
	 */
	public tLIST LAST()
	{
		tLIST walk = this;

		while (!walk.ENDP())
		{
			walk = (tLIST) walk.CDR();
		}

		return walk;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#SET_LAST(aloyslisp.core.types.tT)
	 */
	public tLIST SET_LAST(tT value)
	{
		// IMPLEMENT set-last
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#ENDP()
	 */
	public boolean ENDP()
	{
		return !(CDR() instanceof CONS) || CDR() instanceof NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#LENGTH()
	 */
	public Integer LENGTH()
	{
		tLIST walk = this;
		Integer cnt = 1;

		while (!walk.ENDP())
		{
			walk = (tLIST) walk.CDR();
			cnt++;
		}

		return cnt;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#ELT(java.lang.Integer)
	 */
	public tT ELT(Integer pos)
	{
		Integer i = 0;
		for (tT walk : this)
		{
			if (i++ == pos)
				return walk.CAR();
		}
		throw new LispException("Element " + pos + " hors des bornes de "
				+ this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#SET_ELT(java.lang.Integer,
	 * aloyslisp.core.types.tT)
	 */
	public tLIST SET_ELT(Integer pos, tT value)
	{
		Integer i = 0;
		// TODO walk differently
		for (tT walk : this)
		{
			if (i++ == pos)
				((tCONS) walk).SET_CAR(value);
		}
		throw new LispException("Element " + pos + " hors des bornes de "
				+ this);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#SUBSEQ(java.lang.Integer,
	 * java.lang.Integer)
	 */
	public tLIST SUBSEQ(Integer start, Integer end)
	{
		// TODO Subsequence
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#SET_SUBSEQ(java.lang.Integer,
	 * java.lang.Integer, aloyslisp.core.types.tT)
	 */
	public tSEQUENCE SET_SUBSEQ(Integer start, Integer end, tT value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#APPEND(aloyslisp.core.types.tT)
	 */
	public tT APPEND(tT item)
	{
		LAST().SET_CDR(item);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#COMPILE()
	 */
	public String COMPILE()
	{
		String res = "";
		if (ENDP())
		{
			return "cons(" + CAR().COMPILE() + "," + CDR().COMPILE() + ")";
		}

		res = "list(";
		String sep = "";
		for (tT walk : this)
		{
			tLIST cons = (tLIST) walk;
			if (cons.ENDP())
			{
				return res + ").append(cons(" + CAR().COMPILE() + ","
						+ CDR().COMPILE() + "))";
			}
			res += sep + cons.CAR().COMPILE();
			sep = ",";
		}
		return res + ")";
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
			res[i++] = walk.CAR();
		}
		return (tT[]) res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#copy()
	 */
	public tT copy()
	{
		return new CONS(CAR().copy(), CDR().copy());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#EQUAL(aloyslisp.core.types.tT)
	 */
	public boolean EQUAL(tT cell)
	{
		if (!(cell instanceof tCONS))
			return false;

		return CAR().EQUAL(((tCONS) cell).CAR())
				&& CDR().EQUAL(((tCONS) cell).CDR());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#EQUALP(aloyslisp.core.types.tT)
	 */
	public boolean EQUALP(tT cell)
	{
		if (!(cell instanceof tCONS))
			return false;

		return CAR().EQUALP(((tCONS) cell).CAR())
				&& CDR().EQUALP(((tCONS) cell).CDR());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#COERCE(aloyslisp.core.types.tT)
	 */
	public tT COERCE(tT type)
	{
		// IMPLEMENT Coerce
		return null;
	}

	/**
	 * QUOTE to test constant lists
	 */
	private static final tT	QUOTE	= sym("lisp::quote");

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#CONSTANTP()
	 */
	public boolean CONSTANTP()
	{
		return CAR() == QUOTE;
	}

}
