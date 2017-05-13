package com.jpsolution.vaadin.service;

import com.jpsolution.vaadin.entity.CategoryEntity;

import java.util.List;

/**
 * Created by Admin on 13.05.2017.
 */
public interface CategoryService {

    // Find category.
    List<CategoryEntity> findAll();
    // Save or update category.
    CategoryEntity save(CategoryEntity categoryEntity);
    // Delete category.
    void delete(CategoryEntity categoryEntity);
}
