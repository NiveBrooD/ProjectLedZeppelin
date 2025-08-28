package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.Role;
import com.javarush.ramis.entity.User;
import com.javarush.ramis.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;


class LoginServletTest {

    @Test
    void doGet() throws ServletException, IOException {
        LoginServlet loginServlet = new LoginServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getRequestDispatcher("WEB-INF/login.jsp")).thenReturn(rd);
        loginServlet.doGet(req, resp);

        Mockito.verify(rd).forward(req, resp);
    }

    @Test
    void doPost() throws IOException, ServletException {
        LoginServlet loginServlet = new LoginServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User(0L, "test", "test", Role.ADMIN);
        UserService userService = Mockito.mock(UserService.class);
        loginServlet.setUserService(userService);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(userService.getAll()).thenReturn(List.of(user));
        Mockito.when(req.getParameter("login")).thenReturn("test");
        Mockito.when(req.getParameter("password")).thenReturn("test");
        loginServlet.doPost(req, resp);

        Mockito.verify(resp).sendRedirect("/");
    }

    @Test
    @DisplayName("When user try to login with invalid data")
    void loginWithInvalidData() throws ServletException, IOException {
        LoginServlet loginServlet = new LoginServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);
        User user = new User(0L, "test", "test", Role.ADMIN);
        UserService userService = Mockito.mock(UserService.class);
        loginServlet.setUserService(userService);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getRequestDispatcher("/WEB-INF/login.jsp")).thenReturn(rd);
        Mockito.when(userService.getAll()).thenReturn(List.of(user));
        Mockito.when(req.getParameter("login")).thenReturn("wrongLogin");
        Mockito.when(req.getParameter("password")).thenReturn("test");
        loginServlet.doPost(req, resp);

        Mockito.verify(rd).forward(req, resp);
    }
}