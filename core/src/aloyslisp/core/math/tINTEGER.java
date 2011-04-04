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
// IP 23 déc. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import aloyslisp.annotations.*;
import aloyslisp.core.tT;

/**
 * tINTEGER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "integer", doc = "t_intege", typep = "integerp")
public interface tINTEGER extends tRATIONAL
{
	/**
	 * @param op
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-gcd")
	tINTEGER SINGLE_GCD( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * @param op
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-lcm")
	tINTEGER SINGLE_LCM(//
			@aArg(name = "op") tINTEGER op);

	/**
	 * @param op
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-logand")
	tINTEGER SINGLE_LOGAND( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * @param op
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-logior")
	tINTEGER SINGLE_LOGIOR( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * @param op
	 * @return
	 */
	@aNonStandard
	@aFunction(name = "single-logxor")
	tINTEGER SINGLE_LOGXOR( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * Arithmetic bit shift
	 * 
	 * @param count
	 * @return
	 */
	@aFunction(name = "ash", doc = "f_ash")
	public tINTEGER ASH( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * @return
	 */
	@aFunction(name = "evenp", doc = "f_evenpc")
	public boolean EVENP();

	/**
	 * Greather common denumerator
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "gcd", doc = "f_gcd")
	public tINTEGER GCD( //
			@aRest(name = "op") tT op);

	/**
	 * @return
	 */
	@aFunction(name = "integer-length", doc = "f_intege")
	public tINTEGER INTEGER_LENGTH();

	/**
	 * @return
	 */
	@aFunction(name = "isqrt", doc = "f_sqrt_")
	public tINTEGER ISQRT();

	/********************************************************
	 * LISP FUNCTIONS
	 */
	/* *******************************************************************
	 * OPERATOR
	 */
	/**
	 * Less common multiplicand
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "lcm", doc = "f_lcm")
	public tINTEGER LCM( //
			@aRest(name = "op") tT op);

	/**
	 * Arithmetic AND
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "logand", doc = "f_logand")
	public tINTEGER LOGAND( //
			@aRest(name = "op") tT op);

	/**
	 * Arithmetic NAND
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "lognand", doc = "f_logand")
	public tINTEGER LOGNAND( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * Arithmetic NAND COMPLEMENT 1
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "logandc1", doc = "f_logand")
	public tINTEGER LOGANDC1( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * Arithmetic AND COMPLEMENT 2
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "logandc2", doc = "f_logand")
	public tINTEGER LOGANDC2( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * Arithmetic OR
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "logior", doc = "f_logand")
	public tINTEGER LOGIOR( //
			@aRest(name = "op") tT op);

	/**
	 * Arithmetic OR COMPLEMENT 1
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "logorc1", doc = "f_logand")
	public tINTEGER LOGORC1( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * Arithmetic OR COMPLEMENT 2
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "logorc2", doc = "f_logand")
	public tINTEGER LOGORC2( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * Arithmetic XOR
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "logxor", doc = "f_logand")
	public tINTEGER LOGXOR( //
			@aRest(name = "op") tT op);

	/**
	 * Arithmetic EQUIVALENCE
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "logeqv", doc = "f_logand")
	public tINTEGER LOGEQV( //
			@aRest(name = "op") tT op);

	/**
	 * Arithmetic NOT (complement to 0)
	 * 
	 * @return
	 */
	@aFunction(name = "lognot", doc = "f_logand")
	public tINTEGER LOGNOT();

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Arithmetic bit count
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "logcount", doc = "f_logcou")
	public tINTEGER LOGCOUNT();

	/**
	 * Arithmetic bit test
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "logtest", doc = "f_logtes")
	public Boolean LOGTEST( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * Arithmetic bit test
	 * 
	 * @param op
	 * @return
	 */
	@aFunction(name = "logbitp", doc = "f_logbtp")
	public Boolean LOGBITP( //
			@aArg(name = "op") tINTEGER op);

	/**
	 * @return
	 */
	@aFunction(name = "oddp", doc = "f_evenpc")
	public boolean ODDP();

}
