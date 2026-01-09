package com.nnk.springboot.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        // At least 8 characters
        if (password.length() < 8) {
            return false;
        }

        // At least one uppercase letter
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        if (!hasUpperCase) {
            return false;
        }

        // At least one digit
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        if (!hasDigit) {
            return false;
        }

        // At least one symbol (non-alphanumeric character)
        boolean hasSymbol = password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
        if (!hasSymbol) {
            return false;
        }

        return true;
    }
}

