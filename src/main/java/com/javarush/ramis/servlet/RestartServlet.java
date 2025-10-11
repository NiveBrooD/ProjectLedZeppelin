package com.javarush.ramis.servlet;

import com.javarush.ramis.dto.QuestTo;
import com.javarush.ramis.exception.QuestException;
import com.javarush.ramis.repository.QuestRepository;
import com.javarush.ramis.service.QuestService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

import java.io.IOException;

@Setter
@WebServlet("/restart")
public class RestartServlet extends HttpServlet {
    private QuestService questService = new QuestService(new QuestRepository());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String questId = req.getParameter("questId");
        try {
            QuestTo quest = questService.get(Long.parseLong(questId));
            questService.restartQuest(quest);
            resp.sendRedirect("/quest?id=" + questId);
        } catch (QuestException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
