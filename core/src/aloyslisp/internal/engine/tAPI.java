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
// IP 26 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.clos.*;
import aloyslisp.core.sequences.*;

/**
 * tAPI
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tAPI extends tBUILD_IN_CLASS
{
	/**
	 * Read args for java
	 * 
	 * @return
	 */
	@Function(name = "api-vars")
	public tLIST API_VARS();

	/**
	 * Write vars for java
	 * 
	 * @param vars
	 * @return
	 */
	@Function(name = "set-api-vars")
	public tLIST SET_API_VARS( //
			@Arg(name = "vars") tLIST vars //
	);

	/**
	 * Read args
	 * 
	 * @return
	 */
	@Function(name = "api-args")
	public tLIST API_ARGS();

	/**
	 * Write args
	 * 
	 * @param args
	 * @return
	 */
	@Function(name = "set-api-args")
	public tLIST SET_API_ARGS( //
			@Arg(name = "args") tLIST args //
	);

	/**
	 * Read documentation
	 * 
	 * @return
	 */
	@Function(name = "api-doc")
	public tT API_DOC();

	/**
	 * Write documentation
	 * 
	 * @param doc
	 * @return
	 */
	@Function(name = "set-api-doc")
	public tT SET_API_DOC( //
			@Arg(name = "doc") tT doc //
	);

	/**
	 * Read declare list
	 * 
	 * @return
	 */
	@Function(name = "api-decl")
	public tLIST API_DECL();

	/**
	 * write declare list
	 * 
	 * @param decl
	 * @return
	 */
	@Function(name = "set-api-decl")
	public tLIST SET_API_DECL( //
			@Arg(name = "decl") tLIST decl //
	);

	/**
	 * Create the variables in the LET environment.
	 * Set special initial values and default non calculated values
	 * 
	 * @return
	 */
	@Function(name = "api-intern-args")
	public cENV_LET API_INTERN_ARGS();

	/**
	 * Initialize values of LET environment with current values of a list
	 * in a format of function call
	 * 
	 * @param env
	 * @param values
	 * @return
	 */
	@Function(name = "api-init-values")
	public cENV_LET API_INIT_VALUES( //
			@Arg(name = "values") tLIST values //
	);

	/**
	 * Evaluation function (macros and special forms doesn't evaluate !!!)
	 * 
	 * @param env
	 * @param values
	 * @return
	 */
	@Function(name = "api-eval-arg")
	public tT API_EVAL_ARG( //
			@Arg(name = "values") tT values //
	);

	/**
	 * @return
	 */
	@Function(name = "api-push-env")
	public tT[] API_PUSH_ENV(tLIST args);

	/**
	 * @return
	 */
	@Function(name = "api-pop-env")
	public tAPI API_POP_ENV();

}
