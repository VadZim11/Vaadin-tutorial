package com.jpsolution.vaadin.service;

import com.jpsolution.vaadin.entity.Category;

import java.util.List;

public interface CategoryService {

    // Find category.
    List<Category> getdAll();
    // Save or update category.
    Category save(Category category);
    // Delete category.
    void delete(Long id);
}
