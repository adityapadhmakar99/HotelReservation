package com.ap.model;

import com.ap.constant.HotelAmenity;
import java.util.List;
import java.util.UUID;

public class Hotel {
    private String id;
    private String name;
    private String address;
    private String city;
    private double rating;
    private List<Room> rooms;
    private List<HotelAmenity> amenities;

    public Hotel(String name, String address, String city) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.city = city;
        this.rating = 0.0;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public double getRating() { return rating; }
    public List<Room> getRooms() { return rooms; }
    public List<HotelAmenity> getAmenities() { return amenities; }

    public void setRooms(List<Room> rooms) { this.rooms = rooms; }
    public void setAmenities(List<HotelAmenity> amenities) { this.amenities = amenities; }
    public void setRating(double rating) { this.rating = rating; }
}
