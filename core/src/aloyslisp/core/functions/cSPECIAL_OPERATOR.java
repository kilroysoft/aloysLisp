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
// IP 26 oct. 2010 Creation
// TODO Rethink about SpecialOp form as a 2 way function and macro
// transformations.
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import static aloyslisp.L.*;
import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.packages.cNIL;
import aloyslisp.core.packages.tNULL;
import aloyslisp.core.packages.tSYMBOL;
import aloyslisp.core.sequences.*;
import aloyslisp.exec.*;

/**
 * cSPECIAL_OPERATOR
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cSPECIAL_OPERATOR extends cSYSTEM_FUNCTION implements
		tSPECIAL_OPERATOR
{

	/**
	 * @param cls
	 * @param name
	 * @param decl
	 * @param doc
	 * @param declare
	 */
	public cSPECIAL_OPERATOR(Class<?> cls, String name, tLIST decl, String doc,
			tLIST declare)
	{
		super(cls, name, decl, doc, declare);
		this.setFunctionCall(cls, name);
		object = this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.functions.FUNCTION#printableStruct()
	 */
	protected String printableStruct()
	{
		return "SPECIAL " + getFuncName() + " " + intern.getArgs() + " "
				+ intern.commentary() + " " + intern.declare();
	}

	/*********************************************************************************
	 * @param name
	 * @param args
	 * @param func
	 * @return
	 */
	@Static(name = "lisp::defmacro", doc = "m_defmac")
	@SpecialOp
	public static tT DEFMACRO( //
			@Arg(name = "lisp::name") tSYMBOL name, //
			@Arg(name = "lisp::args") tLIST args, //
			@Rest(name = "lisp::macro") tT... macro)
	{
		tFUNCTION def = new cMACRO_FUNCTION(name, args, list((Object[]) macro));
		((tSYMBOL) name).SET_SYMBOL_FUNCTION(def);
		return name;
	}

	/*********************************************************************************
	 * @param name
	 * @param argList
	 * @param func
	 * @return
	 */
	@Static(name = "lisp::defun", doc = "m_defun")
	@SpecialOp
	public static tT DEFUN( //
			@Arg(name = "lisp::name") tSYMBOL name, //
			@Arg(name = "lisp::args") tLIST argList, //
			@Rest(name = "lisp::func") tT... func)
	{
		if (name instanceof tCONS)
		{
			// (setf func) definition
			if (((tLIST) name).CAR() != sym("lisp::setf")
					|| ((tLIST) name).LENGTH() != 2
					|| !(((tLIST) name).CDR().CAR() instanceof tSYMBOL))
			{
				cCELL.ERROR(
						"DEFUN : Function name as list should have the form (SEFT name) : ~s",
						name);
			}

			// Setf func to manage
			tT newFunc = name.CDR().CAR();
			if (!(newFunc instanceof tSYMBOL))
			{
				cCELL.ERROR(
						"DEFUN : Function name as list should have the form (SEFT name) : ~s",
						name);
			}

			// setf writer symbol
			tSYMBOL setfFunc = sym(((tSYMBOL) newFunc).SYMBOL_PACKAGE(),
					"setf-" + ((tSYMBOL) newFunc).SYMBOL_NAME());

			// setf writer function
			tFUNCTION def = new cBLOCK_FUNCTION(setfFunc, (tLIST) argList,
					list((Object[]) func));

			((tSYMBOL) setfFunc).SET_SYMBOL_FUNCTION(def);

			//
			((tSYMBOL) newFunc).SET_GET(setfKey, setfFunc);

			return newFunc;
		}

		if (!(name instanceof tSYMBOL))
		{
			throw new LispException("Function name not a symbol " + name);
		}

		tFUNCTION def = new cBLOCK_FUNCTION((tSYMBOL) name, argList,
				list((Object[]) func));
		((tSYMBOL) name).SET_SYMBOL_FUNCTION(def);

		return name;
	}

	/***********************************************************************
	 * @param aFunc
	 * @return
	 */
	@Static(name = "lisp::function", doc = "s_fn")
	@Mac(prefix = "#'")
	@SpecialOp
	public static tT FUNCTION( //
			@Arg(name = "lisp::func") tT aFunc)
	{
		tT res = NIL;

		// System.out.println("function:" + args[0]);

		if (aFunc instanceof tFUNCTION)
		{
			res = aFunc;
		}
		else if (aFunc instanceof tSYMBOL)
		{
			tFUNCTION func = ((tSYMBOL) aFunc).SYMBOL_FUNCTION();
			if (func == null)
			{
				// System.out.println(cl.dump());
				throw new LispException(aFunc + " is not a function");
			}
			res = func;
		}
		else if (aFunc instanceof tCONS)
		{
			// anonymous function (lambda)
			res = getLambda(aFunc);
			if (res == null)
			{
				throw new LispException(aFunc
						+ " is not a correct lambda function");
			}
		}
		else
		{
			throw new LispException(aFunc + " is not a function");
		}

		// System.out.println("function (real):" + res);
		return res;
	}

	/**
	 * Read a lambda form
	 * 
	 * @param lambda
	 * @return
	 */
	private static cLAMBDA_FUNCTION getLambda(tT lambda)
	{
		tLIST walk = (tLIST) lambda;
		tT func = NIL;
		tT args = NIL;

		// test if list is correct
		if (!(lambda instanceof tCONS) || ((tCONS) lambda).LENGTH() < 3)
			return null;

		// test lambda header
		if (!(walk.CAR() instanceof tSYMBOL)
				|| !((tSYMBOL) walk.CAR()).SYMBOL_NAME().equalsIgnoreCase(
						"lambda"))
			return null;

		walk = (tLIST) walk.CDR();
		args = walk.CAR();
		func = ((tLIST) walk.CDR());

		if (!(args instanceof tLIST))
			return null;

		System.out.println("args:" + args + " func:" + func);

		return new cLAMBDA_FUNCTION(key("lamda"), (tLIST) args, (tLIST) func);
	}

	/**************************************************************************
	 * @param tag
	 * @return
	 */
	@Static(name = "lisp::go", doc = "s_go")
	@SpecialOp
	public static tT GO( //
			@Arg(name = "lisp::tag") tT tag)
	{
		e.go(tag);
		return NIL;
	}

	/************************************************************
	 * @param cond
	 * @param then
	 * @param or
	 * @return
	 */
	@Static(name = "lisp::if", doc = "s_if")
	@SpecialOp
	public static tT[] IF( //
			@Arg(name = "lisp::cond") tT cond, //
			@Arg(name = "lisp::then") tT then, //
			@Opt(name = "lisp::else") tT or)
	{
		return cond.EVAL()[0] != NIL ? then.EVAL() : or.EVAL();
	}

	/*********************************************************************
	 * @param cell
	 * @return
	 */
	@Static(name = "lisp::quote", doc = "s_quote")
	@SpecialOp
	public static tT QUOTE( //
			@Arg(name = "lisp::cell") tT cell)
	{
		return cell;
	}

	/*********************************************************************
	 * @param tag
	 * @param value
	 * @return
	 */
	@Static(name = "lisp::return-from", doc = "s_ret_fr")
	@SpecialOp
	public static tT RETURN_FROM( //
			@Arg(name = "lisp::tag") tT tag, //
			@Opt(name = "lisp::value") tT value)
	{
		e.returnFrom(tag, value.EVAL());
		return NIL;
	}

	/*************************************************************
	 * @param place
	 * @param value
	 * @return
	 */
	@Static(name = "lisp::setf", doc = "m_setf_")
	@SpecialOp
	public static tT[] SETF( //
			@Arg(name = "lisp::place") tT place, //
			@Arg(name = "lisp::value") tT value)
	{
		// Evaluate value to set
		value = value.EVAL()[0];

		// if symbol it's a SETQ
		if (place instanceof tSYMBOL)
		{
			return new tT[]
			{ SETQ(place, value) };
		}

		// test type validity for place
		if (!(place instanceof tCONS))
		{
			cCELL.ERROR("FSET : Bad type for place : ~s", place);
			return null;
		}

		// get place function
		tT func = place.CAR();
		if (!(func instanceof tSYMBOL))
		{
			cCELL.ERROR("FSET : Bad function for place : ~s", func);
			return null;
		}

		// get setf function
		tT newFunc = ((tSYMBOL) func).GET(setfKey, NIL);
		if (newFunc instanceof tNULL)
		{
			cCELL.ERROR("FSET : Function for place has no FSET definition: ~s",
					func);
			return null;
		}

		// write to place
		tLIST test = cons(newFunc,
				((tCONS) ((tLIST) place.CDR().copy()).APPEND(list(value))));
		return test.EVAL();
	}

	/*******************************************************************************
	 * @param args
	 * @return
	 */
	@Static(name = "lisp::setq", doc = "s_setq")
	@SpecialOp
	public static tT SETQ( //
			@Rest(name = "args") tT... args)
	{
		// we are in the block part values should be found in environment
		tT list = list((Object[]) args);

		if (list instanceof cNIL)
		{
			// no values to set
			return NIL;
		}

		tT var = null;
		tT val = null;

		// loop on each pair var, value
		for (tT cell : (tLIST) list)
		{
			if (var == null)
			{
				// get var
				var = cell;
				if (!(var instanceof tSYMBOL))
				{
					cCELL.ERROR("SETQ : ~s should be a cSYMBOL", var);
				}
			}
			else
			{
				// get and set value
				val = cell.EVAL()[0];
				((tSYMBOL) var).SET_SYMBOL_VALUE(val);

				// prepare to get next variable
				var = null;
			}
		}

		return val;
	}

	/*************************************************************************
	 * @param func
	 * @return
	 */
	@Static(name = "lisp::tagbody", doc = "s_tagbod")
	@SpecialOp
	public static tT TAGBODY( //
			@Rest(name = "func") tT... func)
	{
		Arguments args = new Arguments(NIL, NIL, list((Object[]) func));
		args.pushBlock(NIL);
		e.tagBody();
		e.exec();
		e.popBlock();
		return NIL;
	}

	/*******************************************************************************
	 * @param name
	 * @param block
	 * @return
	 */
	@Static(name = "lisp::block", doc = "s_block")
	@SpecialOp
	public static tT[] BLOCK( //
			@Arg(name = "lisp::name") tSYMBOL name, //
			@Rest(name = "lisp::block") tT... block)
	{
		Arguments args = new Arguments(name, NIL, list((Object[]) block));
		args.pushBlock(NIL);
		tT res[] = e.exec();
		e.popBlock();
		return res;
	}

	/*************************************************************
	 * @param arg
	 * @param func
	 * @return
	 */
	@Static(name = "lisp::let", doc = "s_let_l")
	@SpecialOp
	public static tT[] LET( //
			@Arg(name = "lisp::args") tLIST args, //
			@Rest(name = "lisp::func") tT... func)
	{
		Arguments arguments = new Arguments(NIL, args, list((Object[]) func));
		arguments.pushBlock(NIL);
		tT res[] = e.exec();
		e.popBlock();
		return res;
	}

	/*********************************************************************
	 * @param first
	 * @param rest
	 * @return
	 */
	@Static(name = "lisp::prog1", doc = "m_prog1c")
	@SpecialOp
	public static tT PROG1( //
			@Arg(name = "lisp::first") tT first, //
			@Rest(name = "lisp::rest") tT... rest)
	{
		Arguments arguments = new Arguments(NIL, NIL, list((Object[]) rest));
		arguments.pushBlock(NIL);
		tT res = first.EVAL()[0];
		e.exec();
		e.popBlock();
		return res;
	}

	/*********************************************************************
	 * @param first
	 * @param rest
	 * @return
	 */
	@Static(name = "lisp::multiple-value-prog1", doc = "s_mult_1")
	@SpecialOp
	public static tT[] MULTIPLE_VALUE_PROG1( //
			@Arg(name = "lisp::first") tT first, //
			@Rest(name = "lisp::rest") tT... rest)
	{
		Arguments arguments = new Arguments(NIL, NIL, list((Object[]) rest));
		arguments.pushBlock(NIL);
		tT[] res = new tT[] {};
		try
		{
			res = first.EVAL();
			e.exec();
		}
		finally
		{
			e.popBlock();
		}
		return res;
	}

	/*********************************************************************
	 * @param func
	 * @param block
	 * @return
	 */
	@Static(name = "lisp::multiple-value-call", doc = "s_multip")
	@SpecialOp
	public static tT[] MULTIPLE_VALUE_CALL( //
			@Arg(name = "lisp::func") tT func, //
			@Rest(name = "lisp::block") tT... block)
	{
		tLIST res = NIL;
		tT function = func.EVAL()[0];

		if (!(function instanceof tFUNCTION))
			throw new LispException("Not a function : " + function);

		for (int i = 0; i < block.length; i++)
		{
			tT[] eval = block[i].EVAL();
			for (int j = 0; j < eval.length; j++)
			{
				res = (tLIST) res.APPEND(list(quote(eval[j])));
			}
		}

		tT cmd = list(function).APPEND(res);

		System.out.println(cmd);

		return cmd.EVAL();
	}

	/*********************************************************************
	 * @param block
	 * @return
	 */
	@Static(name = "lisp::progn", doc = "s_progn")
	@SpecialOp
	public static tT[] PROGN( //
			@Rest(name = "lisp::block") tT... block)
	{
		Arguments arguments = new Arguments(NIL, NIL, list((Object[]) block));
		arguments.pushBlock(NIL);
		tT[] res = e.exec();
		e.popBlock();
		return res;
	}

	/*********************************************************************
	 * @param name
	 * @param args
	 * @param block
	 * @return
	 */
	@Static(name = "lisp::prog", doc = "m_prog_")
	@SpecialOp
	public static tT[] PROG( //
			@Arg(name = "lisp::name") tSYMBOL name, //
			@Arg(name = "lisp::args") tLIST args, //
			@Rest(name = "lisp::block") tT... block)
	{
		Arguments arguments = new Arguments(name, args, list((Object[]) block));
		arguments.pushBlock(NIL);
		tT[] res = e.exec();
		e.popBlock();
		return res;
	}

}
