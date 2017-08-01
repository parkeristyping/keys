(ns keys.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [resources]]
            [keys.music :refer [handle-sound-event!]]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))

  (GET "/event" req
    (let [typ (-> req :params (get "type"))
          k (-> req :params (get "key"))]
      (future (handle-sound-event! typ k))
      {:status 200}))

  (resources "/"))

(def dev-handler
  (-> #'routes
      wrap-reload
      wrap-params))

(def handler
  (-> routes
      wrap-params))
