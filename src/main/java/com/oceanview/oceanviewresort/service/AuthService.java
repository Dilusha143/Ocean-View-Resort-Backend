package com.oceanview.oceanviewresort.service;

import com.oceanview.oceanviewresort.model.Staff;
import com.oceanview.oceanviewresort.util.DBConnection;

import java.sql.*;

public class AuthService {

    
    public Staff login(String userName, String staffPassword) throws SQLException {

        String sql = "SELECT staffID, fullName, userName, role, isActive " +
                     "FROM Staff " +
                     "WHERE userName = ? AND staffPassword = ? AND isActive = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userName);
            ps.setString(2, staffPassword);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    
                    Staff staff = new Staff();
                    staff.setStaffID(rs.getInt("staffID"));
                    staff.setFullName(rs.getString("fullName"));
                    staff.setUserName(rs.getString("userName"));
                    staff.setRole(rs.getString("role"));
                    staff.setActive(rs.getBoolean("isActive"));
                    return staff;
                }
            }
        }

        return null; 
    }
}