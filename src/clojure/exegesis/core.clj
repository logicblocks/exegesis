(ns exegesis.core
  (:refer-clojure :exclude [name methods])
  (:require
    [exegesis.internal
     :refer [annotations
             parameter-annotations
             annotation-type
             declared-methods
             methods
             name
             invoke]])
  (:import
    [java.lang.reflect Method]
    [java.lang.annotation Annotation]))

(defn- as-map [^Annotation annotation]
  {:instance annotation})

(defn- with-type
  [{:keys [instance]
    :as   ^Annotation annotation}]
  (let [^Class type (annotation-type instance)]
    (assoc annotation :type type)))

(defn- with-elements
  [{:keys [instance type]
    :as   ^Annotation annotation}]
  (let [methods (declared-methods type)
        elements (set (map
                        (fn [^Method method]
                          {:name  (symbol (name method))
                           :value (invoke method instance)})
                        methods))]
    (assoc annotation :elements elements)))

(defn- ->annotation-detail [annotation]
  (-> annotation
    as-map
    with-type
    with-elements))

(defn- ->annotation-details [annotations]
  (set (map ->annotation-detail annotations)))

(defn- type-annotation-info [type]
  {:annotations (->annotation-details (annotations type))})

(defn- parameters-annotation-info [method]
  (into [] (map ->annotation-details (parameter-annotations method))))

(defn- method-annotation-info [method]
  {:name        (symbol (name method))
   :annotations (->annotation-details (annotations method))
   :parameters (parameters-annotation-info method)})

(defn- methods-annotation-info [type]
  (set (map method-annotation-info (declared-methods type))))

(defn- context-class
  [obj]
  (if (class? obj) obj (type obj)))

(defn annotation-info [type-or-object]
  (let [class (context-class type-or-object)]
    {:type    (type-annotation-info class)
     :methods (methods-annotation-info class)}))
