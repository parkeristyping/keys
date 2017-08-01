(ns keys.music
  (:require [clojure.string :as str]
            [overtone.live :refer [definst sin-osc env-gen midicps perc FREE]]))

(def notes
  {"a" 53
   "w" 54
   "s" 55
   "e" 56
   "d" 57
   "r" 58
   "f" 59
   "g" 60
   "y" 61
   "h" 62
   "u" 63
   "j" 64
   "k" 65
   "o" 66
   "l" 67
   "p" 68
   ";" 69
   "[" 70
   "'" 71})

;; base instrument
(definst beep [note 60]
  (let [sound-src (sin-osc (midicps note))
        env       (env-gen (perc 0.005 1) :action FREE)]
    (* sound-src env)))

(defn handle-sound-event!
  [typ k]
  (if (= "keydown" typ)
    (beep (get notes k))))
