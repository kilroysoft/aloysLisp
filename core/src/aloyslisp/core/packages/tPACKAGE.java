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
	 * @param pack
	 * @return
	 */
	public boolean isInUseList(tPACKAGE pack);

	// ////////////////////////////////////////////////////////////////////
	// Static functions

	// ////////////////////////////////////////////////////////////////////
	// Member functions
	/**
	 * @param symbol
	 * @param pack
	 * @return
	 */
	@aFunction(name = "intern", doc = "f_intern")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL[] INTERN(@aArg(name = "symbol") String symbol //
	);

	/**
	 * @param symbol
	 * @param pack
	 * @return
	 */
	@aFunction(name = "unintern", doc = "f_uninte")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL UNINTERN(@aArg(name = "symbol") String symbol //
	);

	/**
	 * @param symbol
	 * @param pack
	 * @return
	 */
	@aFunction(name = "find-symbol", doc = "f_find_s")
	@aBaseArg(name = "package", pos = 1, type = aOpt.class, def = "*package*")
	public tSYMBOL[] FIND_SYMBOL(@aArg(name = "symbol") String symbol //
	);

	/**
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
	 * @return
	 */
	@aFunction(name = "package-name", doc = "f_pkg_na")
	public String PACKAGE_NAME();

}
