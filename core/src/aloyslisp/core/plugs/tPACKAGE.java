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
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import aloyslisp.core.annotations.*;

/**
 * tPACKAGE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tPACKAGE extends tBUILD_IN_CLASS, tPACKAGE_DESIGNATOR
{
	/**
	 * @param pack
	 * @return
	 */
	public boolean isInUseList(tPACKAGE pack);

	/**
	 * APROPOS
	 * 
	 * @return
	 */
	public String dump();

	// ////////////////////////////////////////////////////////////////////
	// Static functions

	// ////////////////////////////////////////////////////////////////////
	// Member functions
	/**
	 * @param symbol
	 * @param pack
	 * @return
	 */
	@Function(name = "intern", doc = "f_intern")
	public tSYMBOL[] INTERN(
			//
			@Arg(name = "symbol") String symbol, //
			@BaseArg @Opt(name = "package", def = "*package*") tPACKAGE_DESIGNATOR pack);

	/**
	 * @param symbol
	 * @param pack
	 * @return
	 */
	@Function(name = "unintern", doc = "f_uninte")
	public tSYMBOL UNINTERN(
			//
			@Arg(name = "symbol") String symbol, //
			@BaseArg @Opt(name = "package", def = "*package*") tPACKAGE_DESIGNATOR pack);

	/**
	 * @param symbol
	 * @param pack
	 * @return
	 */
	@Function(name = "find-symbol", doc = "f_find_s")
	public tSYMBOL[] FIND_SYMBOL(
			//
			@Arg(name = "symbol") String symbol, //
			@BaseArg @Opt(name = "package", def = "*package*") tPACKAGE_DESIGNATOR pack);

	/**
	 * @param symbols
	 * @param pack
	 * @return
	 */
	@Function(name = "import", doc = "f_import")
	public tSYMBOL IMPORT(
			//
			@Arg(name = "symbols") tT symbols, //
			@BaseArg @Opt(name = "package", def = "*package*") tPACKAGE_DESIGNATOR pack);

	/**
	 * @param symbols
	 * @param pack
	 * @return
	 */
	@Function(name = "export", doc = "f_export")
	public tSYMBOL EXPORT(
			//
			@Arg(name = "symbols") tT symbols, //
			@BaseArg @Opt(name = "package", def = "*package*") tPACKAGE_DESIGNATOR pack);

	/**
	 * @return
	 */
	@Function(name = "package-name", doc = "f_pkg_na")
	public String PACKAGE_NAME();

}
