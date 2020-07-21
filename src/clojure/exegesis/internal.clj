(ns exegesis.internal
  (:refer-clojure :exclude [name methods])
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

(defn declared-method [type name & args]
  (.getDeclaredMethod type name (into-array Class args)))

(defn declared-fields [type]
  (.getDeclaredFields type))

(defn declared-field [type name]
  (.getDeclaredField type name))

(defn name [method]
  (.getName method))

(defn invoke [method instance & args]
  (.invoke method instance (to-array args)))
