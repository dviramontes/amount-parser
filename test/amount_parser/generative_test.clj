(ns amount-parser.generative-test
  (:require
            [amount-parser.filter :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :as ct :refer (defspec)]))


;; Property Based Testing
;; ----------------------

;; (gen/sample gen/int)

;; (gen/sample (gen/choose 0 9999))

;; (gen/sample gen/nat)


;; Property to test.check

(def should-be-the-same-as-x
  (prop/for-all [numbers (gen/list gen/nat)]
          ;; predicate
          (= (first numbers) (peek numbers))))

;;(tc/quick-check 100 should-be-the-same-as-x)
;;(peek (list 1 2 3))
;;(peek (vector 1 2 3))

(defspec foo
  100
  should-be-the-same-as-x)

