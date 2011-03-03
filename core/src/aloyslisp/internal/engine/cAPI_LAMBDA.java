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
import aloyslisp.core.packages.tSYMBOL;
import aloyslisp.core.sequences.*;

/**
 * cAPI_LAMBDA
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class cAPI_LAMBDA extends cAPI
{
	public static tSYMBOL	LAMBDA	= L.key("lambda");

	protected tLIST			func	= L.NIL;

	protected tSYMBOL		name	= LAMBDA;

	/**
	 * @param args
	 * @param doc
	 * @param decl
	 */
	public cAPI_LAMBDA(tSYMBOL name, tLIST args, tLIST compl, Boolean special)
	{
		super(special);
		if (name != null)
			this.name = name;
		tT doc = cAPI.API_PARSE_FUNC(compl);
		SET_API_ARGS(args);
		SET_API_DOC(doc.CAR());
		SET_API_DECL((tLIST) doc.CDR().CAR());
		func = (tLIST) doc.CDR().CDR().CAR();
	}

	// /**
	// * @param args
	// * @param doc
	// * @param decl
	// */
	// public cAPI_LAMBDA(tLIST args, tT doc, tLIST decl, tLIST func,
	// Boolean special)
	// {
	// super(args, doc, decl);
	// this.func = func;
	// this.special = special;
	// }

	/*
	 * (non-Javadoc)
	 * @see
	 * aloyslisp.internal.engine.tCODE#CODE_CALL(aloyslisp.core.sequences.tLIST)
	 */
	@Override
	public tT[] API_CALL(tLIST args)
	{
		// here the args are not used the environment is already set up
		return new cENV_PROGN(func).EVAL();
	}

}
