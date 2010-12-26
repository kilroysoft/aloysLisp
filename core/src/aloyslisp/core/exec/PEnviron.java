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
// IP 9 oct. 2010 Creation
// IP UB20 Disconnect environment from Package
// --------------------------------------------------------------------------

package aloyslisp.core.exec;

import java.util.*;

import aloyslisp.core.conditions.LispException;
import aloyslisp.core.math.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * PEnviron
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class PEnviron extends CELL implements tATOM,
		Iterable<tSYMBOL>, Map<tSYMBOL, Symbol>
{

	protected tSYMBOL	name;

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#printable()
	 */
	public String printable()
	{
		return "#<ENVIRONMENT " + name + ">";
	}

	/**
	 * @return
	 */
	public tSYMBOL getName()
	{
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Symbol get(Object key)
	{
		return map.get((tSYMBOL) key);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Symbol put(tSYMBOL key, Symbol value)
	{
		// System.out.println("<#" + this.getClass().getSimpleName() + " put(" +
		// key + "," + value + ")");
		return map.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public Symbol remove(Object key)
	{
		return map.remove((tSYMBOL) key);
	}

	/**
	 * @param name
	 * @return
	 */
	public Symbol intern(tSYMBOL name)
	{
		// System.out.println("interm(" + name + ")");
		Symbol atom = get(name);
		if (atom != null)
			return atom;

		atom = new Symbol(name, null);
		put(name, atom);
		return atom;
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public Symbol intern(tSYMBOL name, tT value)
	{
		// System.out.println("interm(" + name + "," + value + ")");
		Symbol atom = intern(name);
		atom.SET_SYMBOL_VALUE(value);
		// System.out.println("interm(" + name + "," + value + ") = " +
		// atom.describe());
		return atom;
	}

	/**
	 * @return
	 */
	public String dump()
	{
		StringBuilder str = new StringBuilder();

		for (Symbol var : map.values())
		{
			str.append(var.SYMBOL_NAME() + " " + var.SYMBOL_PACKAGE() + " "
					+ var.SYMBOL_VALUE() + "\n");
		}

		return str.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#DESCRIBE()
	 */
	public String DESCRIBE()
	{
		StringBuilder str = new StringBuilder(super.DESCRIBE() + "\n");
		str.append(dump());
		return str.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.types.tT#COERCE(aloyslisp.core.types.tT)
	 */
	public tT COERCE(tT type)
	{
		// IMPLEMENT Coerce
		return null;
	}

	/**
	 * 
	 */
	protected Map<tSYMBOL, Symbol>	map	= new HashMap<tSYMBOL, Symbol>();

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#copy()
	 */
	@SuppressWarnings("unchecked")
	public tT copy()
	{
		Map<tSYMBOL, Symbol> res;

		try
		{
			res = (Map<tSYMBOL, Symbol>) this.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new LispException("Unable to copy Dictionary "
					+ e.getLocalizedMessage());
		}

		((PEnviron) res)
				.setMap((Map<tSYMBOL, Symbol>) new LinkedHashMap<tSYMBOL, Symbol>(
						getMap()));
		return (tT) res;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<tSYMBOL> iterator()
	{
		return map.keySet().iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	public int size()
	{
		return map.size();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key)
	{
		return map.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value)
	{
		return map.containsValue(value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends tSYMBOL, ? extends Symbol> m)
	{
		putAll(m);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	public void clear()
	{
		map.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	public Set<tSYMBOL> keySet()
	{
		return map.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	public Collection<Symbol> values()
	{
		return map.values();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	public Set<Entry<tSYMBOL, Symbol>> entrySet()
	{
		return entrySet();
	}

	/**
	 * @return
	 */
	public Map<tSYMBOL, Symbol> getMap()
	{
		return map;
	}

	/**
	 * @param map
	 */
	public void setMap(Map<tSYMBOL, Symbol> map)
	{
		this.map = map;
	}

}
