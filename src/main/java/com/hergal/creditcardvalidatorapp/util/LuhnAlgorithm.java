package com.hergal.creditcardvalidatorapp.util;

public class LuhnAlgorithm {

    public static String generateCreditCardNumber() {
        // Generate a random 15-digit number as a base
        StringBuilder number = new StringBuilder("4"); // Starting with 4 for Visa cards
        for (int i = 0; i < 14; i++) {
            number.append((int) (Math.random() * 10));
        }

        // Calculate the Luhn check digit
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        number.append(checkDigit);

        return number.toString();
    }

    public static boolean validateCreditCardNumber(String creditCardNumber) {
        int sum = 0;
        for (int i = creditCardNumber.length() - 2; i >= 0; i -= 2) {
            int digit = Character.getNumericValue(creditCardNumber.charAt(i));
            digit *= 2;
            if (digit > 9) {
                digit -= 9;
            }
            sum += digit;
        }

        for (int i = creditCardNumber.length() - 1; i >= 0; i -= 2) {
            sum += Character.getNumericValue(creditCardNumber.charAt(i));
        }

        return (sum % 10 == 0);
    }
}
