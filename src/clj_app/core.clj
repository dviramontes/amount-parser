(ns clj-app.core
  (:gen-class)
  (:require
            [translate.google :refer :all]
            [environ.core :refer [env]]
            ;;[ring.adapter.jetty :as jetty]
   ))

(def zero-to-nineteen
  (zipmap (range 19) ["zero" "one" "two" "three" "four" "five"
           "six" "seven" "eight" "nine" "ten" "eleven"
           "twelve" "thirteen" "fourteen" "fifteen" "sixteen"
           "seventeen" "eighteen" "nineteen"]))

(def tens
  (zipmap (range 20 100 10) ["twenty" "thirty" "fourty" "fifty",
             "sixty" "seventy" "eighty" "ninety"]))

(defn decimate-tens [n]
  (* (int (/ n 10)) 10))

(defn decimate-hundreds [n]
  (int (/ n 100)))

(defn decimate-thousands [n]
  (int (/ n 1000)))

(defn get-n-after-decimal-point [n]

  " Get 0.00 digits,
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
   (= :quit n) :quit
   (not (number? n)) :not-a-number
   (or (< n -9999.99) (> n 9999.99)) :number-out-of-range
   (rational? n)
   (do
     (cond
      (neg? n) (filter-amount (* -1 n))
      (<= n 19) (zero-to-nineteen n)
      (and (zero? (mod n 10))   (tens-filter n)) (tens (decimate-tens n))
      (and (zero? (mod n 100))  (hundreds-filter n)) (str (zero-to-nineteen (decimate-hundreds n)) " hundred")
      (and (zero? (mod n 1000)) (thousands-filter n)) (str (zero-to-nineteen (decimate-thousands n)) " thousand")
      (tens-filter      n) (str (tens (decimate-tens n)) "-" (zero-to-nineteen (single-digit-parser n)))
      (hundreds-filter  n) (str (zero-to-nineteen (decimate-hundreds n)) " hundred and " (filter-amount (single-digit-parser n 100)))
      (thousands-filter n)
        (do
          (if (<= (single-digit-parser n 1000) 99)
            (str (zero-to-nineteen (decimate-thousands n)) " thousand and "  (filter-amount (single-digit-parser n 1000)))
            (str (zero-to-nineteen (decimate-thousands n)) " thousand "  (filter-amount (single-digit-parser n 1000)))
            ))))
   (not (rational? n))
     (do
       (str (filter-amount (int (rationalize n))) " dollars with " (get-n-after-decimal-point n) "/100 cents"))))


(defn amount [x]

  " Given an amount, it converts it to the appropriate string Dollar/Cent representation."

  (let [answer (filter-amount x)
        cap #(clojure.string/capitalize %)]
    (cond (= answer :not-a-number) "you have passed not a number"
          (= answer :quit) :quit
          (= answer :number-out-of-range) "Number-out-of-range (< n -9999.99) (> n 9999.99)"
          :else (do
            (cond
             (neg? x) (do (if (rational? x)
                            (format "Negative %s dollars" answer)
                            (format "Negative %s" answer)))
             (rational? x)
             (do
               (if (= answer "one")
                 (format "%s dollar" (cap answer)) ;; the singular case
                 (format "%s dollars" (cap answer))));; the plural case
             (not (rational? x))(cap answer)))
          )))


(println "Type :quit to exit program")
(println "Currently, this program only supports floats or
         ints from +-0.00 to +-9999.99")

(defn -main

  "Given an amount in x dollars, I can print the string(English) representation
  of that number. Current limitation 0 - 9999 and 0.00 to 9999.99"

  [& args]
  (print "Enter amount to translate >>> ")
  (flush)
  (let [n (read)
        value (amount n)]
    (if (not= :quit value)
      (do
        (println value)
        (recur -main))
      (System/exit 0))))

(env :google-translate-api-key)

#_(def foo(translate "hello world!" { :key (env :google-translate-api-key)
                     :source "en"
                     :target "es"
                    }))

((fn [n] (time (map #(* % %) (filter odd? (range n))))) 20)

