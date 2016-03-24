(ns test-timings.core-test
  (:require [clojure.test :refer :all]
            [test-timings.core :refer :all]))

(deftest test-config-url
  (testing "Reading config."
    (is (= "http://local.api.spotxchange.com/1.0/Publisher(68800)" (:base_url config)))))


(deftest test-config-token
  (testing "Reading token from config."
    (is (= "71ee02dfca317e558d9b52772d3fdd82842392f2" (:access_token config)))))

