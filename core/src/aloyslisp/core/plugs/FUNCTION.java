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
// IP 13 sept. 2010 Creation
// IP 01.12.2010 Pass function call to invoke() version
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import static aloyslisp.commonlisp.L.*;

import java.lang.reflect.*;

import aloyslisp.core.common.*;
import aloyslisp.core.exec.*;
import aloyslisp.core.types.*;

/**
 * FUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class FUNCTION extends CELL implements tFUNCTION
{
	/**
	 * Representation of arguments of the function
	 */
	public Arguments	intern	= null;

	/**
	 * String used to represent the function in case of macrochar transform
	 */
	public String		mac		= null;

	/**
	 * Java method to call, primitive, function, constructor or Lisp interpreter
	 */
	public Method		method	= null;

	tT					object	= null;

	/**
	 * Creation with arguments detail
	 * 
	 * @param def
	 */
	public FUNCTION(boolean external, Class<?> c, tSYMBOL name, tLIST args,
			tLIST func)
	{
		// Class is null so we call internal impl function on this class
		String f = "IMPL";
		if (c == null)
		{
			c = getClass();
			object = this;
		}
		else
			f = name.SYMBOL_NAME();

		intern = new Arguments(name, args, func);
		System.out.println("Name = " + name + " new Name = " + f + " Class = "
				+ c.getCanonicalName());
		if (!setFunctionCall(c, f))
		{
			System.err.println("Function " + f + " not found in class "
					+ c.getCanonicalName());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.IFunc#exec(aloyslisp.core.plugs.CELL[])
	 */
	@Override
	public tT[] e(Object... args)
	{
		if (args.length == 0)
			return exec(NIL);
		return exec(list(args));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.IFunc#exec(aloyslisp.core.plugs.collections
	 * .IList)
	 */
	public tT[] exec(tLIST args)
	{
		tT[] res = null;

		// Evaluate args if appropriate
		if (!(this instanceof tSPECIAL_OPERATOR || this instanceof tMACRO_FUNCTION))
		{
			args = evalArgs(args);
		}

		// Push a new closure except if it's a lisp function
		// defun function will push the closure
		if (this instanceof tBLOCK_FUNCTION)
			e.newClosure();

		intern.pushBlock(args);

		// trace arguments
		// System.out.println("\nv----TRACE---->" + this + "\n" + e.trace(false)
		// + "^------------");

		try
		{
			Object[] newArgs = null;
			tT actObj = object;

			// test method
			if (method == null)
			{
				throw new LispException("Function call w/o method : (" + this
						+ " " + args + ")");
			}

			// Transform arguments
			if (object == null)
			{
				newArgs = tranformArgs(method.getParameterTypes(),
						(tLIST) args.CDR());
				actObj = args.CAR();
			}
			else
				newArgs = tranformArgs(method.getParameterTypes(), args);

			// Call function
			System.out.println("exec(" + method.getName() + " (" + actObj
					+ ") " + args + ")");
			Object ret = method.invoke(actObj, newArgs);

			// if function return multiple values
			if (ret instanceof tT[])
				res = (tT[]) ret;
			else
				// return value as tT
				res = new tT[]
				{ normalize(ret) };
		}
		catch (IllegalArgumentException e)
		{
			throw new LispException("Function " + intern.getStringName()
					+ " bad arguments : " + e.getLocalizedMessage());
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			if (sym("*trace*").SYMBOL_VALUE() != NIL)
			{
				e.getCause().printStackTrace();
			}
			throw new LispException(e.getCause().getLocalizedMessage());
		}
		catch (RuntimeException e)
		{
			// throw back exception upper
			e.printStackTrace();
			throw new LispException(e.getLocalizedMessage());
		}

		e.popBlock();

		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tFUNCTION#setPrefix(java.lang.String)
	 */
	public void setPrefix(String pref)
	{
		mac = pref;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#copy()
	 */
	@Override
	public tT copy()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#printable()
	 */
	@Override
	public String printable()
	{
		return "#<" + printableStruct() + ">";
	}

	/**
	 * Internal printable value
	 * 
	 * @return
	 */
	protected String printableStruct()
	{
		return getClass().getSimpleName() + " " + intern.getName();
	}

	/**
	 * Evaluate all args
	 * 
	 * @param args
	 * @return
	 */
	tLIST evalArgs(tLIST args)
	{
		if (args == null)
		{
			throw new LispException("Evaluation : arguments null in " + this);
		}

		tSEQUENCE res = NIL;
		for (tT arg : args)
		{
			arg = arg.CAR();

			if (arg == null)
			{
				throw new LispException("Evaluation : null in arguments in "
						+ this + ": " + args);
			}

			tT out = arg.EVAL()[0];

			if (out == null)
			{
				throw new LispException("Evaluation of " + arg
						+ " leads to null");
			}

			// System.out.println("évalué   = " + out);
			res = cons(out, res);
		}
		res = res.REVERSE();
		return (tLIST) res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.functions.IFunc#getName()
	 */
	@Override
	public tSYMBOL getFuncName()
	{
		return intern.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.functions.IFunc#setName(java.lang.String)
	 */
	@Override
	public void setFuncName(tSYMBOL name)
	{
		intern.setName(name);
	}

	/**
	 * Get mandatory and optional arguments by index
	 * 
	 * @param index
	 * @return
	 */
	public tT arg(int index)
	{
		return intern.arg(index);
	}

	/**
	 * Get key arguments by name
	 * 
	 * @param key
	 * @return
	 */
	public tT arg(String key)
	{
		return intern.arg(key);
	}

	/**
	 * Get rest arguments
	 * 
	 * @return
	 */
	public tLIST arg()
	{
		return intern.arg();
	}

	/**
	 * Create a local block context
	 * 
	 * @param name
	 * @param args
	 * @param def
	 */
	public Arguments newBlock(tSYMBOL name, tLIST args, tLIST def, tLIST vals)
	{
		if (!(name == null || name instanceof tSYMBOL))
		{
			throw new LispException("Name of block is not an atom" + name);
		}
		Arguments intern = new Arguments(name, args, def);
		intern.pushBlock(vals);
		return intern;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#coerce(aloyslisp.core.types.tT)
	 */
	@Override
	public tT COERCE(tT type)
	{
		// IMPLEMENT Coerce
		return null;
	}

	/**
	 * @param c
	 * @param obj
	 * @param name
	 * @return
	 */
	public boolean setFunctionCall(Class<?> c, String name)
	{
		Method[] meth = c.getMethods();
		for (Method m : meth)
		{
			// m.getParameterAnnotations();

			if (m.getName().equalsIgnoreCase(name))
			{
				method = m;
				intern.setName(sym(name));
				return true;
			}
		}

		return false;
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
				Object[] newArgs = tranformArgs(paramTypes, arg);

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
					throw new LispException("Function " + name
							+ " bad arguments : " + e.getLocalizedMessage());
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					if (sym("*trace*").SYMBOL_VALUE() != NIL)
					{
						e.getCause().printStackTrace();
					}
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
	 * @param paramTypes
	 * @param arg
	 * @return
	 */
	public Object[] tranformArgs(Class<?>[] paramTypes, tLIST arg)
	{
		// Transform arguments
		int i = 0;
		Object[] newArgs = new Object[paramTypes.length];
		System.out.println("Args : " + arg);
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
		// Direct assign works
		if (tT.class.isAssignableFrom(cl))
			if (cl.isAssignableFrom(arg.getClass()))
			{
				return arg;
			}
			else if (arg instanceof Boolean)
			{
				return (Boolean) arg ? T : NIL;
			}
			else if (arg instanceof String)
			{
				return str((String) arg);
			}
			else
			{
				System.out.println("transform fn " + method.getName() + " arg "
						+ arg + " : " + arg.getClass() + "->" + cl);
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
