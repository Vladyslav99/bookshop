package com.bookshop.controller;

import com.bookshop.entity.Book;
import com.bookshop.entity.Category;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerConstants.Mapping.BOOK_SHOP)
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(ControllerConstants.Mapping.BOOKS)
    public List<Book> all() {
        return bookService.findAll();
    }

    @GetMapping(ControllerConstants.Mapping.BOOKS + ControllerConstants.Mapping.ID)
    public Book one(@PathVariable Long id) {
        return bookService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity %s with id %s not found", Book.class, id)));
    }

    @PostMapping(ControllerConstants.Mapping.BOOKS)
    public Book newBook(@RequestBody Book book) {
        return bookService.save(book);
    }
}
