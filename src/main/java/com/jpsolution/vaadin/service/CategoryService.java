package com.jpsolution.vaadin.service;

import com.jpsolution.vaadin.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {

    // Find category.
    List<CategoryEntity> getdAll();
    // Save or update category.
    CategoryEntity save(CategoryEntity categoryEntity);
    // Delete category.
    void delete(Long id);
}
