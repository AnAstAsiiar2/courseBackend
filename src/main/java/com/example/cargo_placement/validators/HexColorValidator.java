package com.example.cargo_placement.validators;

import com.example.cargo_placement.validators.ValidHexColor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HexColorValidator implements ConstraintValidator<ValidHexColor, String> {

    private static final String HEX_COLOR_PATTERN = "^([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches(HEX_COLOR_PATTERN);
    }
}
