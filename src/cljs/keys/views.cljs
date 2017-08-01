(ns keys.views
  (:require [clojure.string :as cs]
            [re-frame.core :as rf]))

(def main-keys
  [{:key "Esc" :cls [:function :space1]}
   {:key "F1"  :cls [:function]}
   {:key "F2"  :cls [:function]}
   {:key "F3"  :cls [:function]}
   {:key "F4"  :cls [:function :space2]}
   {:key "F5"  :cls [:function]}
   {:key "F6"  :cls [:function]}
   {:key "F7"  :cls [:function]}
   {:key "F8"  :cls [:function :space2]}
   {:key "F9"  :cls [:function]}
   {:key "F10" :cls [:function]}
   {:key "F11" :cls [:function]}
   {:key "F12" :cls [:function]}
   {:key "`" :alt "~" :cls [:num :dual]}
   {:key "1" :alt "!" :cls [:num :dual]}
   {:key "2" :alt "@" :cls [:num :dual]}
   {:key "3" :alt "#" :cls [:num :dual]}
   {:key "4" :alt "$" :cls [:num :dual]}
   {:key "5" :alt "%" :cls [:num :dual]}
   {:key "6" :alt "^" :cls [:num :dual]}
   {:key "7" :alt "&" :cls [:num :dual]}
   {:key "8" :alt "*" :cls [:num :dual]}
   {:key "9" :alt "(" :cls [:num :dual]}
   {:key "0" :alt ")" :cls [:num :dual]}
   {:key "-" :alt "_" :cls [:num :dual]}
   {:key "=" :alt "+" :cls [:num :dual]}
   {:key "Backspace" :cls [:backspace]}
   {:key "Tab" :code "N/A" :cls [:tab]}
   {:key "Q" :code "q" :cls [:letter]}
   {:key "W" :code "w" :cls [:letter]}
   {:key "E" :code "e" :cls [:letter]}
   {:key "R" :code "r" :cls [:letter]}
   {:key "T" :code "t" :cls [:letter]}
   {:key "Y" :code "y" :cls [:letter]}
   {:key "U" :code "u" :cls [:letter]}
   {:key "I" :code "i" :cls [:letter]}
   {:key "O" :code "o" :cls [:letter]}
   {:key "P" :code "p" :cls [:letter]}
   {:key "[" :alt "{" :cls [:dual]}
   {:key "]" :alt "}" :cls [:dual]}
   {:key "\\" :alt "|" :cls [:letter :dual :slash]}
   {:key "Lock" :alt "Caps" :code "Meta" :cls [:caps]}
   {:key "A" :code "a" :cls [:letter]}
   {:key "S" :code "s" :cls [:letter]}
   {:key "D" :code "d" :cls [:letter]}
   {:key "F" :code "f" :cls [:letter]}
   {:key "G" :code "g" :cls [:letter]}
   {:key "H" :code "h" :cls [:letter]}
   {:key "J" :code "j" :cls [:letter]}
   {:key "K" :code "k" :cls [:letter]}
   {:key "L" :code "l" :cls [:letter]}
   {:key ";" :alt ":" :cls [:dual]}
   {:key "'" :alt "\"" :cls [:dual]}
   {:key "Enter" :cls [:enter]}
   {:key "Shift" :cls [:shift :left]}
   {:key "Z" :code "z" :cls [:letter]}
   {:key "X" :code "x" :cls [:letter]}
   {:key "C" :code "c" :cls [:letter]}
   {:key "V" :code "v" :cls [:letter]}
   {:key "B" :code "b" :cls [:letter]}
   {:key "N" :code "n" :cls [:letter]}
   {:key "M" :code "m" :cls [:letter]}
   {:key "," :alt "<" :cls [:dual]}
   {:key "." :alt ">" :cls [:dual]}
   {:key "/" :alt "?" :cls [:dual]}
   {:key "Shift" :code "N/A" :cls [:shift :right]}
   {:key "Ctrl" :code "N/A" :cls [:ctrl]}
   {:key "♥" :code "Alt"}
   {:key "Cmd" :code "Control"}
   {:key " " :code " " :cls [:space]}
   {:key "Cmd" :code "N/A"}
   {:key "♥" :code "N/A"}
   {:key "Prnt" :code "N/A"}
   {:key "Ctrl" :code "N/A" :cls [:ctrl]}])

(defn mk-class
  [cls]
  (cs/join " " (map name (remove nil? cls))))

(defn mk-dual
  [m ks]
  (let [code (or (:code m) (:key m))]
    [:div.key {:class (mk-class (conj (:cls m) (if (@ks code) "glow")))}
     (str "\n" (:alt m) "\n")
     [:br]
     (str "\n" (:key m) "\n")]))

(defn mk-single
  [m ks]
  (let [code (or (:code m) (:key m))]
    [:div.key {:class (mk-class (conj (:cls m) (if (@ks code) "glow")))}
     (str "\n" (:key m) "\n")]))

(defn mk-key
  [m ks]
  (if (:alt m)
    (mk-dual m ks)
    (mk-single m ks)))

(defn main-panel []
  (let [ks (rf/subscribe [:keys])]
    (fn []
      [:div
       [:div.cable]
       [:div.keyboard
        [:div.logo "\nhp\n"]
        [:div.lights [:span "1"] [:span "A"] [:span "V"]]
        (into [:div.section-a] (for [k main-keys] (mk-key k ks)))
        [:div.section-b [:div.key.function.small "\nPrnt Scrn\n"]
         [:div.key.function.small "\nScroll Lock\n"]
         [:div.key.function.small "\nPause Break\n"]
         [:dic.sec-func [:div.key "\nInsert\n"]
          [:div.key "\nHome\n"]
          [:div.key.dual "\nPage Up\n"]
          [:div.key "\nDel\n"]
          [:div.key "\nEnd\n"]
          [:div.key.dual "\nPage Down\n"]
          [:div.arrows
           [:div.key.hidden]
           [:div.key "\n^\n"]
           [:div.key.hidden]
           [:div.key "\n<\n"]
           [:div.key "\nv\n"]
           [:div.key "\n>\n"]]]]]])))
