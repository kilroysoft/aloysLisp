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
// IP 12 sept. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.packages;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.designators.tFUNCTION_DESIGNATOR;
import aloyslisp.core.designators.tPACKAGE_DESIGNATOR;
import aloyslisp.core.functions.*;

/**
 * tSYMBOL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "symbol", doc = "t_symbol", typep = "symbolp")
public interface tSYMBOL extends tDYN_SYMBOL, tFUNCTION_DESIGNATOR,
		tPACKAGE_DESIGNATOR
{
	/**
	 * @param args
	 * @return
	 */
	@aJavaInternal
	public tT[] e(Object... args);

	/**
	 * Create a copy of the symbol plus copy of value define and copy of plist.
	 * 
	 * @return
	 */
	@aFunction(name = "copy-symbol", doc = "f_cp_sym")
	public tT COPY_SYMBOL( //
			@aArg(name = "copy-props") tT copyProps);

	/**
	 * @return
	 */
	@aFunction(name = "fboundp", doc = "f_fbound")
	public boolean FBOUNDP();

	/**
	 * @return
	 */
	@aFunction(name = "fdefinition", doc = "f_fdefin")
	public tFUNCTION FDEFINITION();

	/**
	 * @return
	 */
	@aFunction(name = "fmakunbound", doc = "f_fmakun")
	public tSYMBOL FMAKUNBOUND();

	/**
	 * @param name
	 * @return
	 */
	@aFunction(name = "get", doc = "f_get")
	public tT GET( //
			@aArg(name = "name") tT name, //
			@aArg(name = "def") tT def);

	/**
	 * @return
	 */
	@aFunction(name = "macro-function", doc = "f_macro_")
	public tT MACRO_FUNCTION();

	/**
	 * @param name
	 * @return
	 */
	@aFunction(name = "remprop", doc = "f_rempro")
	public tT REMPROP( //
			@aArg(name = "name") tT name);

	/**
	 * @param constant
	 * @return
	 */
	@aNonStandard
	@aWriter(accessor = "constantp")
	@aFunction(name = "set-constant")
	public tSYMBOL SET_CONSTANT( //
			@aArg(name = "constant") boolean constant);

	/**
	 * @param name
	 * @param data
	 * @return
	 */
	@aFunction(name = "set-get")
	@aNonStandard
	@aWriter(accessor = "get")
	public tSYMBOL SET_GET( //
			@aArg(name = "name") tT name, //
			@aArg(name = "data") tT data);

	/**
	 * @param func
	 * @return
	 */
	@aFunction(name = "set-symbol-function")
	@aNonStandard
	@aWriter(accessor = "symbol-function")
	public tSYMBOL SET_SYMBOL_FUNCTION( //
			@aArg(name = "func") tFUNCTION func);

	/**
	 * @param pack
	 * @return
	 */
	@aFunction(name = "set-symbol-package")
	@aWriter(accessor = "symbol-package")
	public tSYMBOL SET_SYMBOL_PACKAGE( //
			@aArg(name = "pack") tPACKAGE_DESIGNATOR pack);

	/**
	 * @return
	 */
	@aFunction(name = "special-operator-p", doc = "f_specia")
	public tT SPECIAL_OPERATOR_P();

	/**
	 * @return
	 */
	@aFunction(name = "symbol-function", doc = "f_symb_1")
	public tFUNCTION SYMBOL_FUNCTION();

}
