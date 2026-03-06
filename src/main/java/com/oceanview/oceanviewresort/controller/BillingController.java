package com.oceanview.oceanviewresort.controller;

import com.oceanview.oceanviewresort.model.Bill;
import com.oceanview.oceanviewresort.service.BillingService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.Map;

@Path("/billing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillingController {

    private final BillingService service = new BillingService();

    @POST
    public Response createBill(BillRequest request) {

        if (request == null || request.getReservationID() == 0) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"reservationID is required.\"}")
                    .build();
        }

        try {
            Bill bill = service.createBill(request.getReservationID());
            return Response.status(Response.Status.CREATED).entity(bill).build();

        } catch (SQLException e) {

            if (e.getErrorCode() == 1062 ||
                e.getMessage().toLowerCase().contains("duplicate")) {
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity("{\"message\": \"A bill already exists for this reservation.\"}")
                        .build();
            }

            if (e.getMessage().contains("not found")) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"" + e.getMessage() + "\"}")
                        .build();
            }

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    public static class BillRequest {
        private int reservationID;
        public int  getReservationID()       { return reservationID; }
        public void setReservationID(int id) { this.reservationID = id; }
    }
    
    @GET
@Path("/subtotal-sum")
public Response getTotalSubTotal() {

    try {
        double total = service.getTotalSubTotal();
        return Response.ok(Map.of("totalSubTotal", total)).build();

    } catch (SQLException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("message", "Database error"))
                .build();
    }
}
}