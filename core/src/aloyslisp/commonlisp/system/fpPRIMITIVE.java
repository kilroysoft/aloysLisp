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
// IP 11 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.system;

import static aloyslisp.commonlisp.L.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * fpPRIMITIVE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class fpPRIMITIVE extends SYSTEM_FUNCTION
{

	/**
	 * @param args
	 * @param doc
	 * @param declare
	 */
	public fpPRIMITIVE()
	{
		super(list("obj", "func", "&rest", "args"),
				"(%primitive obj func &rest args)", NIL);
	}

	public fpPRIMITIVE(tLIST args, String doc, tLIST declare)
	{
		super(args, doc, declare);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tFUNCTION#impl()
	 */
	@Override
	public tT[] impl()
	{
		Class<?> c = arg(1).getClass();

		// get function name
		tT func = arg(0);
		String name = null;
		if (func instanceof tSTRING)
			name = ((tSTRING) func).getString();
		else if (func instanceof tSYMBOL)
			name = ((tSYMBOL) func).SYMBOL_NAME().getString();
		else
		{
			throw new LispException(
					"%primitive : func argument should be a string or a symbol: "
							+ func);
		}
		return invoke(c, arg(1), name, (tLIST) arg());
	}

	/**
	 * @param c
	 *            Class to invoque
	 * @param obj
	 *            Object to manage (unused if static function)
	 * @param name
	 *            Name of the function
	 * @param arg
	 *            Arguments as the class list
	 * @return
	 */
	public tT[] invoke(Class<?> c, Object obj, String name, tLIST arg)
	{
		Method[] meth = c.getMethods();
		boolean nameFound = false;
		name = name.replace("-", "_");
		for (Method m : meth)
		{

			m.getParameterAnnotations();

			if (m.getName().equalsIgnoreCase(name))
			{
				Class<?>[] paramTypes = m.getParameterTypes();
				nameFound = true;

				// Test arguments number, type is always tT
				if (m.isVarArgs())
				{
					// Variable number mandatory should be given
					if (paramTypes.length > arg.LENGTH())
						continue;
				}
				// test nb of arguments match
				else if (paramTypes.length != arg.LENGTH())
					continue;

				// Transform arguments
				int i = 0;
				Object[] newArgs = new Object[paramTypes.length];
				for (Class<?> classArg : paramTypes)
				{
					if (classArg.isArray())
					{
						// manage rest of args as an array
						newArgs[i] = arg.getArray();
						arg = NIL;
						continue;
					}
					else
						newArgs[i] = transform(arg.CAR(), classArg);

					arg = (tLIST) arg.CDR();
					i++;
				}

				// found
				try
				{
					Object res = m.invoke(obj, newArgs);

					// if function return multiple values
					if (res instanceof tT[])
						return (tT[]) res;

					// return value as tT
					return new tT[]
					{ normalize(res) };
				}
				catch (IllegalArgumentException e)
				{
					throw new LispException("%primitive : Method " + name
							+ " bad arguments : " + e.getLocalizedMessage());
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					e.getCause().printStackTrace();
					throw new LispException(e.getCause().getLocalizedMessage());
				}
				catch (RuntimeException e)
				{
					// throw back exception upper
					throw new LispException(e.getLocalizedMessage());
				}
			}
		}

		if (nameFound)
			throw new LispException("%primitive : Bad argument number for "
					+ name);

		throw new LispErrorFunctionCannotApplyOn("%primitive : " + name, arg(1));
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
		// Direct assign works
		if (tT.class.isAssignableFrom(cl))
			if (cl.isAssignableFrom(arg.getClass()))
			{
				return arg;
			}
			else
			{
				System.out.println("transform " + arg.getClass() + "->" + cl);
				throw new LispException("1Argument " + arg
						+ " should be of type " + cl.getSimpleName());
			}

		if (cl == Boolean.class)
		{
			return arg != NIL;
		}

		if (cl == Integer.class && arg instanceof tNUMBER)
		{
			return ((tNUMBER) arg).intValue();
		}

		if (cl == Long.class && arg instanceof tNUMBER)
		{
			return ((tNUMBER) arg).longValue();
		}

		if (cl == Float.class && arg instanceof tNUMBER)
		{
			return ((tNUMBER) arg).floatValue();
		}

		if (cl == Double.class && arg instanceof tNUMBER)
		{
			return ((tNUMBER) arg).doubleValue();
		}

		if (cl == String.class && arg instanceof tSTRING)
		{
			return ((tSTRING) arg).getString();
		}

		System.out.println("transform " + arg.getClass() + "->" + cl);
		throw new LispException("2Argument " + arg + " should be of type "
				+ cl.getSimpleName());
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
		else if (cell instanceof Long)
			return nLong((Long) cell);
		else if (cell instanceof Float)
			return nFloat((Float) cell);
		else if (cell instanceof Double)
			return nDouble((Double) cell);
		else if (cell instanceof Character)
			return c((Character) cell);

		throw new LispException("primitive : return value of type "
				+ cell.getClass().getSimpleName() + " not managed");
	}

}
