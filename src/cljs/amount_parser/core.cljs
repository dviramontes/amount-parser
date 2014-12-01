(ns amount-parser.core
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [kioo.om :as kioo :include-macros true]))

(defn fetch-amount [n]
  (let [callback (fn [data] (do
                           (.log js/console data)
                           (set! app-state data)))]
   (.get js/$ (str "/amount/" n) callback)))

(def app-state (atom (fetch-amount 1234.22)))

(kioo/deftemplate main "index.html" []
 {[:pre.result] (kioo/content @app-state)})

(defn app [data owner]
  (om/component (main)))

(om/root app app-state {:target (.-body js/document)})

