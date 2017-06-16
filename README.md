# exegesis

Clojure library for interrogating Java types for annotation information.

## Installation

Add the following dependency to your `project.clj` file:

    [exegesis "0.1.0"]

## Usage

Given an annotated Java class like the following:

```java
@ClassAnnotationWithElements(first = "some-value")
@ClassAnnotationWithNoElements
public class TestClass {
    @MethodAnnotationWithNoElements
    @MethodAnnotationWithElements(first = 20)
    public void doFirstThing() {}

    @MethodAnnotationWithNoElements
    public void doSecondThing() {}

    public void doThirdThing() {}

    public void doThingWithParameters(
            @ParameterAnnotationWithNoElements
            @ParameterAnnotationWithElements(name = "first")
            String first,

            @ParameterAnnotationWithElements(name = "second", priority = 10)
            Integer second,

            String third) {}
}
```

then, to obtain details of the annotations:

```clojure
(require '[exegesis.core :refer [annotation-info]])

(annotation-info :class TestClass)
=> [#{{:instance #object[com.sun.proxy.$Proxy5 0xc6b7151 "@ClassAnnotationWithElements(first=some-value)"]
       :type ClassAnnotationWithElements
       :elements #{{:name first :value "some-value"}}}
      {:instance #object[com.sun.proxy.$Proxy5 0xc6b7151 "@ClassAnnotationWithNoElements"]
       :type ClassAnnotationWithNoElements
       :elements #{}}}]
```

## License

Copyright © 2017 Toby Clemson

Distributed under the MIT license.
