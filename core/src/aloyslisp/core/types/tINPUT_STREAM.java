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

import java.io.EOFException;

/**
 * IInput
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tINPUT_STREAM extends tSTREAM
{
	/**
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public Character READ_CHAR(tT eofErrorP, tT eofValue, tT recursiveP)
			throws EOFException;

	/**
	 * @param car
	 * @return
	 */
	public Character UNREAD_CHAR(tT car);

	/**
	 * @param car
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public Character PEEK_CHAR(tT car, tT eofErrorP, tT eofValue, tT recursiveP)
			throws EOFException;

	/**
	 * @return
	 */
	public boolean LISTEN();

	/**
	 * @param eofErrorP
	 * @param eofValue
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public Character READ_CHAR_NO_HANG(tT eofErrorP, tT eofValue, tT recursiveP)
			throws EOFException;

	/**
	 * Read an atom as string
	 * 
	 * @return
	 * @throws EOFException
	 */
	public String readAtom(tT eofErrorP, tT eofValue, tT recursiveP)
			throws EOFException;

	/**
	 * Read an atom as string, first character can be escaped (for char
	 * definition reading)
	 * 
	 * @param firstEscaped
	 * @return
	 * @throws EOFException
	 */
	public String readAtom(boolean firstEscaped, tT eofErrorP, tT eofValue,
			tT recursiveP) throws EOFException;

	/**
	 * @param inputStream
	 * @param recursiveP
	 * @return
	 * @throws EOFException
	 */
	public tT READ(tT eofErrorP, tT eofValue, tT recursiveP)
			throws EOFException;
}
