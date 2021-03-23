package com.bookshop.controller;

import com.bookshop.entity.Category;
import com.bookshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ControllerConstants.Mapping.BOOK_SHOP)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(ControllerConstants.Mapping.CATEGORIES)
    public List<Category> all() {
        return categoryService.findAll();
    }
}
