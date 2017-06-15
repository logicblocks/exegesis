(ns exegesis.core
  (:require
    [clojure.reflect :refer [reflect]]))

(defn- as-map [annotation]
  {:instance annotation})

(defn- with-type [{:keys [instance] :as annotation}]
  (let [type (.annotationType instance)]
    (assoc annotation :type type)))

(defn- with-elements [{:keys [instance type] :as annotation}]
  (let [methods (.getDeclaredMethods type)
        elements (set (map (fn [method] {:name  (symbol (.getName method))
                                         :value (.invoke method instance (to-array []))})
                        methods))]
    (assoc annotation :elements elements)))

(defn annotation-info [type]
  (set (map (comp with-elements with-type as-map)
         (.getAnnotations type))))
