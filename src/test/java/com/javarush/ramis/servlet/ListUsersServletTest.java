package com.javarush.ramis.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;


class ListUsersServletTest {

    @Test
    @DisplayName("When user eq null in attribute (not authenticated)")
    void whenUserEqNullInAttribute() throws ServletException, IOException {
        ListUsersServlet listUsersServlet = new ListUsersServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session =  Mockito.mock(HttpSession.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(null);
        listUsersServlet.doGet(req, resp);

        Mockito.verify(resp).sendRedirect("/login");

    }

    @Test
    void doGet() throws ServletException, IOException {
        ListUsersServlet listUsersServlet = new ListUsersServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session =  Mockito.mock(HttpSession.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(new Object());
        Mockito.when(req.getRequestDispatcher("/WEB-INF/list-users.jsp")).thenReturn(rd);
        listUsersServlet.doGet(req, resp);

        Mockito.verify(req).getRequestDispatcher("/WEB-INF/list-users.jsp");
        Mockito.verify(rd).forward(req, resp);
    }
}