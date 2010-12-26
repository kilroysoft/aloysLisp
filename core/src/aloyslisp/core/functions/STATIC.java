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
// IP 14 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import aloyslisp.core.sequences.tLIST;

/**
 * fpGLOBAL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class STATIC extends SYSTEM_FUNCTION
{

	/**
	 * @param cls
	 * @param name
	 * @param decl
	 * @param doc
	 * @param declare
	 */
	public STATIC(Class<?> cls, String name, tLIST decl, String doc,
			tLIST declare)
	{
		super(cls, name, decl, doc, declare);
		this.setFunctionCall(cls, name);
		object = this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.functions.FUNCTION#printableStruct()
	 */
	protected String printableStruct()
	{
		return "FUNCTION " + getFuncName() + " " + intern.getArgs() + " "
				+ intern.commentary() + " " + intern.declare();
	}

}
