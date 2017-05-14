package com.jpsolution.vaadin.repository;

import java.io.Serializable;
import java.util.List;

public interface GenericDao {
    <T> T merge(T t);
    <T, PK extends Serializable> void delete(Class<T> type, PK id);
    <T, PK extends Serializable> T find(Class<T> type, PK id);
    <T> List<T> list(Class<T> type);
}
