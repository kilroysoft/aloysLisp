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
// IP 16 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp;

import aloyslisp.core.common.*;
import aloyslisp.core.exec.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * Base environment en global functions of Common Lisp
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class L extends Primitives
{
	/**
	 * 
	 */
	public static final String	clPath		= "aloyslisp.commonlisp";

	/**
	 * List of all packages
	 */
	public static tPACKAGE		packages	= new PACKAGE("Packages", false);

	/**
	 * Keywords
	 */
	public static tPACKAGE		key			= new PACKAGE("keyword", true);

	/**
	 * System implementation functions
	 */
	public static tPACKAGE		sys			= new PACKAGE("system", true);

	/**
	 * Main lisp functions
	 */
	public static tPACKAGE		cl			= new PACKAGE("common-lisp", true);

	/**
	 * Extensions
	 */
	public static tPACKAGE		ext			= new PACKAGE("ext", true);

	{
		packages.INTERN(str("common-lisp")).SETF_SYMBOL_VALUE(cl);
		packages.INTERN(str("lisp")).SETF_SYMBOL_VALUE(cl);
		packages.INTERN(str("cl")).SETF_SYMBOL_VALUE(cl);
		packages.INTERN(str("system")).SETF_SYMBOL_VALUE(sys);
		packages.INTERN(str("sys")).SETF_SYMBOL_VALUE(sys);
		packages.INTERN(str("keyword")).SETF_SYMBOL_VALUE(key);
		packages.INTERN(str("key")).SETF_SYMBOL_VALUE(key);
		packages.INTERN(str("ext")).SETF_SYMBOL_VALUE(ext);
	}
	/**
	 * Execution context for Closures
	 */
	public static Environment	e			= new Environment();

	/**
	 * Current Package
	 * Package should be be first hardwired
	 */
	public static tSYMBOL		sPACKAGEs	= cl.INTERN(str("*package*"))
													.setExported(true)
													.setSpecial(true);
	static
	{
		sPACKAGEs.SETF_SYMBOL_VALUE(cl);
	}

	/**
	 * Afterward we can get the current package here
	 */
	public static PACKAGE currPackage()
	{
		return (PACKAGE) sPACKAGEs.SYMBOL_VALUE();
	}

	/**
	 * Base definition of NIL
	 */
	public static NIL				NIL						= new NIL();
	static
	{
		// Nil is directly put in package
		currPackage().put("nil", NIL);
		NIL.setExported(true);
	}

	/*
	 * All standard streams
	 */
	private static final tSTREAM	in						= new INPUT_STREAM(
																	System.in);
	private static final tSTREAM	out						= new OUTPUT_STREAM(
																	System.out);
	private static final tSTREAM	err						= new OUTPUT_STREAM(
																	System.err);
	private static final tSTREAM	terminal				= in;
	private static final tSTREAM	query					= terminal;

	/*
	 * All standard streams variables
	 */
	public static tSYMBOL			standardInput			= sym(
																	"*standard-input*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			in);

	public static tSYMBOL			standardOutput			= sym(
																	"*standard-output*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			out);

	public static tSYMBOL			errorOutput				= sym(
																	"*error-output*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			err);

	public static tSYMBOL			traceOutput				= sym(
																	"*trace-output*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			out);

	public static tSYMBOL			queryIO					= sym("*query-io*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			query);

	public static tSYMBOL			debugIO					= sym("*debug-io*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			query);

	public static tSYMBOL			terminalIO				= sym(
																	"*terminal-io*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			terminal);

	public static tSYMBOL			T						= sym("T");
	static
	{
		T.SETF_SYMBOL_VALUE(T).setConstant(true).setExported(true);
	}

	/**
	 * Special variables for write
	 */
	public static tSYMBOL			printEscape				= sym(
																	"*print-escape*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printRadix				= sym(
																	"*print-radix*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printBase				= sym(
																	"*print-base*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			INTEGER.make(10));

	public static tSYMBOL			printCircle				= sym(
																	"*print-circle*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printPretty				= sym(
																	"*print-pretty*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printLevel				= sym(
																	"*print-level*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printLength				= sym(
																	"*print-Length*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printCase				= sym(
																	"*print-case*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			key("upcase"));

	public static tSYMBOL			printArray				= sym(
																	"*print-array*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printGensym				= sym(
																	"*print-gensym*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printReadably			= sym(
																	"*print-readably*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printRightMargin		= sym(
																	"*print-right-margin*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printMiserWidth			= sym(
																	"*print-misere-width*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printLines				= sym(
																	"*print-lines*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printPprintDispatch		= sym(
																	"*print-pprint-dispatch*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			readBase				= sym("*read-base*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			nInt(10));

	public static tSYMBOL			readDefaultFloatFormat	= sym(
																	"*read-default-float-format*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			sym("single-float"));

	public static tSYMBOL			readEval				= sym("*readEval*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			readSuppress			= sym(
																	"*read-suppress*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			NIL);

	static
	{
		packages.INTERN(str("common-lisp")).SETF_SYMBOL_VALUE(cl);
		packages.INTERN(str("lisp")).SETF_SYMBOL_VALUE(cl);
		packages.INTERN(str("cl")).SETF_SYMBOL_VALUE(cl);
		packages.INTERN(str("system")).SETF_SYMBOL_VALUE(sys);
		packages.INTERN(str("sys")).SETF_SYMBOL_VALUE(sys);
		packages.INTERN(str("keyword")).SETF_SYMBOL_VALUE(key);
		packages.INTERN(str("key")).SETF_SYMBOL_VALUE(key);
		packages.INTERN(str("ext")).SETF_SYMBOL_VALUE(ext);
		cl.loadClasses();
		sys.loadClasses();
	}

	public static tSYMBOL			readTable				= sym("*readtable*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			new READTABLE());

	public static tSYMBOL			lispTraceSuppress		= sym("*trace*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SETF_SYMBOL_VALUE(
																			NIL);

	/**
	 * Search through environment and dictionaries
	 * 
	 * @param name
	 * @return
	 */
	public static Symbol getAll(tSYMBOL name)
	{
		Symbol res = e.arg(name);

		if (res == null)
			res = e.read(name);

		return res;
	}

	/**
	 * Search in global environment
	 * 
	 * @param name
	 * @return
	 */
	public static tSYMBOL getGlobal(String name)
	{
		return currPackage().get(name);
	}

	/**
	 * Search in dynamic environment
	 * 
	 * @param name
	 * @return
	 */
	public static tT arg(tSYMBOL name)
	{
		Symbol atom = e.arg(name);
		if (atom == null)
		{
			throw new LispException("Argument " + name
					+ " not found in environment");
		}
		return atom.SYMBOL_VALUE();
	}

	/**
	 * Create a string cell
	 * 
	 * @param string
	 * @return
	 */
	public static tSTRING str(String string)
	{
		if (string == null)
			string = "";
		return new STRING(string);
	}

	/**
	 * Create a character cell
	 * 
	 * @param car
	 * @return
	 */
	public static tCHARACTER c(Character car)
	{
		return new CHARACTER(car);
	}

	/**
	 * Create a list from ana array
	 * 
	 * @param list
	 * @return
	 */
	public static tLIST list(Object... list)
	{
		return new CONS(list);
	}

	/**
	 * Create a cons
	 * 
	 * @param car
	 * @param cdr
	 * @return
	 */
	public static tLIST cons(tT car, tT cdr)
	{
		return new CONS(car, cdr);
	}

	/**
	 * Create a numeric cell
	 * 
	 * @param value
	 * @return
	 */
	public static INTEGER nInt(Number value)
	{
		return INTEGER.make(value);
	}

	/**
	 * @param value
	 * @return
	 */
	public static LONG nLong(Number value)
	{
		return LONG.make(value);
	}

	/**
	 * @param value
	 * @return
	 */
	public static FLOAT nFloat(Number value)
	{
		return FLOAT.make(value);
	}

	/**
	 * @param value
	 * @return
	 */
	public static DOUBLE nDouble(Number value)
	{
		return DOUBLE.make(value);
	}

	/**
	 * @param real
	 * @param imag
	 * @return
	 */
	public static COMPLEX nComplex(tNUMBER real, tNUMBER imag)
	{
		return COMPLEX.make(real, imag);
	}

	/**
	 * @param real
	 * @param imag
	 * @return
	 */
	public static COMPLEX nComplex(Number real, Number imag)
	{
		return COMPLEX.make(real, imag);
	}

	/**
	 * @param num
	 * @param den
	 * @return
	 */
	public static RATIO mRatio(tNUMBER num, tNUMBER den)
	{
		return RATIO.make(num, den);
	}

	/**
	 * @param num
	 * @param den
	 * @return
	 */
	public static RATIO mRatio(Number num, Number den)
	{
		return RATIO.make(num, den);
	}

	/**
	 * Create a boolean value
	 * 
	 * @param bool
	 * @return
	 */
	public static tT bool(boolean bool)
	{
		return bool ? T : NIL;
	}

	public static tSYMBOL sym(tSTRING name)
	{
		throw new LispException("mheu");
	}

	/**
	 * Create an symbol decoding package name
	 * 
	 * @param name
	 * @return
	 */
	public static tSYMBOL sym(String name)
	{
		if (name.endsWith(":"))
		{
			throw new LispException(
					"Bad formatted symbol name, can't finish with ':' : "
							+ name);
		}
		if (name.startsWith(":"))
		{
			return key.INTERN(str(name.substring(1)));
		}
		int pos = name.indexOf(":");
		if (pos > 0)
		{

			tSYMBOL packName = packages.get(name.substring(0, pos));
			if (packName == null)
			{
				throw new LispException("Package doesn't exist : "
						+ name.substring(0, pos));
			}
			tPACKAGE pack = (tPACKAGE) packName.SYMBOL_VALUE();
			if (pack == null)
			{
				throw new LispException("Package doesn't exist : "
						+ name.substring(0, pos));
			}

			if (name.charAt(pos + 1) == ':')
			{
				pos++;
			}
			return pack.INTERN(str(name.substring(pos + 1, name.length())));
		}
		else
			return currPackage().INTERN(str(name));
	}

	// /**
	// * Create an symbol in a package
	// *
	// * @param name
	// * @return
	// */
	// public static tSYMBOL sym(String pack, String name)
	// {
	// tT in = NIL.FIND_PACKAGE(str(pack));
	// if (in == null || in == NIL)
	// {
	// throw new LispException("Package " + pack + " not found");
	// }
	// return ()in.INTERN(str(name));
	// }

	/**
	 * Create an symbol in a package
	 * 
	 * @param name
	 * @return
	 */
	public static tSYMBOL sym(tT pack, String name)
	{
		tT in = FIND_PACKAGE(pack);
		if (in == null || in == NIL)
		{
			throw new LispException("Package " + pack + " not found");
		}
		return ((tPACKAGE) in).INTERN(str(name));
	}

	/**
	 * Create an keyword
	 * 
	 * @param name
	 * @return
	 */
	public static tSYMBOL key(String name)
	{
		return sym(key, name);
	}

	/**
	 * Test if cell is a list beginning with name
	 * 
	 * @param cell
	 * @param name
	 */
	public static boolean beginWith(tT cell, String name)
	{
		if (cell instanceof NIL)
			return false;
		if (!(cell instanceof tLIST))
			return false;
		tT atom = cell.CAR();
		if (!(atom instanceof tSYMBOL))
			return false;
		return ((tSYMBOL) atom).SYMBOL_NAME().getString()
				.equalsIgnoreCase(name);
	}

}
