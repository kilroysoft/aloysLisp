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

import java.lang.reflect.*;

import aloyslisp.annotations.*;
import aloyslisp.core.cCELL;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.functions.*;
import aloyslisp.core.packages.*;
import static aloyslisp.core.L.*;

/**
 * Library
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class Library extends cCELL
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
				sym.SET_SPECIAL(true);

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
					throw new LispException("Illegal argument " + clas + " : "
							+ e1.getLocalizedMessage());
				}
				catch (IllegalAccessException e1)
				{
					throw new LispException("Illegal access " + clas + " : "
							+ e1.getLocalizedMessage());
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
		System.out.println("class " + cls);
		Method[] meth = clas.getMethods();
		for (Method m : meth)
		{
			// TODO verify that m is not derivated from a base class...
			// Get method data
			Static stat = m.getAnnotation(Static.class);
			Function f = m.getAnnotation(Function.class);
			if (stat == null && f == null)
			{
				// Test for non declared
				if (m.getName().matches("[A-Z_ps]*"))
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

			tSYMBOL sym = null;
			if (stat != null)
			{
				sym = sym(stat.name().toUpperCase());
			}
			else if (f != null)
			{
				sym = sym(f.name().toUpperCase());
			}
			else
				return false;

			tFUNCTION func = null;
			func = new cCOMPILED_FUNCTION(m);

			// add symbol function
			sym.SET_SYMBOL_FUNCTION(func);

			SetF setf = m.getAnnotation(SetF.class);
			if (setf != null)
				sym.SET_GET(setfKey, sym(setf.name()));

		}

		return true;
	}
}
