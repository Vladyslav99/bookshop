package com.bookshop.controller;

import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.service.BookService;
import com.bookshop.service.impl.DefaultBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    private static final Long BOOK_ID = 1L;

    private static final int NOT_EMPTY_BOOK_LIST_SIZE = 2;
    private static final int EMPTY_BOOK_LIST_SIZE = 0;

    @Mock
    private Book book1;

    @Mock
    private Book book2;

    @Mock
    private DefaultBookService bookService;

    @InjectMocks
    private BookController testedInstance;

    @Test
    public void shouldReturnBookWhenInDB() {
        when(bookService.findById(BOOK_ID)).thenReturn(Optional.of(book1));

        Book actual = testedInstance.one(BOOK_ID);

        assertEquals(book1, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldReturnBookWhenNoInDB() {
        when(bookService.findById(BOOK_ID)).thenReturn(Optional.empty());

        testedInstance.one(BOOK_ID);
    }

    @Test
    public void shouldReturnNotEmptyBookList() {
        when(bookService.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> actual = testedInstance.all();

        assertEquals(NOT_EMPTY_BOOK_LIST_SIZE, actual.size());
    }

    @Test
    public void shouldReturnEmptyBookList() {
        when(bookService.findAll()).thenReturn(new ArrayList<>());

        List<Book> actual = testedInstance.all();

        assertEquals(EMPTY_BOOK_LIST_SIZE, actual.size());
    }

}