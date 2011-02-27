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
// IP 26 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.core.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;

/**
 * cAPI_SPECIAL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cAPI_SPECIAL extends cAPI_LAMBDA
{
	tSYMBOL	name	= null;

	/**
	 * @param args
	 * @param doc
	 * @param decl
	 */
	public cAPI_SPECIAL(tSYMBOL name, tLIST args, tT doc, tLIST decl)
	{
		super(args, doc, decl);
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tAPI#API_EVAL(aloyslisp.core.tT)
	 */
	public tT API_EVAL_ARG(tT value)
	{
		return value;
	}

}
