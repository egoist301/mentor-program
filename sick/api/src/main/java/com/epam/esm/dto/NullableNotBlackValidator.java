package com.epam.esm.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NullableNotBlackValidator implements ConstraintValidator<NullableNotBlank, CharSequence> {

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext context) {
        if (Objects.isNull(charSequence)) {
            return true;
        }
        return charSequence.toString().trim().length() > 0;
    }

}

