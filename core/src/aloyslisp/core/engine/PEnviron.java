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

package aloyslisp.core.engine;

import java.util.*;

import aloyslisp.core.*;
import aloyslisp.core.conditions.LispException;
import aloyslisp.core.packages.tSYMBOL;

/**
 * PEnviron
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public abstract class PEnviron extends cCELL implements Iterable<tSYMBOL>,
		Map<tSYMBOL, cDYN_SYMBOL>
{

	protected tSYMBOL	name;

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#printable()
	 */
	public String toString()
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
	public cDYN_SYMBOL get(Object key)
	{
		return map.get((tSYMBOL) key);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public cDYN_SYMBOL put(tSYMBOL key, cDYN_SYMBOL value)
	{
		// System.out.println("<#" + this.getClass().getSimpleName() + " put(" +
		// key + "," + value + ")");
		return map.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public cDYN_SYMBOL remove(Object key)
	{
		return map.remove((tSYMBOL) key);
	}

	/**
	 * @param name
	 * @return
	 */
	public cDYN_SYMBOL intern(tSYMBOL name)
	{
		// System.out.println("interm(" + name + ")");
		cDYN_SYMBOL atom = get(name);
		if (atom != null)
			return atom;

		atom = new cDYN_SYMBOL(name, null);
		put(name, atom);
		return atom;
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public cDYN_SYMBOL intern(tSYMBOL name, tT value)
	{
		// System.out.println("interm(" + name + "," + value + ")");
		cDYN_SYMBOL atom = intern(name);
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

		for (cDYN_SYMBOL var : map.values())
		{
			str.append(var.SYMBOL_NAME() + " " + var.SYMBOL_PACKAGE() + " "
					+ var.SYMBOL_VALUE() + "\n");
		}

		return str.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#DESCRIBE()
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
	protected Map<tSYMBOL, cDYN_SYMBOL>	map	= new HashMap<tSYMBOL, cDYN_SYMBOL>();

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#copy()
	 */
	@SuppressWarnings("unchecked")
	public tT copy()
	{
		Map<tSYMBOL, cDYN_SYMBOL> res;

		try
		{
			res = (Map<tSYMBOL, cDYN_SYMBOL>) this.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new LispException("Unable to copy Dictionary "
					+ e.getLocalizedMessage());
		}

		((PEnviron) res)
				.setMap((Map<tSYMBOL, cDYN_SYMBOL>) new LinkedHashMap<tSYMBOL, cDYN_SYMBOL>(
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
	public void putAll(Map<? extends tSYMBOL, ? extends cDYN_SYMBOL> m)
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
	public Collection<cDYN_SYMBOL> values()
	{
		return map.values();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	public Set<Entry<tSYMBOL, cDYN_SYMBOL>> entrySet()
	{
		return entrySet();
	}

	/**
	 * @return
	 */
	public Map<tSYMBOL, cDYN_SYMBOL> getMap()
	{
		return map;
	}

	/**
	 * @param map
	 */
	public void setMap(Map<tSYMBOL, cDYN_SYMBOL> map)
	{
		this.map = map;
	}

}
