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
// IP UB20 Disconnected from Environment
// --------------------------------------------------------------------------

package aloyslisp.core.packages;

import static aloyslisp.L.*;

import aloyslisp.L;
import aloyslisp.annotations.*;
import aloyslisp.core.cCELL;
import aloyslisp.core.tT;
import aloyslisp.core.conditions.*;
import aloyslisp.core.sequences.*;
import aloyslisp.exec.*;

/**
 * cPACKAGE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cPACKAGE extends cCELL implements tPACKAGE
{
	/**
	 * Package's name
	 */
	String					name;

	/**
	 * Use list
	 */
	SymMap					uses;

	/**
	 * Shadow variables
	 */
	SymMap					internal;

	/**
	 * Shadow variables
	 */
	public SymMap			external;

	/**
	 * Shadow variables
	 */
	SymMap					shadow;

	/**
	 * Case sensitivity
	 */
	boolean					caseSensitive	= false;

	/**
	 * List of all packages
	 */
	public static SymMap	packages		= new SymMap();

	/**
	 * Keywords
	 */
	public static tPACKAGE	key				= new cPACKAGE("keyword");

	/**
	 * System implementation functions
	 */
	public static tPACKAGE	sys				= new cPACKAGE("system");

	/**
	 * Main lisp functions
	 */
	public static tPACKAGE	cl				= new cPACKAGE("common-lisp");

	static
	{
		if (L.e == null)
			e = new Environment();

		packages.put("common-lisp",
				new cSYMBOL("common-lisp").SET_SYMBOL_VALUE(cl));
		packages.put("cl", new cSYMBOL("cl").SET_SYMBOL_VALUE(cl));
		packages.put("lisp", new cSYMBOL("lisp").SET_SYMBOL_VALUE(cl));
		packages.put("system", new cSYMBOL("system").SET_SYMBOL_VALUE(sys));
		packages.put("sys", new cSYMBOL("sys").SET_SYMBOL_VALUE(sys));
		packages.put("keyword", new cSYMBOL("keyword").SET_SYMBOL_VALUE(key));
		packages.put("key", new cSYMBOL("key").SET_SYMBOL_VALUE(key));
	}

	/**
	 * Package constructor
	 * 
	 * @param name
	 *            Name of package
	 */
	public cPACKAGE(String name)
	{
		super();
		// trace = true;
		this.name = name;
		uses = new SymMap();
		internal = new SymMap();
		external = new SymMap();
		shadow = new SymMap();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.Cell#printable()
	 */
	@Override
	public String toString()
	{
		return "#<cPACKAGE " + name + ">";
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.collections.IPackage#isInUseList(aloyslisp.core.
	 * plugs.cCELL)
	 */
	@Override
	public boolean isInUseList(tPACKAGE pack)
	{
		for (String walk : uses)
		{
			if (walk == pack.PACKAGE_NAME())
				return true;

			if (((cPACKAGE) uses.get(walk)).isInUseList(pack))
				return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tPACKAGE#dump()
	 */
	@Override
	public String dump()
	{
		// TODO Auto-generated method stub
		return null;
	}

	// ////////////////////////////////////////////////////////////////////
	// Static functions

	/**
	 * (FIND-cPACKAGE name)
	 * 
	 * @param pack
	 * @return
	 */
	@Static(name = "find-package")
	public static tT FIND_PACKAGE( //
			@Arg(name = "pack") tPACKAGE_DESIGNATOR pack)
	{
		if (pack == null || pack == NIL)
			return currPackage();
		if (pack instanceof tPACKAGE)
			return pack;
		if (!(pack instanceof tSTRING_DESIGNATOR))
			throw new LispException("Type error for " + pack);

		tT packN = packages.get(cSTRING.STRING((tSTRING_DESIGNATOR) pack));
		if (packN == null)
			throw new LispException("FIND-cPACKAGE : Package inconnu : " + pack);

		return ((tSYMBOL) packN).SYMBOL_VALUE();
	}

	// ////////////////////////////////////////////////////////////////////
	// Member functions
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tPACKAGE#INTERN(java.lang.String,
	 * aloyslisp.core.tPACKAGE_DESIGNATOR)
	 */
	@Override
	public tSYMBOL[] INTERN(String symbol, tPACKAGE_DESIGNATOR pack)
	{
		tSYMBOL res[] = FIND_SYMBOL(symbol, null);
		if (res[1] != NIL)
			return res;

		return new tSYMBOL[]
		{ internal.put(symbol, new cSYMBOL(symbol, this)), NIL };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tPACKAGE#UNINTERN(java.lang.String,
	 * aloyslisp.core.tPACKAGE_DESIGNATOR)
	 */
	@Override
	public tSYMBOL UNINTERN(String symbol, tPACKAGE_DESIGNATOR pack)
	{
		tSYMBOL res;
		if ((res = external.get(symbol)) != null)
		{
			if (pack != res.SYMBOL_PACKAGE())
			{
				external.remove(symbol);
				shadow.remove(symbol);
				return T;
			}

			// Test if conflict
			if (shadow.get(symbol) != NIL)
				throw new LispException("Correctable error shadow conflict");

			res.SET_SYMBOL_PACKAGE(null);
			return NIL;
		}
		else if ((res = internal.get(symbol)) != null)
		{
			if (pack != res.SYMBOL_PACKAGE())
			{
				internal.remove(symbol);
				shadow.remove(symbol);
				return T;
			}

			// Test if conflict
			if (shadow.get(symbol) != NIL)
				throw new LispException("Correctable error shadow conflict");

			res.SET_SYMBOL_PACKAGE(null);
			return NIL;
		}
		return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tPACKAGE#FIND_SYMBOL(java.lang.String,
	 * aloyslisp.core.tPACKAGE_DESIGNATOR)
	 */
	@Override
	public tSYMBOL[] FIND_SYMBOL(String symbol, tPACKAGE_DESIGNATOR pack)
	{
		tSYMBOL res = shadow.get(symbol);
		if (res != null || (res = external.get(symbol)) != null)
		{
			return new tSYMBOL[]
			{ res, EXTERNAL };
		}
		else if ((res = internal.get(symbol)) != null)
		{
			return new tSYMBOL[]
			{ res, INTERNAL };
		}
		else
		{
			for (tSYMBOL pac : uses.values())
			{
				tT p = pac.SYMBOL_VALUE();
				res = ((cPACKAGE) p).shadow.get(symbol);
				if (res != null)
				{
					return new tSYMBOL[]
					{ res, INHERITED };
				}
				res = ((cPACKAGE) p).external.get(symbol);
				if (res != null)
				{
					return new tSYMBOL[]
					{ res, INHERITED };
				}
			}
		}
		return new tSYMBOL[]
		{ NIL, NIL };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tPACKAGE#IMPORT(java.lang.String,
	 * aloyslisp.core.tPACKAGE_DESIGNATOR)
	 */
	@Override
	public tSYMBOL IMPORT(tT symbol, tPACKAGE_DESIGNATOR pack)
	{
		if (symbol instanceof tLIST)
		{
			for (tT walk : (tLIST) symbol)
			{
				if (!(walk instanceof tSYMBOL))
					throw new LispException("Not a symbol");

				tSYMBOL sym = (tSYMBOL) walk;
				imp(sym, pack);
			}
		}
		else if (symbol instanceof tSYMBOL)
		{
			imp((tSYMBOL) symbol, pack);
		}
		else
			throw new LispException("Bad type for import");

		return T;
	}

	/**
	 * @param symbol
	 * @param pack
	 */
	private void imp(tSYMBOL symbol, tPACKAGE_DESIGNATOR pack)
	{
		tSYMBOL[] sym = this.FIND_SYMBOL(symbol.SYMBOL_NAME(), null);

		if (sym[1] == INTERNAL || sym[1] == EXTERNAL)
		{
			if (sym[0].SYMBOL_PACKAGE() == symbol.SYMBOL_PACKAGE()
					&& sym[0] != symbol)
				throw new LispException("Correctable symbol discrepency");
			if (sym[0].SYMBOL_PACKAGE() == NIL)
			{
				symbol.SET_SYMBOL_PACKAGE(this);
				shadow.put(symbol.SYMBOL_NAME(), symbol);
			}
			else if (sym[0].SYMBOL_PACKAGE() != symbol.SYMBOL_PACKAGE())
			{
				shadow.put(symbol.SYMBOL_NAME(), symbol);
			}
		}
		else
		{
			if (symbol.SYMBOL_PACKAGE() == NIL)
				symbol.SET_SYMBOL_PACKAGE(this);
			internal.put(symbol.SYMBOL_NAME(), symbol);
		}
		return;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tPACKAGE#EXPORT(aloyslisp.core.tT,
	 * aloyslisp.core.tPACKAGE_DESIGNATOR)
	 */
	public tSYMBOL EXPORT(tT symbol, tPACKAGE_DESIGNATOR pack)
	{
		if (symbol instanceof tLIST)
		{
			for (tT walk : (tLIST) symbol)
			{
				if (!(walk instanceof tSYMBOL))
					throw new LispException("Not a symbol");

				tSYMBOL sym = (tSYMBOL) walk;
				exp(sym, pack);
			}
		}
		else if (symbol instanceof tSYMBOL)
		{
			exp((tSYMBOL) symbol, pack);
		}
		else
			throw new LispException("Bad type for export");

		return T;
	}

	/**
	 * @param symbol
	 * @param pack
	 */
	private void exp(tSYMBOL symbol, tPACKAGE_DESIGNATOR pack)
	{
		tSYMBOL[] sym = this.FIND_SYMBOL(symbol.SYMBOL_NAME(), null);

		if (sym[1] == EXTERNAL)
			return;

		if (sym[1] == INTERNAL)
		{
			internal.remove(sym[0].SYMBOL_NAME());
			external.put(sym[0].SYMBOL_NAME(), sym[0]);
		}
		else if (sym[1] == INHERITED)
		{
			external.put(sym[0].SYMBOL_NAME(), sym[0]);
		}
		else
		{
			if (symbol.SYMBOL_PACKAGE() == NIL)
				symbol.SET_SYMBOL_PACKAGE(this);
			external.put(symbol.SYMBOL_NAME(), symbol);
		}
		return;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.collections.IPackage#getName()
	 */
	@Override
	public String PACKAGE_NAME()
	{
		return name;
	}

}
