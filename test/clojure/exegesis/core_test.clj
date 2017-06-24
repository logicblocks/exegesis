(ns exegesis.core-test
  (:require
    [clojure.test :refer :all]
    [clojure.pprint :refer [pprint]]
    [exegesis.core :refer [annotation-info]]
    [exegesis.internal
     :refer [annotation
             parameter-annotation
             declared-method
             declared-field]])
  (:import [exegesis
            TestClass
            TypeAnnotationWithNoElements
            TypeAnnotationWithElements
            MethodAnnotationWithNoElements
            MethodAnnotationWithElements
            ParameterAnnotationWithElements
            ParameterAnnotationWithNoElements FieldAnnotationWithNoElements]
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

    (testing "field annotations"
      (let [first-field (declared-field TestClass "thing1")
            second-field (declared-field TestClass "thing2")
            third-field (declared-field TestClass "thing3")
            fourth-field (declared-field TestClass "thing4")

            for-fields (get-in result [:fields])
            for-first-field (find-by-name for-fields 'thing1)
            for-second-field (find-by-name for-fields 'thing2)
            for-third-field (find-by-name for-fields 'thing3)
            for-fourth-field (find-by-name for-fields 'thing4)]
        (is (= (get for-first-field :annotations)
              #{{:instance (annotation first-field
                             FieldAnnotationWithNoElements)
                 :type     FieldAnnotationWithNoElements
                 :elements #{}}}))))

    (testing "method annotations"
      (let [first-method (declared-method TestClass "doFirstThing")
            second-method (declared-method TestClass "doSecondThing")
            third-method (declared-method TestClass "doThirdThing")

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
      (let [method (declared-method TestClass "doThingWithParameters"
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
               #{}])))))

  (let [result (annotation-info (TestClass.))]
    (testing "for an object not a class"
      (is (not (nil? (get-in result [:type :annotations])))))))
