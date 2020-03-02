package com.epam.esm.dto;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = NullableNotBlackValidator.class)
@Target({METHOD, FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullableNotBlank {

    String message() default "Can`t be spaces!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
