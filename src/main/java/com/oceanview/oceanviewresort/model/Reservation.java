package com.oceanview.oceanviewresort.model;

public class Reservation {

    
    private int    reservationID;
    private String reservationNumber;
    private int    guestID;
    private int    roomID;
    private String checkInDate;
    private String checkOutDate;
    private String status;        
    private int    createdBy;
    private String createdAt;

  
    private String guestName;
    private String contactNumber;
    private String email;
    private String address;

    
    private String roomNumber;
    private String roomType;       // roomTypeName
    private int    roomTypeID;
    private double pricePerNight;

   
    public Reservation() {}

    
    public int getReservationID() { return reservationID; }
    public void setReservationID(int reservationID) { this.reservationID = reservationID; }

    public String getReservationNumber() { return reservationNumber; }
    public void setReservationNumber(String reservationNumber) { this.reservationNumber = reservationNumber; }

    public int getGuestID() { return guestID; }
    public void setGuestID(int guestID) { this.guestID = guestID; }

    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }

    public String getCheckInDate() { return checkInDate; }
    public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }

    public String getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    
    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getRoomType() { return roomType; }       // roomTypeName
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public int getRoomTypeID() { return roomTypeID; }
    public void setRoomTypeID(int roomTypeID) { this.roomTypeID = roomTypeID; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    @Override
    public String toString() {
        return "Reservation{" +
               "reservationID="     + reservationID     +
               ", reservationNumber='" + reservationNumber + '\'' +
               ", guestName='"      + guestName         + '\'' +
               ", contactNumber='"  + contactNumber      + '\'' +
               ", roomNumber='"     + roomNumber         + '\'' +
               ", roomType='"       + roomType           + '\'' +
               ", checkInDate='"    + checkInDate        + '\'' +
               ", checkOutDate='"   + checkOutDate       + '\'' +
               ", status='"         + status             + '\'' +
               '}';
    }
}