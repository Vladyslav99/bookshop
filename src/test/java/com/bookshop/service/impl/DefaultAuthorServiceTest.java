package com.bookshop.service.impl;

import com.bookshop.entity.Author;
import com.bookshop.repository.AuthorRepository;
import org.junit.Before;
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
public class DefaultAuthorServiceTest {

    private static final Long AUTHOR_ID = 1L;

    private static final int NOT_EMPTY_AUTHOR_LIST_SIZE = 2;
    private static final int EMPTY_AUTHOR_LIST_SIZE = 0;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private Author author;

    @Mock
    private Author author1;

    @InjectMocks
    private DefaultAuthorService testedInstance;

    @Before
    public void setUp() {
        when(authorRepository.save(author)).thenReturn(author);
    }

    @Test
    public void shouldCorrectSaveAuthor() {
        Author actual = testedInstance.save(author);

        assertEquals(author, actual);
    }

    @Test
    public void shouldReturnAuthorWhenInDB() {
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(author));

        Optional<Author> actual = testedInstance.findById(AUTHOR_ID);

        assertTrue(actual.isPresent());
    }

    @Test
    public void shouldReturnAuthorWhenNoInDB() {
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.empty());

        Optional<Author> actual = testedInstance.findById(AUTHOR_ID);

        assertFalse(actual.isPresent());
    }

    @Test
    public void shouldReturnNotEmptyAuthorList() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author, author1));

        List<Author> actual = testedInstance.findAll();

        assertEquals(NOT_EMPTY_AUTHOR_LIST_SIZE, actual.size());
    }

    @Test
    public void shouldReturnEmptyAuthorList() {
        when(authorRepository.findAll()).thenReturn(new ArrayList<>());

        List<Author> actual = testedInstance.findAll();

        assertEquals(EMPTY_AUTHOR_LIST_SIZE, actual.size());
    }
}