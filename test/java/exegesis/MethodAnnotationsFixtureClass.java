package exegesis;

public class MethodAnnotationsFixtureClass {
    @MethodAnnotationWithNoElements
    @MethodAnnotationWithElements(first = 20)
    public void doFirstThing() {}

    @MethodAnnotationWithNoElements
    public void doSecondThing() {}

    public void doThirdThing() {}
}
