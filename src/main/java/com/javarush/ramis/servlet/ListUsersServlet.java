package com.javarush.ramis.servlet;

import com.javarush.ramis.config.SessionCreator;
import com.javarush.ramis.dto.UserTo;
import com.javarush.ramis.repository.UserRepository;
import com.javarush.ramis.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

import java.io.IOException;
import java.util.Collection;

@Setter
@WebServlet("/list-users")
public class ListUsersServlet extends HttpServlet {
    private UserService userService = new UserService(new UserRepository(SessionCreator.getInstance()));

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<UserTo> users = userService.getAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/list-users.jsp").forward(req, resp);
    }
}
