(ns myweb.core
  "Starts a simple Jetty server with one job: call the scramble? function"
  (:require [ring.adapter.jetty :as jetty]
            [myweb.functions :as f]))


(import '[org.eclipse.jetty.util UrlEncoded MultiMap])


(defn parse-query-string
  "Convert the parameters encoded in the query string into a map
  (Source: https://stackoverflow.com/questions/6591604/how-to-parse-url-parameters-in-clojure)"
  [query]
  (let [params (MultiMap.)]
      (UrlEncoded/decodeTo query params "UTF-8")
      (into {} params)))


(defn http-response
  "Create an HTTP response to `request` containing `contents` as body.
  Takes care of SOP and CORS limitations."
  [request contents]
  (if-let [origin (get-in request [:headers "origin"])]
    {:body contents
     :status 200
     :headers {"Content-Type" "text/plain"
               "Access-Control-Allow-Origin" origin
               "Access-Control-Allow-Credentials" "true"}}))


(defn process
  "Calls the `scramble?` function using the parameters included in `request`.
  Returns the corresponding HTTP response"
  [request]
  (let [params (parse-query-string (:query-string request))
        {first-word "str1" second-word "str2"} params
        scramble? (f/scramble? first-word second-word)]
    (println "Got a request with following parameters: " (str params))
    (println "  Answer: " scramble?)
    (http-response request (str scramble?))))


(defn -main []
  (jetty/run-jetty process {:port 3000}))
