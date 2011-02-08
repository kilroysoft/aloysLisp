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
// IP UB20 Disconnected from Environment
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import static aloyslisp.packages.L.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import aloyslisp.core.annotations.Arg;
import aloyslisp.core.annotations.BaseArg;
import aloyslisp.core.annotations.Function;
import aloyslisp.core.annotations.Key;
import aloyslisp.core.annotations.Mac;
import aloyslisp.core.annotations.Opt;
import aloyslisp.core.annotations.Rest;
import aloyslisp.core.annotations.Special;
import aloyslisp.core.annotations.Static;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.exec.SymMap;
import aloyslisp.core.functions.cPRIMITIVE;
import aloyslisp.core.functions.cSPECIAL_OPERATOR;
import aloyslisp.core.functions.cSTATIC;
import aloyslisp.core.functions.tFUNCTION;
import aloyslisp.core.sequences.cSTRING;
import aloyslisp.core.sequences.tLIST;
import aloyslisp.core.sequences.tSTRING_DESIGNATOR;

/**
 * cPACKAGE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cPACKAGE extends cCELL implements tPACKAGE
{
	/**
	 * Package's name
	 */
	String			name;

	/**
	 * Use list
	 */
	SymMap			uses;

	/**
	 * Shadow variables
	 */
	SymMap			internal;

	/**
	 * Shadow variables
	 */
	public SymMap	external;

	/**
	 * Shadow variables
	 */
	SymMap			shadow;

	/**
	 * Case sensitivity
	 */
	boolean			caseSensitive	= false;

	/**
	 * Package constructor
	 * 
	 * @param name
	 *            Name of package
	 */
	public cPACKAGE(String name)
	{
		super();
		// trace = true;
		this.name = name;
		uses = new SymMap();
		internal = new SymMap();
		external = new SymMap();
		shadow = new SymMap();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#printable()
	 */
	@Override
	public String toString()
	{
		return "#<cPACKAGE " + name + ">";
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.collections.IPackage#isInUseList(aloyslisp.core.
	 * plugs.cCELL)
	 */
	@Override
	public boolean isInUseList(tPACKAGE pack)
	{
		for (String walk : uses)
		{
			if (walk == pack.PACKAGE_NAME())
				return true;

			if (((cPACKAGE) uses.get(walk)).isInUseList(pack))
				return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.tPACKAGE#dump()
	 */
	@Override
	public String dump()
	{
		// TODO Auto-generated method stub
		return null;
	}

	// ////////////////////////////////////////////////////////////////////
	// Static functions

	/**
	 * (FIND-cPACKAGE name)
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

		tT packN = packages.get(cSTRING.STRING((tSTRING_DESIGNATOR) pack));
		if (packN == null)
			throw new LispException("FIND-cPACKAGE : Package inconnu : " + pack);

		return ((tSYMBOL) packN).SYMBOL_VALUE();
	}

	// ////////////////////////////////////////////////////////////////////
	// Member functions
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.tPACKAGE#INTERN(java.lang.String,
	 * aloyslisp.core.plugs.tPACKAGE_DESIGNATOR)
	 */
	@Override
	public tSYMBOL INTERN(String symbol, tPACKAGE_DESIGNATOR pack)
	{
		tSYMBOL res[] = FIND_SYMBOL(symbol, null);
		if (res[1] != NIL)
			return res[0];

		return internal.put(symbol, new cSYMBOL(symbol, this));
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.tPACKAGE#UNINTERN(java.lang.String,
	 * aloyslisp.core.plugs.tPACKAGE_DESIGNATOR)
	 */
	@Override
	public tSYMBOL UNINTERN(String symbol, tPACKAGE_DESIGNATOR pack)
	{
		tSYMBOL res;
		if ((res = external.get(symbol)) != null)
		{
			if (pack != res.SYMBOL_PACKAGE())
			{
				external.remove(symbol);
				shadow.remove(symbol);
				return T;
			}

			// Test if conflict
			if (shadow.get(symbol) != NIL)
				throw new LispException("Correctable error shadow conflict");

			res.SET_SYMBOL_PACKAGE(null);
			return NIL;
		}
		else if ((res = internal.get(symbol)) != null)
		{
			if (pack != res.SYMBOL_PACKAGE())
			{
				internal.remove(symbol);
				shadow.remove(symbol);
				return T;
			}

			// Test if conflict
			if (shadow.get(symbol) != NIL)
				throw new LispException("Correctable error shadow conflict");

			res.SET_SYMBOL_PACKAGE(null);
			return NIL;
		}
		return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.tPACKAGE#FIND_SYMBOL(java.lang.String,
	 * aloyslisp.core.plugs.tPACKAGE_DESIGNATOR)
	 */
	@Override
	public tSYMBOL[] FIND_SYMBOL(String symbol, tPACKAGE_DESIGNATOR pack)
	{
		tSYMBOL res = shadow.get(symbol);
		if (res != null || (res = external.get(symbol)) != null)
		{
			return new tSYMBOL[]
			{ res, EXTERNAL };
		}
		else if ((res = internal.get(symbol)) != null)
		{
			return new tSYMBOL[]
			{ res, INTERNAL };
		}
		else
		{
			for (tSYMBOL pac : uses.values())
			{
				tT p = pac.SYMBOL_VALUE();
				res = ((cPACKAGE) p).shadow.get(symbol);
				if (res != null)
				{
					return new tSYMBOL[]
					{ res, INHERITED };
				}
				res = ((cPACKAGE) p).external.get(symbol);
				if (res != null)
				{
					return new tSYMBOL[]
					{ res, INHERITED };
				}
			}
		}
		return new tSYMBOL[]
		{ NIL, NIL };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.tPACKAGE#IMPORT(java.lang.String,
	 * aloyslisp.core.plugs.tPACKAGE_DESIGNATOR)
	 */
	@Override
	public tSYMBOL IMPORT(tT symbol, tPACKAGE_DESIGNATOR pack)
	{
		if (symbol instanceof tLIST)
		{
			for (tT walk : (tLIST) symbol)
			{
				if (!(walk.CAR() instanceof tSYMBOL))
					throw new LispException("Not a symbol");

				tSYMBOL sym = (tSYMBOL) walk.CAR();
				imp(sym, pack);
			}
		}
		else if (symbol instanceof tSYMBOL)
		{
			imp((tSYMBOL) symbol, pack);
		}
		else
			throw new LispException("Bad type for import");

		return T;
	}

	/**
	 * @param symbol
	 * @param pack
	 */
	private void imp(tSYMBOL symbol, tPACKAGE_DESIGNATOR pack)
	{
		tSYMBOL[] sym = this.FIND_SYMBOL(symbol.SYMBOL_NAME(), null);

		if (sym[1] == INTERNAL || sym[1] == EXTERNAL)
		{
			if (sym[0].SYMBOL_PACKAGE() == symbol.SYMBOL_PACKAGE()
					&& sym[0] != symbol)
				throw new LispException("Correctable symbol discrepency");
			if (sym[0].SYMBOL_PACKAGE() == NIL)
			{
				symbol.SET_SYMBOL_PACKAGE(this);
				shadow.put(symbol.SYMBOL_NAME(), symbol);
			}
			else if (sym[0].SYMBOL_PACKAGE() != symbol.SYMBOL_PACKAGE())
			{
				shadow.put(symbol.SYMBOL_NAME(), symbol);
			}
		}
		else
		{
			if (symbol.SYMBOL_PACKAGE() == NIL)
				symbol.SET_SYMBOL_PACKAGE(this);
			internal.put(symbol.SYMBOL_NAME(), symbol);
		}
		return;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.collections.IPackage#getName()
	 */
	@Override
	public String PACKAGE_NAME()
	{
		return name;
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
					func = new cSTATIC(clas, m.getName(), argsDecl(notes),
							stat.doc(), declareArgs());
				else
					// Static normal function
					func = new cSPECIAL_OPERATOR(clas, m.getName(),
							argsDecl(notes), stat.doc(), declareArgs());
				writeMissing(m.getName(), notes);
				sym(stat.name()).SET_SYMBOL_FUNCTION(func);
			}
			else if (f != null)
			{
				// Object primitive
				func = new cPRIMITIVE(clas, m.getName(), argsDecl(notes),
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
						System.out.println("NON DECLARED LISP cSTATIC : "
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
