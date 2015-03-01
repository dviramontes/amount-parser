(ns amount-parser.core
  (:require [quiescent :as q :include-macros true]
            [quiescent.dom :as d]))

(declare app-state) ; var name with no binding

(defn get-element-by-id [id]
  (.getElementById js/document id))

(def app-state 
  (atom 
    {:value "One thousand two hundred and thirty-four dollars with 22/100 cents"}))

(defn fetch-amount [n]
  (let [callback (fn [new-data] (.log js/console new-data))]
  (.get js/$ (str "/amount/" n) callback)))

(q/defcomponent input-amount []
  (d/div {:className "col-lg"}
         (d/input {:type "number" :id "formInput" 
                   :className "form-control" :placeholder 1234.22
                   :onChange (fn [x] (fetch-amount (.val (js/$ "#formInput"))))})))

(q/defcomponent show-amount [amount]
  (d/div {:className "col-lg-6"}
      (input-amount)
      (d/pre {:className "result"} amount)))

(q/defcomponent main [value]
  (show-amount (:value value)))
      
(q/render (main @app-state) (.getElementById js/document "app"))

