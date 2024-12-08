(ns tudorescu.advent2024.day2
   (:require [clojure.pprint :as pprint]
             [clojure.string :as str]))

;;(add-tap (bound-fn* clojure.pprint/pprint))
;;(remove-tap (bound-fn* clojure.pprint/pprint))

 (defn parse-input
 "Reads the input file and returns a list of lists of integers"
 []
 (let [input (-> (slurp "resources/02_input.txt") (str/split #"\n"))]
   (->> input
        (map #(str/split % #"\s+"))
        (map (fn [report] (map #(Integer/parseInt %) report))))))

(defn is-safe?
  "Day2 Puzzle 1: Returns true if the report is safe, false otherwise"
  [report do-action]
  (let [work-report (if (< (first report) (last report)) report (reverse report))
        work-test (map - (rest work-report) (butlast work-report))]
    (tap> work-report)
    (tap> work-test)
    (if (every? #(<= 1 % 3) work-test)
      ((:yes do-action) work-report)
      ((:no do-action) work-report))))

(defn do-puzzle1
  "
   --- Day 2: Red-Nosed Reports ---

   Fortunately, the first location The Historians want to search isn't a long walk from the Chief Historian's 
   office.
   While the Red-Nosed Reindeer nuclear fusion/fission plant appears to contain no sign of the 
   Chief Historian, the engineers there run up to you as soon as they see you. Apparently, they still talk
   about the time Rudolph was saved through molecular synthesis from a single electron.
   
   They're quick to add that - since you're already here - they'd really appreciate your help analyzing 
   some unusual data from the Red-Nosed reactor. You turn to check if The Historians are waiting for you, 
   but they seem to have already divided into groups that are currently searching every corner of the 
   facility. You offer to help with the unusual data.
   
   The unusual data (your puzzle input) consists of many reports, one report per line. Each report is a 
   list of numbers called levels that are separated by spaces. For example:
   
   7 6 4 2 1
   1 2 7 8 9
   9 7 6 2 1
   1 3 2 4 5
   8 6 4 4 1
   1 3 6 7 9
   This example data contains six reports each containing five levels.
   
   The engineers are trying to figure out which reports are safe. 
   The Red-Nosed reactor safety systems can only tolerate levels that are either gradually 
   increasing or gradually decreasing. So, a report only counts as safe if both of the following are true:
   
   The levels are either all increasing or all decreasing.
   Any two adjacent levels differ by at least one and at most three.
   In the example above, the reports can be found safe or unsafe by checking those rules:
   
   7 6 4 2 1: Safe because the levels are all decreasing by 1 or 2.
   1 2 7 8 9: Unsafe because 2 7 is an increase of 5.
   9 7 6 2 1: Unsafe because 6 2 is a decrease of 4.
   1 3 2 4 5: Unsafe because 1 3 is increasing but 3 2 is decreasing.
   8 6 4 4 1: Unsafe because 4 4 is neither an increase or a decrease.
   1 3 6 7 9: Safe because the levels are all increasing by 1, 2, or 3.
   So, in this example, 2 reports are safe.
   
   Analyze the unusual data from the engineers. How many reports are safe?
   "
  []
  (->> (parse-input)
       (map #(is-safe? % {:yes (constantly 1), :no (constantly 0)}))
       (reduce +)))

(defn double-check
  "Day2 Puzzle 2: Implement the 'Problem Dampener.' check"
  [report]
  (tap> report)
  (let [level-differences-map (zipmap (range) (map - (rest report) (butlast report)))
        invalid-differences (do (tap> level-differences-map)
                                (filter (fn [[_ v]] (or (<= v 0) (> v 3))) level-differences-map))
        first-invalid-index (do
                              (tap> invalid-differences)
                              (if (seq invalid-differences)
                                (inc (first (keys invalid-differences)))
                                (count report)))
        report-vector (into [] report)]
    (cond

      (>= first-invalid-index  (count report))
      (is-safe? (butlast report) {:yes (constantly 1), :no (constantly 0)})

      (= first-invalid-index  1)
      (if (= 1 (is-safe? (rest report) {:yes (constantly 1), :no (constantly 0)}))
        1
        (is-safe? (into (subvec report-vector 0 1) (subvec report-vector 2))
                  {:yes (constantly 1), :no double-check}))
      :else
      (is-safe?
       (into (subvec report-vector 0 first-invalid-index)
             (subvec report-vector (inc first-invalid-index)))
       {:yes (constantly 1), :no (constantly 0)}))))

(defn double-check-hard
  "just go one by one and check if it's safe"
  ([report]
   (double-check-hard report 0))
  ([report index]
   (loop [report (vec report)
          index index]
     (if (>= index (count report))
       0
       (if (= 1 (is-safe?
                 (into (subvec report 0 index)
                       (subvec report (inc index)))
                 {:yes (constantly 1), :no (constantly 0)}))
         1
         (recur report (inc index)))))))

(defn do-puzzle2
  "--- Day 2: Red-Nosed Reports ---
     --- Part Two ---
    
    The engineers are surprised by the low number of safe reports until they realize they forgot to tell 
    you about the Problem Dampener.
    
    The Problem Dampener is a reactor-mounted module that lets the reactor safety systems tolerate 
    a single bad level in what would otherwise be a safe report. It's like the bad level never happened!
    
    Now, the same rules apply as before, except if removing a single level from an unsafe report 
    would make it safe, the report instead counts as safe.
    
    More of the above example's reports are now safe:
    
    7 6 4 2 1: Safe without removing any level.
    1 2 7 8 9: Unsafe regardless of which level is removed.
    9 7 6 2 1: Unsafe regardless of which level is removed.
    1 3 2 4 5: Safe by removing the second level, 3.
    8 6 4 4 1: Safe by removing the third level, 4.
    1 3 6 7 9: Safe without removing any level.
    Thanks to the Problem Dampener, 4 reports are actually safe!
    
    Update your analysis by handling situations where the Problem Dampener can remove a single level 
    from unsafe reports. How many reports are now safe?
  "
  []
  (->> (parse-input)
       (map #(is-safe? % {:yes (constantly 1), :no double-check-hard}))
       (reduce +)))
  