package com.aditya.electronic.store.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface  ImageNameValid {
    String message() default "Invalid Image Name";
    Class<?>[] groups() default{ };
    Class<? extends Payload>[] payload() default{ };
}
