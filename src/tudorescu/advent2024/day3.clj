(ns tudorescu.advent2024.day3
  (:require
   [clojure.string :as str]))

(defn parse-and-multiply
  "find the multiply instructions and add-up the results"
  [input]
  (if (nil? input)
    0
    (->> (re-seq #"mul\(\d+,\d+\)" input)
         (map (fn [mul] (re-seq #"\d+" mul)))
         (map (fn [[a b]] (* (Integer/parseInt a) (Integer/parseInt b))))
         (reduce +))))

(defn do-puzzle1
  "--- Day 3: Mull It Over ---

    Our computers are having issues, so I have no idea if we have any Chief Historians in stock! 
    'You're welcome to check the warehouse, though' says the mildly flustered shopkeeper at the North Pole Toboggan Rental Shop. 
    
    The Historians head out to take a look.
    
    The shopkeeper turns to you. 'Any chance you can see why our computers are having issues again?'
    
    The computer appears to be trying to run a program, but its memory (your puzzle input) is corrupted. 
    All of the instructions have been jumbled up!
    
    It seems like the goal of the program is just to multiply some numbers. 
    It does that with instructions like mul(X,Y), where X and Y are each 1-3 digit numbers. 
    For instance, mul(44,46) multiplies 44 by 46 to get a result of 2024. Similarly, mul(123,4) would multiply 123 by 4.
    
    However, because the program's memory has been corrupted, there are also many invalid characters that should be ignored, 
    even if they look like part of a mul instruction. Sequences like mul(4*, mul(6,9!, ?(12,34), or mul ( 2 , 4 ) do nothing.
    
    For example, consider the following section of corrupted memory:
    
    xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
    Only the four highlighted sections are real mul instructions. 
    Adding up the result of each instruction produces 
    161 (2*4 + 5*5 + 11*8 + 8*5).
    
    Scan the corrupted memory for uncorrupted mul instructions. 
    What do you get if you add up all of the results of the multiplications?
    
    Answer: 174336360
    "
  []
  (let [input (slurp "resources/03_input.txt")]
    (parse-and-multiply input)))

;;#"do(?!n't)"

(defn search-untildont
  "search for don't or do or end of string"
  [input]
  (let [end-dont (str/index-of input "don't")
        end-string (count input)]
    (if (nil? end-dont) end-string end-dont)))

(defn get-subsegment
  "partition input into a segment that ends with don't or do or end of string"
  [input]
  (let [end-segment (search-untildont input)]
    (subs input 0 end-segment)))


(defn get-search-segment
  "search for do or begining of string -> get segment until don't or end of string
   and multiply the segment, return result and end-position"
  [input]
  (let [pos-do (str/last-index-of input "do")
        pos-dont (str/last-index-of input "don't")
        end-pos (if (nil? pos-dont) (count input) pos-dont)]
    (cond
      (nil? pos-do)
      {:end-position 0,   
       :segment-result (parse-and-multiply input)}
      
      (= pos-do end-pos)
      {:end-position pos-do,
       :segment-result 0}

      (> end-pos pos-do)
      {:end-position pos-do,
       :segment-result (parse-and-multiply
                        (subs input pos-do end-pos))})))

(defn do-puzzle2

  "--- Day 3: Mull It Over ---
     --- Part Two ---
     
     As you scan through the corrupted memory, you notice that some 
     of the conditional statements are also still intact. 
     If you handle some of the uncorrupted conditional statements in the program, 
     you might be able to get an even more accurate result.
     
     There are two new instructions you'll need to handle:
     
     The do() instruction enables future mul instructions.
     The don't() instruction disables future mul instructions.
     Only the most recent do() or don't() instruction applies. 
     At the beginning of the program, mul instructions are enabled.
     
     For example:
     
     xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
     This corrupted memory is similar to the example from before, 
     but this time the mul(5,5) and mul(11,8) instructions are disabled 
     because there is a don't() instruction before them. 
     The other mul instructions function normally, including the one at the end 
     that gets re-enabled by a do() instruction.
     
     This time, the sum of the results is 48 (2*4 + 8*5).
     
     Handle the new instructions; what do you get if you add up all of the 
     results of just the enabled multiplications?

     Solution:
     
     -----don't----do---don't----do---do---don't---don't---do----
     reverse search do or begining of string:
      --- search don't or do or end of string: substring -> input
      (let start-segment (last-index-of input 'do' (count input))
      (index-of input 'don't' )"

  ([] (do-puzzle2 (slurp "resources/03_input.txt")))
  ([input]
   (loop [segment input
          result 0]
     (if (empty? segment)
       result
       (let [search-segment (get-search-segment segment)]
         (recur (subs segment 0 (:end-position search-segment))
                (+ result (:segment-result search-segment))))))))
