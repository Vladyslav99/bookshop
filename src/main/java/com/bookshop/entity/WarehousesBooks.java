package com.bookshop.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WAREHOUSES_BOOKS")
public class WarehousesBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Warehouse warehouse;

    private Long bookAmount;
}
