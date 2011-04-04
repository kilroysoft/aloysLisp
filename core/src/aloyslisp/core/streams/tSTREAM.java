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
// IP 16 sept. 2010-2011 Creation
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
	@aFunction(name = "open-stream-p", doc = "f_open_s")
	public Boolean OPEN_STREAM_P();

	/**
	 * @param stream
	 * @return
	 */
	@aFunction(name = "close", doc = "f_close")
	@aKey(keys = "(abort)")
	public Boolean CLOSE();

	/**
	 * @return
	 */
	@aFunction(name = "stream-element-type", doc = "f_stm_el")
	public tT STREAM_ELEMENT_TYPE();

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@aFunction(name = "read", doc = "f_rd_rd")
	@aBaseArg(name = "input-stream", pos = 0, type = aOpt.class, def = "*standard-input*")
	public tT READ( //
			@aOpt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@aOpt(name = "eof-value", def = "nil") tT eofValue, //
			@aOpt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE;

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@aFunction(name = "read-char", doc = "f_rd_cha")
	@aBaseArg(name = "input-stream", pos = 0, type = aOpt.class, def = "*standard-input*")
	public Character READ_CHAR( //
			@aOpt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@aOpt(name = "eof-value", def = "nil") tT eofValue, //
			@aOpt(name = "recursive-p", def = "nil") Boolean recursiveP)
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
	@aFunction(name = "peek-char", doc = "f_peek_c")
	@aBaseArg(name = "input-stream", pos = 1, type = aOpt.class, def = "*standard-input*")
	public Character PEEK_CHAR(@aArg(name = "peek-type") tT peekType, //
			@aOpt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@aOpt(name = "eof-value", def = "nil") tT eofValue, //
			@aOpt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE;

	/**
	 * @param stream
	 * @param car
	 * @return
	 */
	@aFunction(name = "unread-char", doc = "f_undr_c")
	@aBaseArg(name = "input-stream", pos = 1, type = aOpt.class, def = "*standard-input*")
	public Character UNREAD_CHAR( //
			@aArg(name = "character") Character character);

	/**
	 * @param stream
	 * @return
	 */
	@aFunction(name = "listen", doc = "f_listen")
	@aBaseArg(name = "input-stream", pos = 0, type = aOpt.class, def = "*standard-input*")
	public boolean LISTEN();

	/**
	 * @param stream
	 * @return
	 */
	@aFunction(name = "clear-input", doc = "f_clear_")
	@aBaseArg(name = "input-stream", pos = 0, type = aOpt.class, def = "*standard-input*")
	public tT CLEAR_INPUT();

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@aFunction(name = "read-byte", doc = "f_rd_by")
	@aBaseArg(name = "input-stream", pos = 0, type = aOpt.class, def = "*standard-input*")
	public Integer READ_BYTE(
			@aOpt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@aOpt(name = "eof-value", def = "nil") tT eofValue, //
			@aOpt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE;

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@aFunction(name = "read-char-no-hang", doc = "f_rd_c_1")
	@aBaseArg(name = "input-stream", pos = 0, type = aOpt.class, def = "*standard-input*")
	public Character READ_CHAR_NO_HANG(
			@aOpt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@aOpt(name = "eof-value", def = "nil") tT eofValue, //
			@aOpt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws END_OF_FILE;

	/**
	 * @param sequence
	 * @param stream
	 * @param start
	 * @param end
	 * @return
	 */
	@aFunction(name = "read-sequence", doc = "f_rd_seq")
	@aBaseArg(name = "input-stream", pos = 1, type = aOpt.class, def = "*standard-input*")
	public tT READ_SEQUENCE( //
			@aArg(name = "sequence") tSEQUENCE sequence, //
			@aOpt(name = "start", def = "0") Integer start, //
			@aOpt(name = "end", def = "-1") Integer end);

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws END_OF_FILE
	 */
	@aFunction(name = "read-line", doc = "f_rd_lin")
	@aBaseArg(name = "input-stream", pos = 0, type = aOpt.class, def = "*standard-input*")
	public tT[] READ_LINE(
			@aOpt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@aOpt(name = "eof-value", def = "nil") tT eofValue, //
			@aOpt(name = "recursive-p", def = "nil") Boolean recursiveP)
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
			@aOpt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@aOpt(name = "eof-value", def = "nil") tT eofValue, //
			@aOpt(name = "recursive-p", def = "nil") Boolean recursiveP)
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
	@aFunction(name = "write-char", doc = "f_wr_cha")
	@aBaseArg(name = "stream", pos = 1, type = aOpt.class, def = "*standard-output*")
	public Character WRITE_CHAR( //
			@aArg(name = "character") Character character);

	/**
	 * @param val
	 * @param stream
	 * @return
	 */
	@aFunction(name = "write-byte", doc = "f_wr_by")
	@aBaseArg(name = "stream", pos = 1, type = aOpt.class, def = "*standard-output*")
	public Integer WRITE_BYTE( //
			@aArg(name = "val") Integer val);

	/**
	 * Write an object. This is a base function not the LISP one.
	 * 
	 * @param obj
	 * @return
	 */
	@aFunction(name = "%write", doc = "f_wr_pr")
	@aBaseArg(name = "stream", pos = 1, type = aOpt.class, def = "*standard-output*")
	@aKey(keys = "(array base case circle escape gensym length level lines miser-width"
			+ " pprint-dispatch pretty radix readably right-margin stream)")
	public tT WRITE( //
			@aArg(name = "object") tT obj);

	/**
	 * @param obj
	 * @param stream
	 * @return
	 */
	@aFunction(name = "prin1", doc = "f_wr_pr")
	@aBaseArg(name = "stream", pos = 1, type = aOpt.class, def = "*standard-output*")
	public tT PRIN1( //
			@aArg(name = "object") tT obj);

	/**
	 * @param obj
	 * @param stream
	 * @return
	 */
	@aFunction(name = "princ", doc = "f_wr_pr")
	@aBaseArg(name = "stream", pos = 1, type = aOpt.class, def = "*standard-output*")
	public tT PRINC( //
			@aArg(name = "object") tT obj);

	/**
	 * @param obj
	 * @param stream
	 * @return
	 */
	@aFunction(name = "print", doc = "f_wr_pr")
	@aBaseArg(name = "stream", pos = 1, type = aOpt.class, def = "*standard-output*")
	public tT PRINT( //
			@aArg(name = "object") tT obj);

	/**
	 * Print new line
	 * 
	 * @param stream
	 * @return
	 */
	@aFunction(name = "terpri", doc = "f_terpri")
	@aBaseArg(name = "stream", pos = 0, type = aOpt.class, def = "*standard-output*")
	public tNULL TERPRI();

	/**
	 * Print newline if necessary
	 * 
	 * @param stream
	 * @return
	 */
	@aFunction(name = "fresh-line", doc = "f_terpri")
	@aBaseArg(name = "stream", pos = 0, type = aOpt.class, def = "*standard-output*")
	public tT FRESH_LINE();

	/**
	 * @param stream
	 * @return
	 */
	@aFunction(name = "finish-output", doc = "f_finish")
	@aBaseArg(name = "stream", pos = 0, type = aOpt.class, def = "*standard-output*")
	public tT FINISH_OUTPUT();

	/**
	 * @param stream
	 * @return
	 */
	@aFunction(name = "force-output", doc = "f_finish")
	@aBaseArg(name = "stream", pos = 0, type = aOpt.class, def = "*standard-output*")
	public tT FORCE_OUTPUT();

	/**
	 * @param stream
	 * @return
	 */
	@aFunction(name = "clear-output", doc = "f_finish")
	@aBaseArg(name = "stream", pos = 0, type = aOpt.class, def = "*standard-output*")
	public tT CLEAR_OUTPUT();

	/**
	 * @param obj
	 * @param stream
	 * @param start
	 * @param end
	 * @return
	 */
	@aFunction(name = "write-string", doc = "f_wr_stg")
	@aKey(keys = "((start 0)(end nil))")
	@aBaseArg(name = "stream", pos = 1, type = aOpt.class, def = "*standard-output*")
	public tT WRITE_STRING( //
			@aArg(name = "object") tT obj);

}
