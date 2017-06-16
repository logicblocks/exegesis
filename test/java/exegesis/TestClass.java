package exegesis;

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
