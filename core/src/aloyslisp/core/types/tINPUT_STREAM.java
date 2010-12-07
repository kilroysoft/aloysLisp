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
import java.io.IOException;

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
	 * @return
	 * @throws IOException
	 */
	public int read() throws IOException;

	/**
	 * @return
	 * @throws IOException
	 */
	public boolean ready() throws IOException;

	/**
	 * @param car
	 * @throws IOException
	 */
	public void unread(Character car) throws IOException;

	/**
	 * @throws IOException
	 */
	public void close() throws IOException;

	/**
	 * Read an atom as string
	 * 
	 * @return
	 * @throws EOFException
	 */
	public String readAtom( //
			Boolean eofErrorP, //
			tT eofValue, //
			Boolean recursiveP) throws EOFException;

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
