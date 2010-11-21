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
// IP 10 nov. 2010 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.types;


/**
 * tREADTABLE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public interface tREADTABLE extends tATOM
{
	/**
	 * Test if character is constituent
	 * 
	 * @param car
	 * @return
	 */
	public boolean isConstituent(Character car);

	/**
	 * Set single macro char be a multiple or single macro char.
	 * nonTerm = true (!NIL) -> multiple
	 * 
	 * @param c
	 * @param nonTerm
	 */
	public void makeDispatchMacroCharacter(Character c, boolean nonTerm);

	/**
	 * Set macro char definition for multiple chars (in standard sharp macro
	 * chars)
	 * 
	 * @param disp
	 * @param sub
	 * @param function
	 */
	public void setDispatchMacroCharacter(Character disp, Character sub,
			tFUNCTION func);

	/**
	 * Get macro char definition for multiple chars (in standard sharp macro
	 * chars)
	 * 
	 * @param disp
	 * @param sub
	 */
	public tLIST getDispatchMacroCharacter(Character disp, Character sub);

	/**
	 * Set macro char definition for single char
	 * 
	 * @param c
	 * @param func
	 * @param nonTerm
	 */
	public void setMacroCharacter(Character c, tFUNCTION func, boolean nonTerm);

	/**
	 * Get macro char definition for single char
	 * 
	 * @param c
	 * @return
	 */
	public tLIST getMacroCharacter(Character c);

	/**
	 * return table case
	 * 
	 * @return
	 */
	public tSYMBOL getCase();

	/**
	 * Set table case
	 * 
	 * @param c
	 */
	public void setCase(tSYMBOL c);

	/**
	 * Change case of char according to table case
	 * 
	 * @param c
	 * @return
	 */
	public Character changeCase(Character c);

}
