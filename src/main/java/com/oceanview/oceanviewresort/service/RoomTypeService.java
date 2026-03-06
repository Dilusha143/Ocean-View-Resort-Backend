package com.oceanview.oceanviewresort.service;

import com.oceanview.oceanviewresort.model.Room;
import com.oceanview.oceanviewresort.model.RoomType;
import com.oceanview.oceanviewresort.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeService {

   
    public List<RoomType> getAllRoomTypes() throws SQLException {

        List<RoomType> list = new ArrayList<>();
        String sql = "SELECT roomTypeID, roomTypeName, pricePerNight, description FROM RoomType";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RoomType rt = new RoomType();
                rt.setRoomTypeID(rs.getInt("roomTypeID"));
                rt.setRoomTypeName(rs.getString("roomTypeName"));
                rt.setPricePerNight(rs.getDouble("pricePerNight"));
                rt.setDescription(rs.getString("description"));
                list.add(rt);
            }
        }

        return list;
    }

    
    public List<Room> getRoomsByRoomTypeID(int roomTypeID) throws SQLException {

        List<Room> list = new ArrayList<>();
        String sql = "SELECT roomID, roomNumber, roomTypeID, floorNumber, status " +
                     "FROM Room " +
                     "WHERE roomTypeID = ? AND status = 'AVAILABLE'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomTypeID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setRoomID(rs.getInt("roomID"));
                    room.setRoomNumber(rs.getString("roomNumber"));
                    room.setRoomTypeID(rs.getInt("roomTypeID"));
                    room.setFloorNumber(rs.getInt("floorNumber"));
                    room.setStatus(rs.getString("status"));
                    list.add(room);
                }
            }
        }

        return list;
    }
}