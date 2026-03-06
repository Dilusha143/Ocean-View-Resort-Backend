package com.oceanview.oceanviewresort.service;

import com.oceanview.oceanviewresort.model.Guest;
import com.oceanview.oceanviewresort.model.Reservation;
import com.oceanview.oceanviewresort.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    
    public List<Reservation> getAllReservations() throws SQLException {
    List<Reservation> list = new ArrayList<>();
    String sql = "SELECT r.reservationID, r.reservationNumber, g.guestName, g.contactNumber, " +
                 "r.roomID, rm.roomNumber, rt.roomTypeName, rt.roomTypeID, rt.pricePerNight, " +
                 "r.checkInDate, r.checkOutDate, r.status, r.createdBy, r.createdAt " +
                 "FROM Reservation r " +
                 "LEFT JOIN Guest    g  ON r.guestID     = g.guestID " +
                 "LEFT JOIN Room     rm ON r.roomID       = rm.roomID " +
                 "LEFT JOIN RoomType rt ON rm.roomTypeID  = rt.roomTypeID " +
                 "ORDER BY r.reservationID DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Reservation r = new Reservation();
            r.setReservationID(rs.getInt("reservationID"));
            r.setReservationNumber(rs.getString("reservationNumber"));
            r.setGuestName(rs.getString("guestName"));
            r.setContactNumber(rs.getString("contactNumber"));
            r.setRoomID(rs.getInt("roomID"));
            r.setRoomNumber(rs.getString("roomNumber"));       // ← added
            r.setRoomType(rs.getString("roomTypeName"));       // ← added
            r.setRoomTypeID(rs.getInt("roomTypeID"));          // ← added
            r.setPricePerNight(rs.getDouble("pricePerNight")); // ← added
            r.setCheckInDate(rs.getString("checkInDate"));
            r.setCheckOutDate(rs.getString("checkOutDate"));
            r.setStatus(rs.getString("status"));
            r.setCreatedBy(rs.getInt("createdBy"));
            r.setCreatedAt(rs.getString("createdAt"));
            list.add(r);
        }
    }
    return list;
}

    
    public Reservation getReservationById(int id) throws SQLException {

    String sql = "SELECT r.reservationID, r.reservationNumber, r.guestID, " +
                 "r.roomID, r.checkInDate, r.checkOutDate, r.status, r.createdBy, " +
                 "g.guestName, g.contactNumber, g.email, g.address, " +
                 "rm.roomNumber, rt.roomTypeName, rt.roomTypeID, rt.pricePerNight " +
                 "FROM Reservation r " +
                 "LEFT JOIN Guest    g  ON r.guestID     = g.guestID " +
                 "LEFT JOIN Room     rm ON r.roomID       = rm.roomID " +
                 "LEFT JOIN RoomType rt ON rm.roomTypeID  = rt.roomTypeID " +
                 "WHERE r.reservationID = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Reservation r = new Reservation();

                
                r.setReservationID(rs.getInt("reservationID"));
                r.setReservationNumber(rs.getString("reservationNumber"));
                r.setGuestID(rs.getInt("guestID"));
                r.setRoomID(rs.getInt("roomID"));
                r.setCheckInDate(rs.getString("checkInDate"));
                r.setCheckOutDate(rs.getString("checkOutDate"));
                r.setStatus(rs.getString("status"));
                r.setCreatedBy(rs.getInt("createdBy"));

                
                r.setGuestName(rs.getString("guestName"));
                r.setContactNumber(rs.getString("contactNumber"));
                r.setEmail(rs.getString("email"));
                r.setAddress(rs.getString("address"));

                
                r.setRoomNumber(rs.getString("roomNumber"));
                r.setRoomType(rs.getString("roomTypeName"));
                r.setRoomTypeID(rs.getInt("roomTypeID"));
                r.setPricePerNight(rs.getDouble("pricePerNight"));

                return r;
            }
        }
    }
    return null;
}

    
    public Reservation getReservationByNumber(String resNumber) throws SQLException {

    String sql = "SELECT r.reservationID, r.reservationNumber, r.guestID, " +
                 "r.roomID, r.checkInDate, r.checkOutDate, r.status, r.createdBy, " +
                 "g.guestName, g.contactNumber, g.email, g.address, " +
                 "rm.roomNumber, rt.roomTypeName, rt.roomTypeID, rt.pricePerNight " +
                 "FROM Reservation r " +
                 "LEFT JOIN Guest    g  ON r.guestID      = g.guestID " +
                 "LEFT JOIN Room     rm ON r.roomID        = rm.roomID " +
                 "LEFT JOIN RoomType rt ON rm.roomTypeID   = rt.roomTypeID " +
                 "WHERE r.reservationNumber = ?";          // ← was reservationID = ?

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, resNumber);                        

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Reservation r = new Reservation();

                
                r.setReservationID(rs.getInt("reservationID"));
                r.setReservationNumber(rs.getString("reservationNumber"));
                r.setGuestID(rs.getInt("guestID"));
                r.setRoomID(rs.getInt("roomID"));
                r.setCheckInDate(rs.getString("checkInDate"));
                r.setCheckOutDate(rs.getString("checkOutDate"));
                r.setStatus(rs.getString("status"));
                r.setCreatedBy(rs.getInt("createdBy"));

                
                r.setGuestName(rs.getString("guestName"));
                r.setContactNumber(rs.getString("contactNumber"));
                r.setEmail(rs.getString("email"));
                r.setAddress(rs.getString("address"));

                
                r.setRoomNumber(rs.getString("roomNumber"));
                r.setRoomType(rs.getString("roomTypeName"));
                r.setRoomTypeID(rs.getInt("roomTypeID"));
                r.setPricePerNight(rs.getDouble("pricePerNight"));

                return r;
            }
        }
    }
    return null;
}

    public Reservation createReservation(Guest guest, Reservation reservation) throws SQLException {
    Connection conn = null;

    try {
        conn = DBConnection.getConnection();
        conn.setAutoCommit(false); 

        
        int guestID = insertGuest(conn, guest);

        
        int reservationID = insertReservation(conn, reservation, guestID);

        
        String reservationNumber = generateReservationNumber(reservationID);
        reservation.setReservationNumber(reservationNumber);

        
        updateReservationNumber(conn, reservationID, reservationNumber);

        
        updateRoomStatus(conn, reservation.getRoomID(), "OCCUPIED");

        conn.commit(); 

        
        reservation.setReservationID(reservationID);
        reservation.setGuestID(guestID);
        return reservation;

    } catch (SQLException e) {
        if (conn != null) conn.rollback();
        throw e;
    } finally {
        if (conn != null) {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}


    
    private int insertGuest(Connection conn, Guest guest) throws SQLException {

        String sql = "INSERT INTO Guest (guestName, address, contactNumber, email) " +
                     "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, guest.getGuestName());
            ps.setString(2, guest.getAddress());
            ps.setString(3, guest.getContactNumber());
            ps.setString(4, guest.getEmail());
            ps.executeUpdate();

            
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        throw new SQLException("Failed to insert guest — no ID returned.");
    }

    private int insertReservation(Connection conn, Reservation reservation, int guestID) throws SQLException {
    String sql = "INSERT INTO Reservation (guestID, roomID, checkInDate, checkOutDate, status, createdBy) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, guestID);
        ps.setInt(2, reservation.getRoomID());
        ps.setString(3, reservation.getCheckInDate());
        ps.setString(4, reservation.getCheckOutDate());
        ps.setString(5, reservation.getStatus());
        ps.setInt(6, reservation.getCreatedBy());

        ps.executeUpdate();

        try (ResultSet keys = ps.getGeneratedKeys()) {
            if (keys.next()) {
                return keys.getInt(1); // reservationID
            }
        }
    }

    throw new SQLException("Failed to insert reservation — no ID returned.");
}
    
    private String generateReservationNumber(int reservationID) {
    return "R-" + String.format("%05d", reservationID); 
}
    
    private void updateReservationNumber(Connection conn, int reservationID, String reservationNumber) throws SQLException {
    String sql = "UPDATE Reservation SET reservationNumber = ? WHERE reservationID = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, reservationNumber);
        ps.setInt(2, reservationID);
        ps.executeUpdate();
    }
}
    
    private void updateRoomStatus(Connection conn, int roomID,
                                  String status) throws SQLException {

        String sql = "UPDATE Room SET status = ? WHERE roomID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, roomID); 
            ps.executeUpdate();
        }
    }
    
    
    
    public boolean updateStatus(int reservationID, String newStatus) throws SQLException {

    
    if (!newStatus.equals("CONFIRMED")   &&
        !newStatus.equals("CHECKED_IN")  &&
        !newStatus.equals("CHECKED_OUT") &&
        !newStatus.equals("CANCELLED")) {

        throw new IllegalArgumentException("Invalid status: " + newStatus +
                ". Allowed: CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED");
    }

    String sql = "UPDATE Reservation SET status = ? WHERE reservationID = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, newStatus);
        ps.setInt(2, reservationID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; 
    }
}
}