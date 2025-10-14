package com.javarush.ramis.servlet;

import com.javarush.ramis.config.SessionCreator;
import com.javarush.ramis.dto.Role;
import com.javarush.ramis.dto.UserTo;
import com.javarush.ramis.repository.UserRepository;
import com.javarush.ramis.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Setter
@Getter
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final UserService userService;

    public SignupServlet() {
        this.userService = new UserService(new UserRepository(SessionCreator.getInstance()));
    }

    public SignupServlet(UserService userService) {
        this.userService = userService;
    }

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
        UserTo user = UserTo.builder().login(login).password(password).role(role).build();
        try {
            userService.create(user);
        } catch (RuntimeException e) {
            resp.sendRedirect("/signup");
            return;
        }
        user = userService.findByLoginAndPassword(login, password);
        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/");
    }
}
