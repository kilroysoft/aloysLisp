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
// IP 23 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.math;

import aloyslisp.core.annotations.*;
import aloyslisp.core.plugs.tT;

/**
 * tINTEGER
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tINTEGER extends tRATIONAL
{
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
	@Function(name = "lcm")
	public tINTEGER LCM( //
			@Rest(name = "op") tT op);

	/**
	 * Greather common denumerator
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "gcd")
	public tINTEGER GCD( //
			@Rest(name = "op") tT op);

	/**
	 * Arithmetic AND
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "logand")
	public tINTEGER LOGAND( //
			@Rest(name = "op") tT op);

	/**
	 * Arithmetic NAND
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "lognand")
	public tINTEGER LOGNAND( //
			@Arg(name = "op") tINTEGER op);

	/**
	 * Arithmetic NAND COMPLEMENT 1
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "logandc1")
	public tINTEGER LOGANDC1( //
			@Arg(name = "op") tINTEGER op);

	/**
	 * Arithmetic AND COMPLEMENT 2
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "logandc2")
	public tINTEGER LOGANDC2( //
			@Arg(name = "op") tINTEGER op);

	/**
	 * Arithmetic OR
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "logior")
	public tINTEGER LOGIOR( //
			@Rest(name = "op") tT op);

	/**
	 * Arithmetic OR COMPLEMENT 1
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "logorc1")
	public tINTEGER LOGORC1( //
			@Arg(name = "op") tINTEGER op);

	/**
	 * Arithmetic OR COMPLEMENT 2
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "logorc2")
	public tINTEGER LOGORC2( //
			@Arg(name = "op") tINTEGER op);

	/**
	 * Arithmetic XOR
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "logxor")
	public tINTEGER LOGXOR( //
			@Rest(name = "op") tT op);

	/**
	 * Arithmetic EQUIVALENCE
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "logeqv")
	public tINTEGER LOGEQV( //
			@Rest(name = "op") tT op);

	/**
	 * Arithmetic NOT (complement to 0)
	 * 
	 * @return
	 */
	@Function(name = "lognot")
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
	@Function(name = "logcount")
	public tINTEGER LOGCOUNT();

	/**
	 * Arithmetic bit test
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "logtest")
	public Boolean LOGTEST( //
			@Arg(name = "op") tINTEGER op);

	/**
	 * Arithmetic bit test
	 * 
	 * @param op
	 * @return
	 */
	@Function(name = "logbitp")
	public Boolean LOGBITP( //
			@Arg(name = "op") tINTEGER op);

	/**
	 * Arithmetic bit shift
	 * 
	 * @param count
	 * @return
	 */
	@Function(name = "ash")
	public tINTEGER ASH( //
			@Arg(name = "op") tINTEGER op);

	/**
	 * @return
	 */
	@Function(name = "evenp")
	public boolean EVENP();

	/**
	 * @return
	 */
	@Function(name = "oddp")
	public boolean ODDP();

	/**
	 * @return
	 */
	@Function(name = "isqrt")
	public tINTEGER ISQRT();

	/**
	 * @return
	 */
	@Function(name = "integer-length")
	public tINTEGER INTEGER_LENGTH();

}
