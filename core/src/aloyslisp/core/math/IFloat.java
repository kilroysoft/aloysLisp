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
// IP 25 déc. 2010 Creation
//--------------------------------------------------------------------------

package aloyslisp.core.math;

/**
 * IFloat
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 *
 */
public interface IFloat extends IReal
{
	/**
	 * @param f
	 * @return
	 */
	public IFloat[] decode_float(IFloat f);
	
	/**
	 * @param f
	 * @return
	 */
	public IFloat[] integer_decode_float(IFloat f);
	
	/**
	 * @param f
	 * @param scale
	 * @return
	 */
	public IFloat scale_float(IFloat f, IInteger scale);
	
	/**
	 * @param f
	 * @return
	 */
	public IFloat float_radix(IFloat f);
	
	/**
	 * @param f
	 * @param f2
	 * @return
	 */
	public IFloat float_sign(IFloat f, IFloat f2);
	
	/**
	 * @param f
	 * @return
	 */
	public IInteger float_digits(IFloat f);
	
	/**
	 * @param f
	 * @return
	 */
	public IInteger float_precision(IFloat f);
	
}
