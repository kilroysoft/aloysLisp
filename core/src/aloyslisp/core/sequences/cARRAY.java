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
// IP 19 mars 2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.sequences;

import aloyslisp.annotations.aBuiltIn;
import aloyslisp.core.cCELL;
import aloyslisp.core.tT;

/**
 * cARRAY
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aBuiltIn(lispClass = "array", doc = "t_array")
public class cARRAY extends cCELL implements tARRAY
{

	/**
	 * 
	 */
	public cARRAY()
	{
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tARRAY#getType()
	 */
	@Override
	public tT ARRAY_ELEMENT_TYPE()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see aloyslisp.core.sequences.tARRAY#setType(aloyslisp.core.tT)
	 */
	@Override
	public tARRAY SET_ARRAY_ELEMENT_TYPE(tT type)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
