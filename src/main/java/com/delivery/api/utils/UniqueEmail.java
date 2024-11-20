package com.delivery.api.utils;

import com.delivery.api.entities.UserType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "O e-mail já está em uso";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    UserType type();
    long ignoreId() default -1;
}
