package com.bookshop.controller;

import com.bookshop.bot.BookBot;
import com.bookshop.dto.OrderDTO;
import com.bookshop.entity.Book;
import com.bookshop.entity.Order;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.service.BookService;
import com.bookshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ControllerConstants.Mapping.BOOK_SHOP)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookBot bookBot;

    @PostMapping(value = "/orders/new", consumes = "application/json", produces = "application/json")
    public Order newOrder(@RequestBody OrderDTO books, @RequestParam String address,
                          @RequestParam String phone, @RequestParam BigDecimal totalPrice) {
        if (books.getBookOrderDTO().isEmpty()) throw new IllegalArgumentException("Book list is empty");
        List<Book> booksList = books.getBookOrderDTO()
                .stream()
                .map(i -> bookService.findById(i.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        String bookOrder = books.getBookOrderDTO()
                .stream()
                .filter(i -> bookService.findById(i.getId()).isPresent())
                .map(i -> bookService.findById(i.getId()).get().getTitle() + "(" + i.getAmount() + ") - $" + i.getTotal())
                .collect(Collectors.joining("\n"));
        bookBot.broadcastAdmins(String.format("New BookWorld order!\n" +
                "\n\n%s\n\n" +
                "Address:%s\n" +
                "Phone:%s\n" +
                "Total Price:%s\n" +
                "Date: %s", bookOrder, address, phone, totalPrice, LocalDate.now().toString()));
        Order order = Order.builder()
                .books(booksList)
                .date(LocalDate.now())
                .commonPrice(totalPrice)
                .address(address)
                .phone(phone)
                .build();
        return orderService.save(order);
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
