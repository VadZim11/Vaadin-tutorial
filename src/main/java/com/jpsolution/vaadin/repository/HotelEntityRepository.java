package com.jpsolution.vaadin.repository;

import com.jpsolution.vaadin.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HotelEntityRepository extends JpaRepository<HotelEntity, Long> {
}
