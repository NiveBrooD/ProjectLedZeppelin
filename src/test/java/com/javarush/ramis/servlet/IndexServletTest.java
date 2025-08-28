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

class IndexServletTest {

    @Test
    @DisplayName("When session is null then dispatch to /login")
    void whenSessionIsNullThenDispatchToLogin() throws ServletException, IOException {
        IndexServlet indexServlet = new IndexServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getSession(false)).thenReturn(null);
        Mockito.when(req.getRequestDispatcher("/WEB-INF/login.jsp")).thenReturn(rd);
        indexServlet.doGet(req, resp);

        Mockito.verify(req).getSession(false);
        Mockito.verify(req).getRequestDispatcher("/WEB-INF/login.jsp");
        Mockito.verify(rd).forward(req, resp);
    }

    @Test
    @DisplayName("When session is not null then return to /index")
    void whenSessionIsNotNullThenDispatchToIndex() throws ServletException, IOException {
        IndexServlet indexServlet = new IndexServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session =  Mockito.mock(HttpSession.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getSession(false)).thenReturn(session);
        Mockito.when(req.getRequestDispatcher("/WEB-INF/index.jsp")).thenReturn(rd);
        indexServlet.doGet(req, resp);


        Mockito.verify(req).getSession(false);
        Mockito.verify(req).getRequestDispatcher("/WEB-INF/index.jsp");
        Mockito.verify(rd).forward(req, resp);

    }

}