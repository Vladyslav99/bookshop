package com.bookshop.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishingDate;

    private Long pageAmount;

    @ManyToMany
    private Set<Author> authors;

    @ManyToMany
    private Set<Category> categories;
}
