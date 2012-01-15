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
// --------------------------------------------------------------------------

package aloyslisp.core.packages;

import aloyslisp.annotations.*;
import aloyslisp.core.tT;
import aloyslisp.core.clos.tBUILT_IN_CLASS;
import aloyslisp.core.designators.tPACKAGE_DESIGNATOR;
import aloyslisp.core.sequences.tLIST;

/**
 * tPACKAGE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "package", doc = "t_pkg", typep = "packagep")
public interface tPACKAGE extends tBUILT_IN_CLASS, tPACKAGE_DESIGNATOR
{
	/**
	 * Test if package is in the use list.
	 * 
	 * @param pack
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "is-in-use-list")
	public boolean IS_IN_USE_LIST( //
			@aArg(name = "pack") tPACKAGE pack);

	/**
	 * export makes one or more symbols that are accessible in package (whether
	 * directly or by inheritance) be external symbols of that package.
	 * <p>
	 * If any of the symbols is already accessible as an external symbol of
	 * package, export has no effect on that symbol. If the symbol is present in
	 * package as an internal symbol, it is simply changed to external status.
	 * If it is accessible as an internal symbol via use-package, it is first
	 * imported into package, then exported. (The symbol is then present in the
	 * package whether or not package continues to use the package through which
	 * the symbol was originally inherited.)
	 * <p>
	 * export makes each symbol accessible to all the packages that use package.
	 * All of these packages are checked for name conflicts: (export s p) does
	 * (find-symbol (symbol-name s) q) for each package q in
	 * (package-used-by-list p). Note that in the usual case of an export during
	 * the initial definition of a package, the result of package-used-by-list
	 * is nil and the name-conflict checking takes negligible time. When
	 * multiple changes are to be made, for example when export is given a list
	 * of symbols, it is permissible for the implementation to process each
	 * change separately, so that aborting from a name conflict caused by any
	 * but the first symbol in the list does not unexport the first symbol in
	 * the list. However, aborting from a name-conflict error caused by export
	 * of one of symbols does not leave that symbol accessible to some packages
	 * and inaccessible to others; with respect to each of symbols processed,
	 * export behaves as if it were as an atomic operation.
	 * <p>
	 * A name conflict in export between one of symbols being exported and a
	 * symbol already present in a package that would inherit the newly-exported
	 * symbol may be resolved in favor of the exported symbol by uninterning the
	 * other one, or in favor of the already-present symbol by making it a
	 * shadowing symbol.
	 * 
	 * @param symbols
	 * @param pack
	 * @return
	 */
	@aFunction(name = "export", doc = "f_export")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL EXPORT(
	//
			@aArg(name = "symbols") tT symbols //
	);

	/**
	 * find-symbol locates a symbol whose name is string in a package. If a
	 * symbol named string is found in package, directly or by inheritance, the
	 * symbol found is returned as the first value; the second value is as
	 * follows:
	 * <p>
	 * :internal
	 * <p>
	 * If the symbol is present in package as an internal symbol.
	 * <p>
	 * :external
	 * <p>
	 * If the symbol is present in package as an external symbol.
	 * <p>
	 * :inherited
	 * <p>
	 * If the symbol is inherited by package through use-package, but is not
	 * present in package.
	 * <p>
	 * If no such symbol is accessible in package, both values are nil.
	 * 
	 * @param symbol
	 * @param pack
	 * @return
	 */
	@aFunction(name = "find-symbol", doc = "f_find_s")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL[] FIND_SYMBOL(@aArg(name = "symbol") String symbol //
	);

	/**
	 * import adds symbol or symbols to the internals of package, checking for
	 * name conflicts with existing symbols either present in package or
	 * accessible to it. Once the symbols have been imported, they may be
	 * referenced in the importing package without the use of a package prefix
	 * when using the Lisp reader.
	 * 
	 * @param symbols
	 * @param pack
	 * @return
	 */
	@aFunction(name = "import", doc = "f_import")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL IMPORT(
	//
			@aArg(name = "symbols") tT symbols //
	);

	/**
	 * intern enters a symbol named string into package. If a symbol whose name
	 * is the same as string is already accessible in package, it is returned.
	 * If no such symbol is accessible in package, a new symbol with the given
	 * name is created and entered into package as an internal symbol, or as an
	 * external symbol if the package is the KEYWORD package; package becomes
	 * the home package of the created symbol.
	 * <p>
	 * The first value returned by intern, symbol, is the symbol that was found
	 * or created. The meaning of the secondary value, status, is as follows:
	 * <p>
	 * :internal
	 * <p>
	 * The symbol was found and is present in package as an internal symbol.
	 * <p>
	 * :external
	 * <p>
	 * The symbol was found and is present as an external symbol.
	 * <p>
	 * :inherited
	 * <p>
	 * The symbol was found and is inherited via use-package (which implies that
	 * the symbol is internal).
	 * <p>
	 * nil
	 * <p>
	 * No pre-existing symbol was found, so one was created.
	 * <p>
	 * It is implementation-dependent whether the string that becomes the new
	 * symbol's name is the given string or a copy of it. Once a string has been
	 * given as the string argument to intern in this situation where a new
	 * symbol is created, the consequences are undefined if a subsequent attempt
	 * is made to alter that string.
	 * 
	 * @param symbol
	 * @param pack
	 * @return
	 */
	@aFunction(name = "intern", doc = "f_intern")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL[] INTERN(@aArg(name = "symbol") String symbol //
	);

	/**
	 * package-name returns the string that names package, or nil if the package
	 * designator is a package object that has no name (see the function
	 * delete-package).
	 * 
	 * @return
	 */
	@aFunction(name = "package-name", doc = "f_pkg_na")
	public String PACKAGE_NAME();

	/**
	 * Returns the list of nickname strings for package, not including the name
	 * of package.
	 * 
	 * @return
	 */
	@aFunction(name = "package-nickname", doc = "f_pkg_ni")
	public tLIST PACKAGE_NICKNAMES();

	/**
	 * Returns a list of symbols that have been declared as shadowing symbols in
	 * package by shadow or shadowing-import (or the equivalent defpackage
	 * options). All symbols on this list are present in package.
	 * 
	 * @return
	 */
	@aFunction(name = "package-shadowing-symbols", doc = "f_pkg_sh")
	public tLIST PACKAGE_SHADOWING_SYMBOLS();

	/**
	 * Returns a list of other packages used by package.
	 * 
	 * @return
	 */
	@aFunction(name = "package-use-list", doc = "f_pkg_us")
	public tLIST PACKAGE_USE_LIST();

	/**
	 * package-used-by-list returns a list of other packages that use package.
	 * 
	 * @return
	 */
	@aFunction(name = "package-used-by-list", doc = "f_pkg_1")
	public tLIST PACKAGE_USED_BY_LIST();

	/**
	 * Replaces the name and nicknames of package. The old name and all of the
	 * old nicknames of package are eliminated and are replaced by new-name and
	 * new-nicknames.
	 * 
	 * @param newName
	 * @param newNicknames
	 * @return
	 */
	@aFunction(name = "rename-package", doc = "f_rn_pkg")
	public tSYMBOL RENAME_PACKAGE(
	//
			@aArg(name = "new-name") tT newName, //
			@aOpt(name = "new-nicknames") tT newNicknames //
	);

	/**
	 * shadow assures that symbols with names given by symbol-names are present
	 * in the package.
	 * <p>
	 * Specifically, package is searched for symbols with the names supplied by
	 * symbol-names. For each such name, if a corresponding symbol is not
	 * present in package (directly, not by inheritance), then a corresponding
	 * symbol is created with that name, and inserted into package as an
	 * internal symbol. The corresponding symbol, whether pre-existing or newly
	 * created, is then added, if not already present, to the shadowing symbols
	 * list of package.
	 * 
	 * @param symbols
	 * @return
	 */
	@aFunction(name = "shadow", doc = "f_shadow")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL SHADOW(
	//
			@aArg(name = "symbols") tLIST symbols //
	);

	/**
	 * shadowing-import is like import, but it does not signal an error even if
	 * the importation of a symbol would shadow some symbol already accessible
	 * in package.
	 * <p>
	 * shadowing-import inserts each of symbols into package as an internal
	 * symbol, regardless of whether another symbol of the same name is shadowed
	 * by this action. If a different symbol of the same name is already present
	 * in package, that symbol is first uninterned from package. The new symbol
	 * is added to package's shadowing-symbols list.
	 * <p>
	 * shadowing-import does name-conflict checking to the extent that it checks
	 * whether a distinct existing symbol with the same name is accessible; if
	 * so, it is shadowed by the new symbol, which implies that it must be
	 * uninterned if it was present in package.
	 * 
	 * @param symbols
	 * @return
	 */
	@aFunction(name = "shadowing-import", doc = "f_shdw_i")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL SHADOWING_IMPORT(
	//
			@aArg(name = "symbols") tLIST symbols //
	);

	/**
	 * unexport reverts external symbols in package to internal status; it
	 * undoes the effect of export.
	 * <p>
	 * unexport works only on symbols present in package, switching them back to
	 * internal status. If unexport is given a symbol that is already accessible
	 * as an internal symbol in package, it does nothing.
	 * 
	 * @param symbols
	 * @return
	 */
	@aFunction(name = "unexport", doc = "f_unexpo")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL UNEXPORT(
	//
			@aArg(name = "symbols") tLIST symbols //
	);

	/**
	 * unintern removes symbol from package. If symbol is present in package, it
	 * is removed from package and also from package's shadowing symbols list if
	 * it is present there. If package is the home package for symbol, symbol is
	 * made to have no home package. Symbol may continue to be accessible in
	 * package by inheritance.
	 * <p>
	 * Use of unintern can result in a symbol that has no recorded home package,
	 * but that in fact is accessible in some package. Common Lisp does not
	 * check for this pathological case, and such symbols are always printed
	 * preceded by #:.
	 * <p>
	 * unintern returns true if it removes symbol, and nil otherwise.
	 * 
	 * @param symbol
	 * @param pack
	 * @return
	 */
	@aFunction(name = "unintern", doc = "f_uninte")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL UNINTERN(@aArg(name = "symbol") String symbol //
	);

	/**
	 * unuse-package causes package to cease inheriting all the external symbols
	 * of packages-to-unuse; unuse-package undoes the effects of use-package.
	 * The packages-to-unuse are removed from the use list of package.
	 * <p>
	 * Any symbols that have been imported into package continue to be present
	 * in package.
	 * <p>
	 * 
	 * @param packages
	 * @return
	 */
	@aFunction(name = "unuse-package", doc = "f_unuse_")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL UNUSE_PACKAGE(@aArg(name = "packages") tLIST packages //
	);

	/**
	 * use-package causes package to inherit all the external symbols of
	 * packages-to-use. The inherited symbols become accessible as internal
	 * symbols of package.
	 * <p>
	 * Packages-to-use are added to the use list of package if they are not
	 * there already. All external symbols in packages-to-use become accessible
	 * in package as internal symbols. use-package does not cause any new
	 * symbols to be present in package but only makes them accessible by
	 * inheritance.
	 * <p>
	 * use-package checks for name conflicts between the newly imported symbols
	 * and those already accessible in package. A name conflict in use-package
	 * between two external symbols inherited by package from packages-to-use
	 * may be resolved in favor of either symbol by importing one of them into
	 * package and making it a shadowing symbol.
	 * 
	 * @param packages
	 * @return
	 */
	@aFunction(name = "use-package", doc = "f_use_pk")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL USE_PACKAGE(@aArg(name = "packages") tLIST packages //
	);

}
