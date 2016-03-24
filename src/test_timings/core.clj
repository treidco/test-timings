(ns test-timings.core
  (:gen-class)
  (:require [clj-http.client :as http])
  (:require [clojure.data.json :as json])
  (:require [clojure.edn :as edn]))

(def config
  (edn/read-string (slurp "resources/config.edn")))

(defn build-url
  [service]
  (str (:base_url config) "/" service))

(defn channel-url [] (build-url "Channel"))
(defn campaign-url [] (build-url "Campaign"))

(defn call-service
  [url]
  (http/get url
            {:headers {:authorization (str "Bearer " (:access_token config))}}))

(defn call-channel
  ([] (call-service channel-url))
  ([channel-id] (call-service (str channel-url "(" channel-id ")"))))

(defn parse-json
  [response key]
  (get (json/read-str response) key))

(defn is_enabled [channel_id] (parse-json (:body (call-channel channel_id)) "enabled"))

(defn get-timing-map []
  (let [resp (call-service (campaign-url))]
    (let [headers (:headers resp)]
      (filter (fn [x]
                (.startsWith (key x) "x_timing")) headers))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println
    (parse-json (:body (call-channel 75779)) "name")))


