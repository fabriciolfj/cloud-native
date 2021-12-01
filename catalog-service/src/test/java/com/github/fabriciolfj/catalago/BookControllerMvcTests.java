package com.github.fabriciolfj.catalago;

import com.github.fabriciolfj.catalago.domain.exceptions.BookNotFoundException;
import com.github.fabriciolfj.catalago.domain.repository.BookRepository;
import com.github.fabriciolfj.catalago.domain.service.BookService;
import com.github.fabriciolfj.catalago.web.api.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository repository;

    @MockBean
    private BookService bookService;

    @Test
    void whenReadingNotExistingBookThenShouldReturn404() throws Exception {
        String isbn = "73737313940";
        given(bookService.viewBookDetails(isbn)).willThrow(new BookNotFoundException(isbn));
        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + isbn).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("The book with ISBN 73737313940 was not found"));
    }
}
