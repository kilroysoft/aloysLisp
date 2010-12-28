/**
 * aloysLisp. 
 * <p>A LISP interpreter, compiler and library.
 * <p>Copyright (C) 2010 kilroySoft <aloyslisp@kilroysoft.ch>
 *
 * <p>This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * <p>This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
//--------------------------------------------------------------------------
// history
//--------------------------------------------------------------------------
// IP 25 d�c. 2010 Creation
//--------------------------------------------------------------------------

package aloyslisp.core.math;

/**
 * tFLOAT
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 *
 */
public interface tFLOAT extends tREAL
{
	/**
	 * @param f
	 * @return
	 */
	public tFLOAT[] decode_float(tFLOAT f);
	
	/**
	 * @param f
	 * @return
	 */
	public tFLOAT[] integer_decode_float(tFLOAT f);
	
	/**
	 * @param f
	 * @param scale
	 * @return
	 */
	public tFLOAT scale_float(tFLOAT f, tINTEGER scale);
	
	/**
	 * @param f
	 * @return
	 */
	public tFLOAT float_radix(tFLOAT f);
	
	/**
	 * @param f
	 * @param f2
	 * @return
	 */
	public tFLOAT float_sign(tFLOAT f, tFLOAT f2);
	
	/**
	 * @param f
	 * @return
	 */
	public tINTEGER float_digits(tFLOAT f);
	
	/**
	 * @param f
	 * @return
	 */
	public tINTEGER float_precision(tFLOAT f);
	
	/*************************************************************
	 * LISP FUNCTION
	 */
	/**
	 * @param f
	 * @return
	 */
	public tFLOAT[] DECODE_FLOAT(tFLOAT f);
	
	/**
	 * @param f
	 * @return
	 */
	public tFLOAT[] INTEGER_DECODE_FLOAT(tFLOAT f);
	
	/**
	 * @param f
	 * @param scale
	 * @return
	 */
	public tFLOAT SCALE_FLOAT(tFLOAT f, tINTEGER scale);
	
	/**
	 * @param f
	 * @return
	 */
	public tFLOAT FLOAT_RADIX(tFLOAT f);
	
	/**
	 * @param f
	 * @param f2
	 * @return
	 */
	public tFLOAT FLOAT_SIGN(tFLOAT f, tFLOAT f2);
	
	/**
	 * @param f
	 * @return
	 */
	public tINTEGER FLOAT_DIGITS(tFLOAT f);
	
	/**
	 * @param f
	 * @return
	 */
	public tINTEGER FLOAT_PRECISION(tFLOAT f);
	
}