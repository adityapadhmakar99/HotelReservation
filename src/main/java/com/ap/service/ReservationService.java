package com.ap.service;

import com.ap.constant.NotificationType;
import com.ap.constant.PaymentStatus;
import com.ap.model.*;
import com.ap.notification.NotificationService;
import com.ap.payment.PaymentContext;
import com.ap.payment.PaymentMethod;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ReservationService {
    private static ReservationService instance;
    private final Map<String, Reservation> reservations;
    private final Map<String, List<Reservation>> userReservations;
    private final HotelService hotelService;
    private final NotificationService notificationService;

    private ReservationService() {
        this.reservations = new ConcurrentHashMap<>();
        this.userReservations = new ConcurrentHashMap<>();
        this.hotelService = HotelService.getInstance();
        this.notificationService = NotificationService.getInstance();
    }

    public static synchronized ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public Reservation createReservation(User user, String hotelId, String roomId, 
                                       LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel not found");
        }

        Room room = findRoomById(hotel, roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found in the specified hotel");
        }

        // Check room availability
        if (!isRoomAvailable(room, checkInDate, checkOutDate)) {
            throw new IllegalStateException("Room is not available for the selected dates");
        }

        // Calculate total amount
        double totalAmount = calculateTotalAmount(room, checkInDate, checkOutDate);

        // Create reservation
        Reservation reservation = new Reservation(user, room, checkInDate, checkOutDate, totalAmount);
        reservations.put(reservation.getId(), reservation);
        
        // Add to user's reservation history
        userReservations.computeIfAbsent(user.getId(), k -> new ArrayList<>()).add(reservation);
        
        // Update room status
        room.setStatus(Room.RoomStatus.RESERVED);
        
        return reservation;
    }

    public boolean processPayment(Reservation reservation, PaymentMethod paymentMethod) {
        PaymentContext paymentContext = new PaymentContext(paymentMethod);
        Payment payment = paymentContext.executePayment(reservation.getTotalAmount());
        
        if (payment != null && payment.getStatus() == PaymentStatus.COMPLETED) {
            reservation.setPayment(payment);
            reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);
            
            // Notify user
            String message = String.format("Your reservation %s has been confirmed. Total amount: $%.2f",
                    reservation.getId(), reservation.getTotalAmount());
            notificationService.sendNotification(NotificationType.EMAIL, reservation.getUser(), message);
            notificationService.sendNotification(NotificationType.SMS, reservation.getUser(), 
                    "Reservation confirmed. Ref: " + reservation.getId());
            
            return true;
        } else {
            // If payment fails, release the room
            Room room = reservation.getRoom();
            room.setStatus(Room.RoomStatus.AVAILABLE);
            
            // Update reservation status
            reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
            
            // Notify user
            String message = String.format("Payment failed for reservation %s. Please try again.", 
                    reservation.getId());
            notificationService.sendNotification(NotificationType.EMAIL, reservation.getUser(), message);
            
            return false;
        }
    }

    public boolean cancelReservation(String reservationId, User user) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null || !reservation.getUser().getId().equals(user.getId())) {
            return false;
        }

        // Check if cancellation is allowed (e.g., within cancellation period)
        if (!isCancellationAllowed(reservation)) {
            return false;
        }

        // Update room status
        Room room = reservation.getRoom();
        room.setStatus(Room.RoomStatus.AVAILABLE);
        
        // Update reservation status
        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        
        // Process refund if applicable
        if (reservation.getPayment() != null) {
            // In a real implementation, we would process the refund here
            System.out.println("Processing refund for reservation: " + reservationId);
        }
        
        // Notify user
        String message = String.format("Your reservation %s has been cancelled. ", reservationId);
        if (reservation.getPayment() != null) {
            message += "Refund will be processed within 5-7 business days.";
        }
        notificationService.sendNotification(NotificationType.EMAIL, user, message);
        
        return true;
    }

    public List<Reservation> getUserReservations(String userId) {
        return userReservations.getOrDefault(userId, Collections.emptyList());
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    private Room findRoomById(Hotel hotel, String roomId) {
        return hotel.getRooms().stream()
                .filter(room -> room.getId().equals(roomId))
                .findFirst()
                .orElse(null);
    }

    private boolean isRoomAvailable(Room room, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        // In a real implementation, we would check the room's availability for the given dates
        // For now, we'll just check the room status
        return room.getStatus() == Room.RoomStatus.AVAILABLE;
    }

    private double calculateTotalAmount(Room room, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        // Calculate number of nights
        long days = java.time.Duration.between(checkInDate, checkOutDate).toDays();
        if (days <= 0) days = 1; // Minimum 1 day
        
        // Get base price from room type and calculate total
        double basePrice = room.getRoomType().getBasePrice();
        return basePrice * days;
    }

    private boolean isCancellationAllowed(Reservation reservation) {
        // In a real implementation, we would check the cancellation policy
        // For now, allow cancellation if check-in is more than 24 hours away
        return LocalDateTime.now().plusHours(24).isBefore(reservation.getCheckInDate());
    }
}
