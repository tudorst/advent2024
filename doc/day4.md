# *Day 4*

```clojure
   (->> test-input (clojure.string/split-lines)
     (map #(clojure.string/trim %))
     (map (fn [str-elem] (re-seq # ."" str-elem)))
     (map (fn [line] (map (fn [char pos] (conj [] pos char)) line (range (count line))))))
(([0 "a"] [1 "b"] [2 "c"] [3 "d"] [4 "e"] [5 "f"] [6 "g"] [7 "h"] [8 "i"] [9 "j"] [10 "k"] [11 "l"] [12 "m"] [13 "n"] [14 "o"]) ([0 "p"] [1 "q"] [2 "r"] [3 "s"] [4 "t"] [5 "u"] [6 "v"] [7 "w"] [8 "x"] [9 "y"] [10 "z"] [11 "a"] [12 "b"] [13 "c"] [14 "d"]) ([0 "e"] [1 "f"] [2 "g"] [3 "h"] [4 "i"] [5 "j"] [6 "k"] [7 "l"] [8 "m"] [9 "n"] [10 "o"] [11 "p"] [12 "q"] [13 "r"] [14 "a"]) ([0 "s"] [1 "t"] [2 "u"] [3 "v"] [4 "w"] [5 "x"] [6 "y"] [7 "z"] [8 "a"] [9 "b"] [10 "c"] [11 "d"] [12 "e"] [13 "f"] [14 "g"]) ([0 "h"] [1 "i"] [2 "j"] [3 "k"] [4 "l"] [5 "m"] [6 "n"] [7 "o"] [8 "p"] [9 "q"] [10 "r"] [11 "s"] [12 "t"] [13 "u"] [14 "v"]))
```

```clojure
Alternative using zipmap:
(->> test-input
     (clojure.string/split-lines)
     (map #(clojure.string/trim %))
     (zipmap (range))
     (map (fn [[k v]] (into {} {:l-index k, :l-text (re-seq #"." v)}))))
({:l-index 0, :l-text ("M" "M" "M" "S" "X" "X" "M" "A" "S" "M")} 
 {:l-index 7, :l-text ("S" "A" "X" "A" "M" "A" "S" "A" "A" "A")} 
 {:l-index 1, :l-text ("M" "S" "A" "M" "X" "M" "S" "M" "S" "A")} 
....)
```


```clojure
Alternative using map-indexed:
(->> test-input
     (clojure.string/split-lines)
     (map #(clojure.string/trim %))
     (map-indexed #(hash-map))
({0 "MMMSXXMASM"} 
 {1 "MSAMXMSMSA"} 
 {2 "AMXSXMAAMM"}...)
 ```

