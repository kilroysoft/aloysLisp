/**
 * aloysLisp.
 * <p>
 * A LISP interpreter, compiler and library.
 * <p>
 * Copyright (C) 2010-2011 kilroySoft <aloyslisp@kilroysoft.ch>
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
// IP 26 oct. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import static aloyslisp.core.streams.cSTRING_STREAM.*;

import java.lang.annotation.*;
import java.lang.reflect.*;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.designators.tPACKAGE_DESIGNATOR;
import aloyslisp.core.designators.tPATHNAME_DESIGNATOR;
import aloyslisp.core.designators.tSTRING_DESIGNATOR;
import aloyslisp.core.math.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import aloyslisp.core.streams.*;
import aloyslisp.internal.engine.*;
import aloyslisp.internal.iterators.*;
import static aloyslisp.core.L.*;

/**
 * cCOMPILED_FUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aBuiltIn(lispClass = "function", lispType = "compiled-function", doc = "03_b")
public class cCOMPILED_FUNCTION extends cFUNCTION implements tCOMPILED_FUNCTION
{
	protected Method	method	= null;

	/**
	 * @param args
	 * @param doc
	 * @param decl
	 */
	public cCOMPILED_FUNCTION(Method method)
	{
		super();
		aFunction func = method.getAnnotation(aFunction.class);
		SET_API_DOC(NIL);
		if (func != null)
		{
			name = sym(func.name());
			if (!func.doc().equals(""))
				SET_API_DOC(str(func.doc()));
		}

		this.special = method.getAnnotation(aSpecialOperator.class) != null;
		this.macro = method.getAnnotation(aMacro.class) != null;
		this.method = method;
		vars = SET_API_ARGS(NIL);
		if ((method.getModifiers() & Modifier.STATIC) == 0)
			vars = API_SET_OBJ(vars, method.getAnnotation(aBaseArg.class));
		SET_API_DECL(NIL);
	}

	public static final tSYMBOL	ELEM	= sym("elem");

	/**
	 * @param vars
	 * @param annotation
	 * @return
	 */
	private tLIST API_SET_OBJ(tLIST vars, aBaseArg base)
	{
		LISTIterator iter = new LISTIterator(vars);
		if (base == null)
		{
			// System.out.println("Before object add : " + name + " " + vars);
			iter.add(new cARG(ELEM, NIL, true));
			// System.out.println("After object add : " + name + " "
			// + (tLIST) iter.getFinal());
			basePos = -1;
			return (tLIST) iter.getFinal();
		}

		basePos = base.pos();
		iter.go(basePos - 1);
		iter.add(new cARG(sym(base.name()), lisp(base.def()), true));

		tLIST res = (tLIST) iter.getFinal();
		// System.out.println("API_SET_OBJ : " + res);
		return (tLIST) res;
	}

	/**
	 * @return
	 */
	public tLIST SET_API_ARGS(tLIST unused)
	{
		LISTIterator vars = new LISTIterator(NIL);
		for (Annotation[] an : method.getParameterAnnotations())
		{
			int state = 0;
			if (an.length == 0)
				System.out.println("Warning missing annotations on " + name
						+ " on " + method.getDeclaringClass().getSimpleName());
			for (Annotation a : an)
			{
				if (a instanceof aArg && state < 1)
				{
					vars.append(new cARG(sym(((aArg) a).name()), NIL, true));
					state = 1;
				}
				else if (a instanceof aOpt && state < 2)
				{
					vars.append(new cARG(sym(((aOpt) a).name()),
							lisp(((aOpt) a).def()), true));
					state = 2;
				}
				else if (a instanceof aRest && state < 3 && rest == null)
				{
					rest = sym(((aRest) a).name());
					state = 3;
				}
				else if (a instanceof aWhole && state < 4 && whole == null)
				{
					whole = sym(((aWhole) a).name());
					state = 4;
				}
			}
		}

		aKey keyDef = method.getAnnotation(aKey.class);
		if (keyDef != null)
		{
			String ks = keyDef.keys();
			if (!(ks.equals("") || ks.equals("()")))
			{
				tT kl = lisp(keyDef.keys());
				for (tT key : (tLIST) kl)
				{
					tARG var = new cARG_KEY(key);
					keys.SET_GETHASH(var, ((cARG_KEY) var).key);
					vars.append(var);
				}
			}
		}

		tT res = vars.getFinal();
		// System.out.println("SET_API_ARGS (java): " + res);
		return (tLIST) res;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tCODE#CODE_CALL(aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public tT[] API_CALL(tLIST args)
	{
		Boolean stat = (method.getModifiers() & Modifier.STATIC) != 0;

		tT[] res = new tT[]
		{ NIL };
		tT object = null;

		if (!stat)
		{
			args = API_OBJECT(args);
			object = args.CAR();
			args = (tLIST) args.CDR();
		}

		try
		{
			// System.out
			// .println("API_CALL " + object + args + tranformArgs(args));
			Object is = method.invoke(object, tranformArgs(args));
			if (is instanceof Object[])
				res = normalizeArray((Object[]) is);
			else
				res = new tT[]
				{ normalize(is) };
		}
		catch (IllegalArgumentException e)
		{
			throw new LispException("Illegal arguments in " + this + " : "
					+ e.getLocalizedMessage());
		}
		catch (IllegalAccessException e)
		{
			throw new LispException("Illegal access in " + this + " : "
					+ e.getLocalizedMessage());
		}
		catch (InvocationTargetException e)
		{
			throw (RuntimeException) e.getTargetException();
		}
		catch (RuntimeException e)
		{
			throw (RuntimeException) e;
		}
		catch (Exception e)
		{
			throw new LispException("Non lisp error in " + this + " : "
					+ e.getLocalizedMessage());
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tCODE#CODE_OBJECT(aloyslisp.core.sequences.
	 * tLIST)
	 */
	@Override
	public tLIST API_OBJECT(tLIST args)
	{
		int pos = 0;
		// aFunction func = method.getAnnotation(aFunction.class);
		// if (func == null)
		// return args;

		aBaseArg position = method.getAnnotation(aBaseArg.class);
		if (position != null)
			pos = position.pos();

		if (pos < 0)
			return args;

		LISTIterator iter = new LISTIterator(args);
		tT obj = iter.go(pos);
		iter.remove();

		return cCONS.CONS(obj, iter.getFinal());
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.internal.engine.tCODE#CODE_GET_MAC()
	 */
	@Override
	public String API_GET_MAC()
	{
		String mac = null;
		aMac annotation = method.getAnnotation(aMac.class);
		if (annotation != null)
			mac = annotation.prefix();
		return mac;
	}

	/**
	 * @return
	 */
	protected String getLispDeclare()
	{
		// TODO api description
		// String lFunc = name.SYMBOL_NAME();
		// tLIST res = list(name);
		// if (!(method.TO_STRING().contains("static")) && baseArg == -1)
		// {
		// res = (tLIST) res.APPEND(list(sym(method.getDeclaringClass()
		// .getSimpleName().substring(1))));
		// }
		// res = (tLIST) res.APPEND(this.API_ARGS());
		// String decl = "* " + res.TO_STRING().replaceAll(" \\*", " \\\\*");
		// decl = decl.replaceFirst(
		// lFunc.replaceAll("\\*", "\\\\*").replaceAll("\\%", "\\\\%")
		// .replaceAll("\\+", "\\\\+"), "[[" + lFunc
		// + "|http://hyper.aloys.li/Body/" + this.API_DOC()
		// + ".htm]]");
		// return decl.toLowerCase().replace("/body/", "/Body/");
		return "";
	}

	/**
	 * @param paramTypes
	 * @param arg
	 * @return
	 */
	protected Object[] tranformArgs(tLIST arg)
	{
		// Transform arguments
		Class<?>[] paramTypes = method.getParameterTypes();
		int i = 0;
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
	protected Object transform(Object arg, Class<?> cl)
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
				throw new LispException("1Argument " + arg + " : "
						+ arg.getClass().getSimpleName()
						+ " should be of type " + cl.getSimpleName() + " in "
						+ method.getName());
			}

		if (cl == Boolean.class)
		{
			res = arg != NIL;
		}

		if (cl == Integer.class && arg instanceof tINTEGER)
		{
			res = ((tINTEGER) arg).INTEGER_VALUE();
		}

		if (cl == Long.class && arg instanceof tINTEGER)
		{
			res = ((tINTEGER) arg).INTEGER_VALUE();
		}

		if (cl == Float.class && arg instanceof tREAL)
		{
			res = ((tREAL) arg).FLOAT_VALUE();
		}

		if (cl == Double.class && arg instanceof tREAL)
		{
			res = ((tREAL) arg).DOUBLE_VALUE();
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

		// trace(" ~~~> " + arg + " (" + arg.getClass().getSimpleName() +
		// ") -> "
		// + res + " (" + cl.getSimpleName() + ")");

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

		if (cell == null)
			throw new LispException("primitive : null return value not managed");
		else
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
	 * @param notes
	 * @return
	 */
	protected tLIST argsDecl(Annotation[][] notes)
	{
		tLIST res = NIL;
		res = (tLIST) res.APPEND(argsBase(notes, aArg.class, ""));
		res = (tLIST) res.APPEND(argsBase(notes, aOpt.class, "&optional"));
		res = (tLIST) res.APPEND(argsBase(notes, aRest.class, "&rest"));
		tLIST key = keys.HASH_TABLE_KEYS();
		if (key != NIL)
		{
			res = (tLIST) res.APPEND(list(sym("&list")));
			res = (tLIST) res.APPEND(key);
		}
		return res;
	}

	/**
	 * @param notes
	 * @param type
	 * @param prefix
	 * @return
	 */
	protected tLIST argsBase(Annotation[][] notes,
			Class<? extends Annotation> type, String prefix)
	{
		tLIST res = NIL;
		boolean call = prefix == null;

		for (Annotation[] an : notes)
		{
			for (Annotation a : an)
			{
				tT arg = NIL;
				if (type.isAssignableFrom(a.getClass()))
				{
					if (a instanceof aArg)
					{
						arg = sym(((aArg) a).name());
						if (call)
							arg = unquote(arg);
					}
					else if (a instanceof aOpt)
					{
						arg = sym(((aOpt) a).name());
						if (call)
							arg = unquote(arg);
						else
							arg = list(arg).APPEND(
									list(getDefault(((aOpt) a).def())));
					}
					else if (a instanceof aRest)
					{
						arg = sym(((aRest) a).name());
						if (call)
							arg = splice(arg);
					}
				}
				if (arg != NIL)
					res = (tLIST) res.APPEND(list(arg));
			}
		}

		// If a prefix is given and
		if (prefix != null && !prefix.equals("") && res.LENGTH() != 0)
			res = (tLIST) decl(prefix).APPEND(res);

		return res;
	}

	/**
	 * @param def
	 * @return
	 */
	protected tT getDefault(String def)
	{
		if (def.equals(""))
			return NIL;

		return MAKE_STRING_INPUT_STREAM(def, NIL, NIL).READ(false, NIL, false);
	}

	public String DESCRIBE()
	{
		return "#<API-JAVA " + method.getName() + " " + API_ARGS() + " "
				+ basePos + " #<SPECIAL " + (special ? T : NIL) + "> #<MACRO "
				+ (macro ? T : NIL) + "> " + environment + ">";
	}

}
