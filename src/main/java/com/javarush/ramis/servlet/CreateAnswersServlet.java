package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.Answer;
import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.entity.Question;
import com.javarush.ramis.entity.User;
import com.javarush.ramis.repository.AnswerRepository;
import com.javarush.ramis.repository.QuestRepository;
import com.javarush.ramis.service.AnswerService;
import com.javarush.ramis.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;


@WebServlet("/create-answers")
public class CreateAnswersServlet extends HttpServlet {
    QuestService questService = new QuestService(QuestRepository.getInstance());
    AnswerService answerService = new AnswerService(AnswerRepository.getInstance());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Question> questions = (List<Question>) req.getSession().getAttribute("newQuestions");
        if (questions == null) {
            resp.sendRedirect("/create-quest");
        } else {
            req.getRequestDispatcher("/WEB-INF/create-answers.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> allParams = req.getParameterMap();

        Quest quest = (Quest) req.getSession().getAttribute("newQuest");
        List<Question> questions = (List<Question>) req.getSession().getAttribute("newQuestions");
        for (Question question : questions) {
            List<Answer> answers = new ArrayList<>();
            Long id = question.getId();
            int totalAnswers = findTotalAnswers(allParams, id);
            for (int i = 1; i <= totalAnswers; i++) {
                String answerDescription = req.getParameter("q" + id + "_a" + i + "_text");
                Long answerNextQuestionId = Long.parseLong(req.getParameter("q" + id + "_a" + i + "_next"));
                Optional<Question> first = questions.stream().filter(q -> q.getId().equals(answerNextQuestionId)).findFirst();
                if (first.isPresent()) {
                    Question nextQuestion = first.get();
                    Answer answer = new Answer(answerDescription, nextQuestion);
                    answerService.create(answer);
                    answers.add(answer);
                } else {
                    resp.sendRedirect("/create-answers");
                    return;
                }
                question.setAnswers(answers);
            }
        }
        questService.create(quest);
        User user = (User) req.getSession().getAttribute("user");
        req.getSession().invalidate();
        req.getSession(true).setAttribute("user", user);
        resp.sendRedirect("/");
    }

    private Integer findTotalAnswers(Map<String, String[]> allParams, Long questionId) {
        int totalAnswers = 0;
        Set<String> params = allParams.keySet();
        for (String param : params) {
            if (param.startsWith("q" + questionId + "_a")) {
                totalAnswers++;
            }
        }
        return totalAnswers/2;
    }
}

