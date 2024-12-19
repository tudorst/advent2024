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
   
  -3  (S|X)   *     *   (S|X)   *     *  (S|X) 
  -2   *    (A|M)   *   (A|M)   *    (A|M)  *
  -1   *      *   (M|A) (M|A) (M|A)   *    *
   0  (S|X) (A|M) (M|A) (X|S) (M|A) (A|M) (S|X)
  +1   *      *   (M|A) (M|A) (M|A)   *    *
  +2   *    (A|M)  *    (A|M)  *    (A|M)  *
  +3  (S|X)   *    *    (S|X)  *      *  (S|X)
  
   pattern:
   (S|X)(A|M)(M|A)(X|S)|
   (S|X)(.{line+1})(A|M)(.{line+2)})(M|A)(.{line+3})(X|S)|
   (S|X)(.{line-1})(A|M)(.{line-2)})(M|A)(.{line-3})(X|S)|
   (S|X)(.{line})(A|M)(.{line)})(M|A)(.{line})(X|S)
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

   
   Take a look at the little Elf's word search. How many times does XMAS appear?"
  ([] (do-puzzle1 (slurp "resources/04_input.txt")))
  ([input]
   (let [xmas-count (fn [input] (->> input (filter #{\X \M \A \S}) (frequencies)))]
     (->> input
          (xmas-count)
          (vals)
          (map #(Math/pow % 4))
          (apply min)))))