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

;; watching a variable on an atom
(defn suffle-speed
  [zombie]
  (* (:cuddle-hunger-level zombie)
     (- 100 (:percent-deteriorated zombie))))

(defn shuffle-alert
  [key watched old-state new-state]
  (let [sph (suffle-speed new-state)]
    (if (> sph 5000)
      (do
        (println "Run, you fool!")
        (println "The zombie's SPH is now " sph)
        (println "This message brought to your courtesy of " key))
      (do
        (println "All's well with " key)
        (println "Cuddle hunger: " (:cuddle-hunger-level new-state))
        (println "Percent deteriorated: " (:percent-deteriorated new-state))
        (println "SPH: " sph)))))

(reset! fred {:cuddle-hunger-level 22
              :percent-deteriorated 2})
(add-watch fred :fred-shuffle-alert shuffle-alert)

(swap! fred update-in [:cuddle-hunger-level] + 30)

;; Example of validators on an atom
(defn percent-deteriorated-validator
  [{:keys [percent-deteriorated]}]
  (or (and (>= percent-deteriorated 0)
           (<= percent-deteriorated 100))
      (throw (IllegalStateException. "That's not mathy!"))))

(def bobby
  (atom
   {:cuddle-hunger-level 0 :percent-deteriorated 0}
    :validator percent-deteriorated-validator))
   ))
(swap! bobby update-in [:percent-deteriorated] + 200)
