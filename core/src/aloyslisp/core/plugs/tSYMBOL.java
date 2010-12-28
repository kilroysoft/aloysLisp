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
// IP 12 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import aloyslisp.core.annotations.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.sequences.*;

/**
 * tSYMBOL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
/**
 * tSYMBOL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tSYMBOL extends tATOM, tFUNCTION_DESIGNATOR
{
	/**
	 * Create a copy of the symbol plus copy of value define and copy of plist.
	 * 
	 * @return
	 */
	public tT copy(tT copyProps);

	/**
	 * @param pack
	 * @return
	 */
	@Function(name = "set-symbol-package")
	public tSYMBOL SET_SYMBOL_PACKAGE( //
			@Arg(name = "pack") tT pack);

	/**
	 * @return
	 */
	@Function(name = "symbol-package")
	public tPACKAGE SYMBOL_PACKAGE();

	/**
	 * @return
	 */
	@Function(name = "unintern")
	public tSYMBOL UNINTERN();

	/**
	 * @param exported
	 * @return
	 */
	public tSYMBOL setExported(Boolean exported);

	/**
	 * @return
	 */
	public boolean isExported();

	/**
	 * @param special
	 * @return
	 */
	public tSYMBOL setSpecial(boolean special);

	/**
	 * @return
	 */
	public boolean isSpecial();

	/**
	 * @param constant
	 * @return
	 */
	public tSYMBOL setDeclare(tLIST declare);

	/**
	 * @return
	 */
	public tLIST getDeclare();

	/**
	 * @param name
	 * @param data
	 * @return
	 */
	@Function(name = "set-get")
	public tSYMBOL SET_GET( //
			@Arg(name = "name") tT name, //
			@Arg(name = "data") tT data);

	/**
	 * @param name
	 * @return
	 */
	@Function(name = "get")
	public tT GET( //
			@Arg(name = "name") tT name, //
			@Arg(name = "def") tT def);

	/**
	 * @param name
	 * @return
	 */
	@Function(name = "remprop")
	public tT REMPROP( //
			@Arg(name = "name") tT name);

	/**
	 * @return
	 */
	@Function(name = "symbol-name")
	public String SYMBOL_NAME();

	/**
	 * @param constant
	 * @return
	 */
	public tSYMBOL setConstant(boolean constant);

	/**
	 * @return
	 */
	@Function(name = "constantp")
	public boolean CONSTANTP();

	/**
	 * @param args
	 * @return
	 */
	public tT[] e(Object... args);

	/**
	 * @param value
	 * @return
	 */
	@Function(name = "set-symbol-value")
	public tSYMBOL SET_SYMBOL_VALUE( //
			@Arg(name = "value") tT value);

	/**
	 * @param func
	 * @return
	 */
	@Function(name = "set-symbol-function")
	public tSYMBOL SET_SYMBOL_FUNCTION( //
			@Arg(name = "func") tFUNCTION func);

	/**
	 * @return
	 */
	@Function(name = "symbol-value")
	public tT SYMBOL_VALUE();

	/**
	 * @return
	 */
	@Function(name = "symbol-function")
	public tFUNCTION SYMBOL_FUNCTION();

	/**
	 * @return
	 */
	@Function(name = "fdefinition")
	public tFUNCTION FDEFINITION();

	/**
	 * @return
	 */
	public tSYMBOL unset();

	/**
	 * @return
	 */
	public tSYMBOL fUnset();

	/**
	 * @return
	 */
	@Function(name = "boundp")
	public boolean BOUNDP();

	/**
	 * @return
	 */
	@Function(name = "fboundp")
	public boolean FBOUNDP();

	/**
	 * @return
	 */
	@Function(name = "special-form-p")
	public boolean SPECIAL_FORM_P();

}
