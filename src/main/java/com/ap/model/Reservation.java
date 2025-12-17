package com.ap.model;

import com.ap.constant.ReservationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {
    private String id;
    private User user;
    private Room room;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private ReservationStatus status;
    private Payment payment;
    private double totalAmount;

    public Reservation(User user, Room room, LocalDateTime checkInDate, LocalDateTime checkOutDate, double totalAmount) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = ReservationStatus.PENDING;
        this.totalAmount = totalAmount;
    }

    // Getters and setters
    public String getId() { return id; }
    public User getUser() { return user; }
    public Room getRoom() { return room; }
    public LocalDateTime getCheckInDate() { return checkInDate; }
    public LocalDateTime getCheckOutDate() { return checkOutDate; }
    public ReservationStatus getStatus() { return status; }
    public Payment getPayment() { return payment; }
    public double getTotalAmount() { return totalAmount; }

    public void setStatus(ReservationStatus status) { this.status = status; }
    public void setPayment(Payment payment) { this.payment = payment; }
}
