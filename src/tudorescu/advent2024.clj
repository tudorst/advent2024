(ns tudorescu.advent2024
  (:gen-class) 
  (:require
   [clojure.pprint :as pprint]
   [clojure.string :as string]
   [tudorescu.advent2024.day1 :as day1]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [input (slurp "resources/01_input.txt")
        list-two (sort (->> (-> input (string/split #"\n")) (map #(re-seq #"\d+$" %)) (map #(Integer/parseInt (first %)))))
        list-one (sort (->> (-> input (string/split #"\n")) (map #(re-seq #"^\d+" %)) (map #(Integer/parseInt (first %)))))]
    (pprint/pprint (day1/day1-puzzle1 list-one list-two))
    (pprint/pprint (day1/day1-puzzle2 list-one list-two))))

