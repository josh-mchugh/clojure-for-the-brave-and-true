(ns clojure-noob-ch6.core
  (:use [clojure-noob-ch6.the-big-cheese :as big-cheese])
  (:gen-class))


;; Create a vector of titles
(def great-books ["East of Eden" "The Glass Bead Game"])

;; Set the value again with 'name collision'
(def great-books ["The Power of Bees" "Journey to Upstairs"])

;; Create a new namespace
;;(create-ns 'cheese.taxonomy)

;; this command creates a namespace if it does exists and switches you to it
;;(in-ns 'cheese.taxonomy)

;; creates fine vector of cheddars
(def cheddars ["mild" "medium" "strong" "sharp" "extra sharp"])

;; create a list vector bries
(def bries ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melum"])

;; example of private function in Clojure
;; notice the dash after 'defn'
(defn- private-function
  "Just an example function that does nothing"
  [])

(defn -main
  [& args]
  (big-cheese/solve-crime args))
