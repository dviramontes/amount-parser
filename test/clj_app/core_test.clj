(ns clj-app.core-test
  (:require [clojure.test :refer :all]
            [clj-app.core :refer :all]))

(deftest decimate-tens-function-test
  (testing "Given 25 should result in 20"
    (is (= (decimate-tens 25) 20))))

(deftest decimate-hundreds-function-test
  (testing "Given 250 should result in 2"
    (is (= (decimate-hundreds 250) 2))))

(deftest get-n-after-decimal-point-test
  (testing "Given 2.01 and 2.10,
            should result in 1 and 10 respectively"
    (is (= (get-n-after-decimal-point 2.01) 1))
    (is (= (get-n-after-decimal-point 2.10) 10))))

(deftest single-digit-parser-test
  (testing "this one does the opposite of the above,
    given 25 should result in 5 for example"
    (is (= (single-digit-parser 25) 5))))

(deftest tens-formater-test
  (testing "Given 25, it should result in the
    string twenty-five with a dash"
    (is (= (amount 25) "Twenty-five dollars"))
    (is (not (empty? (re-find #"-" (amount 25)))))))

(deftest rational-amount-test
  (testing "Given 1, should result in 1 [singular] dollar"
    (is (= (amount 1) "One dollar")))
  (testing "Given a ratinal number 25,
    should result in the string twenty-five dollars"
    (is (= (amount 25) "Twenty-five dollars")))
  (testing "Given 100, should result in one hundred dollars"
    (is (= (amount 100) "One hundred dollars"))))

(deftest irrational-amount-test
  (testing "Given 25.25, should result in.."
    (is (= (amount 25.25) "Twenty-five dollars with 25/100 cents"))))

(deftest negative-number-test
  (testing "Given a negative number, should result in Negative xyz amount"
    (is (= (amount -110) "Negative one hundred and ten dollars"))
    (is (= (amount -110.10) "Negative one hundred and ten dollars with 10/100 cents"))))
