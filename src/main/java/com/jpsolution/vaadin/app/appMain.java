package com.jpsolution.vaadin.app;

import com.jpsolution.vaadin.entity.CategoryEntity;
import com.jpsolution.vaadin.util.HibernateSessionFactory;
import org.hibernate.Session;

class AppMain {

    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");

        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        session.beginTransaction();

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setCategory("Hotel");

        session.save(categoryEntity);
        session.getTransaction().commit();

        session.close();


    }
}
