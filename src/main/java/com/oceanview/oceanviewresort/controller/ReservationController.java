package com.oceanview.oceanviewresort.controller;

import com.oceanview.oceanviewresort.model.Guest;
import com.oceanview.oceanviewresort.model.Reservation;
import com.oceanview.oceanviewresort.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/reservations")                        
@Produces(MediaType.APPLICATION_JSON)  
@Consumes(MediaType.APPLICATION_JSON)      
public class ReservationController {

    private final ReservationService service = new ReservationService();
@Context
private HttpServletRequest httpRequest;
    // GET /api/reservations
    @GET
    public Response getAll() {
        try {
            List<Reservation> list = service.getAllReservations();
            return Response.ok(list).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Database error: " + e.getMessage()).build();
        }
    }

    // GET /api/reservations/1
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        try {
            Reservation r = service.getReservationById(id);
            if (r == null) return Response.status(404).entity("Not found").build();
            return Response.ok(r).build();
        } catch (SQLException e) {
            return Response.status(500).entity("Database error: " + e.getMessage()).build();
        }
    }
    
    @GET
@Path("/number/{resNumber}")
public Response getByReservationNumber(@PathParam("resNumber") String resNumber) {
    try {
        Reservation r = service.getReservationByNumber(resNumber);

        if (r == null) {
            return Response
                    .status(404)
                    .entity("{\"message\": \"Reservation " + resNumber + " not found.\"}")
                    .build();
        }

        return Response.ok(r).build();

    } catch (SQLException e) {
        return Response
                .status(500)
                .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}")
                .build();
    }
}

    @POST
    public Response createReservation(ReservationRequest request) {

     
        if (request.getGuestName()     == null || request.getGuestName().trim().isEmpty() ||
            request.getContactNumber() == null || request.getContactNumber().trim().isEmpty() ||
            request.getRoomID()        == 0    ||
            request.getCheckInDate()   == null || request.getCheckInDate().trim().isEmpty() ||
            request.getCheckOutDate()  == null || request.getCheckOutDate().trim().isEmpty()) {

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Required fields are missing.\"}")
                    .build();
        }

        
        if (request.getCheckOutDate().compareTo(request.getCheckInDate()) <= 0) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Check-out date must be after check-in date.\"}")
                    .build();
        }

        try {
            
            Guest guest = new Guest();
            guest.setGuestName(request.getGuestName());
            guest.setAddress(request.getAddress());
            guest.setContactNumber(request.getContactNumber());
            guest.setEmail(request.getEmail());

            
            Reservation reservation = new Reservation();
            reservation.setRoomID(request.getRoomID());
            reservation.setCheckInDate(request.getCheckInDate());
            reservation.setCheckOutDate(request.getCheckOutDate());
            reservation.setCreatedBy(request.getCreatedBy()); 
            reservation.setStatus(request.isCheckInNow() ? "CHECKED_IN" : "CONFIRMED");

            
            Reservation saved = service.createReservation(guest, reservation);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(saved)
                    .build();

        } catch (SQLException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    
    public static class ReservationRequest {

        private String  guestName;
        private String  address;
        private String  contactNumber;
        private String  email;
        private int     roomID;
        private String  checkInDate;
        private String  checkOutDate;
        private int     createdBy;     // staffID from localStorage
        private boolean checkInNow;

        public String  getGuestName()      { return guestName; }
        public void    setGuestName(String v)      { this.guestName = v; }

        public String  getAddress()        { return address; }
        public void    setAddress(String v)        { this.address = v; }

        public String  getContactNumber()  { return contactNumber; }
        public void    setContactNumber(String v)  { this.contactNumber = v; }

        public String  getEmail()          { return email; }
        public void    setEmail(String v)          { this.email = v; }

        public int     getRoomID()         { return roomID; }
        public void    setRoomID(int v)            { this.roomID = v; }

        public String  getCheckInDate()    { return checkInDate; }
        public void    setCheckInDate(String v)    { this.checkInDate = v; }

        public String  getCheckOutDate()   { return checkOutDate; }
        public void    setCheckOutDate(String v)   { this.checkOutDate = v; }

        public int     getCreatedBy()      { return createdBy; }
        public void    setCreatedBy(int v)         { this.createdBy = v; }

        public boolean isCheckInNow()      { return checkInNow; }
        public void    setCheckInNow(boolean v)    { this.checkInNow = v; }
    }

       
   
    
   @PUT
@Path("/{id}/status")
public Response updateStatus(@PathParam("id") int id, StatusRequest request) {

    // Validate request body
    if (request == null ||
        request.getStatus() == null ||
        request.getStatus().trim().isEmpty()) {

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"message\": \"Status value is required.\"}")
                .build();
    }

    try {
        boolean updated = service.updateStatus(id, request.getStatus().trim().toUpperCase());

        if (!updated) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Reservation ID " + id + " not found.\"}")
                    .build();
        }

        return Response
                .ok("{\"message\": \"Status updated to " + request.getStatus().toUpperCase() + "\"}")
                .build();

    } catch (IllegalArgumentException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"message\": \"" + e.getMessage() + "\"}")
                .build();

    } catch (SQLException e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}")
                .build();
    }
}

public static class StatusRequest {
    private String status;

    public String getStatus()         { return status; }
    public void   setStatus(String s) { this.status = s; }
}
}