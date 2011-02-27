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
// IP 10 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import java.lang.annotation.*;
import java.lang.reflect.*;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import static aloyslisp.core.streams.cSTRING_INPUT_STREAM.*;
import static aloyslisp.internal.engine.L.*;

/**
 * Library
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class Library
{
	/**
	 * Read all lisp functions of the class and create appropriate package entry
	 * 
	 * There is different types of functions
	 * <ul>
	 * <li>@Function Normal Lisp objects function
	 * <li>@Static Normal static function or constructor
	 * </ul>
	 * 
	 * @param cls
	 * @return
	 */
	@Static(name = "instantiate", doc = "TBD")
	public static Boolean INSTANTIATE( //
			@Arg(name = "class") String cls)
	{
		// Search method
		Class<?> clas;
		try
		{
			clas = (Class<?>) Class.forName(cls);
		}
		catch (ClassNotFoundException e1)
		{
			// ERROR("%%global : System error ~s not found", str(cls));
			return false;
		}

		if (!InstFields(cls, clas))
			return false;

		if (!InstMethods(cls, clas))
			return false;

		return true;
	}

	/**
	 * @param clas
	 * @return
	 */
	public static Boolean InstFields(String cls, Class<?> clas)
	{
		Field[] fields = clas.getFields();
		for (Field f : fields)
		{
			// Get method data
			Symb symbol = f.getAnnotation(Symb.class);
			if (symbol == null)
				continue;

			SpecialVar special = f.getAnnotation(SpecialVar.class);
			boolean constant = f.toGenericString().contains(" final ");

			tSYMBOL sym = sym(symbol.name());

			if (constant)
				sym.setConstant(true);
			else if (special != null)
				sym.setSpecial(true);

			if (f.toString().contains(cls + "." + f.getName()))
			{
				String var = f.toString().replace(cls + ".", "")
						.replaceAll("aloyslisp.core.", "")
						.replaceAll("aloyslisp.", "")
						.replaceAll("functions.", "").replaceAll("math.", "")
						.replaceAll("plugs.", "").replaceAll("sequences.", "")
						.replaceAll("streams.", "").replaceAll("packages.", "")
						.replaceAll("java.lang.", "")
						.replaceAll("#<cPACKAGE system>::", "");
				String val = "";
				try
				{
					val = f.get(null).toString();
				}
				catch (IllegalArgumentException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				catch (IllegalAccessException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (constant)
					System.out.println("(constant " + sym + " " + val + ")");
				else if (special != null)
					System.out
							.println("(defparameter " + sym + " " + val + ")");
				else
					System.out.println("(setq " + sym + " " + val + ")");

				System.out.println(sym);
				System.out.println();
				System.out.println("```java");
				System.out.println(var + " = " + val);
				System.out.println("```\n");
			}
		}

		return true;
	}

	/**
	 * @param clas
	 * @return
	 */
	public static Boolean InstMethods(String cls, Class<?> clas)
	{
		Method[] meth = clas.getMethods();
		for (Method m : meth)
		{
			// Get method data
			Static stat = m.getAnnotation(Static.class);
			Function f = m.getAnnotation(Function.class);
			if (stat == null && f == null)
			{
				// Test for non declared
				if (m.getName().matches("[A-Z_\\*\\%]*"))
				{
					if (m.getDeclaringClass().getSimpleName().startsWith("t"))
						// we are in a type definition
						System.out.println("NON DECLARED LISP METHOD : "
								+ m.getName());

					else if ((m.getModifiers() & Modifier.STATIC) != 0)
						// we are in a class static definition
						System.out
								.println("NON DECLARED LISP cCOMPILED_METHOD : "
										+ m.getDeclaringClass()
										+ " "
										+ clas
										+ " " + m.getName());
				}
				continue;
			}

			SetF setf = m.getAnnotation(SetF.class);
			SpecialOp special = m.getAnnotation(SpecialOp.class);
			Mac prefix = m.getAnnotation(Mac.class);
			Annotation[][] notes = m.getParameterAnnotations();

			tFUNCTION func = null;
			tSYMBOL sym = null;
			if (stat != null)
			{
				if (special == null)
					// Static normal function
					func = new cCOMPILED_METHOD(clas, m.getName(),
							argsDecl(notes), stat.doc(), declareArgs());
				else
					// Static normal function
					func = new cCOMPILED_SPECIAL(clas, m.getName(),
							argsDecl(notes), stat.doc(), declareArgs());
				writeMissing(m.getName(), notes);
				func.setFuncName(sym = sym(stat.name()).SET_SYMBOL_FUNCTION(
						func));
			}
			else if (f != null)
			{
				// Object primitive
				func = new cCOMPILED_FUNCTION(clas, m.getName(),
						argsDecl(notes), f.doc(), declareArgs());
				func.setBaseArg(noArgsBase(notes));
				writeMissing(m.getName(), notes);
				func.setFuncName(sym = sym(f.name()).SET_SYMBOL_FUNCTION(func));
			}

			// define setf function
			if (sym != null && setf != null)
			{
				sym.SET_GET(setfKey, sym(setf.name()));
			}

			// write declare
			// TODO move as function of cAPI
			if (func != null && m.toString().contains(cls + "." + m.getName()))
			{
				System.out.println(((cFUNCTION) func).api.getLispDeclare());
				System.out.println();
				if (prefix != null)
				{
					func.setPrefix(prefix.prefix());
					System.out.println(prefix.prefix() + "x -> (" + m.getName()
							+ " x)");
					System.out.println();
				}
				System.out.println("```java");
				System.out.println(m.toString().replace(cls + ".", "")
						.replaceAll("aloyslisp.core.", "")
						.replaceAll("functions.", "").replaceAll("math.", "")
						.replaceAll("plugs.", "").replaceAll("sequences.", "")
						.replaceAll("streams.", "").replaceAll("packages.", "")
						.replaceAll("java.lang.", "")
						.replaceAll("#<cPACKAGE system>::", ""));
				System.out.println("```\n");
			}
		}

		return true;
	}

	/**
	 * @param notes
	 * @return
	 */
	private static tLIST declareArgs()
	{
		return NIL;
	}

	/**
	 * @param notes
	 * @return
	 */
	private static void writeMissing(String name, Annotation[][] notes)
	{
		for (Annotation[] note : notes)
		{
			if (note.length == 0)
			{
				System.out.println("Args not defined in " + name);
				break;
			}
		}
	}

	/**
	 * @param notes
	 * @return
	 */
	private static tLIST argsDecl(Annotation[][] notes)
	{
		tLIST res = NIL;
		res = (tLIST) res.APPEND(argsBase(notes, Arg.class, ""));
		res = (tLIST) res.APPEND(argsBase(notes, Opt.class, "&optional"));
		res = (tLIST) res.APPEND(argsBase(notes, Rest.class, "&rest"));
		res = (tLIST) res.APPEND(argsBase(notes, Key.class, "&key"));
		return res;
	}

	/**
	 * @param notes
	 * @param type
	 * @param prefix
	 * @return
	 */
	private static tLIST argsBase(Annotation[][] notes,
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
					if (a instanceof Arg)
					{
						arg = sym(((Arg) a).name());
						if (call)
							arg = unquote(arg);
					}
					else if (a instanceof Opt)
					{
						arg = sym(((Opt) a).name());
						if (call)
							arg = unquote(arg);
						else
							arg = list(arg).APPEND(
									list(getDefault(((Opt) a).def())));
					}
					else if (a instanceof Key)
					{
						arg = sym(((Key) a).name());
						if (call)
							arg = unquote(arg);
						else
							arg = list(arg).APPEND(
									list(getDefault(((Key) a).def())));
					}
					else if (a instanceof Rest)
					{
						arg = sym(((Rest) a).name());
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
	private static tT getDefault(String def)
	{
		if (def.equals(""))
			return NIL;

		return MAKE_STRING_INPUT_STREAM(def, NIL, NIL).READ(null, false, NIL,
				false);
	}

	/**
	 * @param notes
	 * @return
	 */
	private static Integer noArgsBase(Annotation[][] notes)
	{
		int base = 0;

		for (Annotation[] an : notes)
		{
			for (Annotation a : an)
			{
				if (a instanceof Arg || a instanceof Opt || a instanceof Key
						|| a instanceof Rest)
				{
					base++;
				}
				else if (a instanceof BaseArg)
				{
					return base;
				}
			}
		}

		// First arg is discarded
		return -1;
	}

}
