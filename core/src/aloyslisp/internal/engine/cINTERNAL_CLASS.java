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
// IP 10 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.*;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import static aloyslisp.core.L.*;

/**
 * cINTERNAL_CLASS
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cINTERNAL_CLASS extends cCELL implements tINTERNAL_CLASS
{
	Class<?>	classURI	= NIL.getClass();
	tLIST		methods		= NIL;
	tLIST		functions	= NIL;
	tLIST		fields		= NIL;
	tLIST		variables	= NIL;
	Boolean		valid		= false;

	/**
	 * @param classURI
	 */
	public cINTERNAL_CLASS(String classURI)
	{
		try
		{
			this.classURI = Class.forName(classURI);
		}
		catch (NullPointerException e)
		{
			System.out.println("Error in load class : " + classURI);
			valid = false;
			return;
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Can't load class : " + classURI);
			valid = false;
			return;
		}

		valid = true;

		tPACKAGE save = L.currPackage();
		try
		{
			aNonStandard ns = getClassURI().getAnnotation(aNonStandard.class);
			aPackage pack = getClassURI().getAnnotation(aPackage.class);
			aJavaInternal javaInternal = getClassURI().getAnnotation(
					aJavaInternal.class);
			if (ns != null)
			{
				L.sPACKAGEs.SET_SYMBOL_VALUE(L.sys);
			}
			if (pack != null)
			{
				// todo get package
				L.sPACKAGEs.SET_SYMBOL_VALUE(L.sys);
			}
			if (javaInternal != null)
			{
				valid = false;
				return;
			}

			if (!InstFields())
				valid = false;

			if (!InstMethods())
				valid = false;

			if (!GENERATE_VB("VB\\"))
				valid = false;
		}
		finally
		{
			L.sPACKAGEs.SET_SYMBOL_VALUE(save);
		}
	}

	/**
	 * @return
	 */
	private Class<?> getClassURI()
	{
		return classURI;
	}

	@aFunction(name = "class-exists")
	public static boolean CLASS_EXISTS( //
			@aArg(name = "class-uri") String classURI)
	{
		try
		{
			Class.forName(classURI);
		}
		catch (NullPointerException e)
		{
			System.out.println("Error in testing " + classURI);
			return false;
		}
		catch (ClassNotFoundException e)
		{
			return false;
		}
		return true;
	}

	/**
	 * Read all lisp functions of the class and create appropriate package entry
	 * 
	 * There is different types of functions
	 * <ul>
	 * <li>@aFunction Normal Lisp objects function
	 * <li>@aFunction Normal static function or constructor
	 * </ul>
	 * 
	 * @param cls
	 * @return
	 */
	@aFunction(name = "make-internal-class")
	public static tT MAKE_INTERNAL_CLASS( //
			@aArg(name = "class") String cls)
	{
		if (!CLASS_EXISTS(cls))
			return NIL;

		cINTERNAL_CLASS cla = new cINTERNAL_CLASS(cls);
		if (!cla.valid)
			return NIL;

		return cla;
	}

	static String	EOL	= "\r\n";

	/**
	 * @param cls
	 * @param clas
	 * @return
	 */
	public boolean GENERATE_VB(String path)
	{
		PrintStream write;
		Boolean inter = classURI.getSimpleName().startsWith("t");
		Boolean annot = classURI.getSimpleName().startsWith("a");
		try
		{
			write = new PrintStream(new FileOutputStream(path
					+ classURI.getSimpleName() + ".vb"));
		}
		catch (FileNotFoundException e)
		{
			throw new LispException("Can't generate VB file for "
					+ classURI.getSimpleName());
		}

		// file header
		String clType = inter ? "Interface" : "Class";
		write.print("'" + EOL);
		write.print("' " + clType + " " + classURI.getSimpleName() + EOL);
		write.print("'" + EOL);
		write.print("' aloysLisp." + EOL);
		write.print("'" + EOL);
		write.print("' A LISP interpreter, compiler and library." + EOL);
		write.print("'" + EOL);
		write.print("' Copyright (C) 2010-2011 kilroySoft <aloyslisp@kilroysoft.ch>"
				+ EOL);
		write.print("'" + EOL);
		write.print("' This program is free software: you can redistribute it and/or modify it under"
				+ EOL);
		write.print("' the terms of the GNU General Public License as published by the Free Software"
				+ EOL);
		write.print("' Foundation, either version 3 of the License, or (at your option) any later"
				+ EOL);
		write.print("' version." + EOL);
		write.print("'" + EOL);
		write.print("' This program is distributed in the hope that it will be useful, but WITHOUT"
				+ EOL);
		write.print("' ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS"
				+ EOL);
		write.print("' FOR A PARTICULAR PURPOSE. See the GNU General Public License for more"
				+ EOL);
		write.print("' details." + EOL);
		write.print("'" + EOL);
		write.print("' You should have received a copy of the GNU General Public License along with"
				+ EOL);
		write.print("' this program. If not, see <http://www.gnu.org/licenses/>."
				+ EOL);
		write.print("'--------------------------------------------------------------------------"
				+ EOL);
		write.print("' history" + EOL);
		write.print("'--------------------------------------------------------------------------"
				+ EOL);
		write.print("' Creation" + EOL);
		write.print("'--------------------------------------------------------------------------"
				+ EOL);

		// Write annotation header
		if (!annot)
		{
			Annotation[] clsAnn = classURI.getAnnotations();
			for (Annotation an : clsAnn)
			{
				write.print(getVBAnnotationObject(an) + " _" + EOL);
			}
		}

		// Write class definition header
		if (annot)
		{
			Target tar = classURI.getAnnotation(Target.class);
			String type = "Class";
			if (tar != null)
			{
				ElementType[] elType = tar.value();
				if (elType[0].compareTo(ElementType.METHOD) == 0)
					type = "Method";
				if (elType[0].compareTo(ElementType.PARAMETER) == 0)
					type = "Parameter";
				if (elType[0].compareTo(ElementType.FIELD) == 0)
					type = "Field";
				if (elType[0].compareTo(ElementType.TYPE) == 0)
					type = "Class or AttributeTargets.Interface";

				write.print("<AttributeUsage(AttributeTargets." + type + ")> _"
						+ EOL);
				write.print("Public Class " + classURI.getSimpleName() + EOL);
				write.print("\tInherits Attribute" + EOL);
			}
			else
			{
				System.out.println("Missing class annotation on " + classURI);
			}
		}
		else if (inter)
		{
			write.print("Public Interface " + classURI.getSimpleName() + EOL);
		}
		else
		{
			if ((classURI.getModifiers() & Modifier.ABSTRACT) != 0)
				write.print("Public MustInherit Class "
						+ classURI.getSimpleName() + EOL);
			else
				write.print("Public Class " + classURI.getSimpleName() + EOL);
		}

		// add base class
		if (classURI.getSuperclass() != null)
		{
			write.print("\tInherits "
					+ classURI.getSuperclass().getSimpleName() + EOL);
		}

		// add interfaces
		if (classURI.getInterfaces().length > 0)
		{
			String sep = "";
			if (inter)
			{
				sep = "\tInherits ";
			}
			else
			{
				sep = "\tImplements ";
			}

			Class<?>[] interf = classURI.getInterfaces();
			for (Class<?> cl : interf)
			{
				if (cl.getSimpleName().equals("Annotation"))
					continue;
				write.print(sep + cl.getSimpleName());
				sep = ", ";
			}
			write.printf(EOL);
		}

		if (annot)
			write.printf(getVBAnnotation());
		else
		{
			write.print(getVBFields());
			write.print(getVBMethod());
		}

		// close file
		if (inter)
		{
			write.print("End Interface" + EOL);
		}
		else
		{
			write.print("End Class" + EOL);
		}

		return true;
	}

	/**
	 * @param clas
	 * @return
	 */
	private String getVBMethod()
	{
		Boolean inter = classURI.getSimpleName().startsWith("t");
		String res = "";
		// System.out.println("class " + cls);
		// Method[] meth = clas.getDeclaredMethods();
		Method[] meth = classURI.getDeclaredMethods();
		String name = "";
		for (Method m : meth)
		{
			Boolean isPrivate = ((m.getModifiers() & Modifier.PRIVATE) != 0);
			Boolean isProtected = ((m.getModifiers() & Modifier.PROTECTED) != 0);
			aJavaInternal javaInternal = m.getAnnotation(aJavaInternal.class);

			if (javaInternal != null)
			{
				continue;
			}

			if (name.equals(m.getName()))
				continue;

			name = m.getName();

			if (isPrivate || isProtected)
				continue;

			String implem = "";
			String over = "";
			Boolean isShared = ((m.getModifiers() & Modifier.STATIC) != 0);
			String shared = "";
			Method mDecl = m;

			if (isShared)
				shared = "Shared ";

			aFunction func = m.getAnnotation(aFunction.class);

			if (!inter && !isShared)
			{
				mDecl = getInterfaceMethod(classURI, m.getName(),
						m.getParameterTypes());

				if (mDecl == null)
				{
					// if (m.getName().toUpperCase().equals(m.getName()))
					System.out.println("======NO INTERFACE======"
							+ classURI.getSimpleName() + "." + m.getName());
					continue;
				}
				func = mDecl.getAnnotation(aFunction.class);

				if (getOverridedMethod(classURI, m.getName(),
						m.getParameterTypes()) == null)
				{
					implem = " Implements "
							+ mDecl.getDeclaringClass().getSimpleName() + ".f"
							+ mDecl.getName();
					over = "Overridable ";
				}
				else
				{
					over = "Overrides ";
				}
			}

			// Only take public functions
			if ((m.getModifiers() & Modifier.PRIVATE) != 0)
				continue;

			if (func == null)
			{
				System.out.println("======NO @func def======"
						+ classURI.getSimpleName() + "." + m.getName());
				continue;
			}

			//
			String ret = mDecl.getReturnType().getSimpleName();
			ret = ret.replace("[]", "()");
			ret = getVBType(ret);
			if (ret.equals("void"))
				ret = "";

			res += EOL + "\t'-----------------------------------" + EOL;
			res += "\t' " + name + EOL;
			res += "\t'" + EOL;

			// Write annotations
			Annotation[] ann = m.getAnnotations();
			for (Annotation an : ann)
			{
				res += "\t" + getVBAnnotationObject(an) + " _" + EOL;
			}

			if (ret.equals(""))
			{
				res += "\t";
				if (!inter)
					res += "Public ";
				res += shared + over + "Sub f" + name + "(" + getVBArgs(mDecl)
						+ ")" + implem + EOL;
				if (!inter)
					res += "\tEnd Sub" + EOL;
			}
			else
			{
				res += "\t";
				if (!inter)
					res += "Public ";
				res += shared + over + "Function f" + name + "("
						+ getVBArgs(mDecl) + ") As " + ret + implem + EOL;
				if (!inter)
				{
					res += "\t\tReturn Nothing" + EOL;
					res += "\tEnd Function" + EOL;
				}
			}
		}

		return res;
	}

	/**
	 * @param org
	 * @param name
	 * @param args
	 * @return
	 */
	private Method getInterfaceMethod(Class<?> org, String name, Class<?>[] args)
	{
		while (org != null)
		{
			Class<?>[] interf = org.getInterfaces();
			Method mDecl = null;
			for (Class<?> in : interf)
			{
				// System.out.println("Interface : " + name + " "
				// + org.getCanonicalName() + " " + in.getCanonicalName());
				try
				{
					// System.out.println("Search : " + name + " : "
					// + in.getCanonicalName());
					mDecl = in.getDeclaredMethod(name, args);
					// mDecl = in.getMethod(name, args);
					// System.out.println("Found : " + name + " : "
					// + mDecl.getDeclaringClass().getCanonicalName());
				}
				catch (Exception e)
				{
					mDecl = getInterfaceMethod(in, name, args);
					if (mDecl == null)
						continue;
				}
				return mDecl;
			}
			org = org.getSuperclass();
		}
		return null;
	}

	/**
	 * @param org
	 * @param name
	 * @param args
	 * @return
	 */
	private Method getOverridedMethod(Class<?> org, String name, Class<?>[] args)
	{
		Method mDecl = null;
		org = org.getSuperclass();
		while (org != null)
		{
			// System.out.println("Override : " + org.getCanonicalName());
			try
			{
				mDecl = org.getMethod(name, args);
				return mDecl;
			}
			catch (Exception e)
			{
			}
			org = org.getSuperclass();
		}
		return null;
	}

	private String getVBAnnotationObject(Annotation an)
	{
		Class<? extends Annotation> aClass = an.getClass();
		Method[] m = aClass.getDeclaredMethods();
		String res = "<" + an.annotationType().getSimpleName() + "(";

		String sep = "";
		int i = 0;
		for (Method meth : m)
		{
			String name = meth.getName();
			if (name.equals("equals") || name.equals("TO_STRING")
					|| name.equals("hashCode") || name.equals("annotationType"))
				continue;

			res += sep + name + " := ";

			Object val = null;
			try
			{
				val = meth.invoke(an, new Object[] {});
			}
			catch (IllegalArgumentException e)
			{
				System.out.println(e.getLocalizedMessage() + " : "
						+ meth.getName() + " " + an);
				continue;
			}
			catch (IllegalAccessException e)
			{
				System.out.println(e.getLocalizedMessage() + " : "
						+ meth.getName() + " " + an);
				continue;
			}
			catch (InvocationTargetException e)
			{
				System.out.println(e.getCause().getLocalizedMessage() + " : "
						+ meth.getName() + " " + an);
				continue;
			}

			String value = "Nothing";
			String type = val.getClass().getSimpleName();
			if (type.equals("Class"))
				value = "\"" + ((Class<?>) val).getSimpleName() + "\"";
			else if (type.equals("String"))
				value = "\"" + val.toString() + "\"";
			else
				value = val.toString();

			res += value;
			sep = ", ";
			i++;
		}

		return res + ")>";
	}

	/**
	 * @param m
	 * @return
	 */
	private String getVBArgs(Method m)
	{
		String res = "";
		Class<?>[] args = m.getParameterTypes();
		Annotation[][] ann = m.getParameterAnnotations();

		String sep = " _" + EOL + "\t\t\t";
		for (int i = 0; i < args.length; i++)
		{
			String type = args[i].getSimpleName();
			String arr = "";
			if (type.endsWith("[]"))
			{
				type = type.replace("[]", "");
				arr = "()";
			}
			type = getVBType(type);
			for (int j = 0; j < ann[i].length; j++)
			{
				res += sep + getVBAnnotationObject(ann[i][j]);
				sep = " _" + EOL + "\t\t\t";
			}
			for (int j = 0; j < ann[i].length; j++)
			{
				if (ann[i][j] instanceof aArg)
				{
					res += sep + "ByVal a"
							+ ((aArg) ann[i][j]).name().replace("-", "_") + arr
							+ " As " + type;
				}
				else if (ann[i][j] instanceof aOpt)
				{
					res += sep + "ByVal optional o"
							+ ((aOpt) ann[i][j]).name().replace("-", "_") + arr
							+ " As " + type + " = Nothing";
				}
				else if (ann[i][j] instanceof aRest)
				{
					if (m.isVarArgs())
						res += sep + "ByVal ParamArray r"
								+ ((aRest) ann[i][j]).name().replace("-", "_")
								+ arr + " As " + type;
					else
						res += sep + "ByVal r"
								+ ((aRest) ann[i][j]).name().replace("-", "_")
								+ " As " + type;
				}
			}
			sep = ", _" + EOL + "\t\t\t";
		}

		if (res.equals(""))
			return "";
		return res + " _" + EOL + "\t\t";
	}

	/**
	 * @param cls
	 * @param clas
	 * @return
	 */
	private String getVBAnnotation()
	{
		String res = "";
		String decl = "\tPublic Sub New( _" + EOL;
		String init = "";
		String sep = "";
		Method[] m = classURI.getDeclaredMethods();
		for (Method f : m)
		{
			String name = f.getName();
			String type = f.getReturnType().getSimpleName();
			type = getVBType(type);
			type = type.replace("[]", "()");

			res += "\tPublic ";
			res += "m" + name + " As " + type + EOL;
			res += "\tPublic Property " + name + "() As " + type + EOL;
			res += "\t\tGet" + EOL;
			res += "\t\t\tReturn m" + name + EOL;
			res += "\t\tEnd Get" + EOL;
			res += "\t\tSet(ByVal value As " + type + ")" + EOL;
			res += "\t\t\tm" + name + " = value" + EOL;
			res += "\t\tEnd Set" + EOL;
			res += "\tEnd Property" + EOL;

			init += "\t\tMe.m" + name + " = " + name + EOL;

			String def = "";
			if (type.equals("String"))
				def = "\"\"";
			else
				def = "0";

			decl += sep + "\t\t\tOptional ByVal " + name + " As " + type + "="
					+ def;
			sep = ", _" + EOL;
		}
		decl += " _" + EOL + "\t\t)" + EOL + init;
		decl += "\tEnd Sub" + EOL;
		return res + decl;
	}

	private String getVBFields()
	{
		String res = "";
		Field[] fields = classURI.getDeclaredFields();
		for (Field f : fields)
		{
			boolean stat = (f.getModifiers() & Modifier.STATIC) != 0;
			String name = f.getName();
			String type = f.getType().getSimpleName();
			type = getVBType(type);
			type = type.replace("[]", "()");

			res += "\tPublic ";
			if (stat)
				res += "Shared ";
			res += (stat ? "s" : "m") + name + " As " + type + EOL;
			// if (!fin)
			// {
			// res += "\tPublic ";
			// if (stat)
			// res += "Shared ";
			// res += "Property f" + name + "() As " + type + EOL;
			// res += "\t\tGet" + EOL;
			// res += "\t\t\tReturn " + name + EOL;
			// res += "\t\tEnd Get" + EOL;
			// res += "\t\tSet(ByVal value As " + type + ")" + EOL;
			// res += "\t\t\t" + name + " = value" + EOL;
			// res += "\t\tEnd Set" + EOL;
			// res += "\tEnd Property" + EOL;
			// }
		}

		return res;
	}

	private String getVBType(String type)
	{
		type = type.replace("Character", "Char");
		type = type.replace("Class", "String");
		type = type.replace("int", "Integer");
		return type;
	}

	/**
	 * @param clas
	 * @return
	 */
	private Boolean InstFields()
	{
		Field[] fields = classURI.getDeclaredFields();
		for (Field f : fields)
		{
			// Get method data
			aSymb symbol = f.getAnnotation(aSymb.class);
			if (symbol == null)
				continue;

			aSpecial special = f.getAnnotation(aSpecial.class);
			boolean constant = f.toGenericString().contains(" final ");

			tSYMBOL sym = sym(symbol.name());

			if (constant)
				sym.SET_CONSTANT(true);
			else if (special != null)
				sym.SET_SPECIAL(true);

		}

		return true;
	}

	/**
	 * @param clas
	 * @return
	 */
	private Boolean InstMethods()
	{
		// System.out.println("class " + cls);
		// Method[] meth = clas.getDeclaredMethods();
		Method[] meth = classURI.getDeclaredMethods();
		String name = "";
		for (Method m : meth)
		{
			Boolean isPrivate = ((m.getModifiers() & Modifier.PRIVATE) != 0);
			Boolean isProtected = ((m.getModifiers() & Modifier.PROTECTED) != 0);
			aJavaInternal javaInternal = m.getAnnotation(aJavaInternal.class);

			if (javaInternal != null)
			{
				continue;
			}

			if (isPrivate || isProtected)
				continue;

			if (name.equals(m.getName()))
				continue;

			name = m.getName();

			// TODO verify that m is not derivated from a base class...
			// Get method data
			aFunction f = m.getAnnotation(aFunction.class);
			if (f == null)
			{
				// Test for non declared
				// if (m.getName().matches("[A-Z_ps]*"))
				// {
				// if (m.getDeclaringClass().getSimpleName().startsWith("t"))
				// // we are in a type definition
				// System.out.println("NON DECLARED LISP METHOD : "
				// + m.getName());
				//
				// else if ((m.getModifiers() & Modifier.STATIC) != 0)
				// // we are in a class static definition
				// System.out
				// .println("NON DECLARED LISP cCOMPILED_METHOD : "
				// + m.getDeclaringClass()
				// + " "
				// + clas
				// + " " + m.getName());
				// }
				continue;
			}

			tSYMBOL sym = null;
			sym = sym(f.name().toUpperCase());

			tFUNCTION func = null;
			func = new cCOMPILED_FUNCTION(m);

			// add symbol function
			sym.SET_SYMBOL_FUNCTION(func);

			aWriter setf = m.getAnnotation(aWriter.class);
			if (setf != null)
				sym.SET_GET(setfKey, sym(setf.accessor()));

		}

		return true;
	}
}
