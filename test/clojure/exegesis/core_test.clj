(ns exegesis.core-test
  (:require
    [clojure.test :refer :all]
    [exegesis.core :refer :all])
  (:import [exegesis ClassAnnotationsFixture]
           [javax.jws WebService]))

(deftest annotation-info-test
  (is (= #{{:instance (.getAnnotation ClassAnnotationsFixture Deprecated)
            :type     Deprecated
            :elements #{}}
           {:instance (.getAnnotation ClassAnnotationsFixture WebService)
            :type     WebService
            :elements #{{:name 'name :value "SomeService"}
                        {:name 'targetNamespace :value ""}
                        {:name 'serviceName :value ""}
                        {:name 'portName :value ""}
                        {:name 'wsdlLocation :value ""}
                        {:name 'endpointInterface :value ""}}}}
        (annotation-info ClassAnnotationsFixture))))
