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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class SignupServletTest extends ContainerIT {
    UserService testUserService = new UserService(new UserRepository(SessionCreator.sessionCreatorForTests(ContainerIT.PROPERTIES)));

    @Test
    void doGet() throws ServletException, IOException {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getRequestDispatcher("/WEB-INF/signup.jsp")).thenReturn(rd);
        SignupServlet signupServlet = new SignupServlet(testUserService);

        signupServlet.doGet(req, resp);

        Mockito.verify(rd).forward(req, resp);
    }

    @Test
    void doPost() throws IOException {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getParameter("login")).thenReturn("Test");
        Mockito.when(req.getParameter("password")).thenReturn("Test");
        Mockito.when(req.getParameter("role")).thenReturn("ADMIN");

        SignupServlet signupServlet = new SignupServlet(testUserService);

        signupServlet.doPost(req,resp);

        UserTo user = testUserService.findByLoginAndPassword("Test", "Test");
        assertNotNull(user);
        Mockito.verify(resp).sendRedirect("/");
        log.info("User with id: {}, login: {}, password: {}", user.getId(), user.getLogin(), user.getPassword());
    }
}