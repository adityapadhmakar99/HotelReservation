package com.ap.service;

import com.ap.constant.RoomStatus;
import com.ap.model.Hotel;
import com.ap.model.Room;
import com.ap.model.RoomType;
import java.util.*;

public class HotelService {
    private static HotelService instance;
    private final Map<String, Hotel> hotels;
    private final Map<String, RoomType> roomTypes;

    private HotelService() {
        this.hotels = new HashMap<>();
        this.roomTypes = new HashMap<>();
        initializeRoomTypes();
    }

    public static synchronized HotelService getInstance() {
        if (instance == null) {
            instance = new HotelService();
        }
        return instance;
    }

    private void initializeRoomTypes() {
        // Initialize some default room types
        RoomType standard = new RoomType("Standard", "Standard room with basic amenities", 2, 100.0);
        RoomType deluxe = new RoomType("Deluxe", "Deluxe room with premium amenities", 2, 200.0);
        RoomType suite = new RoomType("Suite", "Luxury suite with separate living area", 4, 400.0);
        
        roomTypes.put(standard.getName().toLowerCase(), standard);
        roomTypes.put(deluxe.getName().toLowerCase(), deluxe);
        roomTypes.put(suite.getName().toLowerCase(), suite);
    }

    public void addHotel(Hotel hotel) {
        hotels.put(hotel.getId(), hotel);
    }

    public Hotel getHotelById(String hotelId) {
        return hotels.get(hotelId);
    }

    public List<Hotel> searchHotels(String location) {
        List<Hotel> result = new ArrayList<>();
        for (Hotel hotel : hotels.values()) {
            if (hotel.getCity().equalsIgnoreCase(location)) {
                result.add(hotel);
            }
        }
        return result;
    }

    public List<Room> searchAvailableRooms(String hotelId, Date checkIn, Date checkOut) {
        Hotel hotel = getHotelById(hotelId);
        if (hotel == null) {
            return Collections.emptyList();
        }
        
        // In a real implementation, we would check room availability based on dates
        // For now, return all rooms that are marked as available
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : hotel.getRooms()) {
            if (room.getStatus() == RoomStatus.AVAILABLE) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public RoomType getRoomType(String typeName) {
        return roomTypes.get(typeName.toLowerCase());
    }

    public List<RoomType> getAllRoomTypes() {
        return new ArrayList<>(roomTypes.values());
    }
}
