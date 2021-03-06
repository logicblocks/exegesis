package exegesis;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={METHOD})
public @interface MethodAnnotationWithElements {
    int first() default 10;
    String second() default "";
}
