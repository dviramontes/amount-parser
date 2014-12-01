(ns amount-parser.core
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [kioo.om :as kioo :include-macros true]))

(defonce app-state (atom 1234.22))

(defn fetch-amount [n]
  (let [callback (fn [d] (.-responseText d))]
  (->
   (.get js/$ (str "/amount/" n))
   (.success callback))))

;;(.log js/console (fetch-amount 100.00))

(kioo/deftemplate main "index.html" []
 {[:pre.result] (kioo/content 123.22)})

(defn app [data owner]
  (fetch-amount data)
  (om/component (main)))

(om/root app app-state {:target (.-body js/document)})

