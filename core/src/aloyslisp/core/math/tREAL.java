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
// IP 25 déc. 2010-2011 Creation
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
@aType(name = "real", doc = "t_real", typep = "realp")
public interface tREAL extends tNUMBER
{

	/**
	 * Test greater a > b
	 * 
	 * @param op
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-greater")
	public abstract boolean SINGLE_GREATER( //
			@aArg(name = "op") tREAL op);

	/**
	 * Test lower a < b
	 * 
	 * @param op
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-lower")
	public boolean SINGLE_LOWER( //
			@aArg(name = "op") tREAL op);

	/****************************************************************
	 * LISP FUNCTIONS
	 */
	/* *******************************************************************
	 * COMPARATORS
	 */
	/**
	 * Ceiling
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "ceiling", doc = "f_floorc")
	public tREAL[] CEILING( //
			@aOpt(name = "div") tT div);

	/**
	 * @return
	 */
	@aFunction(name = "cis", doc = "f_cis")
	public tNUMBER CIS();

	/**
	 * Floor
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "floor", doc = "f_floorc")
	public tREAL[] FLOOR( //
			@aOpt(name = "div") tT div);

	/**
	 * Test greater a > b
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = ">", doc = "f_eq_sle")
	public boolean GREATER( //
			@aRest(name = "op") tT op);

	/**
	 * Test lower a < b
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "<", doc = "f_eq_sle")
	public boolean LOWER( //
			@aRest(name = "op") tT op);

	/**
	 * @return
	 */
	@aFunction(name = "minusp", doc = "f_minusp")
	public boolean MINUSP();

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Modulo
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "mod", doc = "f_mod_r")
	public tREAL MOD( //
			@aArg(name = "op") tREAL op);

	/**
	 * @return
	 */
	@aFunction(name = "plusp", doc = "f_minusp")
	public boolean PLUSP();

	/**
	 * @return
	 */
	@aFunction(name = "random", doc = "f_random")
	public tNUMBER RANDOM( //
			@aOpt(name = "state") tT st);

	/* *******************************************************************
	 * cRATIONAL
	 */
	/**
	 * @return
	 */
	@aFunction(name = "rational", doc = "f_ration")
	public tRATIONAL RATIONAL();

	/**
	 * @return
	 */
	@aFunction(name = "rationalize", doc = "f_ration")
	public tRATIONAL RATIONALIZE();

	/**
	 * Remainder
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "rem", doc = "f_mod_r")
	public tREAL REM( //
			@aArg(name = "op") tREAL op);

	/**
	 * Round
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "round", doc = "f_floorc")
	public tREAL[] ROUND( //
			@aOpt(name = "div") tT div);

	/**
	 * Truncate
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "truncate", doc = "f_floorc")
	public tREAL[] TRUNCATE( //
			@aOpt(name = "div") tT div);
}
