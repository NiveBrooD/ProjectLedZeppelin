package com.javarush.ramis.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter({
        "/create-answers", "/create-quest", "/list-users",
        "/logout", "/restart", "/quest", "/profile", "/delete"
})
public class AuthenticationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        if (user == null) {
            res.sendRedirect("/login");
        } else {
            chain.doFilter(req, res);
        }
    }
}
