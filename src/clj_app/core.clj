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
   16 "sixteen", 17 "seventeen", 18 "eighteen",
   19 "nineteen"})

(def tens   {20 "twenty", 30 "thirty",  40 "fourty", 50 "fifty",
             60 "sixty" , 70 "seventy", 80 "eighty", 90 "ninety"})

(defn decimate-tens [n]
  (* (int (/ n 10)) 10))

(defn decimate-hundreds [n]
  (int (/ n 100)))

(defn decimate-thousands [n]
  (int (/ n 1000)))

(defn get-n-after-decimal-point [n]

  " Get .00 digits,
  up to 2 decimal places :/
  sorry for the regex, taken
  from SO"

  (int ;; make it a whole number
   (* (read-string ;; make it a number again
       (format "0%s" (first (rest (re-find #"\d+(\.\d{1,2})?" (str n))))))
      100)))

(defn single-digit-parser [n & [product]]
  (let [p (or product 10)]
    (rem n p)))

(defn tens-filter [n]
  (and (>= n 20) (<= n 99)))

(defn hundreds-filter [n]
  (and (>= n 100)(<= n 999)))

(defn thousands-filter [n]
  (and (>= n 1000) (<= n 9999)))

(defn filter-amount [n]
  (cond
   (not (number? n)) :not-a-number
   (rational? n)(do
                  (cond
                   (<= n 19) (zero-to-nineteen n)
                   (and (zero? (mod n 10))  (tens-filter n)) (tens (decimate-tens n))
                   (and (zero? (mod n 100)) (hundreds-filter n)) (str (zero-to-nineteen (decimate-hundreds n)) " hundred")
                   (and (zero? (mod n 100)) (thousands-filter n)) (str (zero-to-nineteen (decimate-thousands n)) " thousand")
                   (tens-filter n)(str (tens (decimate-tens n)) "-" (zero-to-nineteen (single-digit-parser n)))
                   (hundreds-filter n) (str (zero-to-nineteen (decimate-hundreds n)) " hundred and " (filter-amount (single-digit-parser n 100)))
                   (thousands-filter n) (str (zero-to-nineteen (decimate-hundreds n)) " hundred and " (filter-amount (single-digit-parser n 100)))))
   (not (rational? n))()))


(defn amount [x]

  "Accepts an amount and convert it to the appropriate string Dollar representation."

    (let [answer (filter-amount x)]
    (do
      (when (not= answer :not-a-number)
        (if (= answer "one")
          (format "%s dollar" answer)        ;; the singular case
          (format "%s dollars" answer)))))) ;; the plural case


(amount 9000)
