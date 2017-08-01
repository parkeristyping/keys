(ns keys.events
  (:require [ajax.core :refer [GET POST]]
            [keys.db :as db]
            [re-frame.core :as re-frame]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
  :keydown
  (fn [db evt]
    (let [k (last evt)]
      (GET (str "http://localhost:3000/event?type=keydown&key=" k))
      (update db :keys conj k))))

(re-frame/reg-event-db
  :keyup
  (fn [db evt]
    (let [k (last evt)]
      (GET (str "http://localhost:3000/event?type=keyup&key=" k))
      (update db :keys disj k))))
