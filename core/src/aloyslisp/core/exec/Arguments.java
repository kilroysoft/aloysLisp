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
// IP 22 oct. 2010 Creation
// IP UB10 Arguments order mandatory, optional, rest, key
// IP UB10 &aux variables
// IP UB10 &allow-other-keys
// IP UB10 Complete key definition ((keyword variable) default)
// IP UB19 A function to print arguments in lisp mode.
// IP UB19 Transform class name for compiled function classes
// --------------------------------------------------------------------------

package aloyslisp.core.exec;

import static aloyslisp.commonlisp.L.*;

import java.util.*;

import aloyslisp.core.common.*;
import aloyslisp.core.plugs.*;
import aloyslisp.core.types.*;

/**
 * Arguments
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
public class Arguments
{
	/**
	 * Name of environment
	 */
	tSYMBOL						name			= NIL;

	/**
	 * Number of mandatory arguments
	 */
	Integer						nbObl			= 0;

	/**
	 * Original list
	 */
	tLIST						orig			= NIL;

	/**
	 * Argument list
	 */
	tLIST						args			= NIL;

	/**
	 * Aux list
	 */
	tLIST						aux				= NIL;

	/**
	 * Key list
	 */
	LinkedHashMap<tSYMBOL, tT>	keyArgs			= new LinkedHashMap<tSYMBOL, tT>();

	/**
	 * Rest of arguments
	 */
	Symbol						rest			= null;

	/**
	 * Commentary on functions
	 */
	String						commentary;

	/**
	 * Declare statements
	 */
	tLIST						declare			= NIL;

	/**
	 * Lisp function
	 */
	tLIST						func			= null;

	/**
	 * For optional values evaluation is only done for non special forms (SF,
	 * macros)
	 */
	boolean						special			= false;

	/**
	 * Switch to display trace
	 */
	boolean						trace			= false;

	/**
	 * 
	 */
	boolean						allowOtherKeys	= false;

	/**
	 * VarType
	 * 
	 * @author Ivan Pierre {ivan@kilroysoft.ch}
	 * @author George Kilroy {george@kilroysoft.ch}
	 * 
	 */
	static enum VarType
	{
		/**
		 * Wait for mandatory arguments
		 */
		MANDATORY,

		/**
		 * Wait for optional arguments
		 */
		OPTIONAL,

		/**
		 * Wait for rest argument
		 */
		REST,

		/**
		 * Wait for keyword arguments
		 */
		KEY,

		/**
		 * Wait for aux arguments
		 */
		AUX
	};

	/**
	 * @param args
	 */
	public Arguments(tSYMBOL name, tLIST args, tLIST func)
	{
		this.name = name;
		this.orig = args;
		createArgs(args);
		readLambda(func);
	}

	/**
	 * @return
	 */
	public tSYMBOL getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public String getStringName()
	{
		if (name == null)
		{
			return "no name in argument";
		}
		return name.SYMBOL_NAME();
	}

	/**
	 * @param name
	 */
	public void setName(tSYMBOL name)
	{
		this.name = name;
	}

	/**
	 * Read lambda list
	 * 
	 * @param func
	 * @return
	 */
	private void readLambda(tLIST func)
	{
		trace("readLambda(" + func + ")");

		if (func == null)
			return;

		this.func = NIL;
		this.declare = NIL;
		tLIST block = func;

		while (block instanceof tCONS)
		{
			tT curr = block.CAR();
			// trace("curr = " + curr);

			if (curr instanceof tSTRING)
			{
				// commentary element
				if (commentary != null)
				{
					throw new LispException(
							"Too much description string in lambda");
				}
				commentary = ((tSTRING) curr).getString();
			}
			else if (beginWith(curr, "declare"))
			{
				// declare element
				declare = new CONS(curr, declare);
			}
			else
			{
				// Code block found stop
				this.func = (tLIST) block;
				break;
			}

			// Next block elem
			block = (tLIST) block.CDR();
		}
		declare = (tLIST) declare.REVERSE();
	}

	/**
	 * Read argument list
	 * 
	 * @param args
	 */
	private void createArgs(tLIST arguments)
	{
		VarType t = VarType.MANDATORY;
		tT val = null;
		String name = "";
		for (tT walk : arguments)
		{
			walk = walk.CAR();

			if (walk instanceof tSYMBOL)
			{
				name = ((tSYMBOL) walk).SYMBOL_NAME();
				if (name.equalsIgnoreCase("&optional"))
				{
					t = VarType.OPTIONAL;
					continue;
				}
				else if (name.equalsIgnoreCase("&rest"))
				{
					t = VarType.REST;
					continue;
				}
				else if (name.equalsIgnoreCase("&key"))
				{
					t = VarType.KEY;
					continue;
				}
				else if (name.equalsIgnoreCase("&allow-other-keys"))
				{
					t = VarType.KEY;
					allowOtherKeys = true;
					continue;
				}
				else if (name.equalsIgnoreCase("&aux"))
				{
					t = VarType.AUX;
					continue;
				}
				else
					val = NIL;
			}
			else if (walk instanceof tCONS)
			{
				val = walk.CDR();
				walk = walk.CAR();
				if (!(val instanceof tCONS))
				{
					throw new LispException("Bad argument definition in "
							+ arguments + " at " + walk);
				}
				val = val.CAR();
			}
			else
			{
				throw new LispException("Bad argument definition in "
						+ arguments + " at " + walk);
			}

			Symbol var;
			switch (t)
			{
				case MANDATORY:
					nbObl++;
					var = new Symbol((tSYMBOL) walk, val);
					trace("add mandatory : " + walk + "=[" + var + ","
							+ var.SYMBOL_VALUE() + "]" + t.name());
					args = (tLIST) args.APPEND(list(var));
					break;

				case OPTIONAL:
					var = new Symbol((tSYMBOL) walk, val);
					trace("add optional : " + walk + "=[" + var + ","
							+ var.SYMBOL_VALUE() + "]" + t.name());
					args = (tLIST) args.APPEND(list(var));
					break;

				case REST:
					var = new Symbol((tSYMBOL) walk, val);
					trace("add rest : " + walk + "=[" + var + ","
							+ var.SYMBOL_VALUE() + "]" + t.name());
					rest = var;
					break;

				case KEY:
					trace("add key : " + walk + "=[" + walk + "," + val + "]"
							+ t.name());
					tT assoc;
					tT key;
					if (walk instanceof tCONS)
					{
						// BUG Implementation of key reading false
						// key is defined as ((key variable) [def])
						key = walk.CAR();
						if (!(key instanceof tSYMBOL))
						{
							throw new LispException("key is not a symbol "
									+ key);
						}
						if (((tCONS) walk).LENGTH() < 2)
						{
							// new key ((pack:key) def)
							assoc = key;
						}
						else
						{
							// new key ((pack:key pack2:variable) def)
							assoc = walk.CDR().CAR();
							if (!(key instanceof tSYMBOL))
							{
								throw new LispException("key is not a symbol "
										+ key);
							}
							// BUG this is false look for Symbol
							((tSYMBOL) assoc).SET_SYMBOL_VALUE(val);
						}
					}
					else
					{
						// key is a (symbol def) -> ((:symbol symbol) def) if in
						// current package
						if (((tSYMBOL) walk).SYMBOL_PACKAGE() == currPackage())
							key = key(((tSYMBOL) walk).SYMBOL_NAME());
						else
							key = walk;
					}

					keyArgs.put((tSYMBOL) key, val);
					break;

				case AUX:
					var = new Symbol((tSYMBOL) walk, val);
					trace("add aux : " + walk + "=[" + var + ","
							+ var.SYMBOL_VALUE() + "]" + t.name());
					aux = (tLIST) args.APPEND(list(var));
					break;
			}
		}
		// trace("args : " + args);
		// trace("rest : " + rest);
		// trace("key : " + keyArgs);
	}

	/**
	 * Init default values for args and aux
	 * 
	 * @param def
	 */
	private void initDef(tLIST def)
	{
		while (def instanceof tCONS)
		{
			// Evaluate default value if non special form
			tT value = ((Symbol) def.CAR()).SYMBOL_VALUE();
			if (!special && value != null)
				value = value.EVAL()[0];

			trace("args create : " + (Symbol) def.CAR() + "=" + value);

			e.intern(((Symbol) def.CAR()).getOrig(), value);
			def = (tLIST) def.CDR();
		}
	}

	/**
	 * Push dynamic variables in block
	 * 
	 * @param args
	 */
	public void pushBlock(tLIST vals)
	{
		trace("pushBlock : " + vals);

		// create block
		e.newBlock(name, func);

		// create all variables with default values
		initDef(args);

		// i rest value
		if (rest != null)
		{
			e.intern(rest.getOrig(), rest.SYMBOL_VALUE());
		}

		// manage values
		tLIST walk = args;
		tLIST restList = NIL;
		tLIST keyList = NIL;
		int nbArgs = 0;

		// read args
		for (tT val : vals)
		{
			// get value
			tT value = val.CAR();

			// is it a key ?
			if (value instanceof tSYMBOL)
			{
				if (((tSYMBOL) value).SYMBOL_PACKAGE() == key)
					trace("Key ? " + value);

				if (keyArgs.get((tSYMBOL) value) != null)
				{
					trace("key found : " + value);
					keyList = (tLIST) val;
					break;
				}
			}

			// One more found
			nbArgs++;

			// still arguments to atribute to ?
			if (walk instanceof tCONS)
			{
				trace("intern var : " + walk.CAR() + "=" + value);
				e.intern(((Symbol) walk.CAR()).getOrig(), value);
				walk = (tLIST) walk.CDR();
			}
			else
			{
				// add to rest
				trace("add to rest : " + value);
				restList = cons(value, restList);
			}
		}

		if (nbArgs < nbObl)
		{
			throw new LispException("Missing arguments in " + name + ". "
					+ nbObl + " needed, " + nbArgs + "found." + getArgs());
		}

		// read keys loop over tCONS (stop on NIL)
		while (keyList instanceof tCONS)
		{
			if (keyList.LENGTH() < 2)
			{
				throw new LispException("No value binded to key "
						+ keyList.CAR());
			}
			tT key = keyList.CAR();
			tT val = keyList.CDR().CAR();

			tT var = null;
			if (key instanceof tSYMBOL
					&& (var = keyArgs.get((tSYMBOL) key)) != null)
			{

			}
			else if (key instanceof tSYMBOL
					&& (allowOtherKeys == true || key == key("allow-other-keys")))
			{
				allowOtherKeys = true;
				var = key;
			}
			else
			{
				throw new LispException("" + key + "is not a key");
			}
			e.intern((tSYMBOL) var, val);
			keyList = (tLIST) keyList.CDR().CDR();
		}

		// init rest variable
		if (rest != null)
		{
			restList = (tLIST) restList.REVERSE();
			trace("rest : " + rest + "=" + restList);
			e.intern(rest.getOrig(), restList);
		}

		// At the end initialization of &aux variables with already initialized
		// local variables
		initDef(aux);
	}

	/**
	 * Push dynamic variables in block
	 * 
	 * @param args
	 */
	public tLIST getValues()
	{
		// Get mandatory and optionals args
		tLIST res = NIL;
		tLIST walk = args;
		while (walk instanceof tCONS)
		{
			// Taking arg list's CARs witch are symbols
			// Using symbol to take in the environment the dynamic variable
			// And appending the value
			res = (tLIST) res.APPEND(list(e.arg(((Symbol) walk.CAR()).orig)
					.SYMBOL_VALUE()));
			walk = (tLIST) walk.CDR();
		}

		// TODO append keys
		// Get mandatory and optionals args

		// Get rest
		if (rest != null)
		{
			res = (tLIST) res.APPEND(e.arg(rest.orig).SYMBOL_VALUE());
		}

		return res;
	}

	/**
	 * @param index
	 * @return
	 */
	public tT arg(int index)
	{
		Symbol res = e.arg(((Symbol) args.ELT(index)).getOrig());

		if (res != null)
		{
			trace("arg(" + index + ")=" + res.SYMBOL_VALUE());
			return res.SYMBOL_VALUE();
		}

		trace("arg(" + index + ")=null");
		return null;
	}

	/**
	 * @param key
	 * @return
	 */
	public tT arg(String key)
	{
		// gat key arg definition
		tSYMBOL var = (tSYMBOL) keyArgs.get(key(key));

		if (var == null)
		{
			throw new LispException("getting invalid key variable argument "
					+ key);
		}

		// read in passed arguments (get variable in environment)
		Symbol eVar = e.arg(var);
		tT def;

		if (eVar != null)
			def = eVar.SYMBOL_VALUE();
		else
			// get default value in key definition
			def = var.SYMBOL_VALUE().EVAL()[0];

		trace("arg('" + var + "')=" + def);
		return def;
	}

	/**
	 * @return
	 */
	public tLIST arg()
	{
		Symbol res = e.arg(rest.getOrig());

		if (res == null)
			return NIL;

		return (tLIST) res.SYMBOL_VALUE();
	}

	/**
	 * Write trace on environment
	 * 
	 * @param msg
	 */
	private void trace(String msg)
	{
		if (trace)
			System.out.println(msg);
	}

	/**
	 * Return original argument list
	 * 
	 * @return
	 */
	public tT getArgs()
	{
		return orig;
	}

	/**
	 * @return
	 */
	public String commentary()
	{
		return commentary;
	}

	/**
	 * @return
	 */
	public tLIST declare()
	{
		return declare;
	}

	/**
	 * @return
	 */
	public tLIST func()
	{
		return func;
	}

	/**
	 * @return
	 */
	public void setFunc(tLIST func)
	{
		this.func = func;
	}

}
