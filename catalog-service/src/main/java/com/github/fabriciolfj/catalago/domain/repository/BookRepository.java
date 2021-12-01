package com.github.fabriciolfj.catalago.domain.repository;

import com.github.fabriciolfj.catalago.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    Collection<Book> findByOrderByTitle();
    boolean existsByIsbn(final String isbn);
    @Transactional
    void deleteByIsbn(final String isbn);
    Optional<Book> findByIsbn(final String isbn);
}
