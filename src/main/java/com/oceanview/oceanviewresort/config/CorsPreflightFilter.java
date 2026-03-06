package com.oceanview.oceanviewresort.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsPreflightFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest  req = (HttpServletRequest)  servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        // Add CORS headers to EVERY request
        res.setHeader("Access-Control-Allow-Origin",      "http://127.0.0.1:5500");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Methods",     "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers",     "Content-Type, Authorization, X-Requested-With");
        res.setHeader("Access-Control-Max-Age",           "3600");

        // If this is a preflight OPTIONS request — respond immediately with 200
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // All other requests pass through normally
        chain.doFilter(req, res);
    }

    @Override public void init(FilterConfig fc) {}
    @Override public void destroy() {}
}