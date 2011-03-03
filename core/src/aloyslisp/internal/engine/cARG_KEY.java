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
// IP 26 févr. 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.internal.engine;

import aloyslisp.core.*;
import static aloyslisp.internal.engine.L.*;
import aloyslisp.core.conditions.*;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;

/**
 * cARG_KEY
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cARG_KEY extends cARG
{

	protected tSYMBOL	key;

	/**
	 * @param orig
	 * @param value
	 */
	public cARG_KEY(tSYMBOL orig, tT value, tSYMBOL key)
	{
		super(orig, value);
		this.key = key;
	}

	public cARG_KEY(tT def)
	{
		super(NIL, NIL);
		if (def instanceof tSYMBOL)
		{
			// the case (&key blah)
			setOrig((tSYMBOL) def);
			key = key(orig.SYMBOL_NAME());
		}
		else if (def instanceof tLIST)
		{
			setOrig((tSYMBOL) def.CAR());
			def = def.CDR();
			if (!(def.CAR() instanceof tLIST))
			{
				// the case (&key (blah bheu))
				value = (tSYMBOL) def.CAR();
				key = key(SYMBOL_NAME());
			}
			else if (def.CAR() instanceof tSYMBOL)
			{
				// the case (&key (blah (:blih bheu)))
				key = (tSYMBOL) def.CAR();
				value = def.CDR().CAR();
			}
			else
				throw new LispException("bad key definition : " + def);
		}
		else
			throw new LispException("bad key definition : " + def);
	}

}
