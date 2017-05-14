package com.jpsolution.vaadin.repository;



import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository(value="categoryRepository")
@Transactional
public class CategoryRepository extends GenericDaoJpaImpl{
        }
