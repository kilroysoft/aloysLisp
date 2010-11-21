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
// IP 13 sept. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import static aloyslisp.commonlisp.L.*;

import aloyslisp.core.common.*;
import aloyslisp.core.exec.Arguments;
import aloyslisp.core.types.*;

/**
 * FUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class FUNCTION extends CELL implements tFUNCTION
{
	public Arguments	intern	= null;
	public String		mac		= null;

	/**
	 * Creation with arguments detail
	 * 
	 * @param def
	 */
	public FUNCTION(tSYMBOL name, tLIST args, tLIST func)
	{
		intern = new Arguments(name, args, func);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.IFunc#exec(aloyslisp.core.plugs.CELL[])
	 */
	@Override
	public tT[] e(tT... args)
	{
		if (args.length == 0)
			return exec(NIL);
		return exec(new CONS(args));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.functions.IFunc#exec(aloyslisp.core.plugs.collections
	 * .IList)
	 */
	public tT[] exec(tLIST args)
	{
		tT[] res = null;

		// Evaluate args if appropriate
		if (!(this instanceof tSPECIAL_OPERATOR || this instanceof tMACRO_FUNCTION))
		{
			args = evalArgs(args);
		}

		// Push a new closure except if it's a lisp function
		// defun function will push the closure
		if (this instanceof tDEFUN_FUNCTION)
			e.newClosure();

		intern.pushBlock(args);

		// trace arguments
		// System.out.println("\nv----TRACE---->" + this + "\n" + e.trace(false)
		// + "^------------");

		res = impl();

		e.popBlock();

		// If it's a special form we have to manage the global environment part
		// of the code. For example sSETQ should have access to the local
		// variable to be set in the closure... but it shouldn't be disturbed by
		// the local variables of the impl() part variables.
		// (sSETQ &rest list), so (sSETQ list 10) should not write the arguments
		// list of sSETQ... which is (list 10) :D
		if (this instanceof tSPECIAL_OPERATOR)
			return ((tSPECIAL_OPERATOR) this).implSpecial(res);
		else
			return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#copy()
	 */
	@Override
	public tT copy()
	{
		return this;
	}

	/**
	 * @return
	 */
	public String compiledName()
	{
		String name = getClass().getSimpleName();
		name = name.replace("h", "-");
		name = name.replace("s", "*");
		name = name.replace("p", "%");
		return name.substring(1);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#printable()
	 */
	@Override
	public String printable()
	{
		return "#<" + printableStruct() + ">";
	}

	/**
	 * Internal printable value
	 * 
	 * @return
	 */
	protected String printableStruct()
	{
		return getClass().getSimpleName() + " " + intern.getName();
	}

	/**
	 * Evaluate all args
	 * 
	 * @param args
	 * @return
	 */
	tLIST evalArgs(tLIST args)
	{
		if (args == null)
		{
			throw new LispException("Evaluation : arguments null in " + this);
		}

		tSEQUENCE res = NIL;
		for (tT arg : args)
		{
			arg = arg.CAR();

			if (arg == null)
			{
				throw new LispException("Evaluation : null in arguments in "
						+ this + ": " + args);
			}

			tT out = arg.EVAL()[0];

			if (out == null)
			{
				throw new LispException("Evaluation of " + arg
						+ " leads to null");
			}

			// System.out.println("évalué   = " + out);
			res = new CONS(out, res);
		}
		res = res.REVERSE();
		return (tLIST) res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.functions.IFunc#getName()
	 */
	@Override
	public tSYMBOL getFuncName()
	{
		return intern.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.functions.IFunc#setName(java.lang.String)
	 */
	@Override
	public void setFuncName(tSYMBOL name)
	{
		intern.setName(name);
	}

	/**
	 * Get mandatory and optional arguments by index
	 * 
	 * @param index
	 * @return
	 */
	public tT arg(int index)
	{
		return intern.arg(index);
	}

	/**
	 * Get key arguments by name
	 * 
	 * @param key
	 * @return
	 */
	public tT arg(String key)
	{
		return intern.arg(key);
	}

	/**
	 * Get rest arguments
	 * 
	 * @return
	 */
	public tLIST arg()
	{
		return intern.arg();
	}

	/**
	 * Create a local block context
	 * 
	 * @param name
	 * @param args
	 * @param def
	 */
	public Arguments newBlock(tSYMBOL name, tLIST args, tLIST def, tLIST vals)
	{
		if (!(name == null || name instanceof tSYMBOL))
		{
			throw new LispException("Name of block is not an atom" + name);
		}
		Arguments intern = new Arguments(name, args, def);
		intern.pushBlock(vals);
		return intern;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#coerce(aloyslisp.core.types.tT)
	 */
	@Override
	public tT COERCE(tT type)
	{
		// IMPLEMENT Coerce
		return null;
	}

}
