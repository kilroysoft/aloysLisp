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

package aloyslisp.core.exec;

import java.util.*;

import aloyslisp.core.plugs.tSYMBOL;

/**
 * cPACKAGE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class SymMap implements Iterable<String>, Map<String, tSYMBOL>
{
	/**
	 * 
	 */
	protected Map<String, tSYMBOL>	map	= new HashMap<String, tSYMBOL>();

	/**
	 * Package constructor
	 * 
	 * @param name
	 *            Name of package
	 * @param real
	 *            Is it a real package or an environment
	 */
	public SymMap()
	{
		super();
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
		map.put(caseSensi((String) key), value);
		return value;
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
		return str.toUpperCase();
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
