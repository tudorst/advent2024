(ns tudorescu.advent2024
  (:gen-class)
  (:require
   [clojure.pprint :as pprint]
   [clojure.string :as string]
   [tudorescu.advent2024.day1 :as day1]
   [tudorescu.advent2024.day2 :as day2]
   [tudorescu.advent2024.day3 :as day3]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [input (slurp "resources/01_input.txt")
        list-two (sort (->> (-> input (string/split #"\n")) (map #(re-seq #"\d+$" %)) (map #(Integer/parseInt (first %)))))
        list-one (sort (->> (-> input (string/split #"\n")) (map #(re-seq #"^\d+" %)) (map #(Integer/parseInt (first %)))))]
    (pprint/pprint "day 1:")
    (pprint/pprint (day1/do-puzzle1 list-one list-two))
    (pprint/pprint (day1/do-puzzle2 list-one list-two))
    (pprint/pprint "day 2:")
    (pprint/pprint (day2/do-puzzle1))
    (pprint/pprint (day2/do-puzzle2))
    (pprint/pprint "day 3:")
    (pprint/pprint (day3/do-puzzle1))))