package com.oceanview.oceanviewresort.controller;

import com.oceanview.oceanviewresort.model.Room;
import com.oceanview.oceanviewresort.model.RoomType;
import com.oceanview.oceanviewresort.service.RoomTypeService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/roomtypes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomTypeController {

    private final RoomTypeService service = new RoomTypeService();

    @GET
    public Response getAllRoomTypes() {
        try {
            List<RoomType> roomTypes = service.getAllRoomTypes();

            if (roomTypes.isEmpty()) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No room types found\"}")
                        .build();
            }

            return Response.ok(roomTypes).build();

        } catch (SQLException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/{roomTypeID}/rooms")
    public Response getRoomsByRoomType(@PathParam("roomTypeID") int roomTypeID) {
        try {
            List<Room> rooms = service.getRoomsByRoomTypeID(roomTypeID);

            if (rooms.isEmpty()) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"No available rooms for this room type\"}")
                        .build();
            }

            return Response.ok(rooms).build();

        } catch (SQLException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}