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
 * tFLOAT
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "float", doc = "t_short", typep = "floatp")
public interface tFLOAT extends tREAL
{
	/*************************************************************
	 * LISP cFUNCTION
	 */
	/**
	 * @param f
	 * @return
	 */
	@aFunction(name = "decode-float", doc = "f_dec_fl")
	public tT[] DECODE_FLOAT();

	/**
	 * @param f
	 * @return
	 */
	@aFunction(name = "float-digits", doc = "f_dec_fl")
	public tINTEGER FLOAT_DIGITS();

	/**
	 * @param f
	 * @return
	 */
	@aFunction(name = "float-precision", doc = "f_dec_fl")
	public tINTEGER FLOAT_PRECISION();

	/**
	 * @param f
	 * @return
	 */
	@aFunction(name = "float-radix", doc = "f_dec_fl")
	public tFLOAT FLOAT_RADIX();

	/**
	 * @param f
	 * @param f2
	 * @return
	 */
	@aFunction(name = "float-sign", doc = "f_dec_fl")
	public tFLOAT FLOAT_SIGN( //
			@aOpt(name = "scale") tFLOAT f2);

	/**
	 * @param f
	 * @return
	 */
	//
	@aFunction(name = "integer-decode-float", doc = "f_dec_fl")
	public tT[] INTEGER_DECODE_FLOAT();

	/**
	 * @param f
	 * @param scale
	 * @return
	 */
	@aFunction(name = "scale-float", doc = "f_dec_fl")
	public tFLOAT SCALE_FLOAT( //
			@aArg(name = "scale") tINTEGER scale);

}
