package com.jpsolution.vaadin.repository;


import com.jpsolution.vaadin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
        }
