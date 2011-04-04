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
// IP 10 nov. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.clos.tBUILT_IN_CLASS;
import aloyslisp.core.designators.tFUNCTION_DESIGNATOR;
import aloyslisp.core.functions.*;
import aloyslisp.core.packages.tSYMBOL;

/**
 * tREADTABLE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tREADTABLE extends tBUILT_IN_CLASS
{
	/**
	 * Change case of char according to table case
	 * 
	 * @param car
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "change-case")
	public Character CHANGE_CASE( //
			@aArg(name = "char") Character car);

	/**
	 * @param fromReadtable
	 * @param toReadtable
	 * @return
	 */
	@aFunction(name = "copy-readtable", doc = "f_cp_rdt")
	@aBaseArg(name = "readtable", pos = 0, type = aOpt.class, def = "*READTABLE*")
	public tREADTABLE COPY_READTABLE(
			@aOpt(name = "to-readtable", def = "nil") tT toReadtable);

	/**
	 * Test if character is constituent
	 * 
	 * @param car
	 * @param nonTerminatingP
	 * @param readTable
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "is-constituent")
	public Boolean IS_CONSTITUENT( //
			@aArg(name = "char") Character car);

	/**
	 * Get macro char definition for multiple chars (in standard sharp macro
	 * chars)
	 * 
	 * @param disp
	 * @param sub
	 * @param readtable
	 * @return
	 */
	@aFunction(name = "get-dispatch-macro-character", doc = "f_set__1")
	@aBaseArg(name = "readtable", pos = 2, type = aOpt.class, def = "*READTABLE*")
	public tFUNCTION_DESIGNATOR GET_DISPATCH_MACRO_CHARACTER(
			@aArg(name = "disp-char") Character disp, //
			@aArg(name = "sub-char") Character sub);

	/**
	 * Get macro char definition for single char
	 * 
	 * @param character
	 * @param readtable
	 * @return
	 */
	@aFunction(name = "get-macro-character", doc = "f_set_ma")
	@aBaseArg(name = "readtable", pos = 1, type = aOpt.class, def = "*READTABLE*")
	public tT[] GET_MACRO_CHARACTER(
			@aArg(name = "character") Character character);

	/**
	 * Set single macro char be a multiple or single macro char.
	 * nonTerm = true (!cNULL) -> multiple
	 * 
	 * @param character
	 * @param nonTerminatingP
	 * @param readtable
	 * @return
	 */
	@aFunction(name = "make-dispatch-macro-character", doc = "f_mk_dis")
	@aBaseArg(name = "readtable", pos = 2, type = aOpt.class, def = "*READTABLE*")
	public tT MAKE_DISPATCH_MACRO_CHARACTER(
			@aArg(name = "character") Character character, //
			@aOpt(name = "non-terminating-p", def = "nil") Boolean nonTerminatingP //
	);

	/**
	 * return table case
	 * 
	 * @param readtable
	 * @return
	 */
	@aFunction(name = "readtable-case", doc = "f_rdtabl")
	@aBaseArg(name = "readtable", pos = 0, type = aOpt.class, def = "*READTABLE*")
	public tSYMBOL READTABLE_CASE();

	/**
	 * Set macro char definition for multiple chars (in standard sharp macro
	 * chars)
	 * 
	 * @param disp
	 * @param sub
	 * @param func
	 * @param readtable
	 * @return
	 */
	@aFunction(name = "set-dispatch-macro-character", doc = "f_set__1")
	@aBaseArg(name = "readtable", pos = 3, type = aOpt.class, def = "*READTABLE*")
	public tT SET_DISPATCH_MACRO_CHRACTER(
			@aArg(name = "disp-char") Character disp, //
			@aArg(name = "sub-char") Character sub, //
			@aArg(name = "function") tFUNCTION_DESIGNATOR func);

	/**
	 * Set macro char definition for single char
	 * 
	 * @param character
	 * @param func
	 * @param nonTerminatingP
	 * @param readtable
	 * @return
	 */
	@aFunction(name = "set-macro-character", doc = "f_set_ma")
	@aBaseArg(name = "readtable", pos = 3, type = aOpt.class, def = "*READTABLE*")
	public tT SET_MACRO_CHARACTER(
			@aArg(name = "character") Character character, //
			@aArg(name = "function") tFUNCTION_DESIGNATOR func,
			@aOpt(name = "non-terminating-p", def = "nil") Boolean nonTerminatingP);

	/**
	 * Set table case
	 * 
	 * @param readtable
	 * @param mode
	 */
	@aFunction(name = "set-readtable-case")
	@aBaseArg(name = "readtable", pos = 0, type = aOpt.class, def = "*READTABLE*")
	public tSYMBOL SET_READTABLE_CASE(@aArg(name = "mode") tSYMBOL mode);

	/**
	 * @param fromChar
	 * @param toChar
	 * @param toReadtable
	 * @param fromReadtable
	 */
	@aFunction(name = "set-syntax-from-char", doc = "f_set_sy")
	@aBaseArg(name = "to-readtable", pos = 2, type = aOpt.class, def = "*READTABLE*")
	public void SET_SYNTAX_FROM_CHAR(
			@aArg(name = "from-char") Character fromChar, //
			@aArg(name = "to-char") Character toChar, //
			@aOpt(name = "from-readtable", def = "*cREADTABLE*") tREADTABLE fromReadtable);
}
