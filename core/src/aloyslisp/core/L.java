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
// IP 9 mars 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

import aloyslisp.annotations.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.math.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import aloyslisp.core.streams.*;
import aloyslisp.internal.engine.*;

/**
 * L
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class L
{
	/**
	 * Execution context for Closures
	 */
	public static cTHREAD		e			= new cTHREAD();

	/**
	 * Current Package
	 * *package* should be be first hardwired, because it's used to api
	 * symbols....
	 */
	// public static tSYMBOL sPACKAGEs = new cSYMBOL("*package*", cPACKAGE.cl)
	// .setSpecial(true)
	// .SET_SYMBOL_VALUE(cPACKAGE.cl);
	//
	// static
	// {
	// ((cPACKAGE) cPACKAGE.cl).external.SET_GETHASH(sPACKAGEs,
	// str("*package*"));
	// }
	public static tSYMBOL		sPACKAGEs	= null;

	/**
	 * List of all packages
	 */
	public static tHASH_TABLE	packages	= cHASH_TABLE.MAKE_HASH_TABLE();

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

	static
	{
		if (e == null)
			e = new cTHREAD();

		packages.SET_GETHASH(cl, str("common-lisp"));
		packages.SET_GETHASH(cl, str("cl"));
		packages.SET_GETHASH(cl, str("lisp"));
		packages.SET_GETHASH(sys, str("system"));
		packages.SET_GETHASH(sys, str("sys"));
		packages.SET_GETHASH(key, str("keyword"));
		packages.SET_GETHASH(key, str("key"));
	}

	/**
	 * Afterward we can get the current package here
	 */
	public static tPACKAGE currPackage()
	{
		// System.out.println("Curr package = " + sPACKAGEs);
		if (sPACKAGEs == null)
			return cl;
		return (cPACKAGE) sPACKAGEs.SYMBOL_VALUE();
	}

	/**
	 * Base definition of cNULL
	 */
	public static final cNULL		NIL						= new cNULL();
	static
	{
		// Nil is directly put in package
		((cPACKAGE) currPackage()).external.SET_GETHASH(NIL, str("NIL"));
		sPACKAGEs = (tSYMBOL) ((cPACKAGE) currPackage()).external.SET_GETHASH(
				new cSYMBOL("*PACKAGE*", cl).SET_SYMBOL_VALUE(cl),
				str("*PACKAGE*"));
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
		T.SET_SYMBOL_VALUE(T).setConstant(true);
	}

	/*
	 * All standard streams
	 */
	@Symb(name = "+in+")
	public static tSTREAM			in						= new cFILE_STREAM(
																	System.in);
	private static final tSTREAM	out						= new cFILE_STREAM(
																	System.out);
	private static final tSTREAM	err						= new cFILE_STREAM(
																	System.err);
	private static final tSTREAM	terminal				= in;

	private static final tSTREAM	query					= terminal;

	// Key for setf in PLIST
	public static tSYMBOL			setfKey					= key("SETF-FUNC");

	// Key for symbol type in PLIST
	public static tSYMBOL			typeClass				= key("TYPE-CLASS");

	/*
	 * All standard streams variables
	 */
	public static tSYMBOL			standardInput			= sym(
																	"*standard-input*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			in);

	public static tSYMBOL			standardOutput			= sym(
																	"*standard-output*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			out);

	public static tSYMBOL			errorOutput				= sym(
																	"*error-output*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			err);

	public static tSYMBOL			traceOutput				= sym(
																	"*trace-output*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			out);

	public static tSYMBOL			queryIO					= sym("*query-io*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			query);

	public static tSYMBOL			debugIO					= sym("*debug-io*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			query);

	public static tSYMBOL			terminalIO				= sym(
																	"*terminal-io*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			terminal);

	/**
	 * SpecialOp variables for write
	 */
	public static tSYMBOL			printEscape				= sym(
																	"*print-escape*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printRadix				= sym(
																	"*print-radix*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printBase				= sym(
																	"*print-base*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			nInt(10));

	public static tSYMBOL			printCircle				= sym(
																	"*print-circle*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printPretty				= sym(
																	"*print-pretty*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printLevel				= sym(
																	"*print-level*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printLength				= sym(
																	"*print-Length*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printCase				= sym(
																	"*print-case*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			key("upcase"));

	public static tSYMBOL			printArray				= sym(
																	"*print-array*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printGensym				= sym(
																	"*print-gensym*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printReadably			= sym(
																	"*print-readably*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			printRightMargin		= sym(
																	"*print-right-margin*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printMiserWidth			= sym(
																	"*print-misere-width*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printLines				= sym(
																	"*print-lines*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			printPprintDispatch		= sym(
																	"*print-pprint-dispatch*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			readBase				= sym("*read-base*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			nInt(10));

	public static tSYMBOL			readDefaultFloatFormat	= sym(
																	"*read-default-float-format*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			sym("single-float"));

	public static tSYMBOL			readEval				= sym("*readEval*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			T);

	public static tSYMBOL			readSuppress			= sym(
																	"*read-suppress*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	public static tSYMBOL			readTable				= sym("*readtable*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			new cREADTABLE());
	{
		// Initialize readtable functions
		((cREADTABLE) readTable.SYMBOL_VALUE()).init();
	}

	public static tSYMBOL			lispTraceSuppress		= sym("*trace*")
																	.SET_SPECIAL(
																			true)
																	.SET_SYMBOL_VALUE(
																			NIL);

	/**
	 * Search through environment and dictionaries
	 * 
	 * @param name
	 * @return
	 */
	public static cDYN_SYMBOL getAll(tSYMBOL name)
	{
		cDYN_SYMBOL res = e.arg(name);

		if (res == null)
			res = e.arg(name);

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
		tSYMBOL[] res = currPackage().FIND_SYMBOL(name);
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
	public static tT arg(String name, Class<?> cla)
	{
		return arg(sym(name), cla);
	}

	/**
	 * Search in dynamic environment
	 * 
	 * @param name
	 * @return
	 */
	public static tT arg(tSYMBOL name, Class<?> cla)
	{
		cDYN_SYMBOL atom = e.arg(name);
		if (atom == null)
		{
			throw new LispException("Argument " + name
					+ " not found in environment");
		}
		if (!atom.SYMBOL_VALUE().getClass().isAssignableFrom(cla))
			throw new LispException("Argument " + name + " should be of type "
					+ cla.getSimpleName());
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
		return new cINTEGER(value);
	}

	/**
	 * Create a numeric cell
	 * 
	 * @param value
	 * @return
	 */
	public static cINTEGER nInt(Long value)
	{
		return new cINTEGER(value);
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

	/**
	 * Read a string and transform it to a list object
	 * 
	 * @param def
	 * @return
	 */
	public static tT lisp(String def)
	{
		return new cSTRING_STREAM(def, 0, -1).READ(false, NIL, false);
	}

	/**
	 * @param name
	 * @return
	 */
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
			return key.INTERN(name.substring(1))[0];
		}
		int pos = name.indexOf(":");
		if (pos > 0)
		{

			tT pack = (tPACKAGE) packages.GETHASH(str(name.substring(0, pos)),
					NIL)[0];
			if (pack == NIL)
			{
				throw new LispException("Package doesn't exist : "
						+ name.substring(0, pos));
			}

			if (name.charAt(pos + 1) == ':')
			{
				pos++;
			}
			return ((tPACKAGE) pack).INTERN(name.substring(pos + 1,
					name.length()))[0];
		}
		else
			return currPackage().INTERN(name)[0];
	}

	// /**
	// * Create an symbol in a package
	// *
	// * @param name
	// * @return
	// */
	// public static tSYMBOL sym(String pack, String name)
	// {
	// tT in = cNULL.FIND_PACKAGE(str(pack));
	// if (in == null || in == cNULL)
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
		return ((tPACKAGE) p).INTERN(name)[0];
	}

	/**
	 * Create an keyword
	 * 
	 * @param name
	 * @return
	 */
	public static tSYMBOL key(String name)
	{
		tSYMBOL res = (tSYMBOL) ((cPACKAGE) key).external.SET_GETHASH(
				new cSYMBOL(name, key).setConstant(true),
				str(name.toUpperCase()));
		if (res == null)
			System.out
					.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRRRRGGGGG : "
							+ name);// + " ->" + res.DESCRIBE());
		// System.out.println("New Key : " + name);// + " ->" + res.DESCRIBE());
		return res;
	}

	/**
	 * Quote the form
	 * 
	 * @param form
	 * @return
	 */
	public static tLIST quote(Object form)
	{
		return decl("quote", form);
	}

	/**
	 * Unquote the form
	 * 
	 * @param form
	 * @return
	 */
	public static tLIST unquote(Object form)
	{
		return decl("%unquote", form);
	}

	/**
	 * Backquote the form
	 * 
	 * @param form
	 * @return
	 */
	public static tLIST backquote(Object form)
	{
		return decl("%backquote", form);
	}

	/**
	 * splice the form
	 * 
	 * @param form
	 * @return
	 */
	public static tLIST splice(Object form)
	{
		return decl("%splice", form);
	}

	/**
	 * Test if cell is a list beginning with name
	 * 
	 * @param cell
	 * @param name
	 */
	public static boolean beginWith(tT cell, String name)
	{
		if (cell instanceof cNULL)
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
		// System.out.println("= Package " + pkg + " =\n");
		try
		{
			List<Class<?>> cla = getClasses(pkg);
			for (Class<?> clas : cla)
			{
				// if (clas.isInterface())
				// System.out.print("=== Interface " + clas.getSimpleName());
				// else
				// System.out.print("=== Class " + clas.getSimpleName());
				// System.out.println(" ===\n");
				Library.INSTANTIATE(clas.getCanonicalName());
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new LispException("Class not found  " + pkg + " : "
					+ e.getLocalizedMessage());
		}
		catch (IOException e)
		{
			throw new LispException("Error reading class " + pkg + " : "
					+ e.getLocalizedMessage());
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
		// Cr�ation de la liste qui sera retourn�e
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		// On r�cup�re toutes les entr�es du CLASSPATH
		String[] entries = System.getProperty("java.class.path").split(
				System.getProperty("path.separator"));

		// Pour toutes ces entr�es, on verifie si elles contiennent
		// un r�pertoire ou un jar
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

		// On g�n�re le chemin absolu du package
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
			// On filtre les entr�es du r�pertoire
			FilenameFilter filter = new DotClassFilter();
			File[] liste = rep.listFiles(filter);

			// Pour chaque classe pr�sente dans le package, on l'ajoute � la
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

		// Pour chaque entr�e du Jar
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
	 * Cette classe permet de filtrer les fichiers d'un r�pertoire. Il n'accepte
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
