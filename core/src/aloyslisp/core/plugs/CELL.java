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
// IP 9 sept. 2010 Creation
// IP UB12 Update commentaries
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.sequences.*;

/**
 * CELL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class CELL implements tT
{

	/**
	 * Enable trace() to be active
	 */
	protected boolean	trace	= false;

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#eval()
	 */
	public tT[] EVAL()
	{
		tT[] res = new CELL[1];
		res[0] = this;
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#describe()
	 */
	public String DESCRIBE()
	{
		return toString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#compile()
	 */
	public String COMPILE()
	{
		// throw new LispException("Error no mean for Symbol(" + value +
		// ") compilation");
		return "/* Error no mean for " + getClass().getCanonicalName()
				+ "() */" + NIL.COMPILE();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#evalCompile()
	 */
	public String EVALCOMPILE()
	{
		return "res = " + COMPILE() + ".eval();";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#macroExpand()
	 */
	public tT[] MACROEXPAND()
	{
		tT[] res = MACROEXPAND_1();
		if (res[1] == NIL)
			return res;

		while (res[1] != NIL)
		{
			res = res[0].MACROEXPAND_1();
		}
		res[1] = T;
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#macroExpand1()
	 */
	public tT[] MACROEXPAND_1()
	{
		return new tT[]
		{ this, NIL };
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#car()
	 */
	public tT CAR()
	{
		if (!(this instanceof tCONS))
		{
			throw new LispErrorFunctionCannotApplyOn("car", this);
		}
		return ((tCONS) this).CAR();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#cdr()
	 */
	public tT CDR()
	{
		if (!(this instanceof tCONS))
		{
			throw new LispErrorFunctionCannotApplyOn("cdr", this);
		}
		return ((tCONS) this).CDR();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#eq(aloyslisp.core.types.tT)
	 */
	public boolean EQ(tT cell)
	{
		return this == cell;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#eql(aloyslisp.core.types.tT)
	 */
	public boolean EQL(tT cell)
	{
		return this == cell;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#equal(aloyslisp.core.types.tT)
	 */
	public boolean EQUAL(tT cell)
	{
		return EQL(cell);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#equal(aloyslisp.core.types.tT)
	 */
	public boolean EQUALP(tT cell)
	{
		return EQUAL(cell);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#istype(aloyslisp.core.types.tT)
	 */
	public boolean pISTYPE(tSYMBOL type)
	{
		// get name of type
		String name = "";
		if (type instanceof tSYMBOL)
			name = "t"
					+ ((tSYMBOL) type).SYMBOL_NAME().toUpperCase()
							.replace('-', '_');
		else
			throw new LispException("type should be defined by a symbol : "
					+ type);

		try
		{
			// Test with assignation ability
			return Class.forName("aloyslisp.core.types." + name)
					.isAssignableFrom(this.getClass());
		}
		catch (ClassNotFoundException e)
		{
			throw new LispException("Unknown type : " + name);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#printable()
	 */
	@Override
	public String toString()
	{
		return "#<" + getClass().getSimpleName() + ">";
	}

	/**
	 * Write trace on environment
	 * 
	 * @param msg
	 */
	protected void trace(String msg)
	{
		if (trace)
			System.err.println(msg);
	}

	// MAIN GLOBAL LISP FUNCTIONS
	/**
	 * PUBLIC STATIC FUNCTION
	 */
	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#copy()
	 */
	public tT copy()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#CONSTANTP()
	 */
	public boolean CONSTANTP()
	{
		return true;
	}

}
