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

;; Instantiation of classes
(defmacro instantiate (class)
	`(%global :instantiate ,class))


(instantiate "aloyslisp.core.plugs.Primitives")
(instantiate "aloyslisp.core.types.tARRAY")
(instantiate "aloyslisp.core.types.tATOM")
(instantiate "aloyslisp.core.types.tBIGNUM")
(instantiate "aloyslisp.core.types.tBIT_VECTOR")
(instantiate "aloyslisp.core.types.tCELL")
(instantiate "aloyslisp.core.types.tCHARACTER")
(instantiate "aloyslisp.core.types.tCOMPILED_FUNCTION")
(instantiate "aloyslisp.core.types.tCOMPLEX")
(instantiate "aloyslisp.core.types.tCONS")
(instantiate "aloyslisp.core.types.tDEFUN_FUNCTION")
(instantiate "aloyslisp.core.types.tDOUBLE_FLOAT")
(instantiate "aloyslisp.core.types.tFILE_STREAM")
(instantiate "aloyslisp.core.types.tFIXNUM")
(instantiate "aloyslisp.core.types.tFLOAT")
(instantiate "aloyslisp.core.types.tFUNCTION")
(instantiate "aloyslisp.core.types.tHASH_TABLE")
(instantiate "aloyslisp.core.types.tINPUT_STREAM")
(instantiate "aloyslisp.core.types.tINTEGER")
(instantiate "aloyslisp.core.types.tLAMBDA_FUNCTION")
(instantiate "aloyslisp.core.types.tLIST")
(instantiate "aloyslisp.core.types.tLONG_FLOAT")
(instantiate "aloyslisp.core.types.tMACRO_FUNCTION")
(instantiate "aloyslisp.core.types.tNIL")
(instantiate "aloyslisp.core.types.tNULL")
(instantiate "aloyslisp.core.types.tNUMBER")
(instantiate "aloyslisp.core.types.tOUTPUT_STREAM")
(instantiate "aloyslisp.core.types.tPACKAGE")
(instantiate "aloyslisp.core.types.tRANDOM_STATE")
(instantiate "aloyslisp.core.types.tRATIO")
(instantiate "aloyslisp.core.types.tRATIONAL")
(instantiate "aloyslisp.core.types.tREADTABLE")
(instantiate "aloyslisp.core.types.tREAL")
(instantiate "aloyslisp.core.types.tSEQUENCE")
(instantiate "aloyslisp.core.types.tSHORT_FLOAT")
(instantiate "aloyslisp.core.types.tSINGLE_FLOAT")
(instantiate "aloyslisp.core.types.tSPECIAL_OPERATOR")
(instantiate "aloyslisp.core.types.tSTREAM")
(instantiate "aloyslisp.core.types.tSTRING_STREAM")
(instantiate "aloyslisp.core.types.tSTRING")
(instantiate "aloyslisp.core.types.tSYMBOL")
(instantiate "aloyslisp.core.types.tT")
(instantiate "aloyslisp.core.types.tVECTOR")

;;;
;;; VARIOUS
;;; just needed to interact with user
;;;
;; (while cond &rest blocks)
(defmacro while (cond &rest blocks)
	`(tagbody
		loop
		(if ,cond
			(progn
				(progn ,@blocks)
				(go loop)
			)
		)
	)	
)

;; (repl)
(defun repl()
	(setq + nil)
	(while (eq (eq + :break) nil)
		(write *package* :escape NIL)
		(write ">> " :escape NIL)
		(setq + (write (eval(read))))
		(write #\Newline :escape NIL)
	)
)

;; (trace &rest code)
;; Debug function write java trace on error
(defmacro trace (&rest code)
	`(let ((*trace* T)) ,@code)
)

