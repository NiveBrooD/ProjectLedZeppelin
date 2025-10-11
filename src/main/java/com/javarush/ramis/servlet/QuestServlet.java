package com.javarush.ramis.servlet;

import com.javarush.ramis.dto.AnswerTo;
import com.javarush.ramis.dto.QuestTo;
import com.javarush.ramis.dto.QuestionTo;
import com.javarush.ramis.exception.QuestException;
import com.javarush.ramis.repository.AnswerRepository;
import com.javarush.ramis.repository.QuestRepository;
import com.javarush.ramis.repository.QuestionRepository;
import com.javarush.ramis.service.AnswerService;
import com.javarush.ramis.service.QuestService;
import com.javarush.ramis.service.QuestionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@Setter
@WebServlet("/quest")
public class QuestServlet extends HttpServlet {
    private QuestService questService = new QuestService(new QuestRepository());
    private AnswerService answerService = new AnswerService(new AnswerRepository());
    private QuestionService questionService = new QuestionService(new QuestionRepository());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            QuestTo quest = questService.get(Long.parseLong(id));
            updateSessionAttributes(req, quest);
            req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
        } catch (QuestException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            log.error("Cannot find quest by ID={}", id);
            resp.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String answerId = req.getParameter("answerId");
        String questId = req.getParameter("questId");
        log.info("questId={}, answerId={}", questId, answerId);

        QuestTo quest = questService.get(Long.parseLong(questId));
        AnswerTo answer = answerService.get(Long.parseLong(answerId));

        quest.setCurrentQuestion(questionService.get(answer.getNextQuestionId()));
        questService.update(quest);
        updateSessionAttributes(req, quest);

        req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
    }

    private void updateSessionAttributes(HttpServletRequest req, QuestTo quest) {
        QuestionTo currentQuestion = quest.getCurrentQuestion();
        List<AnswerTo> answersForQuestion = answerService.getAnswersForQuestion(currentQuestion);

        req.setAttribute("quest", quest);
        req.setAttribute("questId", quest.getId());
        req.setAttribute("question", currentQuestion);
        req.setAttribute("answers", answersForQuestion);
    }
}
