(ns myweb.core-test
  (:require [clojure.test :refer :all]
            [myweb.functions :refer :all]))

(deftest flexiana-01
  (testing ""
      (is (scramble? "rekqodlw" "world"))))

(deftest flexiana-02
  (testing ""
      (is (scramble? "cedewaraaossoqqyt" "codewars"))))

(deftest flexiana-03
  (testing ""
      (is (not (scramble? "katas"  "steak")))))


(deftest same-word
  (testing "any word can be rearranged to match itself"
    (is (scramble? "burrito" "burrito"))))


(deftest superset
  (testing "no word can be rearranged to match the concatenation of itself
and at least one character"
    (is (not (scramble? "burrito" "burritox")))))


(deftest corner-case-empty-string-1
  (testing "any word can be rearranged to match the empty string"
    (is (scramble? "burrito" ""))))


(deftest corner-case-empty-string-2
  (testing "the empty string cannot be rearranged to match any string"
    (is (not (scramble? "" "burrito")))))



