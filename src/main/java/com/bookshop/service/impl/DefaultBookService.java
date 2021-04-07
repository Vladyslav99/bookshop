package com.bookshop.service.impl;

import com.bookshop.entity.Book;
import com.bookshop.repository.BookRepository;
import com.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultBookService implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }


    @PostConstruct
    public void setUpBooks() {
        save(new Book(1L, "1984", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(2L, "Invincible Sun", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(3L, "The Outsider", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(4L, "The magical worlds of Hayao Miyazaki", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(5L, "Brave New World", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(6L, "One Hundred Years of Solitude", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(7L, "The Count of Monte Cristo", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(8L, "The Art of Love", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(9L, "The Man Who Died Twice", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(10L, "$100 startup. How to turn a hobby into a business", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(11L, "Willpower. The way to power over yourself", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(12L, "Inevitably. 12 technological trends that determine our future", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(13L, "The Upside of Inequality: How Good Intentions Undermine the Middle Class", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(14L, "A Complaint Is a Gift: Recovering Customer Loyalty When Things Go Wrong", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(15L, "Leader's Charisma", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(16L, "Device №1: The Secret History of the iPhone", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));
        save(new Book(17L, "Save capitalism. How to make the free market work for people", "", BigDecimal.valueOf(10), LocalDate.now(), 150L, null, null));

    }
}
