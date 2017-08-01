(ns keys.server
  (:require [keys.handler :refer [handler]]
            [config.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]]
            ;; [cider.nrepl :refer [cider-nrepl-handler]]
            ;; [clojure.tools.nrepl.server :refer [start-server stop-server]]
            )
  (:gen-class))

 (defn -main [& args]
   (let [port (Integer/parseInt (or (env :port) "3000"))]
     (run-jetty handler {:port port :join? false})
     ;; (start-server :port 9998)
     ))
