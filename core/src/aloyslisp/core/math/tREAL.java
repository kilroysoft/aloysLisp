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

import aloyslisp.core.annotations.*;
import aloyslisp.core.plugs.tT;

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
	@Function(name = ">")
	public boolean GREATER( //
			@Rest(name = "op") tT op);

	/**
	 * Test lower a < b
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "<")
	public boolean LOWER( //
			@Rest(name = "op") tT op);

	/**
	 * @return
	 */
	@Function(name = "minusp")
	public boolean MINUSP();

	/**
	 * @return
	 */
	@Function(name = "plusp")
	public boolean PLUSP();

	/* *******************************************************************
	 * RATIONAL
	 */
	/**
	 * @return
	 */
	@Function(name = "rational")
	public tRATIONAL RATIONAL();

	/**
	 * @return
	 */
	@Function(name = "rationalize")
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
	@Function(name = "mod")
	public tREAL MOD( //
			@Arg(name = "op") tREAL op);

	/**
	 * Remainder
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "rem")
	public tREAL REM( //
			@Arg(name = "op") tREAL op);

	/**
	 * Floor
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "floor")
	public tREAL[] FLOOR( //
			@Opt(name = "div") tT div);

	/**
	 * Ceiling
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "ceiling")
	public tREAL[] CEILING( //
			@Opt(name = "div") tT div);

	/**
	 * Truncate
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "truncate")
	public tREAL[] TRUNCATE( //
			@Opt(name = "div") tT div);

	/**
	 * Round
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "round")
	public tREAL[] ROUND( //
			@Opt(name = "div") tT div);

	/**
	 * @return
	 */
	@Function(name = "cis")
	public tNUMBER CIS();

	/**
	 * @return
	 */
	@Function(name = "random")
	public tNUMBER RANDOM( //
			@Opt(name = "state") tT st);

}
