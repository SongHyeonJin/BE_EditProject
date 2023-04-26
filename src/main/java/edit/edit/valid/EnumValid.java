package edit.edit.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=EnumValidValidator.class)
public @interface EnumValid {
    String message() default "Invalid Enum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}