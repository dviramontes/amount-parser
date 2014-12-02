(ns amount-parser.core
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [kioo.om :as kioo :include-macros true]))


(def app-state (atom {:value "One thousand two hundred and thirty-four dollars with 22/100 cents"}))

(defn fetch-amount [n]
  (let [callback (fn [data] (do
                           ;; (.log js/console data)
                           (swap! app-state assoc :value data)))]
   (.get js/$ (str "/amount/" n) callback)))

(kioo/deftemplate main "index.html" []
 {[:pre.result] (kioo/content (get @app-state :value))
  [:input] (kioo/listen :on-change
                         (fn [e]
                           (.preventDefault e)
                           ;; (.log js/console (.val (js/$ "input")))
                           (when (not-empty (.val (js/$ "input")))
                             (fetch-amount (.val (js/$ "input"))))
                           ))})

(defn app [data]
  (om/component (main)))

(om/root app app-state {:target (.-body js/document)})



