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
// IP 3 mars 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.packages.common_lisp;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.engine.*;
import aloyslisp.internal.flowcontrol.*;
import static aloyslisp.core.L.*;

/**
 * SpecialOperators
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class SpecialOperators extends cCELL
{
	/*********************************************************************************
	 * @param name
	 * @param args
	 * @param func
	 * @return
	 */
	@aFunction(name = "defmacro", doc = "m_defmac")
	@aSpecialOperator
	public static tT DEFMACRO( //
			@aArg(name = "name") tSYMBOL name, //
			@aArg(name = "args") tLIST args, //
			@aRest(name = "macro") tT... macro)
	{
		tFUNCTION def = new cLAMBDA_FUNCTION(name, args,
				list((Object[]) macro), true, true);
		((tSYMBOL) name).SET_SYMBOL_FUNCTION(def);
		return name;
	}

	/*********************************************************************************
	 * @param name
	 * @param argList
	 * @param func
	 * @return
	 */
	@aFunction(name = "defun", doc = "m_defun")
	@aSpecialOperator
	public static tT DEFUN( //
			@aArg(name = "name") tSYMBOL name, //
			@aArg(name = "args") tLIST argList, //
			@aRest(name = "func") tT... function)
	{
		if (name instanceof tCONS)
		{
			// (setf func) definition
			if (((tLIST) name).CAR() != sym("setf")
					|| ((tLIST) name).LENGTH() != 2
					|| !(((tLIST) name).CDR().CAR() instanceof tSYMBOL))
			{
				cCELL.ERROR(
						"DEFUN : aFunction name as list should have the form (SEFT name) : ~s",
						name);
			}

			// Setf func to manage
			tT newFunc = name.CDR().CAR();
			if (!(newFunc instanceof tSYMBOL))
			{
				cCELL.ERROR(
						"DEFUN : aFunction name as list should have the form (SEFT name) : ~s",
						name);
			}

			// setf writer symbol
			tSYMBOL setfFunc = sym(((tSYMBOL) newFunc).SYMBOL_PACKAGE(),
					"setf-" + ((tSYMBOL) newFunc).SYMBOL_NAME());

			// setf writer function
			tFUNCTION def = new cLAMBDA_FUNCTION(setfFunc, (tLIST) argList,
					list((Object[]) function), false, false);
			((tSYMBOL) setfFunc).SET_SYMBOL_FUNCTION(def);
			((tSYMBOL) newFunc).SET_GET(setfKey, setfFunc);

			return newFunc;
		}

		if (!(name instanceof tSYMBOL))
		{
			throw new LispException("aFunction name not a symbol " + name);
		}

		tFUNCTION def = new cLAMBDA_FUNCTION(name, (tLIST) argList,
				list((Object[]) function), false, false);
		((tSYMBOL) name).SET_SYMBOL_FUNCTION(def);

		return name;
	}

	/***********************************************************************
	 * @param aFunc
	 * @return
	 */
	@aFunction(name = "function", doc = "s_fn")
	@aMac(prefix = "#'")
	@aSpecialOperator
	public static tFUNCTION FUNCTION( //
			@aArg(name = "func") tT aFunc)
	{
		tT res = NIL;

		// System.out.println("FUNCTION(" + aFunc+")");
		// e.ENV_DUMP();

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
			res = getLambda(null, aFunc);
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
		return (tFUNCTION) res;
	}

	/**
	 * Read a lambda form
	 * 
	 * @param lambda
	 * @return
	 */
	private static cLAMBDA_FUNCTION getLambda(tSYMBOL name, tT lambda)
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

		// System.out.println("args:" + args + " func:" + func);

		return new cLAMBDA_FUNCTION(name, (tLIST) args, (tLIST) func, false,
				false);
	}

	/**************************************************************************
	 * @param tag
	 * @return
	 */
	@aFunction(name = "go", doc = "s_go")
	@aSpecialOperator
	public static tT GO( //
			@aArg(name = "tag") tT tag)
	{
		if (e.ENV_TAG_TST(tag) == null)
			throw new LispException("GO to unreachable label : " + tag);

		throw new GOTO_CONDITION(tag);
	}

	/************************************************************
	 * @param cond
	 * @param then
	 * @param or
	 * @return
	 */
	@aFunction(name = "if", doc = "s_if")
	@aSpecialOperator
	public static tT[] IF( //
			@aArg(name = "cond") tT cond, //
			@aArg(name = "then-clause") tT then, //
			@aOpt(name = "else-clause") tT or)
	{
		return cond.EVAL()[0] != NIL ? then.EVAL() : or.EVAL();
	}

	/*********************************************************************
	 * @param cell
	 * @return
	 */
	@aFunction(name = "quote", doc = "s_quote")
	@aSpecialOperator
	public static tT QUOTE( //
			@aArg(name = "cell") tT cell)
	{
		return cell;
	}

	/*********************************************************************
	 * @param tag
	 * @param value
	 * @return
	 */
	@aFunction(name = "return-from", doc = "s_ret_fr")
	@aSpecialOperator
	public static tT RETURN_FROM( //
			@aArg(name = "tag") tSYMBOL tag, //
			@aOpt(name = "value") tT value)
	{
		if (tag != NIL && e.ENV_BLOCK_TST(tag) == null)
			throw new LispException("No return block for (RETURN-FROM " + tag
					+ ")");

		throw new RETURN_CONDITION(tag, value.EVAL());
	}

	/*************************************************************
	 * @param place
	 * @param value
	 * @return
	 */
	@aFunction(name = "setf", doc = "m_setf_")
	@aSpecialOperator
	public static tT[] SETF( //
			@aArg(name = "place") tT place, //
			@aArg(name = "value") tT value)
	{
		// if symbol it's a SETQ
		if (place instanceof tSYMBOL)
		{
			return new tT[]
			{ SETQ(place, value.EVAL()[0]) };
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
			cCELL.ERROR("FSET : aFunction for place has no FSET definition: ~s",
					func);
			return null;
		}

		// write to place
		tLIST test = (tLIST) ((tLIST) list(newFunc).APPEND(list(value)))
				.APPEND(place.CDR());
		// System.out.println("Setf tranform = " + test);
		return test.EVAL();
	}

	/*******************************************************************************
	 * @param args
	 * @return
	 */
	@aFunction(name = "setq", doc = "s_setq")
	@aSpecialOperator
	public static tT SETQ( //
			@aRest(name = "args") tT... args)
	{
		// we are in the block part values should be found in environment
		tT list = list((Object[]) args);

		if (list instanceof cNULL)
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
				// debug dump
				// e.ENV_DUMP();

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
	@aFunction(name = "tagbody", doc = "s_tagbod")
	@aSpecialOperator
	public static tT TAGBODY( //
			@aRest(name = "func") tT... func)
	{
		cENV_TAG tag = new cENV_TAG(func);
		tag.ENV_PUSH();
		try
		{
			tag.EVAL();
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		finally
		{
			tag.ENV_POP();
		}
		return NIL;
	}

	public static final tSYMBOL	BLOCK	= sym("block");

	/*******************************************************************************
	 * @param name
	 * @param block
	 * @return
	 */
	@aFunction(name = "block", doc = "s_block")
	@aSpecialOperator
	public static tT[] BLOCK( //
			@aArg(name = "name") tSYMBOL name, //
			@aRest(name = "block") tT... blocks)
	{
		cENV_BLOCK block = new cENV_BLOCK(name, blocks);
		block.ENV_PUSH();
		tT[] res = new tT[]
		{ NIL };
		try
		{
			res = block.EVAL();
		}
		catch (RETURN_CONDITION ret)
		{
			if (ret.TST_RETURN(name))
				return ret.RETURN_VALUE();

			throw ret;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		finally
		{
			block.ENV_POP();
		}

		return res;
	}

	public static final tSYMBOL	LET	= sym("let");

	/*************************************************************
	 * @param arg
	 * @param func
	 * @return
	 */
	@aFunction(name = "let", doc = "s_let_l")
	@aSpecialOperator
	public static tT[] LET( //
			@aArg(name = "args") tLIST args, //
			@aRest(name = "func") tT... function)
	{
		tLIST func = cAPI.API_PARSE_FUNC(list((Object[]) function));
		tT doc = func.CAR();
		tLIST decl = (tLIST) func.CDR().CAR();
		func = (tLIST) func.CDR().CDR().CAR();
		tT res[] = new tT[]
		{ NIL };

		cAPI arguments = new cAPI(args, doc, decl);
		tENV env = new cENV_LET();
		env.ENV_PUSH();
		try
		{
			arguments.API_PUSH_ENV(NIL, env);
			res = PROGN(func.VALUES_LIST());
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		finally
		{
			env.ENV_POP();
		}
		return res;
	}

	/*********************************************************************
	 * @param first
	 * @param rest
	 * @return
	 */
	@aFunction(name = "prog1", doc = "m_prog1c")
	@aSpecialOperator
	public static tT PROG1( //
			@aArg(name = "first") tT first, //
			@aRest(name = "rest") tT... rest)
	{
		tT res = first.EVAL()[0];
		PROGN(rest);
		return res;
	}

	/*********************************************************************
	 * @param first
	 * @param rest
	 * @return
	 */
	@aFunction(name = "multiple-value-prog1", doc = "s_mult_1")
	@aSpecialOperator
	public static tT[] MULTIPLE_VALUE_PROG1( //
			@aArg(name = "first") tT first, //
			@aRest(name = "rest") tT... rest)
	{
		tT[] res = first.EVAL();
		PROGN(rest);
		return res;
	}

	/*********************************************************************
	 * @param func
	 * @param block
	 * @return
	 */
	@aFunction(name = "multiple-value-call", doc = "s_multip")
	@aSpecialOperator
	public static tT[] MULTIPLE_VALUE_CALL( //
			@aArg(name = "func") tT func, //
			@aRest(name = "block") tT... block)
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

		return ((tFUNCTION) function).FUNCALL(res);
	}

	/*********************************************************************
	 * @param block
	 * @return
	 */
	@aFunction(name = "progn", doc = "s_progn")
	@aSpecialOperator
	public static tT[] PROGN( //
			@aRest(name = "block") tT... block)
	{
		tT[] res = new tT[]
		{ NIL };
		cENV_PROGN progn = new cENV_PROGN(block);
		progn.ENV_PUSH();
		try
		{
			res = progn.EVAL();
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		finally
		{
			progn.ENV_POP();
		}
		return res;
	}

	/*********************************************************************
	 * @param name
	 * @param args
	 * @param block
	 * @return
	 */
	@aFunction(name = "prog", doc = "m_prog_")
	@aSpecialOperator
	public static tT[] PROG( //
			@aArg(name = "name") tSYMBOL name, //
			@aArg(name = "args") tLIST args, //
			@aRest(name = "block") tT... blocks)
	{
		// TODO this is a macro
		return list(BLOCK, name,
				list(LET, args).APPEND(list((Object[]) blocks))).EVAL();
	}

	/*********************************************************************
	 * @param name
	 * @param args
	 * @param block
	 * @return
	 */
	@aFunction(name = "unwind-protect", doc = "s_unwind")
	@aSpecialOperator
	public static tT[] UNWIND_PROTECT( //
			@aArg(name = "protected-form") tT prot, //
			@aRest(name = "block") tT... block)
	{
		RuntimeException ex = null;
		tT[] res = new tT[] {};

		try
		{
			// Eval protected form
			res = prot.EVAL();
		}
		catch (RuntimeException e)
		{
			ex = e;
		}
		finally
		{
			// do cleanup
			try
			{
				PROGN(block);
			}
			catch (LispException e)
			{
				// Flow control are not managed
				ex = e;
			}
		}

		if (ex != null)
		{
			// re-throw exception
			throw ex;
		}

		return res;
	}

	/**
	 * @param tag
	 * @param block
	 * @return
	 */
	@aFunction(name = "catch", doc = "s_catch")
	@aSpecialOperator
	public static tT[] CATCH( //
			@aArg(name = "catch-tag") tT tag, //
			@aRest(name = "block") tT... block)
	{
		tT[] res = new tT[] {};

		try
		{
			PROGN(block);
		}
		catch (LispException e)
		{
			throw e;
		}
		catch (LispFlowControl e)
		{
			if (e instanceof THROW_CONDITION
					&& ((THROW_CONDITION) e).TST_CATCH(tag))
			{
				return ((THROW_CONDITION) e).CATCH_VALUE();
			}

			throw e;
		}

		return res;
	}

}
