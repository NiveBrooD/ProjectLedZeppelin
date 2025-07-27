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
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService(UserRepository.getInstance());
        Collection<User> all = userService.getAll();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> first = all.stream().filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password)).findFirst();
        if (first.isPresent()) {
            req.getSession().setAttribute("user", first.get().getLogin());
            resp.sendRedirect("/");
        } else {
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req,resp);
        }
    }
}
