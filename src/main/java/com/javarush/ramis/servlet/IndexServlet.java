package com.javarush.ramis.servlet;

import com.javarush.ramis.repository.QuestRepository;
import com.javarush.ramis.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            req.getSession(true);
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        } else {
            QuestService questService = new QuestService(QuestRepository.getInstance());
            session.setAttribute("quests", questService.getAll());
            req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
        }
    }
}
