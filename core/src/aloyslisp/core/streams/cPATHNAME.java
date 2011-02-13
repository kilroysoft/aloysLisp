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
// IP 18 déc. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import aloyslisp.annotations.Static;
import aloyslisp.core.*;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.sequences.cSTRING;
import aloyslisp.core.sequences.tSTRING_DESIGNATOR;

/**
 * cPATHNAME
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cPATHNAME extends cCELL implements tPATHNAME
{
	String	file	= "";

	/**
	 * @param file
	 */
	public cPATHNAME(String file)
	{
		this.file = file;
	}

	/**
	 * @return
	 */
	public String getFile()
	{
		return file;
	}

	/**
	 * @param path
	 * @return
	 */
	@Static(name = "pathname", doc = "f_pn")
	public static tPATHNAME PATHNAME(tPATHNAME_DESIGNATOR path)
	{
		if (path instanceof tPATHNAME)
			return (tPATHNAME) path;

		if (path instanceof tFILE_STREAM)
			return ((tFILE_STREAM) path).getPathname();

		if (!(path instanceof tSTRING_DESIGNATOR))
			throw new LispException("Type error for " + path);

		return new cPATHNAME(cSTRING.STRING(path));
	}

}
