package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.repository.QuestRepository;
import com.javarush.ramis.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/restart")
public class RestartServlet extends HttpServlet {
    QuestService questService = new QuestService(QuestRepository.getInstance());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String questId = req.getSession().getAttribute("questId").toString();
        Optional<Quest> questOptional = questService.get(Long.parseLong(questId));
        questOptional.ifPresent(Quest::restartQuest);
        resp.sendRedirect("/quest?id=" + questId);
    }
}
