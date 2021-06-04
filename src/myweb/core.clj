(ns myweb.core
  (:require [ring.adapter.jetty :as jetty]))

(defn myapp [request]
  {:body "Gello World"
   :status 200
   :headers {"Content-Type" "text/html"}})

(defn -main []
  (jetty/run-jetty myapp {:port 3000}))
