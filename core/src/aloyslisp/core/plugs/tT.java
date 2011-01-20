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
// IP 9 sept. 2010 Creation
// IMPLEMENT Insert Special print variables management for printable
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import aloyslisp.core.annotations.*;

/**
 * tT
 * <p>
 * Global base type for all cells.
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 */
public interface tT
{
	/**
	 * Create a copy of the cell.
	 * <p>
	 * Keep reference except for list, sequence, printtable,
	 * 
	 * @return
	 */
	public tT copy();

	/**
	 * Evaluate the cell
	 * 
	 * @return Value as an array (functions can give back multiple values)
	 */
	@Function(name = "eval")
	public tT[] EVAL();

	/**
	 * Return java code for cell creation
	 * 
	 * @return
	 */
	@Function(name = "compile")
	public String COMPILE();

	/**
	 * Return java Code for cell evaluation
	 * 
	 * @return
	 */
	@Function(name = "evalcompile")
	public String EVALCOMPILE();

	/**
	 * Return a printable form using Lisp formatting (see format print
	 * variables)
	 * 
	 * @return Printable form
	 */
	public String toString();

	/**
	 * Return describe string
	 * 
	 * @return
	 */
	@Function(name = "describe")
	public String DESCRIBE();

	/**
	 * Macro expand
	 * 
	 * @return
	 */
	@Function(name = "macroexpand")
	public tT[] MACROEXPAND();

	/**
	 * Macro expand 1 step
	 * 
	 * @return
	 */
	@Function(name = "macroexpand1")
	public tT[] MACROEXPAND1();

	/**
	 * get car
	 * <p>
	 * Only here for code reading simplification
	 * 
	 * @return
	 */
	// definition in tSYMBOL
	@Function(name = "car")
	public tT CAR();

	/**
	 * get cdr
	 * <p>
	 * Only here for code reading simplification
	 * 
	 * @return
	 */
	// definition in tSYMBOL
	@Function(name = "cdr")
	public tT CDR();

	/**
	 * eq same cell
	 * 
	 * @param cell
	 * @return
	 */
	@Function(name = "eq")
	public boolean EQ( //
			@Arg(name = "cell") tT cell);

	/**
	 * eql
	 * 
	 * @param cell
	 * @return
	 *         TODO will be removed for a lisp version
	 */
	@Function(name = "eql")
	public boolean EQL( //
			@Arg(name = "cell") tT cell);

	/**
	 * equal
	 * 
	 * @param cell
	 * @return
	 *         TODO will be removed for a lisp version
	 */
	@Function(name = "equal")
	public boolean EQUAL( //
			@Arg(name = "cell") tT cell);

	/**
	 * equalp
	 * 
	 * @param cell
	 * @return
	 *         TODO will be removed for a lisp version
	 */
	@Function(name = "equalp")
	public boolean EQUALP( //
			@Arg(name = "cell") tT cell);

	/**
	 * Test basic subtype test
	 * 
	 * @param type
	 * @return
	 */
	@Function(name = "%istype")
	public boolean pISTYPE( //
			@Arg(name = "type") tSYMBOL type);

	/**
	 * Test if cell is constant
	 * 
	 * @return
	 */
	@Function(name = "constantp")
	public boolean CONSTANTP();

}
