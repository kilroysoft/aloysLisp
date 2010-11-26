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

package aloyslisp.core.types;

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
public interface tSYMBOL extends tATOM
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
	public tSYMBOL SET_SYMBOL_PACKAGE(tT dictionary);

	/**
	 * @return
	 */
	public tPACKAGE SYMBOL_PACKAGE();

	/**
	 * @return
	 */
	public tSYMBOL UNINTERN();

	/**
	 * @param exported
	 * @return
	 */
	public tSYMBOL setExported(boolean exported);

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
	public tSYMBOL SET_GET(tT name, tT data);

	/**
	 * @param name
	 * @return
	 */
	public tT GET(tT name, tT def);

	/**
	 * @param name
	 * @return
	 */
	public tT REMPROP(tT name);

	/**
	 * @return
	 */
	public String SYMBOL_NAME();

	/**
	 * @param constant
	 * @return
	 */
	public tSYMBOL setConstant(boolean constant);

	/**
	 * @return
	 */
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
	public tSYMBOL SET_SYMBOL_VALUE(tT value);

	/**
	 * @param func
	 * @return
	 */
	public tSYMBOL SET_SYMBOL_FUNCTION(tFUNCTION func);

	/**
	 * @return
	 */
	public tT SYMBOL_VALUE();

	/**
	 * @return
	 */
	public tFUNCTION SYMBOL_FUNCTION();

	/**
	 * @return
	 */
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
	public boolean BOUNDP();

	/**
	 * @return
	 */
	public boolean FBOUNDP();

	/**
	 * @return
	 */
	public boolean SPECIAL_FORM_P();

}
