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
public interface tAPI extends tBUILT_IN_CLASS
{
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
	 * Read declare list
	 * 
	 * @return
	 */
	@Function(name = "api-special")
	public Boolean API_SPECIAL();

	/**
	 * write declare list
	 * 
	 * @param decl
	 * @return
	 */
	@Function(name = "set-api-special")
	public Boolean SET_API_SPECIAL( //
			@Arg(name = "special") Boolean special //
	);

	/**
	 * Read declare list
	 * 
	 * @return
	 */
	@Function(name = "api-macro")
	public Boolean API_MACRO();

	/**
	 * write declare list
	 * 
	 * @param decl
	 * @return
	 */
	@Function(name = "set-api-macro")
	public Boolean SET_API_MACRO( //
			@Arg(name = "macro") Boolean macro //
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
	 * Evaluate the list (if not special)
	 * 
	 * @param list
	 * @return
	 */
	@Function(name = "api-eval-list")
	public tLIST API_EVAL_LIST( //
			@Arg(name = "list") tLIST list //
	);

	/**
	 * @return
	 */
	@Function(name = "api-push-env")
	public tLIST API_PUSH_ENV( //
			@Arg(name = "args") tLIST args, //
			@Arg(name = "let") tENV let //
	);

	/**
	 * @return
	 */
	@Function(name = "api-get-mac")
	public String API_GET_MAC();

	/**
	 * @param args
	 * @return
	 */
	@Function(name = "api-object")
	public tLIST API_OBJECT(tLIST args);

	/**
	 * @param args
	 * @return
	 */
	@Function(name = "api-call")
	public tT[] API_CALL(tLIST args);

	/**
	 * Execute function with separate arguments
	 * 
	 * @param args
	 *            individual args
	 * @return Evaluated results in ana array for eventual multiple values
	 */
	public tT[] e(Object... args);

}
