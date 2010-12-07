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

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	@Static(name = "error")
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
	@Static(name = "format")
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
	@Static(name = "name-char")
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
	@Static(name = "name-char")
	public static tT FIND_PACKAGE( //
			@Arg(name = "pack") tT pack)
	{
		if (pack == null || pack == NIL)
			return currPackage();
		if (pack instanceof tPACKAGE)
			return pack;
		if (!(pack instanceof tSTRING) && !(pack instanceof tSYMBOL))
			return NIL;

		tT packN = packages.get(((tSYMBOL) pack).SYMBOL_NAME());
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
	@Static(name = "list")
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
	@Static(name = "cons")
	public static tLIST CONS( //
			@Arg(name = "car") tT car, //
			@Arg(name = "cdr") tT cdr)
	{
		return new CONS(car, cdr);
	}

	/**
	 * @return
	 */
	@Static(name = "load")
	public tT[] IMPL(@Arg(name = "file") tT file, //
			@Opt(name = "verbose", def = "t") Boolean verbose, //
			@Opt(name = "print", def = "t") Boolean print, //
			@Opt(name = "not-exists", def = "nil") Boolean notExists)
	{
		String name;
		tINPUT_STREAM in;

		if (file instanceof tINPUT_STREAM)
		{
			in = (tINPUT_STREAM) file;
			name = in.printable();
		}
		else
		{
			if (file instanceof tSTRING)
				name = ((tSTRING) file).getString();
			else if (file instanceof tSYMBOL)
				name = ((tSYMBOL) file).SYMBOL_NAME();
			else
			{
				throw new LispException(
						"Filename should be a string or an atom");
			}

			try
			{
				in = new INPUT_STREAM(new FileInputStream(name));
			}
			catch (FileNotFoundException e)
			{
				if (notExists)
				{
					return new tT[]
					{ NIL };
				}
				throw new LispException("Error opening " + name + " "
						+ e.getLocalizedMessage());
			}
		}

		if (verbose)
		{
			System.out.println("; Loading contents of file " + name);
		}

		// while there's something to read
		try
		{
			tT[] res;
			for (;;)
			{
				// read it
				res = new tT[]
				{ INPUT_STREAM.READ(in, false, NIL, false) };

				if (verbose)
				{
					System.out.println("; lisp>" + res[0]);
				}

				// and evaluate it
				// System.out.println("eval : " + res[0]);
				res = res[0].EVAL();

				if (print)
					for (tT cell : res)
					{
						System.out.println("; " + cell);
					}
			}
		}
		catch (EOFException e)
		{
			// End of file
		}
		catch (LispException e)
		{
			// Lisp error transfer
			throw e;
		}
		catch (Exception e)
		{
			throw new LispException(e.getLocalizedMessage());
		}

		if (verbose)
		{
			System.out.println("; Finished loading " + name);
		}
		return new tT[]
		{ T };
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
	@Static(name = "instantiate")
	public static Boolean INSTANTIATE( //
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

			// Get method data
			Static s = m.getAnnotation(Static.class);
			Function f = m.getAnnotation(Function.class);
			Annotation[][] notes = m.getParameterAnnotations();

			tFUNCTION func;
			if (s != null)
			{
				// Static normal function
				func = new STATIC(Primitives.class, m.getName(),
						argsDecl(notes), s.doc(), declareArgs());
				sym(s.name()).SET_SYMBOL_FUNCTION(func);
			}
			else if (f != null)
			{
				// Object primitive
				func = new PRIMITIVE(c, m.getName(), (tLIST) list(sym("obj"))
						.APPEND(argsDecl(notes)), f.doc(), declareArgs());
				sym(f.name()).SET_SYMBOL_FUNCTION(func);
			}
			else
				continue;
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
							arg = list(arg).APPEND(list(((Opt) a).def()));
					}
					else if (a instanceof Key)
					{
						arg = sym(((Key) a).name());
						if (call)
							arg = unquote(arg);
						else
							arg = list(arg).APPEND(list(((Key) a).def()));
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
}
