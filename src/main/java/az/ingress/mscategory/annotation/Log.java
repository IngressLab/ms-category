package az.ingress.mscategory.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Inherited
@Target(value = {TYPE, METHOD})
@Retention(value = RUNTIME)
public @interface Log {
    @Target(value = {FIELD, PARAMETER})
    @Retention(value = RUNTIME)
    @interface Exclude {}
}