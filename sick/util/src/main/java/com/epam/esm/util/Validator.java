package com.epam.esm.util;

import com.epam.esm.constant.AppConstants;
import com.epam.esm.exception.EntityIsNotExistException;
import com.epam.esm.exception.IncorrectPathVariableException;

import java.util.Arrays;

public final class Validator {

    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String DATE_OF_BIRTH = "date_of_birth";

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
        if ((sortBy != null &&
                !(Arrays.asList(FIRST_NAME, LAST_NAME, MIDDLE_NAME, DATE_OF_BIRTH).contains(sortBy))) ||
                (order != null && !(order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc")))) {
            throw new IncorrectPathVariableException("incorrect sort param:" + sortBy + " or order param:" + order);
        }
    }

    public static void validatePageNumberAndSize(int page, int size) {
        if(page < 1) {
            throw new IncorrectPathVariableException("Page number cannot be less than 1.");
        }

        if (size < 0) {
            throw new IncorrectPathVariableException("size cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new IncorrectPathVariableException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
