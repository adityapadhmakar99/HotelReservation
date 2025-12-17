# Hotel Reservation System

A comprehensive hotel reservation system that allows users to book rooms, make payments, and receive notifications. Built with Java and following clean architecture principles.

## 📋 Requirements

1. **User Management**
   - User registration and authentication
   - User roles (Admin, Guest)
   - Profile management

2. **Hotel & Room Management**
   - Add/update/delete hotels
   - Manage room types and availability
   - Set room rates and amenities

3. **Reservation System**
   - Search for available rooms
   - Make reservations
   - Cancel/Modify bookings
   - Check-in/Check-out functionality

4. **Payment Processing**
   - Multiple payment methods (Credit Card, Debit Card, UPI, etc.)
   - Payment status tracking
   - Transaction history

5. **Notification System**
   - Email notifications
   - SMS alerts
   - Push notifications

## 🏗️ Architecture & Design Patterns

### 1. Repository Pattern
- **Purpose**: Abstract data access layer
- **Implementation**:
  - `UserRepository`, `HotelRepository` interfaces
  - `InMemoryUserRepository`, `InMemoryHotelRepository` implementations
- **Benefits**:
  - Decouples business logic from data access
  - Makes testing easier with in-memory implementations

### 2. Factory Pattern
- **Purpose**: Create objects without specifying the exact class
- **Implementation**:
  - `NotificationFactory` creates different types of notifications
  - `PaymentMethodFactory` (if implemented) for different payment methods
- **Benefits**:
  - Encapsulates object creation logic
  - Easy to add new notification types

### 3. Strategy Pattern
- **Purpose**: Define a family of algorithms, encapsulate each one, and make them interchangeable
- **Implementation**:
  - `PaymentMethod` interface with different payment strategies
  - `CreditCardPayment`, `UPIPayment`, etc. implementations
- **Benefits**:
  - Easy to add new payment methods
  - Payment logic is encapsulated and interchangeable

### 4. Builder Pattern
- **Purpose**: Construct complex objects step by step
- **Implementation**:
  - `PaymentBuilder` for creating Payment objects
  - Fluent API for better readability
- **Benefits**:
  - More readable and maintainable code
  - Immutable objects

### 5. Observer Pattern
- **Purpose**: Define a one-to-many dependency between objects
- **Implementation**:
  - Notification system observes reservation status changes
  - Event-based communication between components
- **Benefits**:
  - Loose coupling between components
  - Easy to add new observers

## 🚀 Flow

### 1. User Registration & Authentication
```
User → UserService → UserRepository
```

### 2. Room Search & Booking
```
User → ReservationService → HotelRepository → RoomRepository → PaymentService → NotificationService
```

### 3. Payment Processing
```
User → PaymentContext → PaymentMethod (Strategy) → PaymentProcessor → NotificationService
```

### 4. Notification Flow
```
Event (e.g., booking confirmation) → NotificationService → NotificationFactory → [Email/SMS/Push]Notification
```

## 🛠️ Project Structure

```
src/
├── main/
│   ├── java/com/ap/
│   │   ├── constant/          # Enums and constants
│   │   │   ├── NotificationType.java
│   │   │   ├── PaymentStatus.java
│   │   │   ├── PaymentType.java
│   │   │   ├── RoomAmenity.java
│   │   │   └── HotelAmenity.java
│   │   ├── model/             # Domain models
│   │   ├── repository/        # Data access layer
│   │   ├── service/           # Business logic
│   │   ├── payment/           # Payment processing
│   │   └── notification/      # Notification system
│   └── resources/             # Configuration files
└── test/                      # Test files
```