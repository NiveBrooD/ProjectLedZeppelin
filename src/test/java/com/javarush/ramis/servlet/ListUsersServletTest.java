package com.javarush.ramis.servlet;

import com.javarush.ramis.ContainerIT;
import com.javarush.ramis.config.SessionCreator;
import com.javarush.ramis.repository.UserRepository;
import com.javarush.ramis.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ListUsersServletTest {
    UserService userService = new UserService(new UserRepository(SessionCreator.sessionCreatorForTests(ContainerIT.PROPERTIES)));

    @Test
    void doGet() throws ServletException, IOException {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getRequestDispatcher("/WEB-INF/list-users.jsp")).thenReturn(rd);
        ListUsersServlet listUsersServlet = new ListUsersServlet();
        listUsersServlet.setUserService(userService);

        Mockito.verify(rd).forward(req, resp);
    }
}