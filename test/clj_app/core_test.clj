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
