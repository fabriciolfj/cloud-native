package com.github.fabriciolfj.catalago.domain.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(final String isbn) {
        super("The book with ISBN " + isbn + " was not found");
    }
}
