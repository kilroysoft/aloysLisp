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

package aloyslisp.core.types;

import java.io.*;

import aloyslisp.core.annotations.*;

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
	 * @throws EOFException
	 */
	@Function(name = "read")
	public tT READ(
			@BaseArg @Opt(name = "input-stream", def = "*standard-input*") tINPUT_STREAM stream, //
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws EOFException;

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	@Function(name = "read-char")
	public Character READ_CHAR( //
			@BaseArg @Opt(name = "input-stream", def = "*standard-input*") tINPUT_STREAM stream, //
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws EOFException;

	/**
	 * @param stream
	 * @param car
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	@Function(name = "peek-char")
	public Character PEEK_CHAR(
			@Arg(name = "peek-type") tT peekType, //
			@BaseArg @Opt(name = "input-stream", def = "*standard-input*") tINPUT_STREAM stream, //
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws EOFException;

	/**
	 * @param stream
	 * @param car
	 * @return
	 */
	@Function(name = "unread-char")
	public Character UNREAD_CHAR(
			@Arg(name = "character") Character character,
			@BaseArg @Opt(name = "input-stream", def = "*standard-input*") tINPUT_STREAM stream);

	/**
	 * @param stream
	 * @return
	 */
	@Function(name = "listen")
	public boolean LISTEN(
			@BaseArg @Opt(name = "input-stream", def = "*standard-input*") tINPUT_STREAM stream);

	/**
	 * @param stream
	 * @return
	 */
	@Function(name = "clear-input")
	public tT CLEAR_INPUT(
			@BaseArg @Opt(name = "input-stream", def = "*standard-input*") tINPUT_STREAM stream);

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	@Function(name = "read-byte")
	public Integer READ_BYTE(
			@BaseArg @Opt(name = "input-stream", def = "*standard-input*") tINPUT_STREAM stream, //
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws EOFException;

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	@Function(name = "read-char-no-hang")
	public Character READ_CHAR_NO_HANG(
			@BaseArg @Opt(name = "input-stream", def = "*standard-input*") tINPUT_STREAM stream, //
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws EOFException;

	/**
	 * @param sequence
	 * @param stream
	 * @param start
	 * @param end
	 * @return
	 */
	@Function(name = "read-sequence")
	public tT READ_SEQUENCE( //
			@Arg(name = "sequence") tSEQUENCE sequence, //
			@BaseArg @Arg(name = "input-stream") tINPUT_STREAM stream, //
			@Opt(name = "start", def = "0") Integer start, //
			@Opt(name = "end", def = "-1") Integer end);

	/**
	 * @param stream
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	@Function(name = "read-line")
	public tT[] READ_LINE(
			@BaseArg @Opt(name = "input-stream", def = "*standard-input*") tINPUT_STREAM stream, //
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws EOFException;

	/**
	 * Read an atom as string
	 * 
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public String readAtom( //
			@Opt(name = "eof-error-p", def = "t") Boolean eofErrorP, //
			@Opt(name = "eof-value", def = "nil") tT eofValue, //
			@Opt(name = "recursive-p", def = "nil") Boolean recursiveP)
			throws EOFException;

	/**
	 * Read an atom as string, first character can be escaped (for char
	 * definition reading)
	 * 
	 * @param firstEscaped
	 * @return
	 * @throws EOFException
	 */
	public String readAtom( //
			Boolean firstEscaped, //
			Boolean eofErrorP, //
			tT eofValue, //
			Boolean recursiveP) throws EOFException;

	/**
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public tT readMacroChar(Boolean eofErrorP, tT eofValue, Boolean recursiveP)
			throws EOFException;

}
