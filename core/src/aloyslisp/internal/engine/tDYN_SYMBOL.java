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
// IP 25 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.core.tT;
import aloyslisp.core.clos.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;

/**
 * tDYN_SYMBOL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tDYN_SYMBOL extends tBUILD_IN_CLASS
{
	public tSYMBOL SET_SYMBOL_VALUE(tT value);

	public tSYMBOL SET_SYMBOL_FUNCTION(tFUNCTION func);

	public tT SYMBOL_VALUE();

	public tFUNCTION SYMBOL_FUNCTION();

	public tSYMBOL unset();

	public tSYMBOL fUnset();

	public tPACKAGE SYMBOL_PACKAGE();

	public tSYMBOL setSpecial(boolean special);

	public boolean isSpecial();

	public tSYMBOL setDeclare(tLIST declare);

	public tLIST getDeclare();

	public String SYMBOL_NAME();

	public tSYMBOL getOrig();

	public boolean BOUNDP();

	public boolean FBOUNDP();

}
