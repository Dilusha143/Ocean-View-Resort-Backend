package com.oceanview.oceanviewresort.model;

public class RoomType {

    private int    roomTypeID;
    private String roomTypeName;
    private double pricePerNight;
    private String description;

    
    public RoomType() {}

    public RoomType(String roomTypeName, double pricePerNight, String description) {
        this.roomTypeName  = roomTypeName;
        this.pricePerNight = pricePerNight;
        this.description   = description;
    }

    
    public int getRoomTypeID() { return roomTypeID; }
    public void setRoomTypeID(int roomTypeID) { this.roomTypeID = roomTypeID; }

    public String getRoomTypeName() { return roomTypeName; }
    public void setRoomTypeName(String roomTypeName) { this.roomTypeName = roomTypeName; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "RoomType{" +
               "roomTypeID=" + roomTypeID +
               ", roomTypeName='" + roomTypeName + '\'' +
               ", pricePerNight=" + pricePerNight +
               ", description='" + description + '\'' +
               '}';
    }
}