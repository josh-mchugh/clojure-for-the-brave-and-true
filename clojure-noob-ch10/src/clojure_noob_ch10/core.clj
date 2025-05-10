(ns clojure-noob-ch10.core
  (:gen-class))

;; Example of an atom reference type
(def fred (atom {:cuddle-hunger-level 0
                 :percent-deteriorated 0}))

@fred

;; example of immutable state of atom
(let [zombie-state @fred]
  (if (>= (:percent-deteriorated zombie-state) 50)
    (future (println (:percent-deteriorated zombie-state)))))

;; using swap to increase a value
(swap! fred
       (fn [current-state]
         (merge-with + current-state {:cuddle-hunger-level 1})))

;; using swap to increase two values at once
(swap! fred
       (fn [current-state]
         (merge-with + current-state {:cuddle-hunger-level 1

                                      :percent-deteriorated 1})))

;; using a function to use the value of fred and return a new value,
;; swap was not called so it doesn't effect fred
(defn increase-cuddle-hunger-level
  [zombie-state increase-by]
  (merge-with + zombie-state {:cuddle-hunger-level increase-by}))

(comment
  (increase-cuddle-hunger-level @fred 10)
  @fred
  )

;; calling swap with the function changes freds value
(swap! fred increase-cuddle-hunger-level 10)

@fred

;; example of using Clojure's built in functions for updating attributes
(update-in {:a {:b 3}} [:a :b] inc)

(update-in {:a {:b 3}} [:a :b] + 10)

(swap! fred update-in [:cuddle-hunger-level] + 10)

;; using atoms you can retain past state
(let [num (atom 1)
      s1 @num]
  (swap! num inc)
  (println "State 1:" s1)
  (println "Current state:" @num))

;; You can reset an atoms values with the reset! function
(reset! fred {:cuddle-hunger-level 0
              :percent-deteriorated 0})

@fred
