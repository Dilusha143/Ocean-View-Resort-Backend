package com.oceanview.oceanviewresort.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext req,
                       ContainerResponseContext res) throws IOException {

        res.getHeaders().putSingle("Access-Control-Allow-Origin",      "http://127.0.0.1:5500");
        res.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        res.getHeaders().putSingle("Access-Control-Allow-Methods",     "GET, POST, PUT, DELETE, OPTIONS");
        res.getHeaders().putSingle("Access-Control-Allow-Headers",     "Content-Type, Authorization, X-Requested-With");
        res.getHeaders().putSingle("Access-Control-Max-Age",           "3600");
    }
}