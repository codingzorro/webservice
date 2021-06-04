(ns myweb.core
  (:require [ring.adapter.jetty :as jetty]
            [myweb.functions :as f]))

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
  (if-let [origin (get-in request [:headers "origin"])]
    {:body contents
     :status 200
     :headers {"Content-Type" "text/plain"
               "Access-Control-Allow-Origin" origin
               "Access-Control-Allow-Credentials" "true"}}))


(defn myapp [request]
  (let [params (parse-query-string (:query-string request))
        {first-word "str1" second-word "str2"} params]
    (println "Got a request with following parameters: " (str params))
    (http-response request (str (f/scramble? first-word second-word)))))


(defn -main []
  (jetty/run-jetty myapp {:port 3000}))
