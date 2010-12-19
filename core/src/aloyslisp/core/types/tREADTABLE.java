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
// IP 10 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.types;

import aloyslisp.core.annotations.*;

/**
 * tREADTABLE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tREADTABLE extends tATOM
{
	/**
	 * Test if character is constituent
	 * 
	 * @param car
	 * @param nonTerminatingP
	 * @param readTable
	 * @return
	 */
	public Boolean isConstituent( //
			Character car);

	/**
	 * Set single macro char be a multiple or single macro char.
	 * nonTerm = true (!NIL) -> multiple
	 * 
	 * @param character
	 * @param nonTerminatingP
	 * @param readtable
	 * @return
	 */
	@Function(name = "make-dispatch-macro-character")
	public tT MAKE_DISPATCH_MACRO_CHARACTER(
			@Arg(name = "character") Character character, //
			@Opt(name = "non-terminating-p", def = "nil") Boolean nonTerminatingP, //
			@BaseArg @Opt(name = "readtable", def = "*READTABLE*") tREADTABLE readtable);

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
	@Function(name = "set-dispatch-macro-character")
	public tT SET_DISPATCH_MACRO_CHRACTER(
			@Arg(name = "disp-char") Character disp, //
			@Arg(name = "sub-char") Character sub, //
			@Arg(name = "function") tFUNCTION_DESIGNATOR func,
			@BaseArg @Opt(name = "readtable", def = "*READTABLE*") tREADTABLE readtable);

	/**
	 * Get macro char definition for multiple chars (in standard sharp macro
	 * chars)
	 * 
	 * @param disp
	 * @param sub
	 * @param readtable
	 * @return
	 */
	@Function(name = "get-dispatch-macro-character")
	public tFUNCTION_DESIGNATOR GET_DISPATCH_MACRO_CHARACTER(
			@Arg(name = "disp-char") Character disp, //
			@Arg(name = "sub-char") Character sub, //
			@BaseArg @Opt(name = "readtable", def = "*READTABLE*") tREADTABLE readtable);

	/**
	 * Set macro char definition for single char
	 * 
	 * @param character
	 * @param func
	 * @param nonTerminatingP
	 * @param readtable
	 * @return
	 */
	@Function(name = "set-macro-character")
	public tT SET_MACRO_CHARACTER(
			@Arg(name = "character") Character character, //
			@Arg(name = "function") tFUNCTION_DESIGNATOR func,
			@Opt(name = "non-terminating-p", def = "nil") Boolean nonTerminatingP, //
			@BaseArg @Opt(name = "readtable", def = "*READTABLE*") tREADTABLE readtable);

	/**
	 * Get macro char definition for single char
	 * 
	 * @param character
	 * @param readtable
	 * @return
	 */
	@Function(name = "get-macro-character")
	public tT[] GET_MACRO_CHARACTER(
			@Arg(name = "character") Character character, //
			@BaseArg @Opt(name = "readtable", def = "*READTABLE*") tREADTABLE readtable);

	/**
	 * return table case
	 * 
	 * @param readtable
	 * @return
	 */
	@Function(name = "readtable-case")
	public tSYMBOL READTABLE_CASE(
			@BaseArg @Opt(name = "readtable", def = "*READTABLE*") tREADTABLE readtable);

	/**
	 * Set table case
	 * 
	 * @param readtable
	 * @param mode
	 */
	@Function(name = "set-readtable-case")
	public tSYMBOL SET_READTABLE_CASE(
			@BaseArg @Arg(name = "readtable") tREADTABLE readtable,
			@Arg(name = "mode") tSYMBOL mode);

	/**
	 * @param fromChar
	 * @param toChar
	 * @param toReadtable
	 * @param fromReadtable
	 */
	@Function(name = "set-syntax-from-char")
	public void SET_SYNTAX_FROM_CHAR(
			@Arg(name = "from-char") Character fromChar, //
			@Arg(name = "to-char") Character toChar, //
			@BaseArg @Opt(name = "to-readtable", def = "*READTABLE*") tREADTABLE toReadtable,
			@Opt(name = "from-readtable", def = "*READTABLE*") tREADTABLE fromReadtable);

	/**
	 * @param fromReadtable
	 * @param toReadtable
	 * @return
	 */
	@Function(name = "copy-readtable")
	public tREADTABLE COPY_READTABLE(
			@BaseArg @Opt(name = "from-readtable", def = "*READTABLE*") tREADTABLE fromReadtable,
			@Opt(name = "to-readtable", def = "nil") tT toReadtable);

	/**
	 * Change case of char according to table case
	 * 
	 * @param c
	 * @return
	 */
	public Character changeCase(Character c);

}
