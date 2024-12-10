(ns tudorescu.advent2024.day4)


(defn do-puzzle1
  "
    --- Day 4: Ceres Search ---
   
   'Looks like the Chief's not here. Next!' 
   One of The Historians pulls out a device and pushes the only button on it.
   After a brief flash, you recognize the interior of the Ceres monitoring station!
   
   As the search for the Chief continues, 
   a small Elf who lives on the station tugs on your shirt; 
   she'd like to know if you could help her with her word search (your puzzle input). 
   She only has to find one word: XMAS.
   
   This word search allows words to be horizontal, vertical, diagonal, 
   written backwards, or even overlapping other words. 
   It's a little unusual, though, as you don't merely need to find one instance of XMAS
   - you need to find all of them. 
   Here are a few ways XMAS might appear, w
   here irrelevant characters have been replaced with .:
   
   ..X...
   .SAMX.
   .A..A.
   XMAS.S
   .X....
   The actual word search will be full of letters instead. For example:
   
   MMMSXXMASM
   MSAMXMSMSA
   AMXSXMAAMM
   MSAMASMSMX
   XMASAMXAMM
   XXAMMXXAMA
   SMSMSASXSS
   SAXAMASAAA
   MAMMMXMMMM
   MXMXAXMASX
   In this word search, XMAS occurs a total of 18 times;
   here's the same word search again, but where letters not involved 
   in any XMAS have been replaced with .:
   
    0123456789
   0....XXMAS.
   1.SAMXMS...
   2...S..A...
   3..A.A.MS.X
   4XMASAMX.MM
   5X.....XA.A
   6S.S.S.S.SS
   7.A.A.A.A.A
   8..M.M.M.MM
   9.X.X.XMASX

  -3  -3  *  * 0 *  *  +3 
  -2   * -2  * 0 *  +2  *
  -1   *  * -1 0 +1  *  *
   0  -3 -2 -1 X +1 +2 +3
  +1   *  * -1 0 +1  *  *
  +2   * -2  * 0  * +2  *
  +3  -3  *  * 0  *  * +3
   
   1  (0 5, 0 6, 0 7, 0 8)
   2  (0 4, 1 5, 2 6, 3 7)
   3  (1 1, 1 2, 1 3, 1 4)
   4  (1 6, 2 6, 3 6, 4 6)
   5  (3 9, 4 9, 5 9, 6 9)
   6  (3 9, 4 8, 5 7, 6 6)
   7  (4 0, 4 1, 4 2, 4 3)
   8  (4 3, 4 4, 4 5, 4 6) (SAMX)
   9  (2 3, 3 2, 4 1, 5 0) (SAMX)
   10 (5 6, 4 5, 3 4, 2 3)
   11 (6 0, 7 1, 8 2, 9 3)
   12 (6 2, 7 3, 8 4, 9 5)
   13 (6 4, 7 3, 8 2, 9 1) (SAMX)
   14 (6 6, 7 5, 8 4, 9 3) (SAMX)
   15 (6 8, 7 7, 8 6, 9 5) (SAMX)
   16 (9 5, 9 6, 9 7, 9 8)
   17 (9 9, 8 9, 7 9, 6 9)
   18 (9 9, 8 8, 7 7, 6 6)

   
   Take a look at the little Elf's word search. How many times does XMAS appear?
   
   Idea: just count the number of X, M, A, S in the input string 
   and return the minimum of the counts raised to 4 (since XMAS has 4 letters)

   (->> test-input (clojure.string/split-lines)
     (map #(clojure.string/trim %))
     (map (fn [str-elem] (re-seq #"." str-elem)))
     (map (fn [line] (map (fn [char pos] (conj [] pos char)) line (range (count line))))))
(([0 "a"] [1 "b"] [2 "c"] [3 "d"] [4 "e"] [5 "f"] [6 "g"] [7 "h"] [8 "i"] [9 "j"] [10 "k"] [11 "l"] [12 "m"] [13 "n"] [14 "o"]) ([0 "p"] [1 "q"] [2 "r"] [3 "s"] [4 "t"] [5 "u"] [6 "v"] [7 "w"] [8 "x"] [9 "y"] [10 "z"] [11 "a"] [12 "b"] [13 "c"] [14 "d"]) ([0 "e"] [1 "f"] [2 "g"] [3 "h"] [4 "i"] [5 "j"] [6 "k"] [7 "l"] [8 "m"] [9 "n"] [10 "o"] [11 "p"] [12 "q"] [13 "r"] [14 "a"]) ([0 "s"] [1 "t"] [2 "u"] [3 "v"] [4 "w"] [5 "x"] [6 "y"] [7 "z"] [8 "a"] [9 "b"] [10 "c"] [11 "d"] [12 "e"] [13 "f"] [14 "g"]) ([0 "h"] [1 "i"] [2 "j"] [3 "k"] [4 "l"] [5 "m"] [6 "n"] [7 "o"] [8 "p"] [9 "q"] [10 "r"] [11 "s"] [12 "t"] [13 "u"] [14 "v"]))

 "
  ([] (do-puzzle1 (slurp "resources/04_input.txt")))
  ([input]
   (let [xmas-count (fn [input] (->> input (filter #{\X \M \A \S}) (frequencies)))]
     (->> input
          (xmas-count)
          (vals)
          (map #(Math/pow % 4))
          (apply min)))))