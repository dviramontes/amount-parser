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

(defn decimate-tens [n]
  (* (int (/ n 10)) 10))

(defn decimate-hundreds [n]
  (int (/ n 100)))

(defn get-n-after-decimal-point [n]

  " Get .00 digits,
  up to 2 decimal places :/
  sorry for the regex, taken
  from SO"

  (int ;; make it a whole number
   (*
    (read-string ;; make it a number again
     (format "0%s"
             (first (rest (re-find #"\d+(\.\d{1,2})?" (str n)))))) 100)))

(defn single-digit-parser [n & [product]]
  (let [p (or product 10)]
    (rem n p)))

(defn final-tens-formater [n]
  (str (tens (decimate-tens n)) "-" (zero-to-nineteen (single-digit-parser n))))

(defn final-hundreds-formater [n fun]
  (if (zero? (mod n 100))
  (str (zero-to-nineteen (decimate-hundreds n)) " hundred dollars")
    (str (zero-to-nineteen (decimate-hundreds n)) " hundred and " (fun (single-digit-parser n 100)))))

(defn final-cents-formatter [n]
  (str (final-tens-formater (get-n-after-decimal-point n))))

(final-tens-formater 20)

(defn amount [n]

  "Accepts an amount and convert it to the appropriate string Dollar representation."

  (let [ ;; look for rational numbers
        answer (if (not (number? n)) :not-a-number
                 (if (rational? n)
                   ((fn [r] ;; rational numbers
                      (let
                        [tens-fiter (and (>= r 20) (<= r 99))
                         hundreds-filter (and (>= r 100) (<= r 999))
                         thousands-filter (and (>= r 100) (<= r 999))])
                      (cond
                       (<= r 19) (zero-to-nineteen r)
                       (and (zero? (mod n 10)) 'tens-fiter)  (tens (decimate-tens n))
                       (and (zero? (mod n 100)) 'hundreds-filter) (tens (decimate-tens n))
                       'tens-fiter (final-tens-formater r)
                       'hundreds-filter (final-hundreds-formater r amount))) n)
                   ((fn [i] ;; irrational numbers
                      (if
                        (zero? i) nil
                        ()
                        )) n)))]

    (println answer)
    ;; side-effecting?
    (when (not= answer :not-a-number)
      (if (= answer "one")
        (str (format "%s dollar" answer)) ;; the singular case
        (str (format "%s dollars" answer)))))) ;; the plural case


(amount 1)

(final-tens-formater 22)
(final-hundreds-formater 1 amount)
(final-hundreds-formater 999 amount)
