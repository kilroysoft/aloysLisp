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
// IP 18 nov. 2010 Creation
// IP UB19 Mod commentaries
// IP UB19 Add annotation tags
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import java.lang.annotation.*;
import java.lang.reflect.*;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.annotations.*;
import aloyslisp.core.common.*;
import aloyslisp.core.types.*;

/**
 * Primitives
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class Primitives
{

	// STRING ???? //////////////////////////////////////////////////
	/**
	 * @param message
	 * @param args
	 * @return
	 */
	@Global(name = "error")
	public static String ERROR( //
			@Arg(name = "mess") String message, //
			@Rest(name = "args") tT... args)
	{

		String err = FORMAT(message, args);
		throw new LispException(err);
	}

	/**
	 * @param format
	 * @param args
	 * @return
	 */
	@Global(name = "format")
	public static String FORMAT( //
			@Arg(name = "format") String format, //
			@Rest(name = "args") tT... args)
	{
		// This is a basic format with ony ~S and ~s tags
		String tmpl = format.replaceAll("~[sS]", "%s");
		return String.format(tmpl, (Object[]) args);
	}

	// CHARACTER //////////////////////////////////////////////////////
	/**
	 * @param name
	 * @return
	 */
	@Global(name = "name-char")
	public static tCHARACTER NAME_CHAR( //
			@Arg(name = "name") tT name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	// PACKAGES //////////////////////////////////////////////
	/**
	 * (FIND-PACKAGE name)
	 * 
	 * @param pack
	 * @return
	 */
	@Global(name = "name-char")
	public static tT FIND_PACKAGE( //
			@Arg(name = "pack") tT pack)
	{
		if (pack == null || pack == NIL)
			return currPackage();
		if (pack instanceof tPACKAGE)
			return pack;
		if (pack instanceof tSYMBOL)
			pack = ((tSYMBOL) pack).SYMBOL_NAME();
		if (!(pack instanceof tSTRING))
			return NIL;
		tT packN = packages.get(((tSTRING) pack).getString());
		if (packN == null)
			ERROR("FIND-PACKAGE : Package inconnu : ~s", pack);
		return ((tSYMBOL) packN).SYMBOL_VALUE();
	}

	// LIST //////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////
	// Constructor
	// ////////////////////////////////////////////////////////////////////////////
	/**
	 * @param list
	 * @return
	 */
	@Global(name = "list")
	public static tLIST LIST( //
			@Rest(name = "list") tT... list)
	{
		return new CONS(list);
	}

	/**
	 * @param car
	 * @param cdr
	 * @return
	 */
	@Global(name = "cons")
	public static tLIST CONS( //
			@Arg(name = "car") tT car, //
			@Arg(name = "cdr") tT cdr)
	{
		return new CONS(car, cdr);
	}

	/**
	 * Read all lisp functions of the class and create appropriate
	 * package entry
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Global(name = "%instantiate")
	public static boolean INSTANTIATE( //
			@Arg(name = "class") String cls)
	{
		// Search method
		Class<?> c;
		try
		{
			c = (Class<?>) Class.forName(cls);
		}
		catch (ClassNotFoundException e1)
		{
			ERROR("%%global : System error ~s not found", str(cls));
			return false;
		}

		Method[] meth = c.getMethods();
		for (Method m : meth)
		{
			Annotation[] an = m.getAnnotations();
			Global g = m.getAnnotation(Global.class);
			Primitive p = m.getAnnotation(Primitive.class);
			tCONS cmd;
			if (g != null)
			{
				cmd = list(sym(
						"defun",
						g.name(),
						args(),
						g.doc(),
						declare(),
						list("%primitive", list("quote", g.name())).append(
								args())));
			}
			else if (p != null)
			{
				cmd = list(sym(
						"defun",
						g.name(),
						args(),
						g.doc(),
						declare(),
						list("%global", list("quote", g.name())).append(
								args())));
			}
			else
				continue;

			Class<?>[] paramTypes = m.getParameterTypes();
			Annotation[][] notes = m.getParameterAnnotations();

			// Test arguments number, type is always tT
			// boolean va = m.isVarArgs();
			// int l = paramTypes.length;

			// Transform arguments
			int i = 0;
			for (Class<?> classArg : paramTypes)
			{
				// boolean ia = classArg.isArray();
				System.out.println("Argument " + classArg.getSimpleName());
				System.out.println("Parameter annotations");
				System.out.println(writeAnnotation(notes[i]));
				i++;
			}
		}
		return true;
	}

	/**
	 * @param ann
	 * @return
	 */
	private static String writeAnnotation(Annotation[] ann)
	{
		String res = "";

		for (Annotation an : ann)
		{
			res += an.toString() + "\n";
		}

		return res;
	}

}
