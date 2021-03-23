package com.bookshop.service.impl;

import com.bookshop.entity.Warehouse;
import com.bookshop.repository.WarehouseRepository;
import com.bookshop.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultWarehouseService implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Override
    public Optional<Warehouse> findById(Long id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }
}
