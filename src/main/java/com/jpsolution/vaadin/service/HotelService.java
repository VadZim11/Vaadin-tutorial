package com.jpsolution.vaadin.service;

import com.jpsolution.vaadin.entity.HotelEntity;

import java.util.List;

public interface HotelService {

    // Find all hotels with the given name and address.
    List<HotelEntity> getdAll();
    // Save or update hotel.
    HotelEntity save(HotelEntity hotelEntity);
    // Delete hotel.
    void delete(Long id);
}
