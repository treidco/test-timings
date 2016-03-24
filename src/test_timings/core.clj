(ns test-timings.core
  (:gen-class)
  (:require [clj-http.client :as http])
  (:require [clojure.data.json :as json])
  (:require [clojure.edn :as edn]))
;(use 'test-timings.core :reload-all)

(def config
  (edn/read-string (slurp "resources/config.edn")))

(defn base-url
  [service]
  (str (:base_url config) "/" service))

(defn build-url
  ([service] (base-url service))
  ([service id] (str (base-url service) "(" id ")"))
  ([service id property] (str (build-url service id) "/" property)))

(defn channel-url [& args] (apply build-url "Channel" args))
(defn campaign-url [& args] (apply build-url "Campaign" args))

(defn execute-service
  [url]
  (http/get url
            {:headers {:authorization (str "Bearer " (:access_token config))}}))

(defn call-channel
  ([] (call-service (channel-url) ))
  ([channel-id] (call-service (str (channel-url) "(" channel-id ")"))))

(defn call-service
  ([url] (execute-service url))
  ([url id] (execute-service (str url "(" id ")"))))

(defn call-campaign
  ([] (call-service (campaign-url)))
  ([campaign-id]))

(defn parse-json
  [response key]
  (get (json/read-str response) key))

(defn is_enabled [channel_id] (parse-json (:body (call-channel channel_id)) "enabled"))

(defn get-timing-info
  [response]
  (let [headers (:headers response)]
    (filter (fn [x]
              (.startsWith (key x) "x_timing")) headers)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println
    (parse-json (:body (call-channel 75779)) "name")))


