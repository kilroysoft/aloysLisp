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
// IP 27 mars 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

import static aloyslisp.core.L.*;
import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.sequences.*;

/**
 * cINTERNAL_CLASSES
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cINTERNAL_CLASSES extends cCELL implements tINTERNAL_CLASSES
{
	tLIST	classes	= null;

	/**
	 * 
	 */
	public cINTERNAL_CLASSES()
	{
		classes = NIL;
	}

	/**
	 * @return
	 */
	@aFunction(name = "make-internal-classes")
	public static tINTERNAL_CLASSES MAKE_INTERNAL_CLASSES()
	{
		return new cINTERNAL_CLASSES();
	}

	/**
	 * @param pkg
	 * @return
	 */
	public Boolean LOAD_PACKAGE(String pkg)
	{
		boolean valid = false;
		tLIST cla = INTERNAL_CLASSES_NAMES(pkg);
		for (tT clas : cla)
		{
			tT res = cINTERNAL_CLASS.MAKE_INTERNAL_CLASS(((tSTRING) clas)
					.getString());
			if (res != NIL)
				classes.APPEND(res);
		}
		valid = true;

		return valid;
	}

	/**
	 * @param pckgname
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public tLIST INTERNAL_CLASSES_NAMES(String pckgname)
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

			try
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
			catch (IOException e)
			{
				System.out.println("Warning " + entries[i] + " " + e);
			}
		}

		// ajout => analyse classpath de la webapp
		Enumeration<URL> eUrl;
		try
		{
			eUrl = getClass().getClassLoader().getResources(pckgname);
		}
		catch (IOException e1)
		{
			throw new LispException("Error reading loader " + e1);
		}
		for (; eUrl.hasMoreElements();)
		{
			URL url = eUrl.nextElement();
			String sUrl = url.toString();

			try
			{
				if (sUrl.contains(".jar!"))
				{
					sUrl = sUrl.substring(0, sUrl.indexOf("!"));
					classes.addAll((Collection<? extends Class<?>>) manageJar(
							sUrl, pckgname));
				}
				else
				{
					classes.addAll((Collection<? extends Class<?>>) manageDirectory(
							sUrl, pckgname));
				}
			}
			catch (IOException e)
			{
				System.out.println("Warning " + sUrl + " " + e);
			}
		}

		tLIST res = NIL;

		for (Class<?> cl : classes)
			res = (tLIST) res.APPEND(list(str(cl.getCanonicalName())));

		return res;
	}

	/**
	 * @param repertoire
	 * @param pckgname
	 * @return
	 * @throws ClassNotFoundException
	 */
	private Collection<Class<?>> manageDirectory(String repertoire,
			String pckgname)
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
				try
				{
					classes.add(Class.forName(pckgname + "."
							+ liste[i].getName().split("\\.")[0]));
				}
				catch (ClassNotFoundException e)
				{
					System.out.println("Warning " + liste[i].getName()
							+ " Not found.");
				}
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
	private Collection<Class<?>> manageJar(String jar, String pckgname)
			throws IOException
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

				Class<?> found;
				try
				{
					found = Class.forName(pckgname + "."
							+ nomFichier.split("\\.")[0]);
				}
				catch (ClassNotFoundException e)
				{
					System.out.println("Warning " + nomFichier + " Not found.");
					continue;
				}

				classes.add(found);

			}

		}

		return classes;
	}

	/**
	 * Cette classe permet de filtrer les fichiers d'un répertoire. Il n'accepte
	 * que les fichiers .class.
	 */
	private class DotClassFilter implements FilenameFilter
	{

		public boolean accept(File arg0, String arg1)
		{
			return arg1.endsWith(".class");
		}

	}
}
