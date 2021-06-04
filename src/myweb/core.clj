(ns myweb.core
  (:require [ring.adapter.jetty :as jetty]))

(import '[org.eclipse.jetty.util UrlEncoded MultiMap])


(defn parse-query-string
  ;; source: https://stackoverflow.com/questions/6591604/how-to-parse-url-parameters-in-clojure
  [query]
  (let [params (MultiMap.)]
      (UrlEncoded/decodeTo query params "UTF-8")
      (into {} params)))


(defn myapp [request]
  (let [params (parse-query-string (:query-string request))]
    (println "Got this request: " request)
    {:body (format "Gello World %s and %s" (params "str1") (params "str2"))
     :status 200
     :headers {"Content-Type" "text/plain"
               "Access-Control-Allow-Origin" "http://localhost:9000"
               "Access-Control-Allow-Credentials" "true"}}))


(defn -main []
  (jetty/run-jetty myapp {:port 3000}))
