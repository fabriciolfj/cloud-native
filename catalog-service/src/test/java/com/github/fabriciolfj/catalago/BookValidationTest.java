package com.github.fabriciolfj.catalago;

import com.github.fabriciolfj.catalago.domain.entity.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTest {

   /* private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book = new Book("1234567890", "Title", "Author", Year.of(2000), BigDecimal.ZERO, null, null, 0, null);
        var violations = validator.validate(book);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnDefineButIncorrectThenValidationFails() {
        var book = new Book("a234567890", "Title", "Author", Year.of(2000), BigDecimal.ZERO, null, null, 0, null);
        var violations = validator.validate(book);

        assertThat(violations).hasSize(1);
    }*/
}
