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

import aloyslisp.core.annotations.*;
import aloyslisp.core.plugs.tNULL;
import aloyslisp.core.plugs.tT;

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
	@Function(name = "write-char")
	public Character WRITE_CHAR(
			@Arg(name = "character") Character character, //
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream);

	/**
	 * @param val
	 * @param stream
	 * @return
	 */
	@Function(name = "write-byte")
	public Integer WRITE_BYTE(
			@Arg(name = "val") Integer val, //
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream);

	/**
	 * Write an object. This is a base function not the LISP one.
	 * 
	 * @param obj
	 * @return
	 */
	@Function(name = "%write")
	public tT WRITE(
			//
			@Arg(name = "object") tT obj, //
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream);

	/**
	 * @param obj
	 * @param stream
	 * @return
	 */
	@Function(name = "prin1")
	public tT PRIN1(
			//
			@Arg(name = "object") tT obj, //
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream);

	/**
	 * @param obj
	 * @param stream
	 * @return
	 */
	@Function(name = "princ")
	public tT PRINC(
			//
			@Arg(name = "object") tT obj, //
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream);

	/**
	 * @param obj
	 * @param stream
	 * @return
	 */
	@Function(name = "print")
	public tT PRINT(
			@Arg(name = "object") tT obj, //
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream);

	/**
	 * Print new line
	 * 
	 * @param stream
	 * @return
	 */
	@Function(name = "terpri")
	public tNULL TERPRI(
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream);

	/**
	 * Print newline if necessary
	 * 
	 * @param stream
	 * @return
	 */
	@Function(name = "fresh-line")
	public tT FRESH_LINE(
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream);

	/**
	 * @param stream
	 * @return
	 */
	@Function(name = "finish-output")
	public tT FINISH_OUTPUT(
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream);

	/**
	 * @param stream
	 * @return
	 */
	@Function(name = "force-output")
	public tT FORCE_OUTPUT(
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream);

	/**
	 * @param stream
	 * @return
	 */
	@Function(name = "clear-output")
	public tT CLEAR_OUTPUT(
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream);

	/**
	 * @param obj
	 * @param stream
	 * @param start
	 * @param end
	 * @return
	 */
	@Function(name = "write-string")
	public tT WRITE_STRING(
			@Arg(name = "object") tT obj, //
			@BaseArg @Opt(name = "stream", def = "*standard-output*") tOUTPUT_STREAM stream,
			@Key(name = "start", def = "0") tT start,
			@Key(name = "end", def = "nil") tT end);
}
