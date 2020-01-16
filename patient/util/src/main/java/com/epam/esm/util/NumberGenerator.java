package com.epam.esm.util;

import java.util.Random;

public final class NumberGenerator {
    private NumberGenerator() {
    }

    public static String generateIdentificationNumber(int length) {
        String alphaNumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            result.append(
                    alphaNumeric.charAt(random.nextInt(alphaNumeric.length())));
        }
        return result.toString();
    }
}
