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

package aloyslisp.core.plugs;

import static aloyslisp.commonlisp.L.*;
import aloyslisp.core.conditions.LispErrorFunctionCannotApplyOn;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.exec.*;
import aloyslisp.core.functions.tFUNCTION;
import aloyslisp.core.math.*;
import aloyslisp.core.sequences.tLIST;
import aloyslisp.core.sequences.tSTRING;

/**
 * SYMBOL
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class SYMBOL extends CELL implements tSYMBOL
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
	protected boolean	exported	= false;

	/**
	 * 
	 */
	protected tLIST		pList		= null;

	/**
	 * @param name
	 */
	public SYMBOL(String name)
	{
		// trace = true;
		this.name = name;
		this.value = null;
		pack = currPackage();
	}

	/**
	 * @param name
	 * @param pack
	 */
	public SYMBOL(String name, tPACKAGE pack)
	{
		// trace = true;
		this.name = name;
		this.value = null;
		this.pack = pack;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#image()
	 */
	public tSYMBOL copy()
	{
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#setExported(boolean)
	 */
	public tSYMBOL setExported(boolean exported)
	{
		this.exported = exported;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#isExported()
	 */
	public boolean isExported()
	{
		return exported;
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

		for (tT cell : pList)
		{
			if (sym == null)
			{
				// read symbol
				sym = cell.CAR();
				if (name.EQ(sym))
					return (tLIST) cell;
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
			tLIST nc = (tLIST) list(name, data).APPEND(pList.copy());
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

		// remove first
		if (pList.CAR() == name)
		{
			pList = (tLIST) pList.CDR().CDR();
		}

		// Walk through
		tLIST previous = null;
		for (tT cell : pList)
		{
			if (sym == null)
			{
				// read symbol
				sym = cell.CAR();
				if (name.EQ(sym))
				{
					// Supress entry
					previous.SET_CDR(cell.CDR().CDR());

					// return suppressed with following defs
					return (tLIST) cell;
				}
			}
			else
			{
				// skip value
				sym = null;
				previous = (tLIST) cell;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#set(aloyslisp.core.types.tT)
	 */
	public tSYMBOL SET_SYMBOL_VALUE(tT value)
	{
		if (e.writeVal(this, value) != null)
		{
			return this;
		}

		if (CONSTANTP())
		{
			throw new LispException("Can't set a constant value");
		}

		// in case of special we search in the environment *variables*
		if (isSpecial())
		{
			Symbol atom = getAll(this);

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
		if (isSpecial())
		{
			// If we fall back here, normal set
			Symbol res = getAll(this);
			if (res != null && !res.SYMBOL_NAME().equals(this.SYMBOL_NAME()))
				return res.SYMBOL_VALUE();
		}
		else
		{
			tT val = e.readVal(this);
			if (val != null)
				return val;
		}

		if (value == null)
		{
			e.trace();
			throw new LispException("Symbol " + this + " has no value");
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
		if (isSpecial())
		{
			Symbol res = getAll(this);
			if (res != null && !res.SYMBOL_NAME().equals(this.SYMBOL_NAME()))
				return res.SYMBOL_VALUE();
		}
		else
		{
			tT val = e.readVal(this);
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
		if (isSpecial())
		{
			Symbol res = getAll(this);
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
		Symbol func = e.read(this);

		if (func != null)
		{
			return func.SET_SYMBOL_VALUE(function);
		}

		this.function = function;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#fGet()
	 */
	public tFUNCTION SYMBOL_FUNCTION()
	{
		Symbol func = e.read(this);

		if (func != null)
		{
			return func.SYMBOL_FUNCTION();
		}

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
		Symbol func = e.read(this);

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
		Symbol atom = e.read(this);

		if (atom != null)
		{
			return atom.SET_SYMBOL_VALUE(null);
		}

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
	public tSYMBOL SET_SYMBOL_PACKAGE(tT pack)
	{
		if (pack != null)
			pack = FIND_PACKAGE(pack);

		if (this.pack == pack)
			return this;

		if (this.pack != null)
		{
			this.pack.remove(SYMBOL_NAME());
		}
		if (pack != null)
		{
			if (pack == key)
			{
				value = this;
				constant = true;
			}

			((tPACKAGE) pack).INTERN(name).SET_SYMBOL_VALUE(this);
		}
		this.pack = (tPACKAGE) pack;
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
	 * @see aloyslisp.core.types.tSYMBOL#unsetPack()
	 */
	public tSYMBOL UNINTERN()
	{
		// unintern symbol
		if (pack != null)
		{
			pack.remove(SYMBOL_NAME());
		}
		pack = null;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#eval()
	 */
	public tT[] EVAL()
	{
		tT[] res = new CELL[1];
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
	 * @see aloyslisp.core.plugs.CELL#printable()
	 */
	public String printable()
	{
		if (pack == null)
			return "#:" + name;

		boolean current = currPackage() == pack;
		boolean reachable = current || currPackage().isInUseList(pack);

		if (pack == key)
			return (":" + name);

		return (current ? "" : pack + (reachable ? ":" : "::")) + name;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#e(aloyslisp.core.types.tT[])
	 */
	public tT[] e(Object... args)
	{
		tFUNCTION func = SYMBOL_FUNCTION();
		tT a = NIL;
		if (args.length > 0)
			a = list(args);
		return cons(func, a).EVAL();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#compile()
	 */
	public String COMPILE()
	{
		return "sym(\"" + pack.getName() + "::" + SYMBOL_NAME() + "\")";
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#setSpecial(boolean)
	 */
	public tSYMBOL setSpecial(boolean special)
	{
		Symbol atom = e.read(this);
		if (atom != null)
			return atom.setSpecial(special);

		this.special = special;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#isSpecial()
	 */
	public boolean isSpecial()
	{
		Symbol atom = e.read(this);
		if (atom != null)
			return atom.isSpecial();

		return special;
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
	 * @see aloyslisp.core.plugs.CELL#describe()
	 */
	public String DESCRIBE()
	{
		return printable() + " " + pack + " " + getValue() + " " + fGetValue()
				+ " " + pList;
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

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tSYMBOL#special_form_p()
	 */
	@Override
	public boolean SPECIAL_FORM_P()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param args
	 * @return
	 */
	public static tT EXPORT(tT args)
	{
		return EXPORT(args, currPackage());
	}

	/**
	 * @param symbol
	 * @param pack
	 * @return
	 *         IMPLEMENT EXPORT
	 */
	public static tT EXPORT(tT symbol, tT pack)
	{
		if (pack != NIL)
			pack = PACKAGE.find_package(pack);

		// tPACKAGE p = (tPACKAGE) ((pack == NIL) ? currPackage() : pack);

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

}
