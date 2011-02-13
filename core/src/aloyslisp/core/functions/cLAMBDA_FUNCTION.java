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
// IP 27 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import static aloyslisp.L.*;
import aloyslisp.core.tT;
import aloyslisp.core.packages.tSYMBOL;
import aloyslisp.core.sequences.tLIST;

/**
 * cLAMBDA_FUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cLAMBDA_FUNCTION extends cFUNCTION implements tLAMBDA_FUNCTION
{

	/**
	 * @param name
	 * @param args
	 * @param func
	 */
	public cLAMBDA_FUNCTION(tSYMBOL name, tLIST args, tLIST func)
	{
		super(false, null, name, args, func);
	}

	/**
	 * Execute Lisp code
	 * 
	 * @return
	 */
	public tT[] IMPL()
	{
		return e.exec();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.functions.IFunction#getFunction()
	 */
	@Override
	public tLIST getFunction()
	{
		return intern.func();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.functions.IFunction#setFunction(aloyslisp.core
	 * .functions.ILispFunc)
	 */
	@Override
	public void setFunction(tLIST func)
	{
		intern.setFunc(func);
	}

	/**
	 * Internal printable value
	 * 
	 * @return
	 */
	protected String printableStruct()
	{
		return "LAMBDA " + intern.getArgs() + " " + intern.commentary() + " "
				+ intern.declare() + " " + intern.func();
	}

}
