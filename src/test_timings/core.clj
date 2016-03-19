(ns test-timings.core
  (:gen-class)
  (:require [clj-http.client :as http])
  (:require [clojure.edn :as edn]))

(def config
  (edn/read-string (slurp "resources/config.edn")))


(def default-headers
  {:headers {:authentication (str "Bearer " (:access_token config))}})

(def channel-url
  (str (:base_url config) "/Channel"))

(defn call-channel
  ([] (http/get channel-url default-headers)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (:body response)))


