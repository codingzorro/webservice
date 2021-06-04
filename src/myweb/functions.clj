(ns myweb.functions
"Functions inspired by the Scrabble boardgame but not limited to letters")


(defn enough-maker [coll]
  "Considers `coll` as a 'pool' of non-unique elements and delivers a function
   which checks whether the pool as many instances as required of a given
   element"
  (let [pool (frequencies coll)]
    (fn [[elem required-number]]
      (>= (get pool elem 0) required-number))))


(defn scramble?
  "returns true if a portion of str1 characters can be rearranged to match
   str2, otherwise returns false"
  [str1 str2]
  (let [enough? (enough-maker str1)]
    (every? enough? (frequencies str2))))
