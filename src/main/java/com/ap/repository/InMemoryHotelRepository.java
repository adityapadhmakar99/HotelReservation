package com.ap.repository;

import com.ap.model.Hotel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryHotelRepository implements HotelRepository {
    private final Map<String, Hotel> hotels = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> hotelsByCity = new ConcurrentHashMap<>();

    @Override
    public Hotel save(Hotel hotel) {
        if (hotel.getId() == null) {
            hotel = new Hotel(hotel.getName(), hotel.getAddress(), hotel.getCity());
        }
        hotels.put(hotel.getId(), hotel);
        
        // Update city index
        hotelsByCity.computeIfAbsent(hotel.getCity().toLowerCase(), k -> new HashSet<>())
                  .add(hotel.getId());
                  
        return hotel;
    }

    @Override
    public Optional<Hotel> findById(String id) {
        return Optional.ofNullable(hotels.get(id));
    }

    @Override
    public List<Hotel> findAll() {
        return new ArrayList<>(hotels.values());
    }

    @Override
    public void deleteById(String id) {
        findById(id).ifPresent(hotel -> {
            // Remove from city index
            hotelsByCity.computeIfPresent(hotel.getCity().toLowerCase(), 
                (k, v) -> {
                    v.remove(hotel.getId());
                    return v.isEmpty() ? null : v;
                });
            hotels.remove(id);
        });
    }

    @Override
    public boolean existsById(String id) {
        return hotels.containsKey(id);
    }

    @Override
    public List<Hotel> findByCity(String city) {
        return hotelsByCity.getOrDefault(city.toLowerCase(), Collections.emptySet())
                .stream()
                .map(hotels::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Hotel> findByNameContaining(String name) {
        String searchTerm = name.toLowerCase();
        return hotels.values().stream()
                .filter(hotel -> hotel.getName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
}
