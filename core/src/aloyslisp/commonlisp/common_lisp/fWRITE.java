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
// IP 14 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.commonlisp.common_lisp;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * fWRITE
 * 
 * <p>
 * [Function]
 * <p>
 * write object &key :stream :escape :radix :base :circle :pretty :level :length
 * :case :gensym :array :readably :right-margin :miser-width :lines
 * :pprint-dispatch
 * 
 * <p>
 * The printed representation of object is written to the output stream
 * specified by :stream, which defaults to the value of *standard-output*.
 * 
 * <p>
 * The other keyword arguments specify values used to control the generation of
 * the printed representation. Each defaults to the value of the corresponding
 * global variable: see *print-escape*, *print-radix*, *print-base*,
 * *print-circle*, *print-pretty*, *print-level*, *print-length*, and
 * *print-case*, in addition to *print-array*, *print-gensym*, *print-readably*,
 * *print-right-margin*, *print-miser-width*, *print-lines*, and
 * *print-pprint-dispatch*. (This is the means by which these variables affect
 * printing operations: supplying default values for the write function.) Note
 * that the printing of symbols is also affected by the value of the variable
 * *package*. write returns object.
 * 
 * @see <a href='http://clm.aloys.li/node198.html'>22.3.1. Output to Character
 *      Streams</a>
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class fWRITE extends SYSTEM_FUNCTION
{
	/**
	 * write object &key :stream :escape :radix :base :circle :pretty :level
	 * :length :case :gensym :array :readably :right-margin :miser-width :lines
	 * :pprint-dispatch
	 * 
	 * @param eval
	 */
	public fWRITE()
	{
		super(
				decl("object", "&key", //
						decl("stream", NIL),//
						decl("escape", printEscape), //
						decl("radix", printRadix), //
						decl("base", printBase), //
						decl("circle", printCircle),//
						decl("pretty", printPretty), //
						decl("level", printLevel), //
						decl("length", printLength), //
						decl("case", printCase), //
						decl("gensym", printGensym), //
						decl("array", printArray), //
						decl("readably", printReadably), //
						decl("right-margin", printRightMargin), //
						decl("miser-width", printMiserWidth), //
						decl("lines", printLines), //
						decl("pprint-dispatch", printPprintDispatch) //
				), //
				"(fWRITE object &key stream escape radix base circle pretty level "
						+ "length case gensym array readably right-margin miser-width lines "
						+ "pprint-dispatch)", //
				NIL);
	}

	final static int	arg_object			= 0;

	final static int	key_stream			= 0;
	final static int	key_escape			= 1;
	final static int	key_radix			= 2;
	final static int	key_base			= 3;
	final static int	key_circle			= 4;
	final static int	key_pretty			= 5;
	final static int	key_level			= 6;
	final static int	key_length			= 7;
	final static int	key_case			= 8;
	final static int	key_gensym			= 9;
	final static int	key_array			= 10;
	final static int	key_readably		= 11;
	final static int	key_right_margin	= 12;
	final static int	key_miser_width		= 13;
	final static int	key_lines			= 14;
	final static int	key_pprint_dispatch	= 15;

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.FUNCTION#IMPL(aloyslisp.core.plugs.CELL)
	 */
	public tT[] IMPL()
	{
		e.intern(sym("lisp::*print-escape*"), arg("escape"));
		e.intern(sym("lisp::*print-radix*"), arg("radix"));
		e.intern(sym("lisp::*print-base*"), arg("base"));
		e.intern(sym("lisp::*print-circle*"), arg("circle"));
		e.intern(sym("lisp::*print-pretty*"), arg("pretty"));
		e.intern(sym("lisp::*print-level*"), arg("level"));
		e.intern(sym("lisp::*print-length*"), arg("length"));
		e.intern(sym("lisp::*print-case*"), arg("case"));
		e.intern(sym("lisp::*print-gensym*"), arg("gensym"));
		e.intern(sym("lisp::*print-array*"), arg("array"));
		e.intern(sym("lisp::*print-readably*"), arg("readably"));
		e.intern(sym("lisp::*print-right-margin*"), arg("right-margin"));
		e.intern(sym("lisp::*print-miser-width*"), arg("miser-width"));
		e.intern(sym("lisp::*print-lines*"), arg("lines"));
		e.intern(sym("lisp::*print-dispatch*"), arg("pprint-dispatch"));

		tT obj = arg(arg_object);

		// stream management
		tT stream = arg("stream");
		if (stream instanceof NIL)
			stream = standardOutput.SYMBOL_VALUE();
		else
			e.intern(sym("lisp::*standard-output*"), stream);

		System.err.flush();
		((tOUTPUT_STREAM) stream).WRITE(obj);
		((tOUTPUT_STREAM) stream).FINISH_OUTPUT();

		return new tT[]
		{ obj };
	}

}
