(ns keys.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [keys.events]
            [keys.subs]
            [keys.views :as views]
            [keys.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn init-keys
  []
  (.addEventListener js/document "keydown" (fn [e] (rf/dispatch [:keydown (.-key e)])))
  (.addEventListener js/document "keyup" (fn [e] (rf/dispatch [:keyup (.-key e)]))))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (reagent/render [views/main-panel] (.getElementById js/document "app")))

(defn ^:export init []
  (rf/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root)
  (init-keys))
