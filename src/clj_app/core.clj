(ns clj-app.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(defn count-tens [n] (int (/ n 10)))

(defn count-hundreds [n] (int (/ n 100)))

(defn rational [n] (if (rational? n) n (float (rationalize n))))

(defn amount [n]
  "Accepts an amount and convert it to the appropriate string Dollar representation."
  (let [ zero-to-nineteen
                 {0 "zero"
                  1 "one",  2 "two",  3 "three",  4 "four",
                  5 "five", 6 "six",  7 "seven",  8 "eight",
                  9 "nine", 10 "ten", 11 "eleven",12 "twelve",
                  13 "thirteen", 14 "fourteen",   15 "fifteen",
                  16 "sixteen", 17 "sevente", 18 "eighteen",
                  19 "nineteen"}

         tens {20 "twenty", 30 "thirty", 40 "fourty", 50 "fifty", 60 "sixty" , 70 "seventy", 80 "eighty", 90 "ninety"}


         ;; assumes rational numbers
         answer
           (cond (false? (number? n)) :not-a-number
                 (<= n 19) (zero-to-nineteen (if (integer? n) n (int n)))
                 (and (zero? (mod n 10)) (>= 20) (<= 100)) (tens (* (count-tens n) 10))


                 :else "out of range")]

      (println answer)

      (when (not= :not-a-number answer)
        (str
          (format "%s dollars :: %d" answer n)))
    ))

(amount 30)

