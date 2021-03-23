package com.bookshop.service;

import java.util.List;
import java.util.Optional;

public interface Service <T> {
    T save(T t);

    Optional<T> findById(Long id);

    List<T> findAll();

}
