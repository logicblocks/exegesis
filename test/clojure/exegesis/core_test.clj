(ns exegesis.core-test
  (:require
    [clojure.test :refer :all]
    [exegesis.core :refer :all])
  (:import [exegesis ClassAnnotationsFixtureClass ClassAnnotationWithNoElements ClassAnnotationWithElements]
           [javax.jws WebService]))

(deftest annotation-info-test
  (testing "class annotations"
    (is (= #{{:instance (.getAnnotation
                          ClassAnnotationsFixtureClass
                          ClassAnnotationWithNoElements)
              :type     ClassAnnotationWithNoElements
              :elements #{}}
             {:instance (.getAnnotation
                          ClassAnnotationsFixtureClass
                          ClassAnnotationWithElements)
              :type     ClassAnnotationWithElements
              :elements #{{:name 'first :value "some-value"}
                          {:name 'second :value "some-default"}}}}
          (annotation-info ClassAnnotationsFixtureClass)))))
