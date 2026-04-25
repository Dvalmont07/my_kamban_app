package com.mykambanapp.domain.validation;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TextValidatorTest {

    @Nested
    @DisplayName("NotBlank Validation")
    class NotBlankValidation {

        @Test
        @DisplayName("Should throw exception when text is null")
        void shouldThrowExceptionWhenTextIsNull() {
            assertThatThrownBy(() -> TextValidator.validateNotBlank(null, "field"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("field must not be blank");
        }

        @Test
        @DisplayName("Should throw exception when text is empty")
        void shouldThrowExceptionWhenTextIsEmpty() {
            assertThatThrownBy(() -> TextValidator.validateNotBlank("", "field"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("field must not be blank");
        }

        @Test
        @DisplayName("Should throw exception when text is only spaces")
        void shouldThrowExceptionWhenTextIsOnlySpaces() {
            assertThatThrownBy(() -> TextValidator.validateNotBlank("   ", "field"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("field must not be blank");
        }

        @Test
        @DisplayName("Should not throw exception when text is valid")
        void shouldNotThrowExceptionWhenTextIsValid() {
            assertThatCode(() -> TextValidator.validateNotBlank("Valid text", "field"))
                .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("MaxLength Validation")
    class MaxLengthValidation {

        @Test
        @DisplayName("Should throw exception when text exceeds max length")
        void shouldThrowExceptionWhenTextExceedsMaxLength() {
            assertThatThrownBy(() -> TextValidator.validateMaxLength("Long text", 5, "field"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("field must not exceed 5 characters");
        }

        @Test
        @DisplayName("Should not throw exception when text is within max length")
        void shouldNotThrowExceptionWhenTextIsWithinMaxLength() {
            assertThatCode(() -> TextValidator.validateMaxLength("Short", 5, "field"))
                .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Should handle null text gracefully in maxLength (usually combined with NotBlank)")
        void shouldNotThrowExceptionWhenTextIsNullInMaxLength() {
            assertThatCode(() -> TextValidator.validateMaxLength(null, 5, "field"))
                .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("MinLength Validation")
    class MinLengthValidation {

        @Test
        @DisplayName("Should throw exception when text is shorter than min length")
        void shouldThrowExceptionWhenTextIsShorterThanMinLength() {
            assertThatThrownBy(() -> TextValidator.validateMinLength("A", 3, "field"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("field must be at least 3 characters");
        }

        @Test
        @DisplayName("Should not throw exception when text is at least min length")
        void shouldNotThrowExceptionWhenTextIsAtLeastMinLength() {
            assertThatCode(() -> TextValidator.validateMinLength("ABC", 3, "field"))
                .doesNotThrowAnyException();
        }
    }
}
