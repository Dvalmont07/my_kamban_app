package com.mykambanapp.domain.validation;

/**
 * Utility class for text validation across the application.
 */
public class TextValidator {

    private TextValidator() {
        // Utility class
    }

    /**
     * Validates that a string is not null, not empty, and not only whitespace.
     * 
     * @param text The text to validate
     * @param fieldName The name of the field (used in exception message)
     * @throws IllegalArgumentException if the text is blank
     */
    public static void validateNotBlank(String text, String fieldName) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
    }

    /**
     * Validates that a string does not exceed a maximum length.
     * 
     * @param text The text to validate
     * @param maxLength The maximum allowed length
     * @param fieldName The name of the field (used in exception message)
     * @throws IllegalArgumentException if the text length exceeds maxLength
     */
    public static void validateMaxLength(String text, int maxLength, String fieldName) {
        if (text != null && text.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " must not exceed " + maxLength + " characters");
        }
    }

    /**
     * Validates that a string has at least a minimum length.
     * 
     * @param text The text to validate
     * @param minLength The minimum allowed length
     * @param fieldName The name of the field (used in exception message)
     * @throws IllegalArgumentException if the text length is less than minLength
     */
    public static void validateMinLength(String text, int minLength, String fieldName) {
        if (text == null || text.length() < minLength) {
            throw new IllegalArgumentException(fieldName + " must be at least " + minLength + " characters");
        }
    }
}
