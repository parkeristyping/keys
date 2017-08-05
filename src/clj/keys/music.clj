(ns keys.music
  (:require [clojure.string :as str]
            [overtone.live :refer [FREE definst sin-osc env-gen
                                   midicps perc apply-by now]]))

(def depressed (atom #{}))

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

(def arp-seq (atom []))

(def speed (atom 500))

;; base instrument
(definst beep [note 60]
  (let [sound-src (sin-osc (midicps note))
        env       (env-gen (perc 0.005 1) :action FREE)]
    (* sound-src env)))

(def arpy
  (lazy-cat [0 3 5 3] arpy))

(def rand-arp
  (repeatedly #(rand-int 10)))

(defn sequencer
  [note s]
  (when (@depressed note)
    (beep (+ note (first s)))
    (apply-by (+ @speed (now)) sequencer note (rest s) [])))

(defn handle-sound-event!
  [typ k]
  (let [down? (= "keydown" typ)
        note (get notes k)
        dir (#{"-" "="} k)
        n (if (#{"1" "2" "3" "4" "5" "6" "7" "8" "9" "0"} k)
            (read-string k))]
    (cond
      (and down? note) (when-not (@depressed note)
                         (swap! depressed conj note)
                         (sequencer note (cycle @arp-seq)))
      (and down? n)    (if ((set @arp-seq) n)
                         (swap! arp-seq #(vec (remove #{n} %)))
                         (swap! arp-seq #(conj % n)))
      (and down? dir)  (if (= "-" k) (swap! speed #(+ % 10)) (swap! speed #(- % 10)))
      note             (swap! depressed disj note))))
