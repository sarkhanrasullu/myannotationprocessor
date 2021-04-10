package com.company;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface IsInstanceVariable {

}
