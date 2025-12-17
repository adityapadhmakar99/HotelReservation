package com.ap.model;

import java.util.UUID;

public class Room {
    private String id;
    private String roomNumber;
    private RoomType roomType;
    private Hotel hotel;
    private RoomStatus status;

    public Room(String roomNumber, RoomType roomType, Hotel hotel) {
        this.id = UUID.randomUUID().toString();
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.hotel = hotel;
        this.status = RoomStatus.AVAILABLE;
    }
    
    // For repository use only
    protected Room() {
        // Default constructor for repository
    }

    // Getters and setters
    public String getId() { return id; }
    public String getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public Hotel getHotel() { return hotel; }
    public String getHotelId() { return hotel != null ? hotel.getId() : null; }
    public RoomStatus getStatus() { return status; }

    public void setStatus(RoomStatus status) { this.status = status; }

    public enum RoomStatus {
        AVAILABLE, OCCUPIED, MAINTENANCE, RESERVED
    }
}
