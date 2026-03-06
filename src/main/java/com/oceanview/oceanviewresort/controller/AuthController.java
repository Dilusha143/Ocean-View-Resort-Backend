package com.oceanview.oceanviewresort.controller;

import com.oceanview.oceanviewresort.model.Staff;
import com.oceanview.oceanviewresort.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    private final AuthService service = new AuthService();

   
    @Context
    private HttpServletRequest httpRequest;

    
    @POST
@Path("/login")
public Response login(LoginRequest request) {

    if (request.getUserName()      == null || request.getUserName().trim().isEmpty() ||
        request.getStaffPassword() == null || request.getStaffPassword().trim().isEmpty()) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"message\": \"Username and password are required.\"}")
                .build();
    }

    try {
        Staff staff = service.login(request.getUserName(), request.getStaffPassword());

        if (staff == null) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Invalid username or password.\"}")
                    .build();
        }

        
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("staffID",   staff.getStaffID());
        session.setAttribute("staffName", staff.getFullName());
        session.setAttribute("role",      staff.getRole());
        session.setMaxInactiveInterval(30 * 60);

        return Response
                .status(Response.Status.OK)
                .entity(staff)
                .build();

    } catch (SQLException e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}")
                .build();
    }
}

    @POST
    @Path("/logout")
    public Response logout() {

        HttpSession session = httpRequest.getSession(false); // false = don't create new session

        if (session != null) {
            session.invalidate(); // Destroy the session
        }

        return Response
                .ok("{\"message\": \"Logged out successfully.\"}")
                .build();
    }

    @GET
    @Path("/session")
    public Response getSession() {

        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("staffID") == null) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"No active session. Please log in.\"}")
                    .build();
        }

        // Build response from session attributes
        int    staffID   = (int)    session.getAttribute("staffID");
        String staffName = (String) session.getAttribute("staffName");
        String role      = (String) session.getAttribute("role");

        String json = "{" +
                      "\"staffID\": "   + staffID         + ", " +
                      "\"staffName\": \"" + staffName     + "\", " +
                      "\"role\": \""      + role          + "\"" +
                      "}";

        return Response.ok(json).build();
    }

    public static class LoginRequest {

        private String userName;
        private String staffPassword;

        public String getUserName()      { return userName; }
        public void setUserName(String userName) { this.userName = userName; }

        public String getStaffPassword() { return staffPassword; }
        public void setStaffPassword(String staffPassword) { this.staffPassword = staffPassword; }
    }
}