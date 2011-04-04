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
// IP 21 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.clos.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.tLIST;

/**
 * tENV
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tENV extends tBUILT_IN_CLASS
{
	/**
	 * Push environment
	 * 
	 * @return
	 */
	@aFunction(name = "env-push")
	public tENV ENV_PUSH();

	/**
	 * get previous environment in the stack
	 * 
	 * @return
	 */
	@aFunction(name = "env-previous")
	public tT[] ENV_PREVIOUS();

	/**
	 * get previous
	 * 
	 * @return
	 */
	@aFunction(name = "env-previous-lexical")
	public tT[] ENV_PREVIOUS_LEXICAL();

	/**
	 * Destruct the environment
	 * 
	 * @return
	 */
	@aFunction(name = "env-pop")
	public tENV ENV_POP();

	/**
	 * @param var
	 * @return
	 */
	@aFunction(name = "env-let-get")
	public tT[] ENV_LET_GET( //
			@aArg(name = "var") tSYMBOL var);

	/**
	 * @param var
	 * @param value
	 * @return
	 */
	@aFunction(name = "env-let-set")
	public tT[] SET_ENV_LET_GET( //
			@aArg(name = "var") tSYMBOL var, //
			@aArg(name = "value") tT value //
	);

	/**
	 * @param var
	 * @return
	 */
	@aFunction(name = "env-let-intern")
	public tDYN_SYMBOL ENV_LET_INTERN( //
			@aArg(name = "var") tSYMBOL var, //
			@aArg(name = "value") tT val //
	);

	/**
	 * @param var
	 * @return
	 */
	@aFunction(name = "env-tag-get")
	public tT[] ENV_TAG_GET( //
			@aArg(name = "tag") tSYMBOL tag);

	/**
	 * @param var
	 * @param value
	 * @return
	 */
	@aFunction(name = "env-tag-set")
	public tT[] SET_ENV_TAG_GET( //
			@aArg(name = "tag") tSYMBOL tag, //
			@aArg(name = "value") tSYMBOL value //
	);

	/**
	 * @param tag
	 * @return
	 */
	@aFunction(name = "tst-tag")
	public tLIST ENV_TAG_TST( //
			@aArg(name = "tag") tT tag);

	/**
	 * @param var
	 * @return
	 */
	@aFunction(name = "env-tag-intern")
	public tT[] ENV_TAG_INTERN( //
			@aArg(name = "tag") tSYMBOL tag, //
			@aArg(name = "value") tSYMBOL value //
	);

	/**
	 * Execute next instruction of current environment
	 * 
	 * @return
	 */
	@aFunction(name = "next-step")
	public tT[] ENV_STEP();

	/**
	 * @param name
	 * @return
	 */
	@aFunction(name = "tst-block")
	public cENV_BLOCK ENV_BLOCK_TST( //
			@aArg(name = "name") tSYMBOL name);

	/**
	 * @return
	 */
	@aFunction(name = "env-dump")
	public tLIST ENV_DUMP();
}
