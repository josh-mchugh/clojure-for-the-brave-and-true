(ns clojure-noob-ch7.core
  (:gen-class))

(defmacro backwards
  [form]
  (reverse form))

(backwards (" backwards" " am" "I" str))

(def addition-list (list + 1 2))
