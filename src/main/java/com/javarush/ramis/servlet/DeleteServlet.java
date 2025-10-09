package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.User;
import com.javarush.ramis.repository.UserRepository;
import com.javarush.ramis.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

import java.io.IOException;

@Setter
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private UserService userService = new UserService(new UserRepository());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        userService.delete(user);
        req.getSession().invalidate();
        resp.sendRedirect("/login");
    }
}
