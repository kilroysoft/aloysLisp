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

import aloyslisp.core.conditions.*;
import aloyslisp.annotations.*;
import aloyslisp.core.tT;
import aloyslisp.core.sequences.tSEQUENCE;

/**
 * tINPUT_STREAM
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tINPUT_STREAM extends tSTREAM
{
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

}
