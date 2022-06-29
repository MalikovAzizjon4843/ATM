package uz.oyatjon.annotations;

import java.lang.annotation.*;



@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Inherited
public @interface Unique {
}
