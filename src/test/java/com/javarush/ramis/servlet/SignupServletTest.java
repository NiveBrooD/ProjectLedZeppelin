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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SignupServletTest extends ContainerIT {
    UserService userService = new UserService(new UserRepository(new SessionCreator(ContainerIT.PROPERTIES)));

    @Test
    void doGet() throws ServletException, IOException {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getRequestDispatcher("/WEB-INF/signup.jsp")).thenReturn(rd);
        new SignupServlet().doGet(req,resp);

        Mockito.verify(rd).forward(req, resp);
    }

    @Test
    void doPost() throws IOException {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito.when(req.getParameter("login")).thenReturn("Test");
        Mockito.when(req.getParameter("password")).thenReturn("Test");
        Mockito.when(req.getParameter("role")).thenReturn("ADMIN");

        SignupServlet signupServlet = new SignupServlet();
        signupServlet.setUserService(userService);
        signupServlet.doPost(req,resp);

        UserTo user = userService.findByLoginAndPassword("Test", "Test");
        assertNotNull(user);
        Mockito.verify(resp).sendRedirect("/");
    }
}