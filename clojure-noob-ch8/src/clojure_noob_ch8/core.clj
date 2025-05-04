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

;; when writing a macro we want to tell the compiler clojure
;; that we do not want to evaluate symbols, so was use the single quote
;; to do that. By turning off the symbol evaluation we just return them.
(defmacro my-print
  [expression]
  (list 'let ['result expression]
        (list 'println 'result)
        'result))

;; example of actual 'when' macro
(defmacro when
  "Evaluates test. If logical true, evaluates body in an implicit do."
  {:added "1.0"}
  [test & body]
  (list 'if test (cons 'do body)))

;; example of actual 'unless' macro
(defmacro unless
  "Inverted 'if'"
  [test & branches]
  (conj (reverse branches) test 'if))

;; applying what we learned and making a macro example
(defn criticize-code
  [criticism code]
  `(println ~criticism (quote ~code)))

(defmacro code-critic
  "Phrase are courtesy Hermes Conrad from Futurama"
  [{:keys [good bad]}]
  `(do ~@(map #(apply criticize-code %)
             [["Sweet lion of Zion, this is bad code:" bad]
              ["Great cow of Moscow, this is good code:" good]])))

;; sneaky gotchas of macros
;; variable capture
(def message "Good job!")
(defmacro with-mischief
  [& stuff-to-do]
  (concat (list 'let ['message "Oh, big deal!"])
          stuff-to-do))

(defmacro without-mischief
  [& stuff-to-do]
  (let [macro-message (gensym 'message)]
    `(let [~macro-message "Oh, big deal!"]
       ~@stuff-to-do
       (println "I still need to say: " ~macro-message))))

;; double evaluation gotchas
(defmacro report
  [to-try]
  `(if ~to-try
     (println (quote ~to-try) "was successful:" ~to-try)
     (println (quote ~to-try) "was not successful:" ~to-try)))

;; fix for double evaluation
(defmacro report
  [to-try]
  `(let [result# ~to-try]
     (if result#
       (println (quote ~to-try) "was successful:" result#)
       (println (quote ~to-try) "was not successful:" result#))))

(comment
  (report (do (Thread/sleep 1000) (+ 1 1)))
  (report (= 1 1))
  (report (= 1 2))
  (doseq [code ['(= 1 1) '(= 1 2)]]
    (report code)))

(defmacro doseq-macro
  [macroname & args]
  `(do
     ~@(map (fn [arg] (list macroname arg)) args)))

(comment
  (doseq-macro report (= 1 1) (= 1 2)))
