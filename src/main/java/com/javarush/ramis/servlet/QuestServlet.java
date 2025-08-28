package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.Answer;
import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.repository.AnswerRepository;
import com.javarush.ramis.repository.QuestRepository;
import com.javarush.ramis.service.AnswerService;
import com.javarush.ramis.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

import java.io.IOException;
import java.util.Optional;

@Setter
@WebServlet("/quest")
public class QuestServlet extends HttpServlet {
    private QuestService questService = new QuestService(QuestRepository.getInstance());
    private AnswerService answerService = new AnswerService(AnswerRepository.getInstance());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Optional<Quest> questOptional = questService.get(Long.parseLong(id));
        questOptional.ifPresent(quest -> req.getSession().setAttribute("quest", quest));
        questOptional.ifPresent(quest -> req.getSession().setAttribute("questId", quest.getId()));
        req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String answerId = req.getParameter("answerId");
        String questId = req.getParameter("questId");
//        System.out.println("questId: " + questId + " answerId: " + answerId);
        Optional<Quest> questOptional = questService.get(Long.parseLong(questId));
        Optional<Answer> answerOptional = answerService.get(Long.parseLong(answerId));
        if (questOptional.isPresent() && answerOptional.isPresent()) {
            Quest quest = questOptional.get();
            Answer answer = answerOptional.get();
            quest.setCurrentQuestion(answer.getNextQuestion());
            req.setAttribute("quest", quest);
        }
        req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
    }
}
