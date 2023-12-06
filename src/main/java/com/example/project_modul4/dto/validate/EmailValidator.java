package com.example.project_modul4.dto.validate;
import com.example.project_modul4.model.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EmailValidator implements ConstraintValidator<EmailConstraint,String> {
    @Autowired
    private UserDAO userDAO;
    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.isEmpty()) {
            if (value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                List<String> list = userDAO.uniquelist("email");
                if (list.contains(value)) {
                    String m = "Email đã tồn tại";
                    context.buildConstraintViolationWithTemplate(m).addConstraintViolation();
                    return false;
                } else {
                    return true;
                }
            }else {
                context.buildConstraintViolationWithTemplate("Email sai định dạng").addConstraintViolation();
                return false;
            }
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Email không được để trống").addConstraintViolation();
        return false;
    }
}