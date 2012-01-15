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
// IP 13 sept. 2010-2011 Creation
// IP UB20 Disconnected from cTHREAD
// --------------------------------------------------------------------------

package aloyslisp.core.packages;

import aloyslisp.annotations.*;
import aloyslisp.core.cCELL;
import aloyslisp.core.tT;
import aloyslisp.core.conditions.*;
import aloyslisp.core.designators.tPACKAGE_DESIGNATOR;
import aloyslisp.core.designators.tSTRING_DESIGNATOR;
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
@aBuiltIn(lispClass = "package", doc = "t_pkg")
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
	 * Internal symbols
	 */
	public tHASH_TABLE	internal;

	/**
	 * External symbols
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
		name = name.toUpperCase();
		// trace = true;
		this.name = name;
		uses = MAKE_HASH_TABLE();
		if (cl != null && !name.equals(cl.PACKAGE_NAME()))
		{
			// As standard add COMMON-LISP in uses list
			uses.SET_GETHASH(cl, str(cl.PACKAGE_NAME()));
		}
		internal = MAKE_HASH_TABLE();
		external = MAKE_HASH_TABLE();
		shadow = MAKE_HASH_TABLE();
		trace = true;
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

	// ////////////////////////////////////////////////////////////////////
	// Static functions

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.collections.IPackage#isInUseList(aloyslisp.core.
	 * plugs.cCELL)
	 */
	@Override
	@aNonStandard
	@aFunction(name = "is-in-use-list")
	public boolean IS_IN_USE_LIST( //
			@aArg(name = "pack") tPACKAGE pack)
	{
		tLIST keys = uses.HASH_TABLE_VALUES();
		for (tT walk : keys)
		{
			if (walk == pack)
				return true;

			if (((cPACKAGE) walk).IS_IN_USE_LIST(pack))
				return true;
		}

		return false;
	}

	/**
	 * delete-package deletes package from all package system data structures.
	 * If the operation is successful, delete-package returns true, otherwise
	 * nil. The effect of delete-package is that the name and nicknames of
	 * package cease to be recognized package names. The package object is still
	 * a package (i.e., packagep is true of it) but package-name returns nil.
	 * The consequences of deleting the COMMON-LISP package or the KEYWORD
	 * package are undefined. The consequences of invoking any other package
	 * operation on package once it has been deleted are unspecified. In
	 * particular, the consequences of invoking find-symbol, intern and other
	 * functions that look for a symbol name in a package are unspecified if
	 * they are called with *package* bound to the deleted package or with the
	 * deleted package as an argument.
	 * <p>
	 * If package is a package object that has already been deleted,
	 * delete-package immediately returns nil.
	 * <p>
	 * After this operation completes, the home package of any symbol whose home
	 * package had previously been package is implementation-dependent. Except
	 * for this, symbols accessible in package are not modified in any other
	 * way; symbols whose home package is not package remain unchanged.
	 * 
	 * @param pack
	 * @return
	 */
	@aFunction(name = "delete-package", doc = "f_del_pk")
	public static Boolean DELETE_PACKAGE( //
			@aArg(name = "pack") tPACKAGE_DESIGNATOR pack)
	{
		return true;
	}

	/**
	 * list-all-packages returns a fresh list of all registered packages.
	 * 
	 * @return
	 */
	@aFunction(name = "list-all-packages", doc = "f_list_a")
	public static tLIST LIST_ALL_PACKAGES()
	{
		return null;
	}

	/**
	 * find-all-symbols searches every registered package for symbols that have
	 * a name that is the same (under string=) as string. A list of all such
	 * symbols is returned. Whether or how the list is ordered is
	 * implementation-dependent.
	 * 
	 * @param symbol
	 * @return
	 */
	@aFunction(name = "find-symbol", doc = "f_find_s")
	public static tSYMBOL[] FIND_ALL_SYMBOL( //
			@aArg(name = "symbol") String symbol //
	)
	{
		return null;
	}

	/**
	 * If name is a string designator, find-package locates and returns the
	 * package whose name or nickname is name. This search is case sensitive. If
	 * there is no such package, find-package returns nil.
	 * <p>
	 * If name is a package object, that package object is returned.
	 * 
	 * @param pack
	 * @return
	 */
	@aFunction(name = "find-package", doc = "f_find_p")
	public static tT FIND_PACKAGE( //
			@aArg(name = "pack") tPACKAGE_DESIGNATOR pack)
	{
		if (pack == null || pack == NIL)
			return currPackage();
		if (pack instanceof tPACKAGE)
			return pack;
		if (!(pack instanceof tSTRING_DESIGNATOR))
			throw new LispException("aType error for " + pack);

		tT packN = packages.GETHASH(
				str(cSTRING.STRING((tSTRING_DESIGNATOR) pack)), NIL)[0];
		if (packN == NIL)
			return NIL;

		return packN;
	}

	/**
	 * Causes the the package named by name to become the current package---that
	 * is, the value of *package*. If no such package already exists, an error
	 * of type package-error is signaled.
	 * <p>
	 * Everything in-package does is also performed at compile time if the call
	 * appears as a top level form.
	 * 
	 * @param pack
	 * @return
	 */
	@aFunction(name = "in-package", doc = "f_in_pkg")
	public static tPACKAGE IN_PACKAGE( //
			@aArg(name = "pack") tPACKAGE_DESIGNATOR pack)
	{
		tT found = FIND_PACKAGE(pack);
		if (found == NIL)
			throw new LispException("IN-PACKAGE no package for " + pack);
		return (tPACKAGE) found;
	}

	/**
	 * defpackage creates a package as specified and returns the package.
	 * <p>
	 * If defined-package-name already refers to an existing package, the
	 * name-to-package mapping for that name is not changed. If the new
	 * definition is at variance with the current state of that package, the
	 * consequences are undefined; an implementation might choose to modify the
	 * existing package to reflect the new definition. If defined-package-name
	 * is a symbol, its name is used.
	 * <p>
	 * The standard options are described below.
	 * <p>
	 * :nicknames
	 * <p>
	 * The arguments to :nicknames set the package's nicknames to the supplied
	 * names.
	 * <p>
	 * :documentation
	 * <p>
	 * The argument to :documentation specifies a documentation string; it is
	 * attached as a documentation string to the package. At most one
	 * :documentation option can appear in a single defpackage form.
	 * <p>
	 * :use
	 * <p>
	 * The arguments to :use set the packages that the package named by
	 * package-name will inherit from. If :use is not supplied, it defaults to
	 * the same implementation-dependent value as the :use argument to
	 * make-package.
	 * <p>
	 * :shadow
	 * <p>
	 * The arguments to :shadow, symbol-names, name symbols that are to be
	 * created in the package being defined. These symbols are added to the list
	 * of shadowing symbols effectively as if by shadow.
	 * <p>
	 * :shadowing-import-from
	 * <p>
	 * The symbols named by the argument symbol-names are found (involving a
	 * lookup as if by find-symbol) in the specified package-name. The resulting
	 * symbols are imported into the package being defined, and placed on the
	 * shadowing symbols list as if by shadowing-import. In no case are symbols
	 * created in any package other than the one being defined.
	 * <p>
	 * :import-from
	 * <p>
	 * The symbols named by the argument symbol-names are found in the package
	 * named by package-name and they are imported into the package being
	 * defined. In no case are symbols created in any package other than the one
	 * being defined.
	 * <p>
	 * :export
	 * <p>
	 * The symbols named by the argument symbol-names are found or created in
	 * the package being defined and exported. The :export option interacts with
	 * the :use option, since inherited symbols can be used rather than new ones
	 * created. The :export option interacts with the :import-from and
	 * :shadowing-import-from options, since imported symbols can be used rather
	 * than new ones created. If an argument to the :export option is accessible
	 * as an (inherited) internal symbol via use-package, that the symbol named
	 * by symbol-name is first imported into the package being defined, and is
	 * then exported from that package.
	 * <p>
	 * :intern
	 * <p>
	 * The symbols named by the argument symbol-names are found or created in
	 * the package being defined. The :intern option interacts with the :use
	 * option, since inherited symbols can be used rather than new ones created.
	 * <p>
	 * :size
	 * <p>
	 * The argument to the :size option declares the approximate number of
	 * symbols expected in the package. This is an efficiency hint only and
	 * might be ignored by an implementation.
	 * <p>
	 * The order in which the options appear in a defpackage form is irrelevant.
	 * The order in which they are executed is as follows:
	 * <p>
	 * 1. :shadow and :shadowing-import-from. 2. :use. 3. :import-from and
	 * :intern. 4. :export.
	 * <p>
	 * Shadows are established first, since they might be necessary to block
	 * spurious name conflicts when the :use option is processed. The :use
	 * option is executed next so that :intern and :export options can refer to
	 * normally inherited symbols. The :export option is executed last so that
	 * it can refer to symbols created by any of the other options; in
	 * particular, shadowing symbols and imported symbols can be made external.
	 * <p>
	 * If a defpackage form appears as a top level form, all of the actions
	 * normally performed by this macro at load time must also be performed at
	 * compile time.
	 * 
	 * @param pack
	 * @param options
	 * @return
	 */
	@aFunction(name = "defpackage", doc = "f_defpkg")
	public static tPACKAGE MAKE_PACKAGE( //
			@aArg(name = "pack") tSTRING_DESIGNATOR pack, //
			@aRest(name = "options") tLIST options)
	{
		return null;
	}

	public String DESCRIBE()
	{
		return "#<PACKAGE " + name + " " + uses + " " + internal + " "
				+ external + " " + shadow + ">";
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
	 * @see aloyslisp.core.packages.tPACKAGE#PACKAGE_NICKNAMES()
	 */
	@Override
	public tLIST PACKAGE_NICKNAMES()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.packages.tPACKAGE#PACKAGE_SHADOWING_SYMBOLS()
	 */
	@Override
	public tLIST PACKAGE_SHADOWING_SYMBOLS()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.packages.tPACKAGE#PACKAGE_USE_LIST()
	 */
	@Override
	public tLIST PACKAGE_USE_LIST()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.packages.tPACKAGE#PACKAGE_USED_BY_LIST()
	 */
	@Override
	public tLIST PACKAGE_USED_BY_LIST()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.packages.tPACKAGE#RENAME_PACKAGE(aloyslisp.core.tT,
	 * aloyslisp.core.tT)
	 */
	@Override
	public tSYMBOL RENAME_PACKAGE(tT newName, tT newNicknames)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.packages.tPACKAGE#SHADOW(aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public tSYMBOL SHADOW(tLIST symbols)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.packages.tPACKAGE#SHADOWING_IMPORT(aloyslisp.core.sequences
	 * .tLIST)
	 */
	@Override
	public tSYMBOL SHADOWING_IMPORT(tLIST symbols)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public Integer SXHASH()
	{
		return str(name).SXHASH();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.Cell#printable()
	 */
	@Override
	public String TO_STRING()
	{
		return "#<cPACKAGE " + name + ">";
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.packages.tPACKAGE#UNEXPORT(aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public tSYMBOL UNEXPORT(tLIST symbols)
	{
		// TODO Auto-generated method stub
		return null;
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
	 * @see
	 * aloyslisp.core.packages.tPACKAGE#UNUSE_PACKAGE(aloyslisp.core.sequences
	 * .tLIST)
	 */
	@Override
	public tSYMBOL UNUSE_PACKAGE(tLIST packages)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.packages.tPACKAGE#USE_PACKAGE(aloyslisp.core.sequences
	 * .tLIST)
	 */
	@Override
	public tSYMBOL USE_PACKAGE(tLIST packages)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
