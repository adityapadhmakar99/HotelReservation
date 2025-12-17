package com.ap.model;

import com.ap.constant.RoomAmenity;
import java.util.List;

public class RoomType {
    private String id;
    private String name; // e.g., Deluxe, Suite, Standard
    private String description;
    private int maxOccupancy;
    private List<RoomAmenity> amenities;
    private double basePrice;

    public RoomType(String name, String description, int maxOccupancy, double basePrice) {
        this.name = name;
        this.description = description;
        this.maxOccupancy = maxOccupancy;
        this.basePrice = basePrice;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getMaxOccupancy() { return maxOccupancy; }
    public List<RoomAmenity> getAmenities() { return amenities; }
    public double getBasePrice() { return basePrice; }

    public void setAmenities(List<RoomAmenity> amenities) { this.amenities = amenities; }
}
