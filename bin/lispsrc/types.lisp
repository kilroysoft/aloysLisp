;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; test install aloysLisp
;;;; v1.0 05.11.2010
;;;; (c)kilroySoft 2010 by Ivan Pierre <me@IvanPierre.ch
;;;;
;;;; Here are defined all the implementation of primitive
;;;; lisp functions implemented in java, this is the sole link
;;;; between java code and lisp code for basic functions in a 
;;;; hard coded way (sigh!!!)
;;;;

;;;
;;; TYPES
;;;

;; NULL
(defmacro null (obj)
	`(%istype ,obj :null)
)

(setf (get nil :type-class) "aloyslisp.core.types.tNULL")
(setf (get nil :impl-class) "aloyslisp.core.plugs.atoms.NIL")

;; T (all the cells, no test available ;) how should it be possible ? return T ?)
(setf (get 'atom :type-class) "aloyslisp.core.types.tT")
(setf (get t :impl-class) "aloyslisp.core.plugs.CELL")


;; ATOM
(defmacro atom (obj)
	`(%istype ,obj :atom)
)

(setf (get 'atom :type-class) "aloyslisp.core.types.tATOM")

;; SYMBOLP
(defmacro symbolp (obj)
	`(%istype ,obj :symbol)
)

(setf (get 'symbol :type-class) "aloyslisp.core.types.tSYMBOL")

;; SEQUENCEP
(defmacro sequencep (obj)
	`(%istype ,obj :sequence)
)

(setf (get 'sequence :type-class) "aloyslisp.core.types.tSEQUENCE")

;; LISTP
(defmacro listp (obj)
	`(%istype ,obj :list)
)

(setf (get 'list :type-class) "aloyslisp.core.types.tLIST")

;; CONSP
(defmacro consp (obj)
	`(%istype ,obj :cons)
)

(setf (get 'cons :type-class) "aloyslisp.core.types.tCONS")

;; VECTORP
(defmacro vectorp (obj)
	`(%istype ,obj :vector)
)

(setf (get 'vector :type-class) "aloyslisp.core.types.tVECTOR")

;; SIMPLE-VECTOR-P
(defmacro simple-vector-p (obj)
	`(%istype ,obj :simple-vector)
)

(setf (get 'simple-vector :type-class) "aloyslisp.core.types.tSIMPLE_VECTOR")

;; BIT-VECTOR-P
(defmacro bit-vector-p (obj)
	`(%istype ,obj :bit-vector)
)

(setf (get 'bit-vector :type-class) "aloyslisp.core.types.tBIT_VECTOR")

;; SIMPLE-BIT-VECTOR-P
(defmacro simple-bit-vector-p (obj)
	`(%istype ,obj :simple-bit-vector)
)

(setf (get 'simple-bit-vector :type-class) "aloyslisp.core.types.tSIMPLE_BIT_VECTOR")

;; ARRAYP
(defmacro arrayp (obj)
	`(%istype ,obj :array)
)

(setf (get 'array :type-class) "aloyslisp.core.types.tARRAY")

;; HASH-TABLE-P
(defmacro hash-tablep (obj)
	`(%istype ,obj :hash-table)
)

(setf (get 'hash-table :type-class) "aloyslisp.core.types.tHASH_TABLE")

;; PACKAGEP
(defmacro packagep (obj)
	`(%istype ,obj :package)
)

(setf (get 'package :type-class) "aloyslisp.core.types.tPACKAGE")

;; READTABLEP
(defmacro readtablep (obj)
	`(%istype ,obj :readtable)
)

(setf (get 'readtable :type-class) "aloyslisp.core.types.tREADTABLE")

;; STRINGP
(defmacro stringp (obj)
	`(%istype ,obj :string)
)

(setf (get 'string :type-class) "aloyslisp.core.types.tSTRING")

;; SIMPLE-STRING-P
(defmacro simple-string-p (obj)
	`(%istype ,obj :simple-string)
)

(setf (get 'simple-string :type-class) "aloyslisp.core.types.tSIMPLE_STRING")

;; CHARACTERP
(defmacro characterp (obj)
	`(%istype ,obj :character)
)

(setf (get 'character :type-class) "aloyslisp.core.types.tCHARACTER")

;; NUMBERP
(defmacro numberp (obj)
	`(%istype ,obj :number)
)

(setf (get 'number :type-class) "aloyslisp.core.types.tNUMBER")

;; INTEGERP
(defmacro integerp (obj)
	`(%istype ,obj :integer)
)

(setf (get 'integer :type-class) "aloyslisp.core.types.tINTEGER")

;; REALP
(defmacro realp (obj)
	`(%istype ,obj :real)
)

(setf (get 'real :type-class) "aloyslisp.core.types.tREAL")

;; FLOATP
(defmacro floatp (obj)
	`(%istype ,obj :float)
)

(setf (get 'float :type-class) "aloyslisp.core.types.tFLOAT")

;; RATIONALP
(defmacro rationalp (obj)
	`(%istype ,obj :rational)
)

(setf (get 'rational :type-class) "aloyslisp.core.types.tRATIONAL")

;; RATIOP
(defmacro ratiop (obj)
	`(%istype ,obj :ratio)
)

(setf (get 'ratio :type-class) "aloyslisp.core.types.tRATIO")

;; COMPLEXP
(defmacro complexp (obj)
	`(%istype ,obj :complex)
)

(setf (get 'complex :type-class) "aloyslisp.core.types.tCOMPLEX")

;; FUNCTIONP
(defmacro functionp (obj)
	`(%istype ,obj :function)
)

(setf (get 'function :type-class) "aloyslisp.core.types.tFUNCTION")

;; COMPILED-FUNCTION-P
(defmacro compiled-function-p (obj)
	`(%istype ,obj :function)
)

(setf (get 'compiled-function :type-class) "aloyslisp.core.types.tCOMPILED_FUNCTION")

;; STREAMP
(defmacro streamp (obj)
	`(%istype ,obj :stream)
)

(setf (get 'stream :type-class) "aloyslisp.core.types.tSTREAM")

;; RANDOM-STATE-P
(defmacro random-state-p (obj)
	`(%istype ,obj :random-state)
)

(setf (get 'random-state :type-class) "aloyslisp.core.types.tRANDOM_STATE")

;; PATHNAMEP
(defmacro pathnamep (obj)
	`(%istype ,obj :pathname)
)

(setf (get 'pathname :type-class) "aloyslisp.core.types.tPATHNAME")

;; CONSTANTP
(defmacro constantp (obj)
	`(%primitive :constantp obj)
)

;; logical functions
;; and
(defmacro and (value &rest rest)
	(if value
		(if rest
			`(and ,@rest)
			`t
		)
		`nil
	)
)

;; or
(defmacro or (value &rest rest)
	(if value
		`t
		(if rest
			`(or ,@rest)
			`nil
		)
	)
)

;; not = null
(defmacro not (value)
	`(null ,value)
)
		