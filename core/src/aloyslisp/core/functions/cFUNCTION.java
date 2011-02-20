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

import static aloyslisp.core.engine.L.*;

import java.lang.reflect.*;

import aloyslisp.core.*;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.engine.*;
import aloyslisp.core.math.*;
import aloyslisp.core.packages.cPACKAGE;
import aloyslisp.core.packages.tPACKAGE_DESIGNATOR;
import aloyslisp.core.packages.tSYMBOL;
import aloyslisp.core.sequences.*;
import aloyslisp.core.streams.*;

/**
 * cFUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class cFUNCTION extends cCELL implements tFUNCTION,
		tFUNCTION_DESIGNATOR
{
	/**
	 * Representation of arguments of the function
	 */
	public cAPI		api		= null;

	/**
	 * String used to represent the function in case of macrochar transform
	 */
	public String	mac		= null;

	/**
	 * Static object for static function
	 */
	tT				object	= null;

	/**
	 * Creation with arguments detail
	 * 
	 * @param def
	 */
	public cFUNCTION(boolean external, Class<?> c, tSYMBOL name, tLIST args,
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

		api = new cAPI(name, args, func);
		trace("Name = " + name + " new Name = " + f + " Class = "
				+ c.getCanonicalName());
		if (!api.setFunctionCall(c, f))
		{
			System.err.println("Function " + f + " not found in class "
					+ c.getCanonicalName());
		}
		api.setName(name);

	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.IFunc#exec(aloyslisp.core.cCELL[])
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
	 * aloyslisp.core.functions.IFunc#exec(aloyslisp.core.collections
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

		Object[] newArgs = null;
		tT actObj = object;
		try
		{

			// test method
			if (api.method == null)
			{
				throw new LispException("Function call w/o method : (" + this
						+ " " + args + ")");
			}

			// Transform arguments
			if (object == null)
			{
				if (api.baseArg >= 0)
				{
					// object is in the argument list, no change in arguments
					api.pushBlock(args);
					args = api.getDynValues();
					newArgs = tranformArgs(api.method.getParameterTypes(), args);
					actObj = (tT) newArgs[api.baseArg];
				}
				else
				{
					// First argument is the object, real args follow
					actObj = args.CAR();
					api.pushBlock((tLIST) args.CDR());
					args = api.getDynValues();
					newArgs = tranformArgs(api.method.getParameterTypes(), args);
				}
			}
			else
			{
				// static function arguments are kept
				api.pushBlock(args);
				newArgs = tranformArgs(api.method.getParameterTypes(), args);
			}

			// Suppress the execution environment if special
			if (this instanceof tSPECIAL_OPERATOR)
				e.popBlock();

			// Call function
			trace("exec(" + api.getName() + " (" + actObj + ") " + args + ")");

			Object ret = api.method.invoke(actObj, newArgs);

			// if function return multiple values
			if (ret == null)
			{
				// return value as tT
				trace(" => From (" + api.getName() + ") => " + null);
				res = new tT[] {};
			}
			else if (ret instanceof tT[])
			{
				if (((tT[]) ret).length > 0)
					trace(" => From (" + api.getName() + ") => "
							+ ((Object[]) ret)[0]);
				else
					trace(" PROBLEME => From (" + api.getName() + ") => " + ret);
				res = (tT[]) ret;
			}
			else
			{
				// return value as tT
				trace(" => From (" + api.getName() + ") => " + ret);
				res = new tT[]
				{ normalize(ret) };
			}
		}
		catch (IllegalArgumentException e)
		{
			if (newArgs == null)
				System.out.println("cAPI : null");
			else
			{
				System.out.println("Function : " + api.getStringName());
				System.out.println("Object : " + actObj + " : ("
						+ actObj.getClass().getSimpleName() + ")");
				System.out.println("Meth : " + api.method.toGenericString());
				for (int i = 0; i < newArgs.length; i++)
					System.out.println("Arg(" + i + ") : " + newArgs[i]
							+ " : (" + newArgs[i].getClass().getSimpleName()
							+ ")");
			}
			e.printStackTrace();
			throw new LispException("Function " + api.getStringName()
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
	 * @see aloyslisp.core.Cell#copy()
	 */
	@Override
	public tT copy()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.Cell#printable()
	 */
	@Override
	public String toString()
	{
		return "#<" + printableStruct() + ">";
	}

	public String toDocString()
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
		return getClass().getSimpleName() + " " + api.getName();
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
			if (arg == null)
			{
				throw new LispException("Evaluation : null in arguments in "
						+ this + ": " + args);
			}

			tT out = NIL;
			out = arg.EVAL()[0];

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
	 * @see aloyslisp.core.functions.IFunc#getName()
	 */
	@Override
	public tSYMBOL getFuncName()
	{
		return api.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.functions.IFunc#setName(java.lang.String)
	 */
	@Override
	public void setFuncName(tSYMBOL name)
	{
		api.setName(name);
	}

	/**
	 * Get mandatory and optional arguments by index
	 * 
	 * @param index
	 * @return
	 */
	public tT arg(int index)
	{
		return api.arg(index);
	}

	/**
	 * Get key arguments by name
	 * 
	 * @param key
	 * @return
	 */
	public tT arg(String key)
	{
		return api.arg(key);
	}

	/**
	 * Get rest arguments
	 * 
	 * @return
	 */
	public tLIST arg()
	{
		return api.arg();
	}

	/**
	 * Create a local block context
	 * 
	 * @param name
	 * @param args
	 * @param def
	 */
	public cAPI newBlock(tSYMBOL name, tLIST args, tLIST def, tLIST vals)
	{
		if (!(name == null || name instanceof tSYMBOL))
		{
			throw new LispException("Name of block is not an atom" + name);
		}
		cAPI intern = new cAPI(name, args, def);
		intern.pushBlock(vals);
		return intern;
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
		int posRest = api.getPosRest();
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
				System.out.println("transform fn " + api.method.getName()
						+ " arg " + arg + " : "
						+ arg.getClass().getSimpleName() + "->"
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

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.functions.tFUNCTION#setBaseArg(java.lang.Integer)
	 */
	@Override
	public void setBaseArg(Integer no)
	{
		api.setBaseArg(no);
	}

}
