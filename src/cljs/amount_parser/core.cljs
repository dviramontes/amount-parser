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
  (let [callback (fn [new-data] #(swap! app-state assoc :value new-data))]
  (.get js/$ (str "/amount/" n) callback)))

(q/defcomponent main [value]
  (d/div {:className "col-sm-3"}
    (d/input {:className "form-control" :type "number" :id "formInput"
      :placeholder 1234.22 :step "any" :size 15}))
  (d/div {:className "col-sm-6"} 
      (d/pre {:className "result"} (:lname value))))
      
(q/render (main {:fname 123 :lname "viramontes"}) (.getElementById js/document "app"))

