package com.javarush.ramis.servlet;

import com.javarush.ramis.dto.*;
import com.javarush.ramis.exception.QuestException;
import com.javarush.ramis.mapping.Dto;
import com.javarush.ramis.repository.AnswerRepository;
import com.javarush.ramis.repository.QuestRepository;
import com.javarush.ramis.repository.QuestionRepository;
import com.javarush.ramis.repository.UserQuestRepository;
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
    private QuestService questService = new QuestService(new QuestRepository(), new UserQuestRepository());
    private AnswerService answerService = new AnswerService(new AnswerRepository());
    private QuestionService questionService = new QuestionService(new QuestionRepository());
    private Dto dto = Dto.MAPPER;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UserTo user = (UserTo) req.getSession().getAttribute("user");
        try {
            QuestTo quest = questService.getQuest(Long.parseLong(id));
            UserQuestTo userQuest = questService.getUserQuest(quest, user);
            if (userQuest != null) {
                updateSessionAttributes(req, quest, userQuest);
                req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
            } else {
                questService.startQuest(quest, user);
                resp.sendRedirect("/quest?id=" + id);
            }
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
        UserTo user = (UserTo) req.getSession().getAttribute("user");
        log.info("questId={}, answerId={}", questId, answerId);

        QuestTo quest = questService.getQuest(Long.parseLong(questId));
        UserQuestTo userQuest = questService.getUserQuest(quest, user);
        AnswerTo answer = answerService.get(Long.parseLong(answerId));

        QuestionTo nextQuestion = questionService.getNextQuestion(answer);
        userQuest.setCurrentQuestion(nextQuestion);
        questService.updateUserQuest(dto.from(userQuest));
        updateSessionAttributes(req, quest, userQuest);

        req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
    }

    private void updateSessionAttributes(HttpServletRequest req, QuestTo quest, UserQuestTo userQuest) {
        QuestionTo currentQuestion = userQuest.getCurrentQuestion();
        List<AnswerTo> answersForQuestion = answerService.getAnswersForQuestion(currentQuestion);

        req.setAttribute("quest", quest);
        req.setAttribute("userQuest", userQuest);
        req.setAttribute("questId", quest.getId());
        req.setAttribute("question", currentQuestion);
        req.setAttribute("answers", answersForQuestion);
    }
}
