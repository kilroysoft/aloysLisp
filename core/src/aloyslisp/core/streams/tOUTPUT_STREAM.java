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
// IP 16 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import aloyslisp.annotations.*;
import aloyslisp.core.tT;
import aloyslisp.core.packages.tNULL;

/**
 * IOutput
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tOUTPUT_STREAM extends tSTREAM
{
	/**
	 * Write a char
	 * 
	 * @param character
	 * @param stream
	 * @return
	 */
	@Function(name = "write-char", doc = "f_wr_cha")
	@BaseArg(name = "stream", pos = 1, type = Opt.class, def = "*standard-output*")
	public Character WRITE_CHAR( //
			@Arg(name = "character") Character character);

	/**
	 * @param val
	 * @param stream
	 * @return
	 */
	@Function(name = "write-byte", doc = "f_wr_by")
	@BaseArg(name = "stream", pos = 1, type = Opt.class, def = "*standard-output*")
	public Integer WRITE_BYTE( //
			@Arg(name = "val") Integer val);

	/**
	 * Write an object. This is a base function not the LISP one.
	 * 
	 * @param obj
	 * @return
	 */
	@Function(name = "%write", doc = "f_wr_pr")
	@BaseArg(name = "stream", pos = 1, type = Opt.class, def = "*standard-output*")
	@Key(keys = "(array base case circle escape gensym length level lines miser-width"
			+ " pprint-dispatch pretty radix readably right-margin stream)")
	public tT WRITE( //
			@Arg(name = "object") tT obj);

	/**
	 * @param obj
	 * @param stream
	 * @return
	 */
	@Function(name = "prin1", doc = "f_wr_pr")
	@BaseArg(name = "stream", pos = 1, type = Opt.class, def = "*standard-output*")
	public tT PRIN1( //
			@Arg(name = "object") tT obj);

	/**
	 * @param obj
	 * @param stream
	 * @return
	 */
	@Function(name = "princ", doc = "f_wr_pr")
	@BaseArg(name = "stream", pos = 1, type = Opt.class, def = "*standard-output*")
	public tT PRINC( //
			@Arg(name = "object") tT obj);

	/**
	 * @param obj
	 * @param stream
	 * @return
	 */
	@Function(name = "print", doc = "f_wr_pr")
	@BaseArg(name = "stream", pos = 1, type = Opt.class, def = "*standard-output*")
	public tT PRINT( //
			@Arg(name = "object") tT obj);

	/**
	 * Print new line
	 * 
	 * @param stream
	 * @return
	 */
	@Function(name = "terpri", doc = "f_terpri")
	@BaseArg(name = "stream", pos = 0, type = Opt.class, def = "*standard-output*")
	public tNULL TERPRI();

	/**
	 * Print newline if necessary
	 * 
	 * @param stream
	 * @return
	 */
	@Function(name = "fresh-line", doc = "f_terpri")
	@BaseArg(name = "stream", pos = 0, type = Opt.class, def = "*standard-output*")
	public tT FRESH_LINE();

	/**
	 * @param stream
	 * @return
	 */
	@Function(name = "finish-output", doc = "f_finish")
	@BaseArg(name = "stream", pos = 0, type = Opt.class, def = "*standard-output*")
	public tT FINISH_OUTPUT();

	/**
	 * @param stream
	 * @return
	 */
	@Function(name = "force-output", doc = "f_finish")
	@BaseArg(name = "stream", pos = 0, type = Opt.class, def = "*standard-output*")
	public tT FORCE_OUTPUT();

	/**
	 * @param stream
	 * @return
	 */
	@Function(name = "clear-output", doc = "f_finish")
	@BaseArg(name = "stream", pos = 0, type = Opt.class, def = "*standard-output*")
	public tT CLEAR_OUTPUT();

	/**
	 * @param obj
	 * @param stream
	 * @param start
	 * @param end
	 * @return
	 */
	@Function(name = "write-string", doc = "f_wr_stg")
	@Key(keys = "((start 0)(end nil))")
	@BaseArg(name = "stream", pos = 1, type = Opt.class, def = "*standard-output*")
	public tT WRITE_STRING( //
			@Arg(name = "object") tT obj);
}
