package com.epam.esm.util;

import com.epam.esm.exception.EntityIsNotExistException;
import com.epam.esm.exception.IncorrectPathVariableException;

import java.util.Arrays;
import java.util.Objects;

public final class Validator {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String MIDDLE_NAME = "middleName";
    private static final String DATE_OF_BIRTH = "dateOfBirth";

    private Validator() {
    }

    public static void validateId(Long id) {
        if (id < 0) {
            throw new IncorrectPathVariableException("Incorrect id (should be positive)");
        }
        if (id == 0) {
            throw new EntityIsNotExistException("object is not exist");
        }
    }

    public static void validateSortAndOrder(String sortBy, String order) {
        if ((Objects.nonNull(sortBy) &&
                !(Arrays.asList(FIRST_NAME, LAST_NAME, MIDDLE_NAME, DATE_OF_BIRTH).contains(sortBy))) ||
                !("asc".equalsIgnoreCase(order) || "desc".equalsIgnoreCase(order))) {
            throw new IncorrectPathVariableException("incorrect sort param:" + sortBy + " or order param:" + order);
        }
    }

    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 1) {
            throw new IncorrectPathVariableException("Page number cannot be less than 1.");
        }
        if (size < 1) {
            throw new IncorrectPathVariableException("size cannot be less than 1.");
        }
    }
}
