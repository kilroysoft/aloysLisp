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

package aloyslisp.core.functions;

import static aloyslisp.commonlisp.L.*;

import java.lang.reflect.*;

import aloyslisp.core.conditions.LispException;
import aloyslisp.core.exec.*;
import aloyslisp.core.math.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.sequences.*;
import aloyslisp.core.streams.*;

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
	public Arguments	intern			= null;

	/**
	 * Representation of arguments of the function
	 */
	public String		documentation	= null;

	/**
	 * String used to represent the function in case of macrochar transform
	 */
	public String		mac				= null;

	/**
	 * Java method to call, primitive, function, constructor or Lisp interpreter
	 */
	public Method		method			= null;

	/**
	 * Static object for static function
	 */
	tT					object			= null;

	/**
	 * Number of argument used as base object for primitives
	 */
	Integer				baseArg			= -1;

	/**
	 * Creation with arguments detail
	 * 
	 * @param def
	 */
	public FUNCTION(boolean external, Class<?> c, tSYMBOL name, tLIST args,
			tLIST func)
	{
		// trace = true;

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
		trace("Name = " + name + " new Name = " + f + " Class = "
				+ c.getCanonicalName());
		if (!setFunctionCall(c, f))
		{
			System.err.println("Function " + f + " not found in class "
					+ c.getCanonicalName());
		}
		intern.setName(name);

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tFUNCTION#setBaseArg(java.lang.Integer)
	 */
	public void setBaseArg(Integer no)
	{
		baseArg = no;
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
				if (baseArg >= 0)
				{
					// object is in the argument list, no change in arguments
					intern.pushBlock(args);
					args = intern.getValues();
					newArgs = tranformArgs(method.getParameterTypes(), args);
					actObj = args.ELT(baseArg);
				}
				else
				{
					// First argument is the object, real args follow
					actObj = args.CAR();
					intern.pushBlock((tLIST) args.CDR());
					args = intern.getValues();
					newArgs = tranformArgs(method.getParameterTypes(), args);
				}
			}
			else
			{
				// static function arguments are kept
				intern.pushBlock(args);
				newArgs = tranformArgs(method.getParameterTypes(), args);
			}

			// Suppress the execution environment if special
			if (this instanceof tSPECIAL_OPERATOR)
				e.popBlock();

			// Call function
			trace("exec(" + intern.getName() + " (" + actObj + ") " + args
					+ ")");

			Object ret = method.invoke(actObj, newArgs);

			// if function return multiple values
			if (ret == null)
			{
				// return value as tT
				trace(" => From (" + intern.getName() + ") => " + null);
				res = new tT[] {};
			}
			else if (ret instanceof tT[])
			{
				if (((tT[]) ret).length > 0)
					trace(" => From (" + intern.getName() + ") => "
							+ ((Object[]) ret)[0]);
				else
					trace(" PROBLEME => From (" + intern.getName() + ") => "
							+ ret);
				res = (tT[]) ret;
			}
			else
			{
				// return value as tT
				trace(" => From (" + intern.getName() + ") => " + ret);
				res = new tT[]
				{ normalize(ret) };
			}
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
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

		if (!(this instanceof tSPECIAL_OPERATOR))
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
	public String toString()
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
				return true;
			}
		}

		return false;
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
		int posRest = intern.getPosRest();
		Object[] newArgs = new Object[paramTypes.length];

		// System.out.println("Args : " + arg);

		for (Class<?> classArg : paramTypes)
		{
			if (classArg.isArray())
			{
				// manage rest of args as an array, tT... argument.
				newArgs[i] = arg.getArray();
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
						+ arg + " : " + arg.getClass() + "->" + cl);
				throw new LispException("1Argument " + arg
						+ " should be of type " + cl.getSimpleName());
			}

		if (cl == Boolean.class)
		{
			res = arg != NIL;
		}

		if (cl == Integer.class && arg instanceof tINTEGER)
		{
			res = ((INTEGER) arg).integerValue();
		}

		if (cl == Long.class && arg instanceof tINTEGER)
		{
			res = ((INTEGER) arg).integerValue();
		}

		if (cl == Float.class && arg instanceof tREAL)
		{
			res = ((REAL) arg).floatValue();
		}

		if (cl == Double.class && arg instanceof tREAL)
		{
			res = ((REAL) arg).doubleValue();
		}

		if (cl == String.class && arg instanceof tSTRING)
		{
			res = ((tSTRING) arg).getString();
		}

		if (cl == Character.class && arg instanceof tCHARACTER)
		{
			res = ((tCHARACTER) arg).getChar();
		}

		trace(" ~~~> " + arg + " (" + arg.getClass().getSimpleName() + ") -> "
				+ res + " (" + cl.getSimpleName() + ")");

		if (res == null)
			throw new LispException("Argument " + arg + " should be of type "
					+ cl.getSimpleName());
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

}
