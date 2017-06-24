package exegesis;

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
