(ns keys.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [resources]]
            [keys.music :refer [beep-letter]]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))

  (GET "/event" []
    (do
      (beep-letter "a")
      {:status 200}))

  (resources "/"))

(def dev-handler
  (-> #'routes
      (wrap-params)
      (wrap-reload)))

(def handler routes)
