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
// IP 26 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.core.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import static aloyslisp.core.L.*;

/**
 * cARG_KEY
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cARG_KEY extends cARG
{

	public tSYMBOL	key;

	/**
	 * @param orig
	 * @param value
	 */
	public cARG_KEY(tSYMBOL orig, tT value, tSYMBOL key)
	{
		super(orig, value, false);
		this.key = key;
	}

	public cARG_KEY(tT def)
	{
		super(NIL, NIL, false);
		// System.out.println("adding key : " + def);
		if (def instanceof tSYMBOL)
		{
			// the case (&key var)
			SET_SYMBOL_ORIG((tSYMBOL) def);
			key = key(orig.SYMBOL_NAME());
		}
		else if (def instanceof tLIST)
		{
			tT symbol = def.CAR();
			if (symbol instanceof tLIST)
			{
				// the case (&key ((:key var) val)))
				key = (tSYMBOL) symbol.CAR();
				SET_SYMBOL_ORIG((tSYMBOL) symbol.CDR().CAR());
			}
			else if (symbol instanceof tSYMBOL)
			{
				// the case (&key (var val))
				SET_SYMBOL_ORIG((tSYMBOL) symbol);
				key = key(SYMBOL_NAME());
			}
			else
				throw new LispException("bad key definition : " + def);

			def = def.CDR();
			value = def.CAR();
		}
		else
			throw new LispException("bad key definition : " + def);
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.cCELL#DESCRIBE()
	 */
	public String DESCRIBE()
	{
		return "#<DYN-ARG-KEY " + orig.TO_STRING() + " " + key + " " + value
				+ "" + (special ? T : NIL) + " " + (base ? T : NIL) + " "
				+ value + ">";
	}

}
