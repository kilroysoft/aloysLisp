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
// IP 16 déc. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.streams;

import java.io.File;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.clos.*;
import aloyslisp.core.designators.tPATHNAME_DESIGNATOR;
import aloyslisp.core.sequences.*;

/**
 * tPATHNAME
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tPATHNAME extends tBUILT_IN_CLASS, tPATHNAME_DESIGNATOR
{
	/**
	 * @return
	 */
	@aJavaInternal
	public File getFile();

	/**
	 * @return Path as string
	 */
	@aFunction(name = "namestring", doc = "f_namest")
	public String NAMESTRING();

	/**
	 * @return Pathnames list
	 */
	@aFunction(name = "directory", doc = "f_dir")
	@aBaseArg(name = "path", type = tPATHNAME_DESIGNATOR.class)
	public tLIST DIRECTORY();

	/**
	 * @return pathname or NIL
	 */
	@aFunction(name = "probe-file", doc = "f_probe")
	@aBaseArg(name = "path", type = tPATHNAME_DESIGNATOR.class)
	public tT PROBE_FILE();

	/**
	 * @return pathspec, created
	 */
	@aFunction(name = "ensure-directories-exist", doc = "f_ensu_1")
	@aKey(keys = "(verbose)")
	@aBaseArg(name = "path", type = tPATHNAME_DESIGNATOR.class)
	public tT[] ENSURE_DIRECTORIES_EXIST();

	/**
	 * @return pathname
	 */
	@aFunction(name = "truename", doc = "f_tn")
	@aBaseArg(name = "path", type = tPATHNAME_DESIGNATOR.class)
	public tPATHNAME TRUENAME();

	/**
	 * @return author name or nil
	 */
	@aFunction(name = "file-author", doc = "f_file_a")
	@aBaseArg(name = "path", type = tPATHNAME_DESIGNATOR.class)
	public tT FILE_AUTHOR();

	/**
	 * @return defaulted-new-name, old-truename, new-truename
	 */
	@aFunction(name = "rename-file", doc = "f_rn_fil")
	@aBaseArg(name = "path", type = tPATHNAME_DESIGNATOR.class)
	public tT[] RENAME_FILE( //
			@aArg(name = "new-file") tPATHNAME_DESIGNATOR newFile //
	);

	/**
	 * @return T or error
	 */
	@aFunction(name = "delete-file", doc = "f_del_fi")
	@aBaseArg(name = "path", type = tPATHNAME_DESIGNATOR.class)
	public tT DELETE_FILE();

}
