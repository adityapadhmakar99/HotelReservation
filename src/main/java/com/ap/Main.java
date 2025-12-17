package com.ap;

import com.ap.constant.UserType;
import com.ap.model.*;
import com.ap.notification.NotificationService;
import com.ap.payment.CreditCardPayment;
import com.ap.payment.PaymentMethod;
import com.ap.service.HotelService;
import com.ap.service.ReservationService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Main class to demonstrate the Hotel Reservation System.
 * This class showcases the functionality of the system including:
 * - Hotel and room management
 * - User registration
 * - Room reservation
 * - Payment processing
 * - Notification system
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hotel Reservation System ===\n");

        // Initialize services
        HotelService hotelService = HotelService.getInstance();
        ReservationService reservationService = ReservationService.getInstance();
        NotificationService notificationService = NotificationService.getInstance();

        // Create a hotel
        Hotel hotel = new Hotel("Grand Plaza", "123 Main St", "New York");
        
        // Add room types
        RoomType standard = hotelService.getRoomType("standard");
        RoomType deluxe = hotelService.getRoomType("deluxe");
        
        // Add rooms to the hotel
        Room room101 = new Room("101", standard, hotel);
        Room room201 = new Room("201", deluxe, hotel);
        Room room202 = new Room("202", deluxe, hotel);
        
        hotel.setRooms(List.of(room101, room201, room202));
        hotelService.addHotel(hotel);

        // Create a user
        User user = new User("John Doe", "john.doe@example.com", "+1234567890", UserType.CUSTOMER);
        
        System.out.println("=== Hotel and Room Setup ===");
        System.out.println("Created hotel: " + hotel.getName() + " in " + hotel.getCity());
        System.out.println("Added rooms: " + room101.getRoomNumber() + " (" + room101.getRoomType().getName() + "), " +
                room201.getRoomNumber() + " (" + room201.getRoomType().getName() + "), " +
                room202.getRoomNumber() + " (" + room202.getRoomType().getName() + ")\n");

        // Search for available rooms
        LocalDateTime checkIn = LocalDateTime.now().plusDays(7);
        LocalDateTime checkOut = checkIn.plusDays(3);
        
        System.out.println("=== Searching for Available Rooms ===");
        System.out.println("Check-in: " + checkIn.toLocalDate());
        System.out.println("Check-out: " + checkOut.toLocalDate() + "\n");
        
        List<Room> availableRooms = hotelService.searchAvailableRooms(hotel.getId(), 
                java.sql.Timestamp.valueOf(checkIn), 
                java.sql.Timestamp.valueOf(checkOut));
                
        System.out.println("Available Rooms:");
        availableRooms.forEach(room -> System.out.println(
                "- Room " + room.getRoomNumber() + 
                " (" + room.getRoomType().getName() + ") - $" + 
                room.getRoomType().getBasePrice() + "/night"));
        
        // Make a reservation
        System.out.println("\n=== Making a Reservation ===");
        Room selectedRoom = availableRooms.get(0);
        System.out.println("Selected room: " + selectedRoom.getRoomNumber() + 
                         " (" + selectedRoom.getRoomType().getName() + ")");
        
        Reservation reservation = reservationService.createReservation(
                user, 
                hotel.getId(), 
                selectedRoom.getId(), 
                checkIn, 
                checkOut
        );
        
        System.out.println("Reservation created with ID: " + reservation.getId());
        System.out.println("Total amount: $" + reservation.getTotalAmount());
        
        // Process payment
        System.out.println("\n=== Processing Payment ===");
        PaymentMethod paymentMethod = new CreditCardPayment("4111111111111111", "John Doe", "12/25", "123");
        boolean paymentSuccess = reservationService.processPayment(reservation, paymentMethod);
        
        if (paymentSuccess) {
            System.out.println("Payment successful! Reservation confirmed.");
            System.out.println("Confirmation sent to: " + user.getEmail() + " and " + user.getPhoneNumber());
        } else {
            System.out.println("Payment failed. Please try again.");
        }
        
        // View user's reservations
        System.out.println("\n=== User's Reservations ===");
        List<Reservation> userReservations = reservationService.getUserReservations(user.getId());
        if (userReservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            userReservations.forEach(r -> 
                System.out.printf("Reservation %s: %s to %s - %s - $%.2f%n",
                    r.getId(),
                    r.getCheckInDate().toLocalDate(),
                    r.getCheckOutDate().toLocalDate(),
                    r.getRoom().getRoomNumber(),
                    r.getTotalAmount()
                )
            );
        }
        
        // Demonstrate cancellation
        System.out.println("\n=== Cancelling Reservation ===");
        if (reservationService.cancelReservation(reservation.getId(), user)) {
            System.out.println("Reservation cancelled successfully. Refund will be processed.");
        } else {
            System.out.println("Unable to cancel reservation. It may be too close to the check-in date.");
        }
        
        System.out.println("\n=== Hotel Reservation System Demo Complete ===");
    }
}