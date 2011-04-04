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
// IP 9 oct. 2010-2011 Creation
// --------------------------------------------------------------------------

package aloyslisp.core.sequences;

import aloyslisp.annotations.*;
import aloyslisp.core.*;
import aloyslisp.core.clos.*;
import aloyslisp.core.functions.*;
import aloyslisp.core.math.*;

/**
 * tHASH_TABLE
 * 
 * @author Ivan Pierre {ivan@kilroysoft.ch}
 * @author George Kilroy {george@kilroysoft.ch}
 * 
 */
@aType(name = "hash-table", doc = "t_hash_t", typep = "hash-table-p")
public interface tHASH_TABLE extends tBUILT_IN_CLASS
{
	/**
	 * @return
	 */
	@aFunction(name = "hash-table-count", doc = "f_hash_1")
	public tINTEGER HASH_TABLE_COUNT( //
	);

	/**
	 * @return
	 */
	@aFunction(name = "hash-table-rehash-size", doc = "f_hash_2")
	public tINTEGER HASH_TABLE_REHASH_SIZE( //
	);

	/**
	 * @return
	 */
	@aFunction(name = "hash-table-rehash-threshold", doc = "f_hash_3")
	public tFLOAT HASH_TABLE_REHASH_THRESHOLD( //
	);

	/**
	 * @return
	 */
	@aFunction(name = "hash-table-size", doc = "f_hash_4")
	public tINTEGER HASH_TABLE_SIZE( //
	);

	/**
	 * @return
	 */
	@aFunction(name = "hash-table-test", doc = "f_hash_4")
	public tFUNCTION HASH_TABLE_TEST( //
	);

	@aFunction(name = "hash-table-keys")
	public tLIST HASH_TABLE_KEYS( //
	);

	@aFunction(name = "hash-table-values")
	public tLIST HASH_TABLE_VALUES( //
	);

	/**
	 * @param key
	 * @param hashTable
	 * @param def
	 * @return
	 */
	@aFunction(name = "gethash", doc = "f_gethas")
	@aBaseArg(name = "hash-table", pos = 1)
	public tT[] GETHASH( //
			@aArg(name = "key") tT key, //
			@aOpt(name = "default") tT def //
	);

	/**
	 * @param value
	 * @param key
	 * @param hashTable
	 * @param def
	 * @return
	 */
	@aFunction(name = "setf-gethash", doc = "f_gethas")
	@aBaseArg(name = "hash-table", pos = 2)
	public tT SET_GETHASH( //
			@aArg(name = "value") tT value, //
			@aArg(name = "key") tT key //
	);

	/**
	 * @param key
	 * @param hashTable
	 * @return
	 */
	@aFunction(name = "remhash", doc = "f_remhas")
	@aBaseArg(name = "hash-table", pos = 1)
	public tT REMHASH( //
			@aArg(name = "key") tT key //
	);

	/**
	 * @param func
	 * @param hashTable
	 * @return
	 */
	@aFunction(name = "maphash", doc = "f_maphas")
	@aBaseArg(name = "hash-table", pos = 1)
	public tT MAPHASH( //
			@aArg(name = "function") tFUNCTION func //
	);

	/**
	 * @return
	 */
	@aFunction(name = "clrhash", doc = "f_clrhas")
	public tT CLRHASH( //
	);

}
