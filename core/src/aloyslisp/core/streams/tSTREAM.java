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
import aloyslisp.core.*;
import aloyslisp.core.clos.tBUILT_IN_CLASS;
import aloyslisp.core.conditions.END_OF_FILE;
import aloyslisp.core.packages.tNULL;
import aloyslisp.core.sequences.tSEQUENCE;

/**
 * tSTREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tSTREAM extends tBUILT_IN_CLASS
{
	/**
	 * @return
	 */
	@Function(name = "open-stream-p", doc = "f_open_s")
	public Boolean OPEN_STREAM_P();

	/**
	 * @param stream
	 * @return
	 */
	@Function(name = "close", doc = "f_close")
	@Key(keys = "(abort)")
	public Boolean CLOSE();

	/**
	 * @return
	 */
	@Function(name = "stream-element-type", doc = "f_stm_el")
	public tT STREAM_ELEMENT_TYPE();

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@Function(name = "read", doc = "f_rd_rd")
	@BaseArg(name = "input-stream", pos = 0, type = Opt.class, def = "*standard-input*")
	public tT READ( //
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE;

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@Function(name = "read-char", doc = "f_rd_cha")
	@BaseArg(name = "input-stream", pos = 0, type = Opt.class, def = "*standard-input*")
	public Character READ_CHAR( //
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE;

	/**
	 * @param stream
	 * @param car
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@Function(name = "peek-char", doc = "f_peek_c")
	@BaseArg(name = "input-stream", pos = 1, type = Opt.class, def = "*standard-input*")
	public Character PEEK_CHAR(@Arg(name = "peek-type") tT peekType, //
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE;

	/**
	 * @param stream
	 * @param car
	 * @return
	 */
	@Function(name = "unread-char", doc = "f_undr_c")
	@BaseArg(name = "input-stream", pos = 1, type = Opt.class, def = "*standard-input*")
	public Character UNREAD_CHAR( //
			@Arg(name = "character") Character character);

	/**
	 * @param stream
	 * @return
	 */
	@Function(name = "listen", doc = "f_listen")
	@BaseArg(name = "input-stream", pos = 0, type = Opt.class, def = "*standard-input*")
	public boolean LISTEN();

	/**
	 * @param stream
	 * @return
	 */
	@Function(name = "clear-input", doc = "f_clear_")
	@BaseArg(name = "input-stream", pos = 0, type = Opt.class, def = "*standard-input*")
	public tT CLEAR_INPUT();

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@Function(name = "read-byte", doc = "f_rd_by")
	@BaseArg(name = "input-stream", pos = 0, type = Opt.class, def = "*standard-input*")
	public Integer READ_BYTE(
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE;

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@Function(name = "read-char-no-hang", doc = "f_rd_c_1")
	@BaseArg(name = "input-stream", pos = 0, type = Opt.class, def = "*standard-input*")
	public Character READ_CHAR_NO_HANG(
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE;

	/**
	 * @param sequence
	 * @param stream
	 * @param start
	 * @param end
	 * @return
	 */
	@Function(name = "read-sequence", doc = "f_rd_seq")
	@BaseArg(name = "input-stream", pos = 1, type = Opt.class, def = "*standard-input*")
	public tT READ_SEQUENCE( //
			@Arg(name = "sequence") tSEQUENCE sequence, //
			@Opt(name = "start", def = "0") Integer start, //
			@Opt(name = "end", def = "-1") Integer end);

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@Function(name = "read-line", doc = "f_rd_lin")
	@BaseArg(name = "input-stream", pos = 0, type = Opt.class, def = "*standard-input*")
	public tT[] READ_LINE(
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE;

	/**
	 * Read an atom as string
	 * 
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	public String readAtom( //
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE;

	/**
	 * Read an atom as string, first character can be escaped (for char
	 * definition reading)
	 * 
	 * @param firstEscaped
	 * @return
	 * @throws END_OF_FILE
	 */
	public String readAtom( //
			Boolean firstEscaped, //
			Boolean eofErrorP, //
			tT eofValue, //
			Boolean recursiveP);

	/**
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	public tT readMacroChar(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
			throws END_OF_FILE;

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
