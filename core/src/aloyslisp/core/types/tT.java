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

package aloyslisp.core.types;

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
	 * Convert object to type
	 * 
	 * @return converted object
	 */
	@Primitive(name = "coerce")
	public tT COERCE( //
			@Arg(name = "type") tT type);

	/**
	 * Evaluate the cell
	 * 
	 * @return Value as an array (functions can give back multiple values)
	 */
	@Primitive(name = "eval")
	public tT[] EVAL();

	/**
	 * Return java code for cell creation
	 * 
	 * @return
	 */
	@Primitive(name = "compile")
	public String COMPILE();

	/**
	 * Return java Code for cell evaluation
	 * 
	 * @return
	 */
	@Primitive(name = "evalcompile")
	public String EVALCOMPILE();

	/**
	 * Return a printable form using Lisp formatting (see format print
	 * variables)
	 * 
	 * @return Printable form
	 */
	public String printable();

	/**
	 * Return describe string
	 * 
	 * @return
	 */
	@Primitive(name = "describe")
	public String DESCRIBE();

	/**
	 * Macro expand
	 * 
	 * @return
	 */
	@Primitive(name = "macroexpand")
	public tT[] MACROEXPAND();

	/**
	 * Macro expand 1 step
	 * 
	 * @return
	 */
	@Primitive(name = "macroexpand1")
	public tT[] MACROEXPAND1();

	/**
	 * get car
	 * <p>
	 * Only here for code reading simplification
	 * 
	 * @return
	 */
	// definition in tSYMBOL
	public tT CAR();

	/**
	 * get cdr
	 * <p>
	 * Only here for code reading simplification
	 * 
	 * @return
	 */
	// definition in tSYMBOL
	public tT CDR();

	/**
	 * eq same cell
	 * 
	 * @param cell
	 * @return
	 */
	@Primitive(name = "eq")
	public boolean EQ( //
			@Arg(name = "cell") tT cell);

	/**
	 * eql
	 * 
	 * @param cell
	 * @return
	 *         TODO will be removed for a lisp version
	 */
	@Primitive(name = "eql")
	public boolean EQL( //
			@Arg(name = "cell") tT cell);

	/**
	 * equal
	 * 
	 * @param cell
	 * @return
	 *         TODO will be removed for a lisp version
	 */
	@Primitive(name = "equal")
	public boolean EQUAL( //
			@Arg(name = "cell") tT cell);

	/**
	 * equalp
	 * 
	 * @param cell
	 * @return
	 *         TODO will be removed for a lisp version
	 */
	@Primitive(name = "equalp")
	public boolean EQUALP( //
			@Arg(name = "cell") tT cell);

	/**
	 * Test basic subtype test
	 * 
	 * @param type
	 * @return
	 */
	@Primitive(name = "%istype")
	public boolean pISTYPE(tSYMBOL type);

	/**
	 * Test if cell is constant
	 * 
	 * @return
	 */
	@Primitive(name = "constantp")
	public boolean CONSTANTP();

}
