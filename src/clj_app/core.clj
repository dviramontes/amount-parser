(ns clj-app.core
  (:gen-class))

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

  " Get x.0 digits,
  up to 2 decimal places"

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

  "Defines the filtering rules for determining Dollar amount"

  (cond
   (not (number? n)) :not-a-number
   (rational? n)(do
                  (cond
                   (<= n 19) (zero-to-nineteen n)
                   (and (zero? (mod n 10))   (tens-filter n)) (tens (decimate-tens n))
                   (and (zero? (mod n 100))  (hundreds-filter n)) (str (zero-to-nineteen (decimate-hundreds n)) " hundred")
                   (and (zero? (mod n 1000)) (thousands-filter n)) (str (zero-to-nineteen (decimate-thousands n)) " thousand")
                   (tens-filter      n) (str (tens (decimate-tens n)) "-" (zero-to-nineteen (single-digit-parser n)))
                   (hundreds-filter  n) (str (zero-to-nineteen (decimate-hundreds n)) " hundred and " (filter-amount (single-digit-parser n 100)))
                   (thousands-filter n) (do
                                          (if (<= (single-digit-parser n 1000) 99)
                                            (str (zero-to-nineteen (decimate-thousands n)) " thousand and "  (filter-amount (single-digit-parser n 1000)))
                                            (str (zero-to-nineteen (decimate-thousands n)) " thousand "  (filter-amount (single-digit-parser n 1000)))
                                            ))))
   (not (rational? n))
   (do
     (str (filter-amount (int (rationalize n))) " dollars with " (get-n-after-decimal-point n) "/100 cents"))))


(defn amount [x]

  " Given an amount, it converts it to the appropriate string Dollar / Cent representation."

  (let [answer (filter-amount x)
        cap #(clojure.string/capitalize %)]
    (when (not= answer :not-a-number)
      (if (rational? x)
        (do
          (if (= answer "one")
            (format "%s dollar" (cap answer)) ;; the singular case
            (format "%s dollars" (cap answer))));; the plural case
        (cap answer)))))

(defn -main

  "Given an amount in x dollars, I can print the string(English) representation
  of that number. Current limitation 0 - 9999 and 0.00 to 9999.99"

  [& args]
  (print "Amount to translate ")
  (print ">>> ")
  (flush)
  (let [n (read)
        value (amount n)]
    (if (not= :quit value)
      (do
        (println value)
        (recur -main))
      (System/exit 0))))

