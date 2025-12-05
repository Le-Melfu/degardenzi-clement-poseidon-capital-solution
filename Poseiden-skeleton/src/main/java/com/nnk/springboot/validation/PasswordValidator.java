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

        // Au moins 8 caractères
        if (password.length() < 8) {
            return false;
        }

        // Au moins une lettre majuscule
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        if (!hasUpperCase) {
            return false;
        }

        // Au moins un chiffre
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        if (!hasDigit) {
            return false;
        }

        // Au moins un symbole (caractère non alphanumérique)
        boolean hasSymbol = password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
        if (!hasSymbol) {
            return false;
        }

        return true;
    }
}

