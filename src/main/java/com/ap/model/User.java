package com.ap.model;

import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private UserType userType;

    public User(String name, String email, String phoneNumber, UserType userType) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public UserType getUserType() { return userType; }
    
    // For repository use only
    protected User() {
        // Default constructor for repository
    }

    public enum UserType {
        CUSTOMER, ADMIN, HOTEL_STAFF
    }
}
