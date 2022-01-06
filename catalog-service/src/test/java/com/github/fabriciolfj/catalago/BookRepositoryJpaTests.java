package com.github.fabriciolfj.catalago;

import com.github.fabriciolfj.catalago.domain.entity.Book;
import com.github.fabriciolfj.catalago.domain.repository.BookRepository;
import com.github.fabriciolfj.catalago.persistence.JpaConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

/*
@DataJpaTest
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryJpaTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findBookByIsbnWhenExisting() {
        String bookIsbn = "1234561235";
        Book expectedBook = new Book(bookIsbn, "Title", "Author", Year.of(2000), BigDecimal.valueOf(12.90), null, null, 0, null);
        entityManager.persist(expectedBook);

        var actualBook = bookRepository.findByIsbn(bookIsbn);

        assertThat(actualBook.get().getIsbn().equals(expectedBook.getIsbn()));
    }
}*/