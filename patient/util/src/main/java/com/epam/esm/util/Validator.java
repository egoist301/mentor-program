package com.epam.esm.util;

import com.epam.esm.exception.IncorrectPathVariableException;

public final class Validator {
    private Validator() {
    }

    public static void validateId(Long id) {
        if (id <= 0) {
            throw new IncorrectPathVariableException("Incorrect id (should be positive)");
        }
    }

    public static void validateSortAndOrder(String sortBy, String order) {
        if ((sortBy != null &&
                !(sortBy.equals("first_name") || sortBy.equals("last_name") || sortBy.equals("middle_name") ||
                        sortBy.equals("date_of_birth"))) ||
                (order != null && !(order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc")))) {
            throw new IncorrectPathVariableException();
        }
    }
}
