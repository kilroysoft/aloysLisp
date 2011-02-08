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

package aloyslisp.packages;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

import aloyslisp.core.conditions.LispException;
import aloyslisp.core.exec.*;
import aloyslisp.core.math.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.sequences.*;
import aloyslisp.core.streams.*;

/**
 * Base environment en global functions of Common Lisp
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class L
{
	/**
	 * 
	 */
	public static final String	clPath		= "aloyslisp.packages";

	/**
	 * List of all packages
	 */
	public static SymMap		packages	= new SymMap();

	/**
	 * Keywords
	 */
	public static tPACKAGE		key			= new cPACKAGE("keyword");

	/**
	 * System implementation functions
	 */
	public static tPACKAGE		sys			= new cPACKAGE("system");

	/**
	 * Main lisp functions
	 */
	public static tPACKAGE		cl			= new cPACKAGE("common-lisp");

	/**
	 * Extensions
	 */
	public static tPACKAGE		ext			= new cPACKAGE("ext");

	/**
	 * Execution context for Closures
	 */
	public static Environment	e			= new Environment();

	static
	{
		packages.put("common-lisp",
				new cSYMBOL("common-lisp").SET_SYMBOL_VALUE(cl));
		packages.put("cl", new cSYMBOL("cl").SET_SYMBOL_VALUE(cl));
		packages.put("lisp", new cSYMBOL("lisp").SET_SYMBOL_VALUE(cl));
		packages.put("system", new cSYMBOL("system").SET_SYMBOL_VALUE(sys));
		packages.put("sys", new cSYMBOL("sys").SET_SYMBOL_VALUE(sys));
		packages.put("keyword", new cSYMBOL("keyword").SET_SYMBOL_VALUE(key));
		packages.put("key", new cSYMBOL("key").SET_SYMBOL_VALUE(key));
	}
	/**
	 * Current Package
	 * *package* should be be first hardwired, because it's used to intern
	 * symbols....
	 */
	public static tSYMBOL		sPACKAGEs	= new cSYMBOL("*package*", cl)
													.setExported(true)
													.setSpecial(true)
													.SET_SYMBOL_VALUE(cl);

	static
	{
		((cPACKAGE) cl).external.put("*package*", sPACKAGEs);
	}

	/**
	 * Afterward we can get the current package here
	 */
	public static cPACKAGE currPackage()
	{
		return (cPACKAGE) sPACKAGEs.SYMBOL_VALUE();
	}

	/**
	 * Base definition of cNIL
	 */
	public static final cNIL		NIL						= new cNIL();
	static
	{
		// Nil is directly put in package
		((cPACKAGE) currPackage()).external.put("nil", NIL);
		NIL.setExported(true);
	}

	/**
	 * Base keys for package symbol postition
	 */
	public static final tSYMBOL		INHERITED				= key("INHERITED");

	public static final tSYMBOL		EXTERNAL				= key("EXTERNAL");

	public static final tSYMBOL		INTERNAL				= key("INTERNAL");

	public static final tSYMBOL		T						= sym("T");
	static
	{
		T.SET_SYMBOL_VALUE(T).setConstant(true).setExported(true);
	}

	/*
	 * All standard streams
	 */
	private static final tSTREAM	in						= new cFILE_INPUT_STREAM(
																	System.in);
	private static final tSTREAM	out						= new cFILE_OUTPUT_STREAM(
																	System.out);
	private static final tSTREAM	err						= new cFILE_OUTPUT_STREAM(
																	System.err);
	private static final tSTREAM	terminal				= in;
	private static final tSTREAM	query					= terminal;

	// Key for setf in PLIST
	public static final tSYMBOL		setfKey					= key("setf-func");

	// Key for symbol type in PLIST
	public static final tSYMBOL		typeClass				= key("type-class");

	/*
	 * All standard streams variables
	 */
	public static tSYMBOL			standardInput			= sym(
																	"*standard-input*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			in);

	public static tSYMBOL			standardOutput			= sym(
																	"*standard-output*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			out);

	public static tSYMBOL			errorOutput				= sym(
																	"*error-output*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			err);

	public static tSYMBOL			traceOutput				= sym(
																	"*trace-output*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			out);

	public static tSYMBOL			queryIO					= sym("*query-io*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			query);

	public static tSYMBOL			debugIO					= sym("*debug-io*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			query);

	public static tSYMBOL			terminalIO				= sym(
																	"*terminal-io*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			terminal);

	/**
	 * Special variables for write
	 */
	public static tSYMBOL			printEscape				= sym(
																	"*print-escape*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printRadix				= sym(
																	"*print-radix*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printBase				= sym(
																	"*print-base*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			nInt(10));

	public static tSYMBOL			printCircle				= sym(
																	"*print-circle*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printPretty				= sym(
																	"*print-pretty*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printLevel				= sym(
																	"*print-level*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printLength				= sym(
																	"*print-Length*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printCase				= sym(
																	"*print-case*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			key("upcase"));

	public static tSYMBOL			printArray				= sym(
																	"*print-array*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printGensym				= sym(
																	"*print-gensym*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printReadably			= sym(
																	"*print-readably*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printRightMargin		= sym(
																	"*print-right-margin*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printMiserWidth			= sym(
																	"*print-misere-width*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printLines				= sym(
																	"*print-lines*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printPprintDispatch		= sym(
																	"*print-pprint-dispatch*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			readBase				= sym("*read-base*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			nInt(10));

	public static tSYMBOL			readDefaultFloatFormat	= sym(
																	"*read-default-float-format*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			sym("single-float"));

	public static tSYMBOL			readEval				= sym("*readEval*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			readSuppress			= sym(
																	"*read-suppress*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	// static
	// {
	// packages.INTERN("common-lisp").SET_SYMBOL_VALUE(cl);
	// packages.INTERN("lisp").SET_SYMBOL_VALUE(cl);
	// packages.INTERN("cl").SET_SYMBOL_VALUE(cl);
	// packages.INTERN("system").SET_SYMBOL_VALUE(sys);
	// packages.INTERN("sys").SET_SYMBOL_VALUE(sys);
	// packages.INTERN("keyword").SET_SYMBOL_VALUE(key);
	// packages.INTERN("key").SET_SYMBOL_VALUE(key);
	// packages.INTERN("ext").SET_SYMBOL_VALUE(ext);
	// }

	public static tSYMBOL			readTable				= sym("*readtable*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
																			new cREADTABLE());

	public static tSYMBOL			lispTraceSuppress		= sym("*trace*")
																	.setExported(
																			true)
																	.setSpecial(
																			true)
																	.SET_SYMBOL_VALUE(
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
		tSYMBOL[] res = currPackage().FIND_SYMBOL(name, null);
		if (res[1] == NIL)
			return null;
		else
			return res[0];
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
		return new cSTRING(string);
	}

	/**
	 * Create a character cell
	 * 
	 * @param car
	 * @return
	 */
	public static tCHARACTER c(Character car)
	{
		return new cCHARACTER(car);
	}

	/**
	 * Create a list from an array
	 * 
	 * @param list
	 * @return
	 */
	public static tLIST list(Object... list)
	{
		if (list.length == 0)
			return NIL;

		return new cCONS(list);
	}

	/**
	 * Create a list from an array for declaration
	 * 
	 * @param list
	 * @return
	 */
	public static tLIST decl(Object... list)
	{
		return new cCONS(true, list);
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
		return new cCONS(car, cdr);
	}

	/**
	 * Create a numeric cell
	 * 
	 * @param value
	 * @return
	 */
	public static cINTEGER nInt(Integer value)
	{
		return new cBIGNUM(value);
	}

	/**
	 * Create a numeric cell
	 * 
	 * @param value
	 * @return
	 */
	public static cBIGNUM nInt(Long value)
	{
		return new cBIGNUM(value);
	}

	/**
	 * @param value
	 * @return
	 */
	public static cSINGLE_FLOAT nFloat(Float value)
	{
		return new cSINGLE_FLOAT(value);
	}

	/**
	 * @param value
	 * @return
	 */
	public static cDOUBLE_FLOAT nDouble(Double value)
	{
		return new cDOUBLE_FLOAT(value);
	}

	/**
	 * @param value
	 * @return
	 */
	public static cSHORT_FLOAT nShort(Short value)
	{
		return new cSHORT_FLOAT(value);
	}

	/**
	 * @param real
	 * @param imag
	 * @return
	 */
	public static cCOMPLEX nComplex(tREAL real, tREAL imag)
	{
		return new cCOMPLEX(real, imag);
	}

	/**
	 * @param num
	 * @param den
	 * @return
	 */
	public static cRATIO mRatio(tNUMBER num, tNUMBER den)
	{
		return new cRATIO(num.getIntegerValue(), den.getIntegerValue());
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
			return key.INTERN(name.substring(1), null);
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
			return pack.INTERN(name.substring(pos + 1, name.length()), null);
		}
		else
			return currPackage().INTERN(name, null);
	}

	// /**
	// * Create an symbol in a package
	// *
	// * @param name
	// * @return
	// */
	// public static tSYMBOL sym(String pack, String name)
	// {
	// tT in = cNIL.FIND_PACKAGE(str(pack));
	// if (in == null || in == cNIL)
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
	public static tSYMBOL sym(tPACKAGE_DESIGNATOR pack, String name)
	{
		tT p = cPACKAGE.FIND_PACKAGE(pack);
		if (p == null || p == NIL)
		{
			throw new LispException("Package " + pack + " not found");
		}
		return ((tPACKAGE) p).INTERN(name, null);
	}

	/**
	 * Create an keyword
	 * 
	 * @param name
	 * @return
	 */
	public static tSYMBOL key(String name)
	{
		return ((cPACKAGE) key).external.put(name,
				new cSYMBOL(name).setConstant(true));
	}

	/**
	 * Quote the form
	 * 
	 * @param form
	 * @return
	 */
	public static tLIST quote(Object form)
	{
		return decl("lisp::quote", form);
	}

	/**
	 * Unquote the form
	 * 
	 * @param form
	 * @return
	 */
	public static tLIST unquote(Object form)
	{
		return decl("sys::%unquote", form);
	}

	/**
	 * Backquote the form
	 * 
	 * @param form
	 * @return
	 */
	public static tLIST backquote(Object form)
	{
		return decl("sys::%backquote", form);
	}

	/**
	 * splice the form
	 * 
	 * @param form
	 * @return
	 */
	public static tLIST splice(Object form)
	{
		return decl("sys::%splice", form);
	}

	/**
	 * Test if cell is a list beginning with name
	 * 
	 * @param cell
	 * @param name
	 */
	public static boolean beginWith(tT cell, String name)
	{
		if (cell instanceof cNIL)
			return false;
		if (!(cell instanceof tLIST))
			return false;
		tT atom = cell.CAR();
		if (!(atom instanceof tSYMBOL))
			return false;
		return ((tSYMBOL) atom).SYMBOL_NAME().equalsIgnoreCase(name);
	}

	/**
	 * 
	 */
	public static void loadClasses(String pkg)
	{
		System.out.println("Loading package " + pkg);
		System.out.println("----------------------------------------");
		try
		{
			List<Class<?>> cla = getClasses(pkg);
			for (Class<?> clas : cla)
			{
				System.out.println("(INSTANCIATE " + clas.getCanonicalName()
						+ ")");
				cPACKAGE.INSTANTIATE(clas.getCanonicalName());
			}
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param pckgname
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static List<Class<?>> getClasses(String pckgname)
			throws ClassNotFoundException, IOException
	{
		// Création de la liste qui sera retournée
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		// On récupère toutes les entrées du CLASSPATH
		String[] entries = System.getProperty("java.class.path").split(
				System.getProperty("path.separator"));

		// Pour toutes ces entrées, on verifie si elles contiennent
		// un répertoire ou un jar
		for (int i = 0; i < entries.length; i++)
		{

			if (entries[i].endsWith(".jar"))
			{
				classes.addAll((Collection<? extends Class<?>>) manageJar(
						entries[i], pckgname));
			}
			else
			{
				classes.addAll((Collection<? extends Class<?>>) manageDirectory(
						entries[i], pckgname));
			}

		}

		// ajout => analyse classpath de la webapp
		Enumeration<URL> eUrl = NIL.getClass().getClassLoader()
				.getResources(pckgname);
		for (; eUrl.hasMoreElements();)
		{
			URL url = eUrl.nextElement();
			String sUrl = url.toString();

			if (sUrl.contains(".jar!"))
			{
				sUrl = sUrl.substring(0, sUrl.indexOf("!"));
				classes.addAll((Collection<? extends Class<?>>) manageJar(sUrl,
						pckgname));
			}
			else
			{
				classes.addAll((Collection<? extends Class<?>>) manageDirectory(
						sUrl, pckgname));
			}

		}

		return classes;
	}

	/**
	 * @param repertoire
	 * @param pckgname
	 * @return
	 * @throws ClassNotFoundException
	 */
	private static Collection<Class<?>> manageDirectory(String repertoire,
			String pckgname) throws ClassNotFoundException
	{
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		// On génère le chemin absolu du package
		StringBuffer sb = new StringBuffer(repertoire);
		String[] repsPkg = pckgname.split("\\.");
		for (int i = 0; i < repsPkg.length; i++)
		{
			sb.append(System.getProperty("file.separator") + repsPkg[i]);
		}
		File rep = new File(sb.toString());

		// Si le chemin existe, et que c'est un dossier, alors, on le liste
		if (rep.exists() && rep.isDirectory())
		{
			// On filtre les entrées du répertoire
			FilenameFilter filter = new DotClassFilter();
			File[] liste = rep.listFiles(filter);

			// Pour chaque classe présente dans le package, on l'ajoute à la
			// liste
			for (int i = 0; i < liste.length; i++)
			{
				classes.add(Class.forName(pckgname + "."
						+ liste[i].getName().split("\\.")[0]));
			}
		}

		return classes;
	}

	/**
	 * @param jar
	 * @param pckgname
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static Collection<Class<?>> manageJar(String jar, String pckgname)
			throws IOException, ClassNotFoundException
	{
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		JarFile jfile = new JarFile(jar);
		String pkgpath = pckgname.replace(".", "/");

		// Pour chaque entrée du Jar
		for (Enumeration<JarEntry> entries = jfile.entries(); entries
				.hasMoreElements();)
		{
			JarEntry element = entries.nextElement();

			// all the .class in package and sub-package
			if (element.getName().startsWith(pkgpath)
					&& element.getName().endsWith(".class"))
			{
				// get
				String nomFichier = element.getName().substring(
						pckgname.length() + 1);

				Class<?> found = Class.forName(pckgname + "."
						+ nomFichier.split("\\.")[0]);

				classes.add(found);

			}

		}

		return classes;
	}

	/**
	 * Cette classe permet de filtrer les fichiers d'un répertoire. Il n'accepte
	 * que les fichiers .class.
	 */
	private static class DotClassFilter implements FilenameFilter
	{

		public boolean accept(File arg0, String arg1)
		{
			return arg1.endsWith(".class");
		}

	}

}
