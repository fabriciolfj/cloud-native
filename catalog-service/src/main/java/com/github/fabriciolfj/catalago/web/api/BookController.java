package com.github.fabriciolfj.catalago.web.api;

import com.github.fabriciolfj.catalago.domain.entity.Book;
import com.github.fabriciolfj.catalago.domain.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Collection<Book> get() {
        log.info("Feching the list of books in the catalog");
        return bookService.viewBookList();
    }

    @GetMapping("/{isbn}")
    public Book getByIsbn(@PathVariable final String isbn) {
        log.info("Find book to isbn {}", isbn);
        return bookService.viewBookDetails(isbn);
    }

    @PostMapping
    public Book post(@RequestBody @Valid final Book book) {
        log.info("Create book: {}", book.getIsbn());
        return bookService.addBookToCatalog(book);
    }

    @DeleteMapping("/{isbn}")
    public void delete(@PathVariable final String isbn) {
        log.info("Delete book {}", isbn);
        bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("/{isbn}")
    public Book put(@PathVariable final String isbn, @RequestBody final Book book) {
        log.info("Update book {}", isbn);
        return bookService.editBookDetails(isbn, book);
    }
}
