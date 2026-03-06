package com.oceanview.oceanviewresort.config;

import com.oceanview.oceanviewresort.controller.AuthController;
import com.oceanview.oceanviewresort.controller.BillingController;
import com.oceanview.oceanviewresort.controller.ReservationController;
import com.oceanview.oceanviewresort.controller.RoomTypeController;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class AppConfig extends Application {

    @OPTIONS
    @Path("{path:.*}")
    public Response handleOptions() {
        return Response.ok()
                .header("Access-Control-Allow-Origin",      "http://127.0.0.1:5500")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods",     "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers",     "Content-Type, Authorization, X-Requested-With")
                .header("Access-Control-Max-Age",           "3600")
                .build();
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        classes.add(CORSFilter.class);
        classes.add(AuthController.class);
        classes.add(ReservationController.class);
        classes.add(RoomTypeController.class);
        classes.add(BillingController.class);

        return classes;
    }
}