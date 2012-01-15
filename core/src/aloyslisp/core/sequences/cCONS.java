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
// IP 11 sept. 2010-2011 Creation
// IP UB19 Update commentaries
// --------------------------------------------------------------------------

package aloyslisp.core.sequences;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.packages.*;
import aloyslisp.internal.iterators.*;
import aloyslisp.packages.common_lisp.SpecialOperators;
import static aloyslisp.core.L.*;

/**
 * cCONS
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aBuiltIn(lispClass = "cons", doc = "t_cons")
public class cCONS extends cCELL implements tCONS
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
	public cCONS(tT car, tT cdr)
	{
		RPLACA(car);
		RPLACD(cdr);
	}

	/**
	 * Create cons from am array
	 * 
	 * @param list
	 */
	public cCONS(Object... list)
	{
		initCons(false, list);
	}

	/**
	 * Create cons from am array, for declaration
	 * 
	 * @param list
	 */
	public cCONS(boolean decl, Object... list)
	{
		initCons(decl, list);
	}

	/**
	 * Make a new Sequence of values
	 * 
	 * @param cnt
	 * @param value
	 */
	public cCONS(Integer cnt, tT value)
	{
		if (cnt < 1)
		{
			throw new LispException(
					"Can't make a sequence of less than 1 element");
		}

		tLIST res = NIL;
		for (Integer i = 0; i < cnt; i++)
		{
			res = new cCONS(value, res);
		}

		RPLACA(res.CAR());
		RPLACD(res.CDR());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@aJavaInternal
	public LISTIterator iterator()
	{
		return iterator(true);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tSEQUENCE#sequenceIterator(boolean)
	 */
	@Override
	@aJavaInternal
	public LISTIterator iterator(boolean destructive)
	{
		return new LISTIterator(this, destructive);
	}

	/**
	 * @param decl
	 * @param list
	 */
	private void initCons(boolean decl, Object... list)
	{
		if (list.length == 0)
			throw new LispException(
					"Unable to init tCONS with empty array, use cNULL");

		// We'll write in the cell first
		tLIST walk = NIL;
		for (Object cell : list)
		{
			tT newCell;
			if (cell == null)
				newCell = NIL;
			else if (cell.getClass().isArray())
			{
				newCell = list(cell);
				// System.out.println("list : " + newCell);
			}
			else if (cell instanceof tT)
				newCell = (tT) cell;
			else if (cell instanceof String)
			{
				if (decl)
					newCell = sym((String) cell);
				else
					newCell = str((String) cell);
			}
			else if (cell instanceof Boolean)
				newCell = bool((Boolean) cell);
			else if (cell instanceof Integer)
				newCell = nInt((Integer) cell);
			else if (cell instanceof Float)
				newCell = nFloat((Float) cell);
			else if (cell instanceof Character)
				newCell = c((Character) cell);
			else
			{
				throw new LispException(
						"Bad type creating a list cCONS(...) : "
								+ cell.getClass().getCanonicalName());
			}

			walk = new cCONS(newCell, walk);
		}

		// System.out.println("old cons : " + walk);
		walk = (tLIST) walk.REVERSE();
		car = walk.CAR();
		cdr = walk.CDR();
		// System.out.println("new cons : " + this);
	}

	/**
	 * QUOTE to test constant lists
	 */
	private static final tT	QUOTE	= sym("quote");

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#APPEND(aloyslisp.core.types.tT)
	 */
	public tT APPEND(tT item)
	{
		LAST().RPLACD(item);
		return this;
	}

	// ////////////////////////////////////////////////////////////////////////////
	// Lisp functions
	// ////////////////////////////////////////////////////////////////////////////
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#CAR()
	 */
	public tT CAR()
	{
		return car;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#SET_CAR(aloyslisp.core.types.tT)
	 */
	public tLIST RPLACA(tT val)
	{
		car = val;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#CDR()
	 */
	public tT CDR()
	{
		return cdr;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#SET_CDR(aloyslisp.core.types.tT)
	 */
	public tLIST RPLACD(tT val)
	{
		cdr = val;
		return this;
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

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#COMPILE()
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
		LISTIterator iter = (LISTIterator) iterator();
		while (iter.hasNext())
		{
			tT walk = iter.next();
			if (!iter.hasNext())
			{
				return res + ").append(cons(" + CAR().COMPILE() + ","
						+ CDR().COMPILE() + "))";
			}
			res += sep + walk.COMPILE();
			sep = ",";
		}
		return res + ")";
	}

	/**
	 * @param car
	 * @param cdr
	 * @return
	 */
	@aFunction(name = "cons", doc = "f_cons")
	public static tLIST CONS( //
			@aArg(name = "car") tT car, //
			@aArg(name = "cdr") tT cdr)
	{
		return new cCONS(car, cdr);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#CONSTANTP()
	 */
	public boolean CONSTANTP()
	{
		return CAR() == QUOTE;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#copy()
	 */
	public tT COPY_CELL()
	{
		return new cCONS(CAR().COPY_CELL(), CDR().COPY_CELL());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#ELT(java.lang.Integer)
	 */
	public tT ELT(Integer pos)
	{
		LISTIterator iter = (LISTIterator) iterator();
		return iter.go(pos);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#EQUAL(aloyslisp.core.types.tT)
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
	 * @see aloyslisp.core.cCELL#EQUALP(aloyslisp.core.types.tT)
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
	 * @see aloyslisp.core.sequences.tSEQUENCE#FIND(aloyslisp.core.tT)
	 */
	public tT FIND(tT item)
	{
		// TODO implement keys
		LISTIterator iter = new LISTIterator(this);
		while (iter.hasNext())
			if (iter.next().EQL(item))
				return iter.getNode();
		return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#SET_ELT(java.lang.Integer,
	 * aloyslisp.core.types.tT)
	 */
	public tT SET_ELT(tT value, Integer pos)
	{
		LISTIterator iter = (LISTIterator) iterator();
		iter.go(pos);
		return iter.set(value);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tLIST#ENDP()
	 */
	public boolean ENDP()
	{
		return !(CDR() instanceof cCONS) || CDR() instanceof cNULL;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#EVAL()
	 */
	public tT[] EVAL()
	{
		if (car instanceof tSYMBOL && ((tSYMBOL) car).MACRO_FUNCTION() != NIL)
		{
			// Expand macros
			tT me = MACROEXPAND()[0];
			// System.out.println("MACROEXPAND : " + me + " args : " + cdr);
			return me.EVAL();
		}

		// System.out.println("Eval of cons : " + this);
		if (!(cdr instanceof tLIST))
			throw new LispException("Can't eval a non LIST cons");

		tFUNCTION func = SpecialOperators.FUNCTION(car);
		return ((tFUNCTION) func).FUNCALL((tLIST) cdr);
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
	 * @see aloyslisp.core.types.tSEQUENCE#LENGTH()
	 */
	public Integer LENGTH()
	{
		Integer cnt = 0;

		for (tLIST walk = this; walk != NIL; walk = (tLIST) walk.CDR(), cnt++)
			;

		return cnt;
	}

	/**
	 * @param list
	 * @return
	 */
	@aFunction(name = "list", doc = "f_list")
	public static tLIST LIST( //
			@aRest(name = "list") Object... list)
	{
		if (list.length == 0)
			return NIL;
		return new cCONS(list);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#MACROEXPAND1()
	 */
	public tT[] MACROEXPAND_1()
	{
		if (!(car instanceof tSYMBOL))
			throw new LispException("CAR of CONS is not a function : " + car);

		tT func = ((tSYMBOL) car).SYMBOL_FUNCTION();

		// System.out.println("executing " + func);
		if (((tFUNCTION) func).API_MACRO())
		{
			// Expand macros
			tT res = ((tFUNCTION) func).FUNCALL((tLIST) CDR())[0];
			return new tT[]
			{ res, T };
		}
		return new tT[]
		{ this, NIL };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#NREVERSE()
	 */
	public tLIST NREVERSE()
	{
		tLIST newCons = REVERSE();
		RPLACA(newCons.CAR());
		RPLACD(newCons.CDR());
		return this;
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
			res = new cCONS(walk, res);
		}

		return (tLIST) res;
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
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public Integer SXHASH()
	{
		return car.SXHASH() ^ cdr.SXHASH();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#SET_SUBSEQ(java.lang.Integer,
	 * java.lang.Integer, aloyslisp.core.types.tT)
	 */
	public tSEQUENCE SET_SUBSEQ(tT value, Integer start, Integer end)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#printable()
	 */
	public String TO_STRING()
	{
		tT func = CAR();
		String mac = null;
		if (func instanceof tSYMBOL)
		{
			func = ((tSYMBOL) func).SYMBOL_FUNCTION();
			if (func != null)
				mac = ((tFUNCTION) func).API_GET_MAC();
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
			res += car == null ? "*(null)*" : car.TO_STRING();

			walk = walk.CDR();
		}

		// dotted pair
		if (!(walk instanceof cNULL))
		{
			res += " . ";
			res += walk.TO_STRING();
		}

		if (mac == null)
			res += ")";

		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSEQUENCE#getArray()
	 */
	public tT[] VALUES_LIST()
	{
		tT[] res = new tT[LENGTH()];
		int i = 0;
		for (tT walk : this)
		{
			res[i++] = walk;
		}
		return (tT[]) res;
	}

}
