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
// IP 26 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import static aloyslisp.internal.engine.L.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import aloyslisp.core.*;
import aloyslisp.annotations.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.math.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import aloyslisp.core.streams.*;
import aloyslisp.internal.engine.*;

/**
 * cCOMPILED_FUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cCOMPILED_FUNCTION extends cFUNCTION implements tCOMPILED_FUNCTION
{
	/**
	 * Java method to call, primitive, function, constructor or Lisp interpreter
	 */
	public Method	method	= null;

	/**
	 * @param def
	 */
	public cCOMPILED_FUNCTION(Class<?> cls, tSYMBOL name, tLIST args, tT doc,
			tLIST decl)
	{
		super();
		api = new cAPI_FUNC(name, args, doc, decl);
		method = setFunctionCall(cls, name);
	}

	public cCOMPILED_FUNCTION()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.functions.IFunc#exec(aloyslisp.core.collections
	 * .IList)
	 */
	public tT[] exec(tLIST values)
	{
		tT[] res = new tT[]
		{ NIL };
		Object[] args = api.API_PUSH_ENV(values);

		try
		{
			Object ret = normalize(method.invoke(null, args));
			if (ret instanceof Object[])
				res = normalizeArray((Object[]) ret);
			else
				res = new tT[]
				{ normalize(ret) };
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			api.API_POP_ENV();
		}

		return res;
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
			String lispName = m.getAnnotation(Static.class).name();
			if (lispName.equalsIgnoreCase(name.SYMBOL_NAME()))
			{
				return m;
			}
		}

		return null;
	}

	/**
	 * @return
	 */
	public String getLispDeclare()
	{
		String lFunc = name.SYMBOL_NAME();
		tLIST res = list(name);
		if (!(method.toString().contains("static")) && baseArg == -1)
		{
			res = (tLIST) res.APPEND(list(sym(method.getDeclaringClass()
					.getSimpleName().substring(1))));
		}
		res = (tLIST) res.APPEND(this.API_ARGS());
		String decl = "* " + res.toString().replaceAll(" \\*", " \\\\*");
		decl = decl.replaceFirst(
				lFunc.replaceAll("\\*", "\\\\*").replaceAll("\\%", "\\\\%")
						.replaceAll("\\+", "\\\\+"), "[[" + lFunc
						+ "|http://hyper.aloys.li/Body/" + this.API_DOC()
						+ ".htm]]");
		return decl.toLowerCase().replace("/body/", "/Body/");
	}

	/**
	 * @param paramTypes
	 * @param arg
	 * @return
	 */
	public Object[] tranformArgs(Class<?>[] paramTypes, tLIST arg)
	{
		// Transform arguments
		int i = 0;
		int posRest = -1; // TODO this.getPosRest();
		Object[] newArgs = new Object[paramTypes.length];

		// System.out.println("Args : " + arg);

		for (Class<?> classArg : paramTypes)
		{
			if (classArg.isArray())
			{
				// manage rest of args as an array, tT... argument.
				newArgs[i] = arg.VALUES_LIST();
				arg = NIL;
				continue;
			}
			else
			{
				if (i == posRest)
					// rest as list in an argument
					newArgs[i] = transform(arg, classArg);
				else
					// normal arg
					newArgs[i] = transform(arg.CAR(), classArg);
			}

			arg = (tLIST) arg.CDR();
			i++;
		}
		return newArgs;
	}

	/**
	 * Coerce a lisp argument to the object class of the function
	 * 
	 * @param arg
	 * @param cl
	 * @return
	 */
	public Object transform(Object arg, Class<?> cl)
	{
		Object res = null;

		// Direct assign works
		if (tT.class.isAssignableFrom(cl))
			if (cl.isAssignableFrom(arg.getClass()))
			{
				res = arg;
			}
			else if (arg instanceof Boolean)
			{
				res = ((Boolean) arg) ? T : NIL;
			}
			else if (arg instanceof String)
			{
				res = str((String) arg);
			}
			else
			{
				System.out.println("transform fn " + method.getName() + " arg "
						+ arg + " : " + arg.getClass().getSimpleName() + "->"
						+ cl.getSimpleName());
				throw new LispException("1Argument " + arg + " : "
						+ arg.getClass().getSimpleName()
						+ " should be of type " + cl.getSimpleName());
			}

		if (cl == Boolean.class)
		{
			res = arg != NIL;
		}

		if (cl == Integer.class && arg instanceof tINTEGER)
		{
			res = ((cINTEGER) arg).integerValue();
		}

		if (cl == Long.class && arg instanceof tINTEGER)
		{
			res = ((cINTEGER) arg).integerValue();
		}

		if (cl == Float.class && arg instanceof tREAL)
		{
			res = ((cREAL) arg).floatValue();
		}

		if (cl == Double.class && arg instanceof tREAL)
		{
			res = ((cREAL) arg).doubleValue();
		}

		if (cl == String.class)
		{
			if (arg instanceof tSTRING)
			{
				res = ((tSTRING) arg).getString();
			}
		}

		if (cl == Character.class && arg instanceof tCHARACTER)
		{
			res = ((tCHARACTER) arg).getChar();
		}

		if (res != null)
			arg = res;

		if ((cl == tPATHNAME_DESIGNATOR.class && arg instanceof tPATHNAME_DESIGNATOR))
		{
			res = cPATHNAME.PATHNAME((tPATHNAME_DESIGNATOR) arg);
		}
		else if ((cl == tPACKAGE_DESIGNATOR.class && arg instanceof tPACKAGE_DESIGNATOR))
		{
			res = cPACKAGE.FIND_PACKAGE((tPACKAGE_DESIGNATOR) arg);
		}
		else if ((cl == tSTRING_DESIGNATOR.class && arg instanceof tSTRING_DESIGNATOR))
		{
			res = cSTRING.STRING((tSTRING_DESIGNATOR) arg);
		}

		if (res == null)
			throw new LispException("Argument " + arg + "("
					+ arg.getClass().getSimpleName() + ")"
					+ " should be of type " + cl.getSimpleName());

		trace(" ~~~> " + arg + " (" + arg.getClass().getSimpleName() + ") -> "
				+ res + " (" + cl.getSimpleName() + ")");

		return res;
	}

	/**
	 * Change object to tT according to object type
	 * 
	 * @param cell
	 * @return
	 */
	protected tT normalize(Object cell)
	{
		if (cell instanceof tT)
			return (tT) cell;
		else if (cell instanceof String)
			return str((String) cell);
		else if (cell instanceof Boolean)
			return bool((Boolean) cell);
		else if (cell instanceof Integer)
			return nInt((Integer) cell);
		else if (cell instanceof Float)
			return nFloat((Float) cell);
		else if (cell instanceof Double)
			return nDouble((Double) cell);
		else if (cell instanceof Character)
			return c((Character) cell);

		throw new LispException("primitive : return value of type "
				+ cell.getClass().getSimpleName() + " not managed");
	}

	/**
	 * Normalize result of function call to a
	 * 
	 * @param vals
	 * @return
	 */
	protected tT[] normalizeArray(Object[] vals)
	{
		tT[] res = new tT[vals.length];

		for (int i = 0; i < vals.length; i++)
		{
			res[i] = normalize(vals[i]);
		}

		return res;
	}

	/**
	 * Number of argument used as base object for primitives
	 */
	public Integer	baseArg	= -1;

	/**
	 * Static object for static function
	 */
	tT				object	= null;

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

}
