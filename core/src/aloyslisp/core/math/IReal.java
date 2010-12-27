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

/**
 * IReal
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface IReal extends INumber
{

	/* *******************************************************************
	 * COMPARATORS
	 */
	/**
	 * Test equality a == b
	 * 
	 * @param op
	 * @return
	 */
	public boolean equal(INumber op);

	/**
	 * Test greater a > b
	 * 
	 * @param op
	 * @return
	 */
	public boolean greater(INumber op);

	/**
	 * Test lower a < b
	 * 
	 * @param op
	 * @return
	 */
	public boolean lower(INumber op);

	/**
	 * @return
	 */
	public boolean minusp();

	/**
	 * @return
	 */
	public boolean plusp();

	/* *******************************************************************
	 * RATIONAL
	 */
	/**
	 * @return
	 */
	public IRational rational();

	/**
	 * @return
	 */
	public IRational rationalize();

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Modulo
	 * 
	 * @param op
	 * @return
	 */
	public IReal mod(IReal op);

	/**
	 * Remainder
	 * 
	 * @param op
	 * @return
	 */
	public IReal rem(IReal op);

	/**
	 * Floor
	 * 
	 * @return
	 */
	public IReal[] floor();

	/**
	 * Floor
	 * 
	 * @param op
	 * @return
	 */
	public IReal[] floor(IReal op);

	/**
	 * Ceiling
	 * 
	 * @return
	 */
	public IReal[] ceiling();

	/**
	 * Ceiling
	 * 
	 * @param op
	 * @return
	 */
	public IReal[] ceiling(IReal op);

	/**
	 * Truncate
	 * 
	 * @param op
	 * @return
	 */
	public IReal[] truncate();

	/**
	 * Truncate
	 * 
	 * @param op
	 * @return
	 */
	public IReal[] truncate(IReal op);

	/**
	 * Round
	 * 
	 * @param op
	 * @return
	 */
	public IReal[] round();

	/**
	 * Round
	 * 
	 * @param op
	 * @return
	 */
	public IReal[] round(IReal op);

}
