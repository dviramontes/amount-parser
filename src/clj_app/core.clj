(ns clj-app.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(def zero-to-nineteen
                 {0 "zero"
                  1 "one",  2 "two",  3 "three",  4 "four",
                  5 "five", 6 "six",  7 "seven",  8 "eight",
                  9 "nine", 10 "ten", 11 "eleven",12 "twelve",
                  13 "thirteen", 14 "fourteen",   15 "fifteen",
                  16 "sixteen", 17 "sevente", 18 "eighteen",
                  19 "nineteen"})

(def tens     {20 "twenty", 30 "thirty",  40 "fourty", 50 "fifty",
               60 "sixty" , 70 "seventy", 80 "eighty", 90 "ninety"})

(conj [] (tens (count-tens 22)) (zero-to-nineteen 2))

(defn count-tens [n]
  (* (int (/ n 10)) 10))

(count-tens 30)

(defn count-hundreds [n]
  (* (int (/ n 100))) 100)

(defn get-n-after-decimal-point [n]
  " Get .00 digits,
    up to 2 decimal places? :("
  (int
   (* ;; make it a whole number
     (read-string ;; make it a number again
       (format "0%s"
               (first (rest (re-find #"\d+(\.\d{1,2})?" (str n)))))) 100)))

(get-n-after-decimal-point 2.10) ;; => 10
(get-n-after-decimal-point 2.01) ;; => 1

(defn digit-parser [x & [power]]
  (let [p (or power 10)]
  (rem x (or power 10))))

(digit-parser 12)

(defn amount [n]

  "Accepts an amount and convert it to the appropriate string Dollar representation."

  (let [
         ;; look for rational numbers if not then ...
         answer (if (not (number? n)) :not-a-number
                 (if (rational? n)
                   ((fn [r] ;; rational
                      (
                        [tens-fiter (and (>= r 20) (<= r 99))
                         hundreds-filter (and (>= r 100) (<= r 999))])
                        (cond
                         (<= r 19) (zero-to-nineteen r)
                         (and (zero? (mod n 10)) 'tens-fiter)  (tens (count-tens n))
                         (and (zero? (mod n 100)) 'hundreds-filter) (tens (count-tens n))
                         ;;'(tens-fiter) "tes"
                        )) n)
                    ((fn [i] ;; irrational
                       :irrational
                       ) n)))]

      (println answer)

      (when (not= answer :not-a-number)
        (str
          (format "%s dollars :: %d" answer n)))))

(amount 20)





