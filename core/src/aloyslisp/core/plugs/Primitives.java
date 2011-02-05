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
import aloyslisp.core.conditions.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.sequences.*;
import aloyslisp.core.streams.*;

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
	@Static(name = "string", doc = "f_string")
	public static String STRING( //
			@Arg(name = "mess") tSTRING_DESIGNATOR str)
	{
		if (str instanceof tSTRING)
			return ((STRING) str).getString();
		if (str instanceof tSYMBOL)
			return ((tSYMBOL) str).SYMBOL_NAME();
		if (str instanceof tCHARACTER)
			return "" + ((CHARACTER) str).getChar();
		throw new LispException("Type error");
	}

	/**
	 * @param message
	 * @param args
	 * @return
	 */
	@Static(name = "error", doc = "f_error")
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
	@Static(name = "format", doc = "f_format")
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
	@Static(name = "name-char", doc = "f_name_c")
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
	@Static(name = "find-package")
	public static tT FIND_PACKAGE( //
			@Arg(name = "pack") tPACKAGE_DESIGNATOR pack)
	{
		if (pack == null || pack == NIL)
			return currPackage();
		if (pack instanceof tPACKAGE)
			return pack;
		if (!(pack instanceof tSTRING_DESIGNATOR))
			throw new LispException("Type error for " + pack);

		tT packN = packages.get(STRING((tSTRING_DESIGNATOR) pack));
		if (packN == null)
			throw new LispException("FIND-PACKAGE : Package inconnu : " + pack);

		return ((tSYMBOL) packN).SYMBOL_VALUE();
	}

	// PATHNAME //////////////////////////////////////////////
	/**
	 * @param path
	 * @return
	 */
	@Static(name = "pathname" doc="f_pn")
	public static tPATHNAME PATHNAME(tPATHNAME_DESIGNATOR path)
	{
		if (path instanceof tPATHNAME)
			return (tPATHNAME) path;

		if (!(path instanceof tSTRING_DESIGNATOR))
			throw new LispException("Type error for " + path);

		return new PATHNAME(STRING(path));
	}

	// LIST //////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////
	// Constructor
	// ////////////////////////////////////////////////////////////////////////////
	/**
	 * @param list
	 * @return
	 */
	@Static(name = "list", doc = "f_list")
	public static tLIST LIST( //
			@Rest(name = "list") Object... list)
	{
		return new CONS(list);
	}

	/**
	 * @param car
	 * @param cdr
	 * @return
	 */
	@Static(name = "cons", doc = "f_cons")
	public static tLIST CONS( //
			@Arg(name = "car") tT car, //
			@Arg(name = "cdr") tT cdr)
	{
		return new CONS(car, cdr);
	}

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

		// test if class is a type or a class
		Boolean type = (clas.getModifiers() & Modifier.INTERFACE) != 0;
		Method[] meth = clas.getMethods();
		for (Method m : meth)
		{

			// Get method data
			Special special = m.getAnnotation(Special.class);
			Mac prefix = m.getAnnotation(Mac.class);
			Static stat = m.getAnnotation(Static.class);
			Function f = m.getAnnotation(Function.class);
			Annotation[][] notes = m.getParameterAnnotations();

			tFUNCTION func;
			if (stat != null)
			{
				if (special == null)
					// Static normal function
					func = new STATIC(clas, m.getName(), argsDecl(notes),
							stat.doc(), declareArgs());
				else
					// Static normal function
					func = new SPECIAL_OPERATOR(clas, m.getName(),
							argsDecl(notes), stat.doc(), declareArgs());
				writeMissing(m.getName(), notes);
				sym(stat.name()).SET_SYMBOL_FUNCTION(func);
			}
			else if (f != null)
			{
				// Object primitive
				func = new PRIMITIVE(clas, m.getName(), argsDecl(notes),
						f.doc(), declareArgs());
				func.setBaseArg(noArgsBase(notes));
				writeMissing(m.getName(), notes);
				sym(f.name()).SET_SYMBOL_FUNCTION(func);
			}
			else
			{
				if (m.getName().matches("[A-Z_\\*\\%]*"))
				{
					if (type)
						System.out.println("NON DECLARED LISP METHOD : "
								+ m.getName());
					else if ((m.getModifiers() & Modifier.STATIC) != 0)
						System.out.println("NON DECLARED LISP STATIC : "
								+ m.getDeclaringClass() + " " + clas + " "
								+ m.getName());
				}

				continue;
			}

			if (prefix != null)
				func.setPrefix(prefix.prefix());
			System.out.println(func);
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
							arg = list(arg).APPEND(list(sym(((Opt) a).def())));
					}
					else if (a instanceof Key)
					{
						arg = sym(((Key) a).name());
						if (call)
							arg = unquote(arg);
						else
							arg = list(arg).APPEND(list(sym(((Key) a).def())));
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
