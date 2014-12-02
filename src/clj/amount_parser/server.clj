(ns amount-parser.server
  (:require [clojure.java.io :as io]
            [amount-parser.dev :refer [is-dev? inject-devmode-html browser-repl start-figwheel]]
            [compojure.core :refer [GET defroutes]] ;; you can include verbs here like POST PUT
            [compojure.route :refer [resources]]
            [compojure.handler :refer [api]]
            [net.cgrand.enlive-html :refer [deftemplate]]
            [ring.middleware.reload :as reload]
            [environ.core :refer [env]]
            [ring.handler.dump :refer [handle-dump]] ;; see formated req for debugging
            [org.httpkit.server :refer [run-server]]
            [amount-parser.filter :refer :all :exclude [-main]]))

(deftemplate page
  (io/resource "index.html") [] [:body] (if is-dev? inject-devmode-html identity))

(defn filter-handler [req]
  (let [number (get-in req [:route-params :number])]
    {:status 200
     :body (str (amount (read-string number)))
     :headers {}}))

(defroutes routes
  (resources "/")
  (resources "/react" {:root "react"})
  (GET "/amount/:number" [] filter-handler)
  (GET "/*" req (page)))
  ;;(POST "/filter" [n] handle-dump))

(def http-handler
  (if is-dev?
    (reload/wrap-reload (api #'routes))
    (api routes)))

(defn run [& [port]]
  (defonce ^:private server
    (do
      (if is-dev? (start-figwheel))
      (let [port (Integer. (or port (env :port) 10555))]
        (print "Starting web server on port" port ".\n")
        (run-server http-handler {:port port
                          :join? false}))))
  server)

(defn -main [& [port]]
  (run port))
