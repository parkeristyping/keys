(ns keys.events
  (:require [ajax.core :refer [GET POST]]
            [keys.db :as db]
            [re-frame.core :as re-frame]))

(def nums #{"0" "1" "2" "3" "4" "5" "6" "7" "8" "9"})

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
  :keydown
  (fn [db evt]
    (let [k (last evt)]
      (GET (str "http://localhost:3000/event?type=keydown&key=" k))
      (if (and (nums k) ((:keys db) k))
        (update db :keys disj k)
        (update db :keys conj k)))))

(re-frame/reg-event-db
  :keyup
  (fn [db evt]
    (let [k (last evt)]
      (GET (str "http://localhost:3000/event?type=keyup&key=" k))
      (if-not (nums k)
        (update db :keys disj k)
        db))))
