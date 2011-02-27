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
// IP 29 oct. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.functions;

import static aloyslisp.internal.engine.L.NIL;
import aloyslisp.core.tT;
import aloyslisp.core.packages.*;
import aloyslisp.core.sequences.*;
import aloyslisp.internal.engine.*;

/**
 * cMACRO_FUNCTION
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cMACRO_FUNCTION extends cFUNCTION implements tMACRO_FUNCTION
{

	tLIST	expander	= null;

	/**
	 * Lisp function
	 */
	tLIST	func		= null;

	/**
	 * @param def
	 */
	public cMACRO_FUNCTION(tSYMBOL name, tLIST args, tLIST func)
	{
		super();
		tT doc = API_PARSE_FUNC(func);
		api = new cAPI_MACRO(name, args, doc.CAR(), (tLIST) doc.CDR().CAR());
		this.func = (tLIST) doc.CDR().CDR().CAR();
	}

	public cMACRO_FUNCTION()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.core.functions.tFUNCTION#exec(aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public tT[] exec(tLIST args)
	{
		tT[] res = new tT[]
		{ NIL };
		cENV_LET env = api.API_INIT_VALUES(args);

		try
		{
			res = cCOMPILED_SPECIAL.PROGN(func.VALUES_LIST());
		}
		finally
		{
			env.ENV_STOP();
		}

		return res;
	}

}
