package exegesis;

@TypeAnnotationWithElements(first = "some-value")
@TypeAnnotationWithNoElements
public class TestClass {
    private String thing;

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
