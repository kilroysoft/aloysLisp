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
// IP 9 sept. 2010-2011 Creation
// IMPLEMENT Insert aSpecialOperator print variables management for printable
// --------------------------------------------------------------------------

package aloyslisp.core;

import aloyslisp.annotations.*;
import aloyslisp.core.clos.tCLASS;
import aloyslisp.core.packages.tSYMBOL;

/**
 * tT
 * <p>
 * Global base type for all cells.
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 */
@aType(name = "t", doc = "t_t")
public interface tT
{
	/**
	 * Get car
	 * 
	 * @return fCAR of cons
	 */
	@aFunction(name = "car", doc = "f_car_c")
	@aWriter(accessor = "setf-car")
	public tT CAR();

	/**
	 * Get cdr
	 * 
	 * @return fCDR of cons
	 */
	@aFunction(name = "cdr", doc = "f_car_c")
	@aWriter(accessor = "setf-cdr")
	public tT CDR();

	/**
	 * Return the class of object
	 * 
	 * @return
	 */
	@aFunction(name = "class-of", doc = "f_clas_1")
	public tCLASS CLASS_OF();

	/**
	 * @param type
	 * @return
	 */
	@aFunction(name = "coerce", doc = "f_coerce")
	public tT COERCE( //
			@aArg(name = "type") tT type);

	/**
	 * Return java code for cell creation
	 * 
	 * @return
	 */
	@aFunction(name = "compile", doc = "f_cmp")
	public String COMPILE();

	/**
	 * Test if cell is constant
	 * 
	 * @return
	 */
	@aFunction(name = "constantp", doc = "f_consta")
	public boolean CONSTANTP();

	/**
	 * Create a copy of the cell.
	 * <p>
	 * Keep reference except for list, sequence, printtable,
	 * 
	 * @return
	 */
	@aFunction(name = "copy-cell")
	public tT COPY_CELL();

	/**
	 * Return describe string
	 * 
	 * @return
	 */
	@aFunction(name = "describe", doc = "f_descri")
	public String DESCRIBE();

	/**
	 * eq same cell
	 * 
	 * @param cell
	 * @return
	 */
	@aFunction(name = "eq", doc = "f_eq")
	public boolean EQ( //
			@aArg(name = "cell") tT cell);

	/**
	 * eql
	 * 
	 * @param cell
	 * @return
	 *         TODO will be removed for a lisp version
	 */
	@aFunction(name = "eql", doc = "f_eql")
	public boolean EQL( //
			@aArg(name = "cell") tT cell);

	/**
	 * equal
	 * 
	 * @param cell
	 * @return
	 *         TODO will be removed for a lisp version
	 */
	@aFunction(name = "equal", doc = "f_equal")
	public boolean EQUAL( //
			@aArg(name = "cell") tT cell);

	/**
	 * equalp
	 * 
	 * @param cell
	 * @return
	 *         TODO will be removed for a lisp version
	 */
	@aFunction(name = "equalp", doc = "f_rqualp")
	public boolean EQUALP( //
			@aArg(name = "cell") tT cell);

	/**
	 * Evaluate the cell
	 * 
	 * @return Value as an array (functions can give back multiple values)
	 */
	@aFunction(name = "eval", doc = "f_eval")
	public tT[] EVAL();

	/**
	 * Return java Code for cell evaluation
	 * 
	 * @return
	 */
	@aFunction(name = "evalcompile")
	public String EVALCOMPILE();

	/**
	 * Test basic subtype test
	 * 
	 * @param type
	 * @return
	 */
	@aFunction(name = "%istype")
	public boolean ISTYPE( //
			@aArg(name = "type") tSYMBOL type);

	/**
	 * Test if thsi is a keyword
	 * 
	 * @return
	 */
	@aFunction(name = "keywordp", doc = "f_kwdp")
	public boolean KEYWORDP();

	/**
	 * aMacro expand
	 * 
	 * @return
	 */
	@aFunction(name = "macroexpand", doc = "f_mexp_")
	public tT[] MACROEXPAND();

	/**
	 * aMacro expand 1 step
	 * 
	 * @return
	 */
	@aFunction(name = "macroexpand-1", doc = "f_mexp_")
	public tT[] MACROEXPAND_1();

	/**
	 * @return
	 */
	@aFunction(name = "sxhash", doc = "f_sxhash")
	public Integer SXHASH();

	/**
	 * Return a printable form using Lisp formatting (see format print
	 * variables)
	 * 
	 * @return Printable form
	 */
	@aNonStandard
	@aFunction(name = "to-string")
	public String TO_STRING();

}
