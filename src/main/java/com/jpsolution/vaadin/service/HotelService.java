package com.jpsolution.vaadin.service;

import com.jpsolution.vaadin.entity.Hotel;

import java.util.List;

public interface HotelService {

    // Find all hotels with the given name and address.
    List<Hotel> getHotel();
    // Save or update hotel.
    void saveHotel(Hotel hotel);
    // Delete hotel.
    void deleteHotel(Hotel hotel);
}
