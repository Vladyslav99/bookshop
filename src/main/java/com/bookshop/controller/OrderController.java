package com.bookshop.controller;

import com.bookshop.entity.Book;
import com.bookshop.entity.Order;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.service.BookService;
import com.bookshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;

@RestController
@RequestMapping(ControllerConstants.Mapping.BOOK_SHOP)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BookService bookService;

    @PostMapping("/orders/new")
    public Order newOrder(@RequestBody Long bookId){
        Book book = bookService.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Entity %s with id %s not found", Book.class, bookId)));
        return Order.builder()
                .books(Collections.singletonList(book))
                .date(LocalDate.now())
                .commonPrice(book.getPrice())
                .build();
    }

    @PostMapping("/orders/{id}")
    public Order addBookToOrder(@PathVariable Long id, @RequestBody Long bookId) {
        Book book = bookService.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Entity %s with id %s not found", Book.class, bookId)));

        Order order = orderService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Entity %s with id %s not found", Order.class, id)));
        order.getBooks().add(book);
        order.setCommonPrice(order.getCommonPrice().add(book.getPrice()));
        return order;
    }

}
