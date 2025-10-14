package com.javarush.ramis.servlet;

import com.javarush.ramis.ContainerIT;
import com.javarush.ramis.config.SessionCreator;
import com.javarush.ramis.dto.UserTo;
import com.javarush.ramis.repository.UserRepository;
import com.javarush.ramis.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LoginServletTest extends ContainerIT {
    UserService userService = new UserService(new UserRepository(SessionCreator.sessionCreatorForTests(ContainerIT.PROPERTIES)));

    @Test
    void doPost() throws ServletException, IOException {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getParameter("login")).thenReturn("admin");
        Mockito.when(req.getParameter("password")).thenReturn("admin");
        LoginServlet loginServlet = new LoginServlet();
        loginServlet.setUserService(userService);
        loginServlet.doPost(req, resp);

        Mockito.verify(resp).sendRedirect("/");
    }

    @Test
    void doPostWithUnpersistedLoginAndPassword() throws ServletException, IOException {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getRequestDispatcher("/WEB-INF/login.jsp")).thenReturn(rd);
        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getParameter("login")).thenReturn("admin1");
        Mockito.when(req.getParameter("password")).thenReturn("admin");
        LoginServlet loginServlet = new LoginServlet();
        loginServlet.setUserService(userService);
        loginServlet.doPost(req, resp);

        Mockito.verify(rd).forward(req, resp);
    }
}