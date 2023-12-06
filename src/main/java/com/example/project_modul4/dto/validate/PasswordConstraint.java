package com.example.project_modul4.dto.validate;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Password yêu cầu một chữ in hoa, chữ thường, môt số, và kí tự đặc biệt ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
