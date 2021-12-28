package com.github.fabriciolfj.catalago;

import com.github.fabriciolfj.catalago.domain.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    //@Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = new Book("1231231231", "Title", "Author", Year.of(2000), BigDecimal.ZERO, null, null, 0, null);
        var response = restTemplate.postForEntity("/books", expectedBook, Book.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
