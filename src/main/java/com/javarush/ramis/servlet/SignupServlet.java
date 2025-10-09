package com.javarush.ramis.servlet;

import com.javarush.ramis.dto.Role;
import com.javarush.ramis.entity.User;
import com.javarush.ramis.repository.UserRepository;
import com.javarush.ramis.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

import java.io.IOException;

@Setter
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private UserService userService = new UserService(new UserRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String roleStr = req.getParameter("role");
        Role role = Role.valueOf(roleStr);
        User user = new User(login,password,role);
        userService.create(user);
        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/");
    }
}
