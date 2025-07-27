package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.User;
import com.javarush.ramis.repository.UserRepository;
import com.javarush.ramis.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/list-users")
public class ListUsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService(UserRepository.getInstance());
        Collection<User> users = userService.getAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/list-users.jsp").forward(req, resp);
    }
}
