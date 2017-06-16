(ns exegesis.core
  (:refer-clojure :exclude [name])
  (:require
    [clojure.reflect :refer [reflect]])
  (:import
    [java.lang.reflect Method]
    [java.lang.annotation Annotation]))

(defn annotation [type annotation-type]
  (.getAnnotation type annotation-type))

(defn annotations [type]
  (.getAnnotations type))

(defn annotation-type [annotation]
  (.annotationType annotation))

(defn has-annotation-type? [type annotation]
  (= (annotation-type annotation) type))

(defn parameter-annotations [method]
  (.getParameterAnnotations method))

(defn parameter-annotation [method index annotation-type]
  (let [all (parameter-annotations method)
        indexed (get all index)
        has-type? (partial has-annotation-type? annotation-type)
        required (first (filter has-type? indexed))]
    required))

(defn declared-methods [type]
  (.getDeclaredMethods type))

(defn method [type name & args]
  (.getMethod type name (into-array Class args)))

(defn name [method]
  (.getName method))

(defn invoke [method instance & args]
  (.invoke method instance (to-array args)))

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

(defn annotation-info [target type]
  (let [annotations-groups (condp = target
                             :class [(annotations type)]
                             :method [(annotations type)]
                             :parameter (parameter-annotations type))]
    (into [] (map
               (fn [annotations]
                 (set (map (comp with-elements with-type as-map) annotations)))
               annotations-groups))))
