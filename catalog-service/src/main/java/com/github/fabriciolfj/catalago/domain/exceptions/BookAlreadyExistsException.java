package com.github.fabriciolfj.catalago.domain.exceptions;

public class BookAlreadyExistsException extends RuntimeException {

    public BookAlreadyExistsException(final String isbn) {
        super("A book with ISB " + isbn + " already exists.");
    }
}
