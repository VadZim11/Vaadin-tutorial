package com.jpsolution.vaadin.repository;

import com.jpsolution.vaadin.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
