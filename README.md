# exegesis

Clojure library for interrogating Java types for annotation information.

## Installation

Add the following dependency to your `project.clj` file:

    [exegesis "0.2.0"]

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

then, to obtain details of the annotations for the type:

```clojure
(require '[exegesis.core :refer [annotation-info]])
(import '[TestClass]

(annotation-info TestClass)
=>
{:type {:annotations #{{:instance #object[com.sun.proxy.$Proxy1
                                          0xc544689
                                          "@exegesis.TypeAnnotationWithElements(first=some-value, second=some-default)"],
                        :type exegesis.TypeAnnotationWithElements,
                        :elements #{{:name second, :value "some-default"}
                                    {:name first, :value "some-value"}}}
                       {:instance #object[com.sun.proxy.$Proxy2
                                          0x5820f14b
                                          "@exegesis.TypeAnnotationWithNoElements()"],
                        :type exegesis.TypeAnnotationWithNoElements,
                        :elements #{}}}},
 :methods #{{:name doThirdThing, :annotations #{}, :parameters []}
            {:name doThingWithParameters,
             :annotations #{},
             :parameters [#{{:instance #object[com.sun.proxy.$Proxy6
                                               0x7486565f
                                               "@exegesis.ParameterAnnotationWithElements(priority=0, name=first)"],
                             :type exegesis.ParameterAnnotationWithElements,
                             :elements #{{:name priority, :value 0}
                                         {:name name, :value "first"}}}
                            {:instance #object[com.sun.proxy.$Proxy5
                                               0x5c471ec5
                                               "@exegesis.ParameterAnnotationWithNoElements()"],
                             :type exegesis.ParameterAnnotationWithNoElements,
                             :elements #{}}}
                          #{{:instance #object[com.sun.proxy.$Proxy6
                                               0x7f17b4a5
                                               "@exegesis.ParameterAnnotationWithElements(priority=10, name=second)"],
                             :type exegesis.ParameterAnnotationWithElements,
                             :elements #{{:name priority, :value 10}
                                         {:name name, :value "second"}}}}
                          #{}]}
            {:name doFirstThing,
             :annotations #{{:instance #object[com.sun.proxy.$Proxy4
                                               0x5a060e57
                                               "@exegesis.MethodAnnotationWithElements(first=20, second=)"],
                             :type exegesis.MethodAnnotationWithElements,
                             :elements #{{:name first, :value 20}
                                         {:name second, :value ""}}}
                            {:instance #object[com.sun.proxy.$Proxy3
                                               0x1c749fe6
                                               "@exegesis.MethodAnnotationWithNoElements()"],
                             :type exegesis.MethodAnnotationWithNoElements,
                             :elements #{}}},
             :parameters []}
            {:name doSecondThing,
             :annotations #{{:instance #object[com.sun.proxy.$Proxy3
                                               0x78211b9b
                                               "@exegesis.MethodAnnotationWithNoElements()"],
                             :type exegesis.MethodAnnotationWithNoElements,
                             :elements #{}}},
             :parameters []}}}
```

## Development

May be needed to get commit signing working.

```bash
git config --global user.signingkey [key-id]
git config --global gpg.program [gpg-path]
```

## License

Copyright © 2017 Toby Clemson

Distributed under the MIT license.
