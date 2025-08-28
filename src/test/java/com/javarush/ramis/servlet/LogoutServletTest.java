package com.javarush.ramis.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;


class LogoutServletTest {

    @Test
    void doGet() throws ServletException, IOException {
        LogoutServlet logoutServlet = new LogoutServlet();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(request.getSession()).thenReturn(session);
        logoutServlet.doGet(request, response);

        Mockito.verify(response).sendRedirect("/login");
        Mockito.verify(session).invalidate();
    }
}