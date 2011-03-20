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
// IP 19 sept. 2010 Creation
// IP UB12 Update commentaries
// --------------------------------------------------------------------------

package aloyslisp.core.packages;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.engine.*;
import aloyslisp.internal.iterators.*;
import static aloyslisp.core.L.*;

/**
 * cSYMBOL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@BuiltIn(classOf = "symbol", doc = "t_symbol")
public class cSYMBOL extends cCELL implements tSYMBOL
{
	/**
	 * 
	 */
	protected String	name;

	/**
	 * 
	 */
	protected tPACKAGE	pack		= null;

	/**
	 * 
	 */
	protected tT		value		= null;

	/**
	 * 
	 */
	protected tFUNCTION	function	= null;

	/**
	 * 
	 */
	protected tLIST		declare		= NIL;		;

	/**
	 * 
	 */
	protected boolean	constant	= false;

	/**
	 * 
	 */
	protected boolean	special		= false;

	/**
	 * 
	 */
	protected tLIST		pList		= null;

	/**
	 * @param name
	 */
	public cSYMBOL(String name)
	{
		// trace = true;
		this.name = name.toUpperCase();
		if (this.name.equals("NIL"))
			System.out
					.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRRRRGGGGG : "
							+ name);// + " ->" + res.DESCRIBE());

		this.value = null;
		pack = null;
	}

	/**
	 * @param name
	 * @param pack
	 */
	public cSYMBOL(String name, tPACKAGE pack)
	{
		// trace = true;
		this.name = name.toUpperCase();
		if (this.name.equals("NIL"))
			System.out
					.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRRRRGGGGG : "
							+ pack.DESCRIBE());// + " ->" + res.DESCRIBE());
		this.value = null;
		this.pack = pack;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.Cell#image()
	 */
	public tSYMBOL COPY_CELL()
	{
		return this;
	}

	/**
	 * Ret PList attribute position or null
	 * 
	 * @param name
	 * @return
	 */
	private tLIST findPList(tT name)
	{
		tT sym = null;
		if (pList == null)
			return null;

		LISTIterator iter = (LISTIterator) pList.iterator();
		while (iter.hasNext())
		{
			tT cell = iter.next();
			if (sym == null)
			{
				// read symbol
				sym = cell;
				if (name.EQ(sym))
					return iter.getNode();
			}
			else
				// skip value
				sym = null;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#setPList(java.lang.String,
	 * aloyslisp.core.types.tT)
	 */
	public tSYMBOL SET_GET(tT name, tT data)
	{
		tLIST sym = findPList(name);
		if (sym == null)
		{
			// add entry
			if (pList == null || pList == NIL)
			{
				pList = list(name, data);
				return this;
			}
			tLIST nc = (tLIST) list(name, data).APPEND(pList.COPY_CELL());
			pList.SET_CAR(nc.CAR());
			pList.SET_CDR(nc.CDR());
			return (tSYMBOL) name;
		}
		// Write data
		((tLIST) sym.CDR()).SET_CAR(data);
		return (tSYMBOL) name;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#get(aloyslisp.core.types.tT,
	 * aloyslisp.core.types.tT)
	 */
	@Override
	public tT GET(tT name, tT def)
	{
		tLIST sym = findPList(name);

		if (sym != null)
		{
			return sym.CDR().CAR();
		}

		return def;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#unsetPList(java.lang.String)
	 */
	public tT REMPROP(tT name)
	{
		tT sym = null;

		if (pList == null)
			return null;

		LISTIterator iter = (LISTIterator) pList.iterator(true);

		// Walk through
		while (iter.hasNext())
		{
			tT cell = iter.next();
			if (sym == null)
			{
				// read symbol
				sym = cell;
				if (name.EQ(sym))
				{
					iter.remove();
					iter.remove();
					return T;
				}
			}
			else
				// skip value
				sym = null;
		}

		return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#set(aloyslisp.core.types.tT)
	 */
	public tSYMBOL SET_SYMBOL_VALUE(tT value)
	{
		if (e.writeEnv(this, value) != null)
		{
			return this;
		}

		if (CONSTANTP())
		{
			throw new LispException("Can't set a constant value");
		}

		// in case of special we search in the environment *variables*
		if (SPECIALP())
		{
			cDYN_SYMBOL atom = getAll(this);

			// If we fall back here, normal set
			if (atom != null && !atom.SYMBOL_NAME().equals(this.SYMBOL_NAME()))
			{
				return atom.SET_SYMBOL_VALUE(value);
			}
		}

		this.value = value;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#get()
	 */
	public tT SYMBOL_VALUE()
	{
		// keyworks are keywords
		if (pack == key)
			return this;

		// in case of special we search in the environment. Dynamic *variables*.
		if (SPECIALP())
		{
			// If we fall back here, normal set
			cDYN_SYMBOL res = getAll(this);
			if (res != null && res.SYMBOL_NAME().equals(this.SYMBOL_NAME()))
				return res.SYMBOL_VALUE();
		}
		else
		{
			tT val = e.readEnv(this);
			if (val != null)
				return val;
		}

		if (value == null)
		{
			// e.trace();
			throw new LispException("cSYMBOL " + this + " has no value");
		}

		return value;
	}

	/**
	 * Used for describe
	 * 
	 * @return
	 */
	private tT getValue()
	{
		// keyworks are keywords
		if (pack == key)
			return this;

		// in case of special we search in the environment. Dynamic *variables*.
		if (SPECIALP())
		{
			cDYN_SYMBOL res = getAll(this);
			if (res != null && !res.SYMBOL_NAME().equals(this.SYMBOL_NAME()))
				return res.SYMBOL_VALUE();
		}
		else
		{
			tT val = e.readEnv(this);
			if (val != null)
				return val;
		}

		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#unset()
	 */
	public tSYMBOL unset()
	{
		// in case of special we search in the environment *variables*
		if (SPECIALP())
		{
			cDYN_SYMBOL res = getAll(this);
			if (res != null && !res.SYMBOL_NAME().equals(this.SYMBOL_NAME()))
				return res.unset();
		}

		value = null;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#isSet()
	 */
	public boolean BOUNDP()
	{
		return SYMBOL_VALUE() != null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#fSet(aloyslisp.core.types.tFUNCTION)
	 */
	public tSYMBOL SET_SYMBOL_FUNCTION(tFUNCTION function)
	{
		this.function = function;
		// System.out.println("SET_SYMBOL_FUNCTION : " + DESCRIBE());
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#fGet()
	 */
	public tFUNCTION SYMBOL_FUNCTION()
	{
		if (function == null)
		{
			return null;
		}

		return function;
	}

	/**
	 * Used for describe
	 * 
	 * @return
	 */
	private tT fGetValue()
	{
		cDYN_SYMBOL func = e.arg(this);

		if (func != null)
			if (!(func.SYMBOL_VALUE() instanceof tFUNCTION))
				return null;
			else
				return func.SYMBOL_VALUE();

		return function;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#fUnset()
	 */
	public tSYMBOL fUnset()
	{
		function = null;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#isSetf()
	 */
	public boolean FBOUNDP()
	{
		return function != null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#setPack(aloyslisp.core.types.tPACKAGE)
	 */
	public tSYMBOL SET_SYMBOL_PACKAGE(tPACKAGE_DESIGNATOR pack)
	{
		if (pack != null)
			this.pack = (tPACKAGE) cPACKAGE.FIND_PACKAGE(pack);
		else
			this.pack = null;

		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#getPack()
	 */
	public tPACKAGE SYMBOL_PACKAGE()
	{
		return pack;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#eval()
	 */
	public tT[] EVAL()
	{
		tT[] res = new cCELL[1];
		res[0] = SYMBOL_VALUE();
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#getName()
	 */
	public String SYMBOL_NAME()
	{
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#copy(aloyslisp.core.types.tT)
	 */
	public tT copy(tT copyProps)
	{
		// TODO Implement copy of symbols
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#printable()
	 */
	public String toString()
	{
		if (pack == null)
			return "#:" + name;

		boolean current = currPackage() == pack;
		boolean reachable = current || currPackage().isInUseList(pack);

		if (pack == key)
			return (":" + name);

		return (current ? "" : ((tPACKAGE) pack).PACKAGE_NAME()
				+ (reachable ? ":" : "::"))
				+ name;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#e(aloyslisp.core.types.tT[])
	 */
	public tT[] e(Object... args)
	{
		tT a = NIL;
		if (args.length > 0)
			a = list(args);
		return cons(this, a).EVAL();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#compile()
	 */
	public String COMPILE()
	{
		return "sym(\"" + pack.PACKAGE_NAME() + "::" + SYMBOL_NAME() + "\")";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#setSpecial(boolean)
	 */
	public tSYMBOL setSpecial(boolean special)
	{
		cDYN_SYMBOL atom = e.arg(this);
		if (atom != null)
			return atom.SETSPECIAL(special);

		this.special = special;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#setDeclare(aloyslisp.core.types.tLIST)
	 */
	public tSYMBOL setDeclare(tLIST declare)
	{
		// TODO test environment
		this.declare = declare;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#getDeclare()
	 */
	public tLIST getDeclare()
	{
		// TODO test environment
		return declare;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#setConstant(boolean)
	 */
	public tSYMBOL setConstant(boolean constant)
	{
		this.constant = constant;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#isConstant()
	 */
	public boolean CONSTANTP()
	{
		return constant;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#getOrig()
	 */
	public tSYMBOL getOrig()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#describe()
	 */
	public String DESCRIBE()
	{
		tT func = fGetValue();
		return toString() + " " + pack + " " + getValue() + " "
				+ ((func instanceof tFUNCTION) ? func.DESCRIBE() : func) + " "
				+ pList;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#fdefinition()
	 */
	@Override
	public tFUNCTION FDEFINITION()
	{
		// TODO different behaviours
		return SYMBOL_FUNCTION();
	}

	/**
	 * @param symbol
	 * @param pack
	 * @return
	 *         IMPLEMENT EXPORT
	 * @deprecated
	 */
	@Static(name = "export", doc = "f_export")
	public static tT EXPORT( //
			@Arg(name = "symbol") tT symbol, //
			@Opt(name = "pack") tT pack)
	{
		if (pack != NIL)
			pack = (tPACKAGE) cPACKAGE.FIND_PACKAGE((tPACKAGE_DESIGNATOR) pack);

		if (symbol instanceof tLIST)
		{
		}
		if (symbol instanceof tSYMBOL)
		{
		}
		else if (symbol instanceof tSTRING)
		{

		}
		else
		{
			throw new LispErrorFunctionCannotApplyOn("EXPORT", symbol);
		}
		return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#hashCode()
	 */
	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return str(name).hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.tT#KEYWORDP()
	 */
	@Override
	public boolean KEYWORDP()
	{
		return SYMBOL_PACKAGE() == key;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.packages.tSYMBOL#SPECIAL_OPERATOR_P()
	 */
	@Override
	public tT SPECIAL_OPERATOR_P()
	{
		if (!FBOUNDP())
			return NIL;

		if (SYMBOL_FUNCTION().API_SPECIAL())
			return SYMBOL_FUNCTION();
		else
			return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.packages.tSYMBOL#MACRO_FUNCTION()
	 */
	@Override
	public tT MACRO_FUNCTION()
	{
		if (!FBOUNDP())
			return NIL;

		if (SYMBOL_FUNCTION().API_MACRO())
			return SYMBOL_FUNCTION();
		else
			return NIL;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.packages.tSYMBOL#SPECIALP()
	 */
	@Override
	public Boolean SPECIALP()
	{
		return special;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.packages.tSYMBOL#SET_SPECIALP()
	 */
	@Override
	public tSYMBOL SET_SPECIAL(Boolean special)
	{
		this.special = special;
		return this;
	}

}
