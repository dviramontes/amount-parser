(ns clj-app.core-test
  (:require [clojure.test :refer :all]
            [clj-app.core :refer :all]))

(deftest decimate-tens-function-test
  (testing "Given 25 should result in 20"
    (is (= (decimate-tens 25) 20) "some message if failed")))

(deftest decimate-hundreds-function-test
  (testing "Given 250 should result in 2"
    (is (= (decimate-hundreds 250) 2) "some message if failed")))

(deftest get-n-after-decimal-point-test
  (testing "Given 2.01 and 2.10,
            should result in 1 and 10 respectively"
    (is (= (get-n-after-decimal-point 2.01) 1))
    (is (= (get-n-after-decimal-point 2.10) 10))))

(deftest single-digit-parser-test
  (testing "this one does the opposite of the above,
    given 25 should result in 5 for example"
    (is (= (single-digit-parser 25) 5))))

(deftest final-tens-formater-test
  (testing "Given 25, it should result in the
    str twenty-five with dash"
    (is (= (final-tens-formater 25) "twenty-five"))
    (is (not (empty? (re-find #"-" (final-tens-formater 25)))))))

(deftest final-hundreds-formater-test
  (testing "Given 250, should result in
    the str two hundred and fifty dollars"
    (is (= (final-hundreds-formater 250 amount) "two hundred and fifty dollars"))))
