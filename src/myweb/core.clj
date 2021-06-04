(ns myweb.core
  (:require [ring.adapter.jetty :as jetty]))

(import '[org.eclipse.jetty.util UrlEncoded MultiMap])


(defn parse-query-string
  ;; source: https://stackoverflow.com/questions/6591604/how-to-parse-url-parameters-in-clojure
  [query]
  (let [params (MultiMap.)]
      (UrlEncoded/decodeTo query params "UTF-8")
      (into {} params)))


(defn http-response
  "Create an HTTP response for `request` containing `contents` as body"
  [request contents]
  (let [origin ((request :headers) "origin")]   ;TODO: map in map
    {:body contents
     :status 200
     :headers {"Content-Type" "text/plain"
               "Access-Control-Allow-Origin" origin
               "Access-Control-Allow-Credentials" "true"}}))


(defn myapp [request]
  (let [params (parse-query-string (:query-string request))]
    (println "Got a request with following parameters: " (str params))
    (http-response request (format "Hello World %s and %s" (params "str1") (params "str2")))))


(defn -main []
  (jetty/run-jetty myapp {:port 3000}))
