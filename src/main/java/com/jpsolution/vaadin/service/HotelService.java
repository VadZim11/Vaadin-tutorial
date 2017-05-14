package com.jpsolution.vaadin.service;

import com.jpsolution.vaadin.entity.Hotel;

import java.util.List;

public interface HotelService {

    // Find all hotels with the given name and address.
    List<Hotel> getdAll();
    // Save or update hotel.
    Hotel save(Hotel hotel);
    // Delete hotel.
    void delete(Long id);
}
