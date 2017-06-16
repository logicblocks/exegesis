(ns exegesis.core-test
  (:require
    [clojure.test :refer :all]
    [exegesis.core
     :refer [annotation-info
             annotation
             parameter-annotation
             method]])
  (:import [exegesis
            TestClass
            ClassAnnotationWithNoElements
            ClassAnnotationWithElements
            MethodAnnotationWithNoElements
            MethodAnnotationWithElements
            ParameterAnnotationWithElements
            ParameterAnnotationWithNoElements]
           [javax.jws WebService]))

(deftest annotation-info-test
  (testing "type annotations"
    (is (= [#{{:instance (annotation TestClass ClassAnnotationWithNoElements)
               :type     ClassAnnotationWithNoElements
               :elements #{}}
              {:instance (annotation TestClass ClassAnnotationWithElements)
               :type     ClassAnnotationWithElements
               :elements #{{:name 'first :value "some-value"}
                           {:name 'second :value "some-default"}}}}]
          (annotation-info :class TestClass))))

  (testing "method annotations"
    (let [first-method (method TestClass "doFirstThing")
          second-method (method TestClass "doSecondThing")
          third-method (method TestClass "doThirdThing")]
      (is (= [#{{:instance (annotation first-method
                             MethodAnnotationWithNoElements)
                 :type     MethodAnnotationWithNoElements
                 :elements #{}},
                {:instance (annotation first-method
                             MethodAnnotationWithElements)
                 :type     MethodAnnotationWithElements
                 :elements #{{:name 'first :value 20}
                             {:name 'second :value ""}}}}]
            (annotation-info :method first-method)))

      (is (= [#{{:instance (annotation second-method
                             MethodAnnotationWithNoElements)
                 :type     MethodAnnotationWithNoElements
                 :elements #{}}}]
            (annotation-info :method second-method)))
      (is (= [#{}] (annotation-info :method third-method)))))

  (testing "parameter annotations"
    (let [method (method TestClass "doThingWithParameters"
                   String Integer String)]
      (is (= [#{{:instance (parameter-annotation method 0
                             ParameterAnnotationWithNoElements)
                 :type     ParameterAnnotationWithNoElements
                 :elements #{}}
                {:instance (parameter-annotation method 0
                             ParameterAnnotationWithElements)
                 :type     ParameterAnnotationWithElements
                 :elements #{{:name 'name :value "first"}
                             {:name 'priority :value 0}}}}
              #{{:instance (parameter-annotation method 1
                             ParameterAnnotationWithElements)
                 :type     ParameterAnnotationWithElements
                 :elements #{{:name 'name :value "second"}
                             {:name 'priority :value 10}}}}
              #{}]
            (annotation-info :parameter method))))))
