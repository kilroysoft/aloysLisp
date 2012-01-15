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
// IP 18 déc. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import java.io.File;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.designators.tPATHNAME_DESIGNATOR;
import aloyslisp.core.designators.tSTRING_DESIGNATOR;
import aloyslisp.core.sequences.*;
import static aloyslisp.core.L.*;

/**
 * cPATHNAME
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cPATHNAME extends cCELL implements tPATHNAME
{
	File	file	= null;

	/**
	 * @param file
	 */
	public cPATHNAME(String file)
	{
		this.file = new File(file);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#TO_STRING()
	 */
	public String TO_STRING()
	{
		return "#P\"" + NAMESTRING() + "\"";
	}

	/**
	 * @return
	 */
	public String NAMESTRING()
	{
		return file.getPath();
	}

	/**
	 * @param path
	 * @return
	 */
	@aFunction(name = "pathname", doc = "f_pn")
	public static tPATHNAME PATHNAME( //
			@aArg(name = "pathname") tPATHNAME_DESIGNATOR path)
	{
		if (path instanceof tPATHNAME)
			return (tPATHNAME) path;

		if (path instanceof tFILE_STREAM)
			return ((tFILE_STREAM) path).GET_PATHNAME();

		if (!(path instanceof tSTRING_DESIGNATOR))
			throw new LispException("aType error for " + path);

		return new cPATHNAME(cSTRING.STRING(path));
	}

	/**
	 * @return
	 */
	@aFunction(name = "make-pathname", doc = "f_mk_pn")
	@aKey(keys = "(host device directory name type version defaults case)")
	public static tPATHNAME MAKE_PATHNAME()
	{
		// TODO Implements MAKE_PATHNAME
		return null;
	}

	@aFunction(name = "logical-pathname", doc = "f_logi_1")
	@aKey(keys = "(host device directory name type version defaults case)")
	public static tPATHNAME LOGICAL_PATHNAME( //
			@aArg(name = "pathspec") tT path //
	)
	{
		if (path instanceof tSTRING)
			return new cLOGICAL_PATHNAME(((tSTRING) path).getString());

		if (path instanceof tFILE_STREAM)
		{
			tPATHNAME res = ((tFILE_STREAM) path).GET_PATHNAME();
			if (res != null)
				return res;
		}

		throw new LispException("LOGICAL_PATHNAME bad argument : " + path);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public Integer SXHASH()
	{
		return str(NAMESTRING()).SXHASH();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tPATHNAME#DIRECTORY()
	 */
	@Override
	public tLIST DIRECTORY()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tPATHNAME#PROBE_FILE()
	 */
	@Override
	public tT PROBE_FILE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tPATHNAME#ENSURE_DIRECTORIES_EXIST()
	 */
	@Override
	public tT[] ENSURE_DIRECTORIES_EXIST()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tPATHNAME#TRUENAME()
	 */
	@Override
	public tPATHNAME TRUENAME()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tPATHNAME#FILE_AUTHOR()
	 */
	@Override
	public tT FILE_AUTHOR()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tPATHNAME#RENAME_FILE(aloyslisp.core.streams.
	 * tPATHNAME_DESIGNATOR)
	 */
	@Override
	public tT[] RENAME_FILE(tPATHNAME_DESIGNATOR newFile)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tPATHNAME#DELETE_FILE()
	 */
	@Override
	public tT DELETE_FILE()
	{
		// TODO Auto-generated method stub
		return T;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.streams.tPATHNAME#getFile()
	 */
	@Override
	@aJavaInternal
	public File getFile()
	{
		// TODO Auto-generated method stub
		return file;
	}

}
