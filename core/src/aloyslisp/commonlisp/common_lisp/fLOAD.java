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
// IP 7 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import java.io.*;
import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.annotations.*;
import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * fLOAD
 * 
 * <p>
 * [Function]
 * <p>
 * load filename &key :verbose :print :if-does-not-exist :external-format
 * 
 * <p>
 * This function loads the file named by filename into the Lisp environment. It
 * is assumed that a text (character file) can be automatically distinguished
 * from an object (binary) file by some appropriate implementation-dependent
 * means, possibly by the file type. The defaults for filename are taken from
 * the variable *default-pathname-defaults*. If the filename (after the merging
 * in of the defaults) does not explicitly specify a type, and both text and
 * object types of the file are available in the file system, load should try to
 * select the more appropriate file by some implementation-dependent means.
 * 
 * <p>
 * If the first argument is a stream rather than a pathname, then load
 * determines what kind of stream it is and loads directly from the stream.
 * 
 * <p>
 * The :verbose argument (which defaults to the value of *load-verbose*), if
 * true, permits load to print a message in the form of a comment (that is, with
 * a leading semicolon) to *standard-output* indicating what file is being
 * loaded and other useful information.
 * 
 * @see <a href='http://clm.aloys.li/node217.html'>23.4. Loading Files</a>
 * @see <a href=
 *      "http://www.mat.uc.pt/~pedro/cientificos/funcional/lisp/gcl_24.html#SEC1378"
 *      > for :external-format</a>
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class fLOAD extends SYSTEM_FUNCTION
{
	// private static final Integer file = 0;
	// private static final Integer verbose = 1;
	// private static final Integer print = 2;
	// private static final Integer ifDoesNotExist = 3;
	// private static final Integer externalFormat = 4;

	/**
	 * @param eval
	 */
	public fLOAD()
	{
		super(
				decl("file", //
						"&key", //
						decl("verbose", true), // *load-verbose*
						decl("print", true), // *load-print*
						decl("if-does-not-exist", true), //
						decl("external-format", key("default")) //
				), //
				"(fLOAD file &key verbose print if-does-not-exist external-format)", //
				NIL);
	}

	static final tSYMBOL	WRITE	= sym("lisp::write");

	/**
	 * @return
	 */
	@Global(name = "load")
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
			print("; Loading contents of file " + name);
		}

		// while there's something to read
		try
		{
			tT[] res;
			for (;;)
			{
				// read it
				res = new tT[]
				{ in.READ(NIL, NIL, NIL) };

				if (verbose)
				{
					print("; lisp>" + res[0]);
				}

				// and evaluate it
				// System.out.println("eval : " + res[0]);
				res = res[0].EVAL();

				if (print)
					for (tT cell : res)
					{
						print("; " + cell);
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
			print("; Finished loading " + name);
		}
		return new tT[]
		{ T };
	}

	private void print(String str)
	{
		WRITE.e(str(str), key("escape"), NIL);
		WRITE.e(str("\n"), key("escape"), NIL);
	}

}
