(ns exegesis.core-test
  (:require
    [clojure.test :refer :all]
    [exegesis.core
     :refer [annotation-info
             annotation
             method]])
  (:import [exegesis
            ClassAnnotationsFixtureClass
            ClassAnnotationWithNoElements
            ClassAnnotationWithElements
            MethodAnnotationsFixtureClass
            MethodAnnotationWithNoElements
            MethodAnnotationWithElements]
           [javax.jws WebService]))

(deftest annotation-info-test
  (testing "class annotations"
    (is (= #{{:instance (annotation
                          ClassAnnotationsFixtureClass
                          ClassAnnotationWithNoElements)
              :type     ClassAnnotationWithNoElements
              :elements #{}}
             {:instance (annotation
                          ClassAnnotationsFixtureClass
                          ClassAnnotationWithElements)
              :type     ClassAnnotationWithElements
              :elements #{{:name 'first :value "some-value"}
                          {:name 'second :value "some-default"}}}}
          (annotation-info ClassAnnotationsFixtureClass))))

  (testing "method annotations"
    (let [first-method (method MethodAnnotationsFixtureClass "doFirstThing")
          second-method (method MethodAnnotationsFixtureClass "doSecondThing")
          third-method (method MethodAnnotationsFixtureClass "doThirdThing")]
      (is (= #{{:instance (annotation first-method
                            MethodAnnotationWithNoElements)
                :type     MethodAnnotationWithNoElements
                :elements #{}},
               {:instance (annotation first-method
                            MethodAnnotationWithElements)
                :type     MethodAnnotationWithElements
                :elements #{{:name 'first :value 20}
                            {:name 'second :value ""}}}}
            (annotation-info first-method)))

      (is (= #{{:instance (annotation second-method
                            MethodAnnotationWithNoElements)
                :type MethodAnnotationWithNoElements
                :elements #{}}}
            (annotation-info second-method)))
      (is (= #{} (annotation-info third-method))))))
