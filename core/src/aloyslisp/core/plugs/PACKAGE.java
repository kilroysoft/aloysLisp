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
// IP UB20 Disconnected from Environment
// --------------------------------------------------------------------------

package aloyslisp.core.plugs;

import static aloyslisp.commonlisp.L.*;

import java.util.*;

import aloyslisp.core.conditions.LispException;
import aloyslisp.core.math.*;
import aloyslisp.core.types.*;

/**
 * PACKAGE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class PACKAGE extends CELL implements tPACKAGE
{
	/**
	 * Package's name
	 */
	String				name;

	/**
	 * Use list
	 */
	tPACKAGE			uses;

	/**
	 * Shadow variables
	 */
	tPACKAGE			shadow;

	/**
	 * Case sensitivity
	 */
	boolean				caseSensitive	= false;

	/**
	 * Where are the packages in packages
	 */
	static final String	baseClass		= "aloyslisp.commonlisp.";

	/**
	 * Where the classes are stored
	 * TODO For now we look for class files, should be extended to jar files
	 */
	static final String	basePath		= "bin/aloyslisp/commonlisp/";

	/**
	 * Package constructor
	 * 
	 * @param name
	 *            Name of package
	 * @param real
	 *            Is it a real package or an environment
	 */
	public PACKAGE(String name, boolean real)
	{
		super();
		// trace = true;
		this.name = name;
		if (real)
		{
			uses = new PACKAGE("Uses of " + name, false);
			shadow = new PACKAGE("Shadow of " + name, false);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.Cell#printable()
	 */
	@Override
	public String printable()
	{
		return "#<PACKAGE " + name + ">";
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.plugs.collections.IPackage#isInUseList(aloyslisp.core.
	 * plugs.CELL)
	 */
	@Override
	public boolean isInUseList(tPACKAGE pack)
	{
		for (String walk : uses)
		{
			if (walk == pack.getName())
				return true;

			if (((PACKAGE) uses.get(walk)).isInUseList(pack))
				return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.collections.IPackage#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public String dump()
	{
		StringBuilder str = new StringBuilder();

		for (String key : map.keySet())
		{
			str.append(key + "=" + get(key).DESCRIBE() + "\n");
		}

		return str.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public tSYMBOL get(Object key)
	{
		return map.get(caseSensi((String) key));
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public tSYMBOL put(String key, tSYMBOL value)
	{
		// System.out.println("<#" + this.getClass().getSimpleName() + " put(" +
		// key + "," + value + ")");
		return map.put(caseSensi((String) key), value);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public tSYMBOL remove(Object key)
	{
		return map.remove(caseSensi((String) key));
	}

	private String caseSensi(String str)
	{
		if (caseSensitive)
			return str;

		return str.toUpperCase();
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

	/**
	 * @param args
	 * @return
	 */
	public static tT make_package(tT name, tT nicknames, tT use)
	{
		return in_package(name, NIL, NIL);
	}

	/**
	 * @param args
	 * @return
	 */
	public static tT in_package(tT name, tT nicknames, tT use)
	{
		return NIL;
	}

	/**
	 * @param args
	 * @return
	 */
	public static tT find_package(tT args)
	{
		return NIL;
	}

	/**
	 * @return
	 */
	public static tT list_all_packages()
	{
		return NIL;
	}

	/**
	 * (INTERN symbol &optional package)
	 * 
	 * @param symbol
	 * @param pack
	 * @return
	 */
	public tSYMBOL INTERN(String symbol)
	{
		tT sym = get(symbol);
		if (sym == null)
		{
			sym = new SYMBOL(symbol, this);
			put(symbol, (tSYMBOL) sym);
			trace("------------------->INTERN : " + this + "::" + symbol
					+ " = " + sym);
		}
		return (tSYMBOL) sym;
	}

	/**
	 * 
	 */
	protected Map<String, tSYMBOL>	map	= new HashMap<String, tSYMBOL>();

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.plugs.CELL#copy()
	 */
	@SuppressWarnings("unchecked")
	public tT copy()
	{
		Map<String, tSYMBOL> res;

		try
		{
			res = (Map<String, tSYMBOL>) this.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new LispException("Unable to copy Dictionary "
					+ e.getLocalizedMessage());
		}

		((PACKAGE) res)
				.setMap((Map<String, tSYMBOL>) new LinkedHashMap<String, tSYMBOL>(
						getMap()));
		return (tT) res;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<String> iterator()
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
	public void putAll(Map<? extends String, ? extends tSYMBOL> m)
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
	public Set<String> keySet()
	{
		return map.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	public Collection<tSYMBOL> values()
	{
		return map.values();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	public Set<Entry<String, tSYMBOL>> entrySet()
	{
		return entrySet();
	}

	/**
	 * @return
	 */
	public Map<String, tSYMBOL> getMap()
	{
		return map;
	}

	/**
	 * @param map
	 */
	public void setMap(Map<String, tSYMBOL> map)
	{
		this.map = map;
	}

}
