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
// IP UB20 Disconnected from cTHREAD
// --------------------------------------------------------------------------

package aloyslisp.core.packages;

import aloyslisp.annotations.*;
import aloyslisp.core.cCELL;
import aloyslisp.core.tT;
import aloyslisp.core.conditions.*;
import aloyslisp.core.sequences.*;
import static aloyslisp.core.sequences.cHASH_TABLE.*;
import static aloyslisp.core.L.*;

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
	public String		name;

	/**
	 * Use list
	 */
	public tHASH_TABLE	uses;

	/**
	 * Shadow variables
	 */
	public tHASH_TABLE	internal;

	/**
	 * Shadow variables
	 */
	public tHASH_TABLE	external;

	/**
	 * Shadow variables
	 */
	public tHASH_TABLE	shadow;

	/**
	 * Case sensitivity
	 */
	boolean				caseSensitive	= false;

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
		this.name = name.toUpperCase();
		uses = MAKE_HASH_TABLE();
		internal = MAKE_HASH_TABLE();
		external = MAKE_HASH_TABLE();
		shadow = MAKE_HASH_TABLE();
		trace = true;
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
		tLIST keys = uses.HASH_TABLE_VALUES();
		for (tT walk : keys)
		{
			if (walk == pack)
				return true;

			if (((cPACKAGE) walk).isInUseList(pack))
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
		trace(DESCRIBE());
		trace("Internal : " + internal.DESCRIBE());
		trace("External : " + external.DESCRIBE());
		trace("Shadow : " + external.DESCRIBE());
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

		tT packN = packages.GETHASH(
				str(cSTRING.STRING((tSTRING_DESIGNATOR) pack)), NIL)[0];
		if (packN == NIL)
			throw new LispException("FIND-cPACKAGE : Package inconnu : " + pack);

		return packN;
	}

	// ////////////////////////////////////////////////////////////////////
	// Member functions
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tPACKAGE#INTERN(java.lang.String,
	 * aloyslisp.core.tPACKAGE_DESIGNATOR)
	 */
	@Override
	public tSYMBOL[] INTERN(String symbol)
	{
		symbol = symbol.toUpperCase();
		tSYMBOL res[] = FIND_SYMBOL(symbol);
		if (res[1] != NIL)
			return res;

		tSYMBOL sym = new cSYMBOL(symbol, this);
		internal.SET_GETHASH(sym, str(symbol));
		return new tSYMBOL[]
		{ sym, NIL };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tPACKAGE#UNINTERN(java.lang.String,
	 * aloyslisp.core.tPACKAGE_DESIGNATOR)
	 */
	@Override
	public tSYMBOL UNINTERN(String symbol)
	{
		symbol = symbol.toUpperCase();
		tT res;
		tSTRING sym = str(symbol);
		if ((res = external.GETHASH(sym, NIL)[0]) != NIL)
		{
			if (this != res)
			{
				external.REMHASH(sym);
				shadow.REMHASH(sym);
				return T;
			}

			// Test if conflict
			if (shadow.GETHASH(sym, NIL)[0] != NIL)
				throw new LispException("Correctable error shadow conflict");

			((tSYMBOL) res).SET_SYMBOL_PACKAGE(null);
			return NIL;
		}
		if ((res = internal.GETHASH(sym, NIL)[0]) != NIL)
		{
			if (this != res)
			{
				internal.REMHASH(sym);
				shadow.REMHASH(sym);
				return T;
			}

			// Test if conflict
			if (shadow.GETHASH(sym, NIL)[0] != NIL)
				throw new LispException("Correctable error shadow conflict");

			((tSYMBOL) res).SET_SYMBOL_PACKAGE(null);
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
	public tSYMBOL[] FIND_SYMBOL(String symbol)
	{
		symbol = symbol.toUpperCase();
		tSTRING sym = str(symbol);
		tT res = shadow.GETHASH(sym, null)[0];
		if (res != null || (res = external.GETHASH(sym, null)[0]) != null)
		{
			return new tSYMBOL[]
			{ (tSYMBOL) res, EXTERNAL };
		}
		else if ((res = internal.GETHASH(sym, null)[0]) != null)
		{
			return new tSYMBOL[]
			{ (tSYMBOL) res, INTERNAL };
		}
		else
		{
			for (tT p : uses.HASH_TABLE_VALUES())
			{
				res = ((cPACKAGE) p).shadow.GETHASH(sym, null)[0];
				if (res != null)
				{
					return new tSYMBOL[]
					{ (tSYMBOL) res, INHERITED };
				}
				res = ((cPACKAGE) p).external.GETHASH(sym, null)[0];
				if (res != null)
				{
					return new tSYMBOL[]
					{ (tSYMBOL) res, INHERITED };
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
	public tSYMBOL IMPORT(tT symbol)
	{
		if (symbol instanceof tLIST)
		{
			for (tT walk : (tLIST) symbol)
			{
				if (!(walk instanceof tSYMBOL))
					throw new LispException("Not a symbol");

				tSYMBOL sym = (tSYMBOL) walk;
				imp(sym);
			}
		}
		else if (symbol instanceof tSYMBOL)
		{
			imp((tSYMBOL) symbol);
		}
		else
			throw new LispException("Bad type for import");

		return T;
	}

	/**
	 * @param symbol
	 * @param pack
	 */
	private void imp(tSYMBOL symbol)
	{
		tSYMBOL[] sym = this.FIND_SYMBOL(symbol.SYMBOL_NAME());

		if (sym[1] == INTERNAL || sym[1] == EXTERNAL)
		{
			if (sym[0].SYMBOL_PACKAGE() == symbol.SYMBOL_PACKAGE()
					&& sym[0] != symbol)
				throw new LispException("Correctable symbol discrepency");
			if (sym[0].SYMBOL_PACKAGE() == NIL)
			{
				symbol.SET_SYMBOL_PACKAGE(this);
				shadow.SET_GETHASH(symbol, str(symbol.SYMBOL_NAME()));
			}
			else if (sym[0].SYMBOL_PACKAGE() != symbol.SYMBOL_PACKAGE())
			{
				shadow.SET_GETHASH(symbol, str(symbol.SYMBOL_NAME()));
			}
		}
		else
		{
			if (symbol.SYMBOL_PACKAGE() == NIL)
				symbol.SET_SYMBOL_PACKAGE(this);
			internal.SET_GETHASH(symbol, str(symbol.SYMBOL_NAME()));
		}
		return;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tPACKAGE#EXPORT(aloyslisp.core.tT,
	 * aloyslisp.core.tPACKAGE_DESIGNATOR)
	 */
	public tSYMBOL EXPORT(tT symbol)
	{
		if (symbol instanceof tLIST)
		{
			for (tT walk : (tLIST) symbol)
			{
				if (!(walk instanceof tSYMBOL))
					throw new LispException("Not a symbol");

				tSYMBOL sym = (tSYMBOL) walk;
				exp(sym);
			}
		}
		else if (symbol instanceof tSYMBOL)
		{
			exp((tSYMBOL) symbol);
		}
		else
			throw new LispException("Bad type for export");

		return T;
	}

	/**
	 * @param symbol
	 * @param pack
	 */
	private void exp(tSYMBOL symbol)
	{
		tSYMBOL[] sym = this.FIND_SYMBOL(symbol.SYMBOL_NAME());

		if (sym[1] == EXTERNAL)
			return;

		if (sym[1] == INTERNAL)
		{
			internal.REMHASH(str(sym[0].SYMBOL_NAME()));
			external.SET_GETHASH(sym[0], str(sym[0].SYMBOL_NAME()));
		}
		else if (sym[1] == INHERITED)
		{
			external.SET_GETHASH(sym[0], str(sym[0].SYMBOL_NAME()));
		}
		else
		{
			if (symbol.SYMBOL_PACKAGE() == NIL)
				symbol.SET_SYMBOL_PACKAGE(this);
			external.SET_GETHASH(symbol, str(symbol.SYMBOL_NAME()));
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

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return str(name).hashCode();
	}

	public String DESCRIBE()
	{
		return "#<PACKAGE " + name + " " + uses + " " + internal + " "
				+ external + " " + shadow + ">";
	}

}
