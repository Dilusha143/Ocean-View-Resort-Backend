package com.oceanview.oceanviewresort.model;

public class Room {

    private int    roomID;
    private String roomNumber;
    private int    roomTypeID;
    private int    floorNumber;
    private String status;       

    
    public Room() {}

    public Room(String roomNumber, int roomTypeID, int floorNumber, String status) {
        this.roomNumber  = roomNumber;
        this.roomTypeID  = roomTypeID;
        this.floorNumber = floorNumber;
        this.status      = status;
    }

    
    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public int getRoomTypeID() { return roomTypeID; }
    public void setRoomTypeID(int roomTypeID) { this.roomTypeID = roomTypeID; }

    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Room{" +
               "roomID=" + roomID +
               ", roomNumber='" + roomNumber + '\'' +
               ", roomTypeID=" + roomTypeID +
               ", floorNumber=" + floorNumber +
               ", status='" + status + '\'' +
               '}';
    }
}