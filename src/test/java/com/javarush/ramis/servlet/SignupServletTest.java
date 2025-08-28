package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.User;
import com.javarush.ramis.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class SignupServletTest {

    @Test
    void doGet() throws ServletException, IOException {
        SignupServlet profileServlet = new SignupServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getRequestDispatcher("/WEB-INF/signup.jsp")).thenReturn(rd);
        profileServlet.doGet(req, resp);

        Mockito.verify(rd).forward(req,resp);
    }

    @Test
    void doPost() throws ServletException, IOException {
        SignupServlet profileServlet = new SignupServlet();
        UserService  userService = Mockito.mock(UserService.class);
        profileServlet.setUserService(userService);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getParameter("login")).thenReturn("admin");
        Mockito.when(req.getParameter("password")).thenReturn("admin");
        Mockito.when(req.getParameter("role")).thenReturn("ADMIN");
        profileServlet.doPost(req, resp);

        Mockito.verify(userService).create(Mockito.any(User.class));
        Mockito.verify(session).setAttribute(Mockito.anyString(),Mockito.any(User.class));
        Mockito.verify(resp).sendRedirect("/");

    }
}