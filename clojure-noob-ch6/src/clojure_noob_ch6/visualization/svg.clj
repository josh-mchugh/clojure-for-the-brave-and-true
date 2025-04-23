(ns clojure-noob-ch6.visualization.svg)

(defn latlng->pont
  "Convert lat/lng map to comma-seperated string"
  [latlng]
  (str (:lng latlng) "," (:lat latlng)))

(defn points
  [locations]
  (clojure.string/join " " (map latlng->pont locations)))
