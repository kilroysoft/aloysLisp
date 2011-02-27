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

import java.lang.reflect.Method;

import aloyslisp.annotations.Function;
import aloyslisp.core.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;

/**
 * fpGLOBAL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cCOMPILED_METHOD extends cCOMPILED_FUNCTION
{

	/**
	 * @param cls
	 * @param name
	 * @param decl
	 * @param doc
	 * @param declare
	 */
	public cCOMPILED_METHOD(Class<?> cls, tSYMBOL name, tLIST decl, tT doc,
			tLIST declare)
	{
		super(cls, name, decl, doc, declare);
		object = this;
	}

	/**
	 * 
	 */
	public cCOMPILED_METHOD()
	{
		super();
	}

	/**
	 * @param no
	 */
	public void setBaseArg(Integer no)
	{
		baseArg = no;
	}

	/**
	 * @return
	 */
	public Integer getBaseArg()
	{
		return baseArg;
	}

	/**
	 * @param c
	 * @param obj
	 * @param name
	 * @return
	 */
	public Method setFunctionCall(Class<?> c, tSYMBOL name)
	{
		Method[] meth = c.getMethods();
		for (Method m : meth)
		{
			String lispName = m.getAnnotation(Function.class).name();
			if (lispName.equalsIgnoreCase(name.SYMBOL_NAME()))
			{
				return m;
			}
		}

		return null;
	}

}
