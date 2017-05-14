package com.jpsolution.vaadin.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GenericDaoJpaImpl implements GenericDao{

    @Autowired
    private SessionFactory sessionFactory;

    public GenericDaoJpaImpl(){
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T merge(T t){
        return (T) currentSession().merge(t);
    }

    public <T, PK extends Serializable> T find(Class<T> type, PK id){
        return (T) currentSession().get(type, id);
    }

    public <T, PK extends Serializable> void delete(Class<T> type, PK id){
        Session currentSession = currentSession();
        Object object = currentSession.get(type, id);
        currentSession.delete(object);
    }

    public <T> List<T> list(Class<T> type){
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<T> root = criteria.from(type);
        criteria.select(root);
        return currentSession().createQuery(criteria).getResultList();
    }

    protected Session currentSession(){
        Session currentSession = sessionFactory.getCurrentSession();
        //Session currentSession = sessionFactory.openSession();
        return currentSession;
    }
}
