package com.github.fabriciolfj.catalago.web.api;

import com.github.fabriciolfj.catalago.domain.entity.Book;
import com.github.fabriciolfj.catalago.domain.service.BookService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Collection<Book> get() {
        return bookService.viewBookList();
    }

    @GetMapping("/{isbn}")
    public Book getByIsbn(@PathVariable final String isbn) {
        return bookService.viewBookDetails(isbn);
    }

    @PostMapping
    public Book post(@RequestBody @Valid final Book book) {
        return bookService.addBookToCatalog(book);
    }

    @DeleteMapping("/{isbn}")
    public void delete(@PathVariable final String isbn) {
        bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("/{isbn}")
    public Book put(@PathVariable final String isbn, @RequestBody final Book book) {
        return bookService.editBookDetails(isbn, book);
    }
}
