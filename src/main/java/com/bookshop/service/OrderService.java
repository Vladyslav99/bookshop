package com.bookshop.service;

import com.bookshop.entity.Order;

public interface OrderService extends Service<Order>{

    void delete(Long id);

}
