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
// IP 25 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.packages;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.clos.*;

/**
 * tDYN_SYMBOL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tDYN_SYMBOL extends tBUILT_IN_CLASS
{
	/**
	 * @return
	 */
	@aFunction(name = "boundp", doc = "f_boundp")
	public boolean BOUNDP();

	/**
	 * @return
	 */
	@aFunction(name = "makunbound", doc = "f_makunb")
	public tSYMBOL MAKUNBOUND();

	/**
	 * @return
	 */
	@aNonStandard
	@aWriter(accessor = "specialp")
	@aFunction(name = "set-special")
	public tSYMBOL SET_SPECIAL( //
			@aArg(name = "special-p") Boolean special);

	/**
	 * @param newOrig
	 * @return
	 */
	@aNonStandard
	@aWriter(accessor = "symbol-orig")
	@aFunction(name = "set-symbol-orig")
	public tSYMBOL SET_SYMBOL_ORIG( //
			@aArg(name = "new-orig") tSYMBOL newOrig);

	/**
	 * @param value
	 * @return
	 */
	@aNonStandard
	@aWriter(accessor = "symbol-value")
	@aFunction(name = "set-symbol-value")
	public tSYMBOL SET_SYMBOL_VALUE( //
			@aArg(name = "value") tT value);

	/**
	 * @return
	 */
	@aFunction(name = "specialp")
	public Boolean SPECIALP();

	/**
	 * @return
	 */
	@aFunction(name = "symbol-name", doc = "f_symb_2")
	public String SYMBOL_NAME();

	/**
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "symbol-orig")
	public tSYMBOL SYMBOL_ORIG();

	/**
	 * @return
	 */
	@aFunction(name = "symbol-package", doc = "f_symb_3")
	public tPACKAGE SYMBOL_PACKAGE();

	/**
	 * @return
	 */
	@aFunction(name = "symbol-value", doc = "f_symb_5")
	public tT SYMBOL_VALUE();
}
