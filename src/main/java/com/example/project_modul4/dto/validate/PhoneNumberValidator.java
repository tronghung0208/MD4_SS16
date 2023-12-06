package com.example.project_modul4.dto.validate;
import com.example.project_modul4.model.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.List;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint,String> {
    @Autowired
    private UserDAO userDAO;
    @Override
    public void initialize(PhoneNumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (!value.isEmpty()) {
            if (value.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
                List<String> list = userDAO.uniquelist("phone");
                if (list.contains(value)) {
                    String m = "Phone đã tồn tại";
                    context.buildConstraintViolationWithTemplate(m).addConstraintViolation();
                    return false;
                } else {
                    return true;
                }
            }else {
                context.buildConstraintViolationWithTemplate("Phone không đúng định dạng").addConstraintViolation();
                return false;
            }
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Phone không được để trống").addConstraintViolation();
        return false;
    }


}