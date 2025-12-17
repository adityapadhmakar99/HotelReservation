package com.ap.repository;

import com.ap.model.Hotel;

import java.util.List;

public interface HotelRepository extends Repository<Hotel, String> {
    List<Hotel> findByCity(String city);
    List<Hotel> findByNameContaining(String name);
}
