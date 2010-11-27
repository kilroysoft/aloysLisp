;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; some lisp test
;;;;

;; some tests
(defun a ()())

(dolist (cell (apropos-list ""))
	(if (macro-function cell) 
		(princ (macro-function cell))
	)
)
