package com.oceanview.oceanviewresort.service;

import com.oceanview.oceanviewresort.model.Bill;
import com.oceanview.oceanviewresort.util.DBConnection;

import java.sql.*;

public class BillingService {

   
    public Bill createBill(int reservationID) throws SQLException {

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); 

            
            String fetchSQL =
    "SELECT r.checkInDate, r.checkOutDate, r.roomID, rt.pricePerNight " +
    "FROM Reservation r " +
    "LEFT JOIN Room rm ON r.roomID = rm.roomID " +
    "LEFT JOIN RoomType rt ON rm.roomTypeID = rt.roomTypeID " +
    "WHERE r.reservationID = ?";

            int    numberOfNights = 0;
            double pricePerNight  = 0;
            int roomID = 0;

            try (PreparedStatement ps = conn.prepareStatement(fetchSQL)) {
                ps.setInt(1, reservationID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        numberOfNights = calculateNights(
                            rs.getString("checkInDate"),
                            rs.getString("checkOutDate")
                        );
                        pricePerNight = rs.getDouble("pricePerNight");
                        roomID = rs.getInt("roomID");
                    } else {
                        throw new SQLException("Reservation ID " + reservationID + " not found.");
                    }
                }
            }

            
            double taxRate     = 10.00;
            double subTotal    = numberOfNights * pricePerNight;
            double taxAmount   = subTotal * (taxRate / 100);
            double totalAmount = subTotal + taxAmount;

            
            String insertSQL =
                "INSERT INTO Bill " +
                "(reservationID, numberOfNights, pricePerNight, subTotal, " +
                " taxRate, taxAmount, totalAmount, paymentStatus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 'PAID')";

            int billID = 0;
            try (PreparedStatement ps = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1,    reservationID);
                ps.setInt(2,    numberOfNights);
                ps.setDouble(3, pricePerNight);
                ps.setDouble(4, subTotal);
                ps.setDouble(5, taxRate);
                ps.setDouble(6, taxAmount);
                ps.setDouble(7, totalAmount);
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) billID = keys.getInt(1);
                }
            }

            
            String updateSQL =
                "UPDATE Reservation SET status = 'CHECKED_OUT' WHERE reservationID = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSQL)) {
                ps.setInt(1, reservationID);
                ps.executeUpdate();
            }
            
           String updateRoomStatus =
    "UPDATE Room SET status = 'AVAILABLE' WHERE roomID = ?";

try (PreparedStatement ps = conn.prepareStatement(updateRoomStatus)) {
    ps.setInt(1, roomID); 
    ps.executeUpdate();
}

            conn.commit(); 

            
            Bill bill = new Bill();
            bill.setBillID(billID);
            bill.setReservationID(reservationID);
            bill.setNumberOfNights(numberOfNights);
            bill.setPricePerNight(pricePerNight);
            bill.setSubTotal(subTotal);
            bill.setTaxRate(taxRate);
            bill.setTaxAmount(taxAmount);
            bill.setTotalAmount(totalAmount);
            bill.setPaymentStatus("PAID");
            return bill;

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

    
    private int calculateNights(String checkIn, String checkOut) {
        try {
            java.time.LocalDate ci = java.time.LocalDate.parse(checkIn);
            java.time.LocalDate co = java.time.LocalDate.parse(checkOut);
            return (int) java.time.temporal.ChronoUnit.DAYS.between(ci, co);
        } catch (Exception e) {
            return 0;
        }
    }
    
    public double getTotalSubTotal() throws SQLException {

    String sql = "SELECT IFNULL(SUM(subTotal), 0) AS total FROM Bill WHERE paymentStatus = 'PAID'";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            return rs.getDouble("total");
        }
    }

    return 0;
}
}