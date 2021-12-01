package com.github.fabriciolfj.catalago.domain.service;

import com.github.fabriciolfj.catalago.domain.entity.Book;
import com.github.fabriciolfj.catalago.domain.exceptions.BookAlreadyExistsException;
import com.github.fabriciolfj.catalago.domain.exceptions.BookNotFoundException;
import com.github.fabriciolfj.catalago.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public Collection<Book> viewBookList() {
        return repository.findByOrderByTitle();
    }

    public Book viewBookDetails(final String isbn) {
        return repository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(final Book book) {
        if (repository.existsByIsbn(book.getIsbn())) {
            throw new BookAlreadyExistsException(book.getIsbn());
        }

        return repository.save(book);
    }

    public void removeBookFromCatalog(final String isbn) {
        try {
            repository.deleteByIsbn(isbn);
        } catch (Exception e) {
            new BookNotFoundException(isbn);
        }
    }

    public Book editBookDetails(final String isbn, final Book book) {
        return repository.findByIsbn(isbn)
                .map(bookEntity -> {
                    BeanUtils.copyProperties(book, bookEntity, "id");
                    return repository.save(bookEntity);
                }).orElseThrow(() -> new BookNotFoundException(isbn));
    }
 }
