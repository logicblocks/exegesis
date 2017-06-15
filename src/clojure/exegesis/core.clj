(ns exegesis.core
  (:require
    [clojure.reflect :refer [reflect]])
  (:import
    [java.lang.reflect Method]
    [java.lang.annotation Annotation]))

(defn- as-map [^Annotation annotation]
  {:instance annotation})

(defn- with-type
  [{:keys [instance]
    :as   ^Annotation annotation}]
  (let [^Class type (.annotationType instance)]
    (assoc annotation :type type)))

(defn- with-elements
  [{:keys [instance type]
    :as   ^Annotation annotation}]
  (let [methods (.getDeclaredMethods type)
        elements (set (map
                        (fn [^Method method]
                          {:name  (symbol (.getName method))
                           :value (.invoke method instance (to-array []))})
                        methods))]
    (assoc annotation :elements elements)))

(defn annotation-info [type]
  (set (map (comp with-elements with-type as-map)
         (.getAnnotations type))))
