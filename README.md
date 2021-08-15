# exegesis

[![Clojars Project](https://img.shields.io/clojars/v/io.logicblocks/exegesis.svg)](https://clojars.org/io.logicblocks/exegesis)
[![Clojars Downloads](https://img.shields.io/clojars/dt/io.logicblocks/exegesis.svg)](https://clojars.org/io.logicblocks/exegesis)
[![GitHub Contributors](https://img.shields.io/github/contributors-anon/logicblocks/exegesis.svg)](https://github.com/logicblocks/exegesis/graphs/contributors)

Clojure library for interrogating Java types for annotation information.

## Installation

Add the following dependency to your `project.clj` file:

    [io.logicblocks/exegesis "0.4.2"]

## Usage

Given an annotated Java class like the following:

```java
@TypeAnnotationWithElements(first = "some-value")
@TypeAnnotationWithNoElements
public class TestClass {
    @FieldAnnotationWithNoElements
    private String thing1;

    @FieldAnnotationWithNoElements
    @FieldAnnotationWithElements(first = 20)
    public Integer thing2;

    @FieldAnnotationWithElements(first = 30)
    protected Float thing3;

    Boolean thing4;

    @MethodAnnotationWithNoElements
    @MethodAnnotationWithElements(first = 20)
    protected void doFirstThing() {}

    @MethodAnnotationWithNoElements
    public void doSecondThing() {}

    private void doThirdThing() {}

    void doThingWithParameters(
            @ParameterAnnotationWithNoElements
            @ParameterAnnotationWithElements(name = "first")
            String first,

            @ParameterAnnotationWithElements(name = "second", priority = 10)
            Integer second,

            String third) {}
}
```

then, to obtain details of the annotations for the type:

```clojure
(require '[exegesis.core :refer [annotation-info]])
(import '[TestClass]

(annotation-info TestClass)
=>
{:type
 {:annotations
  #{{:instance
     #object[com.sun.proxy.$Proxy2 0x1a003b32 "@exegesis.TypeAnnotationWithNoElements()"],
     :type exegesis.TypeAnnotationWithNoElements,
     :elements #{}}
    {:instance
     #object[com.sun.proxy.$Proxy1 0x449128f7 "@exegesis.TypeAnnotationWithElements(first=some-value, second=some-default)"],
     :type exegesis.TypeAnnotationWithElements,
     :elements
     #{{:name second, :value "some-default"}
       {:name first, :value "some-value"}}}}},
 :fields
 #{{:name thing2,
    :annotations
    #{{:instance
       #object[com.sun.proxy.$Proxy8 0x6f40b2d7 "@exegesis.FieldAnnotationWithElements(first=20, second=)"],
       :type exegesis.FieldAnnotationWithElements,
       :elements #{{:name first, :value 20} {:name second, :value ""}}}
      {:instance
       #object[com.sun.proxy.$Proxy7 0x452b0711 "@exegesis.FieldAnnotationWithNoElements()"],
       :type exegesis.FieldAnnotationWithNoElements,
       :elements #{}}}}
   {:name thing3,
    :annotations
    #{{:instance
       #object[com.sun.proxy.$Proxy8 0x700c3c05 "@exegesis.FieldAnnotationWithElements(first=30, second=)"],
       :type exegesis.FieldAnnotationWithElements,
       :elements
       #{{:name second, :value ""} {:name first, :value 30}}}}}
   {:name thing1,
    :annotations
    #{{:instance
       #object[com.sun.proxy.$Proxy7 0x3db862ff "@exegesis.FieldAnnotationWithNoElements()"],
       :type exegesis.FieldAnnotationWithNoElements,
       :elements #{}}}}
   {:name thing4, :annotations #{}}},
 :methods
 #{{:name doThirdThing, :annotations #{}, :parameters []}
   {:name doThingWithParameters,
    :annotations #{},
    :parameters
    [#{{:instance
        #object[com.sun.proxy.$Proxy6 0x77176cdd "@exegesis.ParameterAnnotationWithElements(priority=0, name=first)"],
        :type exegesis.ParameterAnnotationWithElements,
        :elements
        #{{:name priority, :value 0} {:name name, :value "first"}}}
       {:instance
        #object[com.sun.proxy.$Proxy5 0x73155d90 "@exegesis.ParameterAnnotationWithNoElements()"],
        :type exegesis.ParameterAnnotationWithNoElements,
        :elements #{}}}
     #{{:instance
        #object[com.sun.proxy.$Proxy6 0x71075c64 "@exegesis.ParameterAnnotationWithElements(priority=10, name=second)"],
        :type exegesis.ParameterAnnotationWithElements,
        :elements
        #{{:name priority, :value 10} {:name name, :value "second"}}}}
     #{}]}
   {:name doFirstThing,
    :annotations
    #{{:instance
       #object[com.sun.proxy.$Proxy4 0x53e5811e "@exegesis.MethodAnnotationWithElements(first=20, second=)"],
       :type exegesis.MethodAnnotationWithElements,
       :elements #{{:name first, :value 20} {:name second, :value ""}}}
      {:instance
       #object[com.sun.proxy.$Proxy3 0x8fd2c52 "@exegesis.MethodAnnotationWithNoElements()"],
       :type exegesis.MethodAnnotationWithNoElements,
       :elements #{}}},
    :parameters []}
   {:name doSecondThing,
    :annotations
    #{{:instance
       #object[com.sun.proxy.$Proxy3 0x7878440d "@exegesis.MethodAnnotationWithNoElements()"],
       :type exegesis.MethodAnnotationWithNoElements,
       :elements #{}}},
    :parameters []}}}
```

## License

Copyright &copy; 2021 LogicBlocks

Distributed under the MIT license.
