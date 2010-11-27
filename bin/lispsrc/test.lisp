;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; some lisp test
;;;;

(defun a ()())

(dolist (cell (apropos-list ""))
	(if (macro-function cell) 
		(princ (macro-function cell))
	)
)
