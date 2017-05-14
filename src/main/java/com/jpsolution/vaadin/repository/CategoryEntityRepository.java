package com.jpsolution.vaadin.repository;


import com.jpsolution.vaadin.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Long> {
        }
