package com.tradeware.securelogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tradeware.clouddatabase.service.UserService;
import com.tradeware.stockmarket.bean.UserDataBean;


@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDataBean.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDataBean user = (UserDataBean) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apiKey", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "secretKey", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "histApiKey", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "histSecretKey", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "NotEmpty");
        
        
        if (user.getUserName().length() < 6 || user.getUserName().length() > 32) {
            errors.rejectValue("userName", "Size.userForm.userName");
        }
        if (userService.findByUserId(user.getUserId()) != null) {
            errors.rejectValue("userName", "Duplicate.userForm.userName");
        }

        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (null!= user.getPasswordConfirm() && !"".equals(user.getPasswordConfirm() ) && !user.getPasswordConfirm().equals(user.getPassword())) {
           errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
            
           /* ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
        			"required.userName", "Field name is required.");*/
        }
    }
}
