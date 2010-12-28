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

	/* *******************************************************************
	 * COMPARATORS
	 */
	/**
	 * Test equality a == b
	 * 
	 * @param op
	 * @return
	 */
	public boolean equal(tNUMBER op);

	/**
	 * Test greater a > b
	 * 
	 * @param op
	 * @return
	 */
	public boolean greater(tNUMBER op);

	/**
	 * Test lower a < b
	 * 
	 * @param op
	 * @return
	 */
	public boolean lower(tNUMBER op);

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
	public tRATIONAL rational();

	/**
	 * @return
	 */
	public tRATIONAL rationalize();

	/* *******************************************************************
	 * FUNCTIONS
	 */
	/**
	 * Modulo
	 * 
	 * @param op
	 * @return
	 */
	public tREAL mod(tREAL op);

	/**
	 * Remainder
	 * 
	 * @param op
	 * @return
	 */
	public tREAL rem(tREAL op);

	/**
	 * Floor
	 * 
	 * @return
	 */
	public tREAL[] floor();

	/**
	 * Floor
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] floor(tREAL op);

	/**
	 * Ceiling
	 * 
	 * @return
	 */
	public tREAL[] ceiling();

	/**
	 * Ceiling
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] ceiling(tREAL op);

	/**
	 * Truncate
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] truncate();

	/**
	 * Truncate
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] truncate(tREAL op);

	/**
	 * Round
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] round();

	/**
	 * Round
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] round(tREAL op);

	/****************************************************************
	 * LISP FUNCTIONS
	 */
	/* *******************************************************************
	 * COMPARATORS
	 */
	/**
	 * Test equality a == b
	 * 
	 * @param op
	 * @return
	 */
	public boolean EQUAL(tT op);

	/**
	 * Test greater a > b
	 * 
	 * @param op
	 * @return
	 */
	public boolean GREATER(tT op);

	/**
	 * Test lower a < b
	 * 
	 * @param op
	 * @return
	 */
	public boolean LOWER(tT op);

	/**
	 * @return
	 */
	public boolean MINUSP();

	/**
	 * @return
	 */
	public boolean PLUSP();

	/* *******************************************************************
	 * RATIONAL
	 */
	/**
	 * @return
	 */
	public tRATIONAL RATIONAL();

	/**
	 * @return
	 */
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
	public tREAL MOD(tREAL op);

	/**
	 * Remainder
	 * 
	 * @param op
	 * @return
	 */
	public tREAL REM(tREAL op);

	/**
	 * Floor
	 * 
	 * @return
	 */
	public tREAL[] FLOOR();

	/**
	 * Floor
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] FLOOR(tREAL op);

	/**
	 * Ceiling
	 * 
	 * @return
	 */
	public tREAL[] CEILING();

	/**
	 * Ceiling
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] CEILING(tREAL op);

	/**
	 * Truncate
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] TRUNCATE();

	/**
	 * Truncate
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] TRUNCATE(tREAL op);

	/**
	 * Round
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] ROUND();

	/**
	 * Round
	 * 
	 * @param op
	 * @return
	 */
	public tREAL[] ROUND(tREAL op);

}
