(ns clojure-noob-ch8.core
  (:gen-class))

;; example macro for rearranges a list into the correct order for infix notation
(defmacro infix
  "Use this macro when you pine for the notation of your childhood"
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))

;; example of argument destructuring in macro definitions
(defmacro infix-2
  [[operand1 op operand2]]
  (list op operand1 operand2))

;; copied example of the source for the 'and' macro
;; the 'and' macro has multiple-arity. It has 3 bodies.
;; Plus it recursively calls itself in the last arity.
(defmacro and
  "Evaluates exprs one at a time, from left to right. If a form
  returns logical false (nil or false), and returns that value and
  doesn't evaluate any of the other expressions, otherwise it returns
  the value of the last expr. (and) return true."
  {:added "1.0"}
  ([] true)
  ([x] x)
  ([x & next]
   `(let [and# ~x]
      (if and# (and ~@next) and#))))
