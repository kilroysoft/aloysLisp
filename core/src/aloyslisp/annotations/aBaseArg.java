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
// IP 14 déc. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.annotations;

import java.lang.annotation.*;

import aloyslisp.core.tT;

/**
 * Defines object used as base object for method function call.
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface aBaseArg
{
	/**
	 * Lisp name of object as arg
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * Default value as a string
	 * 
	 * @return
	 */
	int pos() default 0;

	/**
	 * Default value as a string
	 * 
	 * @return
	 */
	Class<?> type() default tT.class;

	/**
	 * Default value as a string
	 * 
	 * @return
	 */
	String def() default "NIL";

}
