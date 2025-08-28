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


class ProfileServletTest {

    @Test
    void doGet() throws ServletException, IOException {
        ProfileServlet profileServlet = new ProfileServlet();
        UserService userService = Mockito.mock(UserService.class);
        profileServlet.setUserService(userService);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getRequestDispatcher("/WEB-INF/profile.jsp")).thenReturn(rd);
        profileServlet.doGet(req, resp);

        Mockito.verify(rd).forward(req, resp);
    }

    @Test
    @DisplayName("user post to /profile when no current User in session scope")
    void doPost() throws ServletException, IOException {
        ProfileServlet profileServlet = new ProfileServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(null);
        profileServlet.doPost(req, resp);

        Mockito.verify(resp).sendRedirect("/login");
    }

    @Test
    @DisplayName("doPost when everything ok")
    void doPost_whenEverythingOk() throws ServletException, IOException {
        ProfileServlet profileServlet = new ProfileServlet();
        UserService userService = Mockito.mock(UserService.class);
        profileServlet.setUserService(userService);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User(99L, "test", "test", Role.ADMIN);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        Mockito.when(req.getParameter("login")).thenReturn("newLogin");
        Mockito.when(req.getParameter("password")).thenReturn("newPassword");
        Mockito.when(req.getParameter("role")).thenReturn(Role.ADMIN.name());
        profileServlet.doPost(req, resp);

        Mockito.verify(userService).update(Mockito.any(User.class));
        Mockito.verify(session).setAttribute(Mockito.any(), Mockito.any(User.class));
        Mockito.verify(resp).sendRedirect("/profile");
    }

}