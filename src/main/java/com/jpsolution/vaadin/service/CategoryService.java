package com.jpsolution.vaadin.service;

import com.jpsolution.vaadin.entity.Category;

import java.util.List;

public interface CategoryService {

    // Find category
    List<Category> getCategory();
    // Save or update category
    void saveCategory(Category category);
    // Delete category
    void deleteCategory(Category category);
}
