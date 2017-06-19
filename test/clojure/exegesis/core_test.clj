(ns exegesis.core-test
  (:require
    [clojure.test :refer :all]
    [exegesis.core :refer [annotation-info]]
    [exegesis.internal :refer [annotation parameter-annotation method]])
  (:import [exegesis
            TestClass
            TypeAnnotationWithNoElements
            TypeAnnotationWithElements
            MethodAnnotationWithNoElements
            MethodAnnotationWithElements
            ParameterAnnotationWithElements
            ParameterAnnotationWithNoElements]
           [javax.jws WebService]))

(defn find-by-name [coll name]
  (some (fn [obj] (and (= (:name obj) name) obj)) coll))

(deftest annotation-info-test
  (let [result (annotation-info TestClass)]
    (testing "type annotations"
      (is (= (get-in result [:type :annotations])
            #{{:instance (annotation TestClass TypeAnnotationWithNoElements)
               :type     TypeAnnotationWithNoElements
               :elements #{}}
              {:instance (annotation TestClass TypeAnnotationWithElements)
               :type     TypeAnnotationWithElements
               :elements #{{:name 'first :value "some-value"}
                           {:name 'second :value "some-default"}}}})))

    (testing "method annotations"
      (let [first-method (method TestClass "doFirstThing")
            second-method (method TestClass "doSecondThing")
            third-method (method TestClass "doThirdThing")

            for-methods (get-in result [:methods])
            for-first-method (find-by-name for-methods 'doFirstThing)
            for-second-method (find-by-name for-methods 'doSecondThing)
            for-third-method (find-by-name for-methods 'doThirdThing)]
        (is (= (get for-first-method :annotations)
              #{{:instance (annotation first-method
                             MethodAnnotationWithNoElements)
                 :type     MethodAnnotationWithNoElements
                 :elements #{}},
                {:instance (annotation first-method
                             MethodAnnotationWithElements)
                 :type     MethodAnnotationWithElements
                 :elements #{{:name 'first :value 20}
                             {:name 'second :value ""}}}}))

        (is (= (get for-second-method :annotations)
              #{{:instance (annotation second-method
                             MethodAnnotationWithNoElements)
                 :type     MethodAnnotationWithNoElements
                 :elements #{}}}))
        (is (= (get for-third-method :annotations) #{}))))

    (testing "parameter annotations"
      (let [method (method TestClass "doThingWithParameters"
                     String Integer String)

            for-methods (get-in result [:methods])
            for-parameters-method (find-by-name for-methods
                                    'doThingWithParameters)]
        (is (= (get for-parameters-method :parameters)
              [#{{:instance (parameter-annotation method 0
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
               #{}]))))))
