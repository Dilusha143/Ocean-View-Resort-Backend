package com.oceanview.oceanviewresort.model;

public class Guest {

    private int    guestID;
    private String guestName;
    private String address;
    private String contactNumber;
    private String email;

    
    public Guest() {}

    public Guest(String guestName, String address, String contactNumber, String email) {
        this.guestName     = guestName;
        this.address       = address;
        this.contactNumber = contactNumber;
        this.email         = email;
    }

    
    public int getGuestID() { return guestID; }
    public void setGuestID(int guestID) { this.guestID = guestID; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Guest{" +
               "guestID=" + guestID +
               ", guestName='" + guestName + '\'' +
               ", address='" + address + '\'' +
               ", contactNumber='" + contactNumber + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}