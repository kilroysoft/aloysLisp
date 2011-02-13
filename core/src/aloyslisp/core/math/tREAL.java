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
// IP 25 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import aloyslisp.annotations.*;
import aloyslisp.core.tT;

/**
 * tREAL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tREAL extends tNUMBER
{

	/****************************************************************
	 * LISP FUNCTIONS
	 */
	/* *******************************************************************
	 * COMPARATORS
	 */
	/**
	 * Test greater a > b
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = ">", doc = "f_eq_sle")
	public boolean GREATER( //
			@Rest(name = "op") tT op);

	/**
	 * Test lower a < b
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "<", doc = "f_eq_sle")
	public boolean LOWER( //
			@Rest(name = "op") tT op);

	/**
	 * @return
	 */
	@Function(name = "minusp", doc = "f_minusp")
	public boolean MINUSP();

	/**
	 * @return
	 */
	@Function(name = "plusp", doc = "f_minusp")
	public boolean PLUSP();

	/* *******************************************************************
	 * cRATIONAL
	 */
	/**
	 * @return
	 */
	@Function(name = "rational", doc = "f_ration")
	public tRATIONAL RATIONAL();

	/**
	 * @return
	 */
	@Function(name = "rationalize", doc = "f_ration")
	public tRATIONAL RATIONALIZE();

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Modulo
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "mod", doc = "f_mod_r")
	public tREAL MOD( //
			@Arg(name = "op") tREAL op);

	/**
	 * Remainder
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "rem", doc = "f_mod_r")
	public tREAL REM( //
			@Arg(name = "op") tREAL op);

	/**
	 * Floor
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "floor", doc = "f_floorc")
	public tREAL[] FLOOR( //
			@Opt(name = "div") tT div);

	/**
	 * Ceiling
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "ceiling", doc = "f_floorc")
	public tREAL[] CEILING( //
			@Opt(name = "div") tT div);

	/**
	 * Truncate
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "truncate", doc = "f_floorc")
	public tREAL[] TRUNCATE( //
			@Opt(name = "div") tT div);

	/**
	 * Round
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "round", doc = "f_floorc")
	public tREAL[] ROUND( //
			@Opt(name = "div") tT div);

	/**
	 * @return
	 */
	@Function(name = "cis", doc = "f_cis")
	public tNUMBER CIS();

	/**
	 * @return
	 */
	@Function(name = "random", doc = "f_random")
	public tNUMBER RANDOM( //
			@Opt(name = "state") tT st);

}
