package com.javarush.ramis.servlet;

import com.javarush.ramis.dto.AnswerTo;
import com.javarush.ramis.dto.QuestTo;
import com.javarush.ramis.dto.QuestionTo;
import com.javarush.ramis.dto.UserTo;
import com.javarush.ramis.repository.QuestRepository;
import com.javarush.ramis.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

import java.io.IOException;
import java.util.*;


@Setter
@WebServlet("/create-answers")
public class CreateAnswersServlet extends HttpServlet {
    private final QuestService questService = new QuestService(new QuestRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<QuestionTo> questions = (List<QuestionTo>) req.getSession().getAttribute("newQuestions");
        if (questions == null) {
            resp.sendRedirect("/create-quest");
        } else {
            req.getRequestDispatcher("/WEB-INF/create-answers.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> allParams = req.getParameterMap();

        QuestTo quest = (QuestTo) req.getSession().getAttribute("newQuest");
        List<QuestionTo> questions = (List<QuestionTo>) req.getSession().getAttribute("newQuestions");

        for (QuestionTo question : questions) {
            List<AnswerTo> answers = new ArrayList<>();
            Long id = question.getId();

            int totalAnswers = findTotalAnswers(allParams, id);
            for (int i = 1; i <= totalAnswers; i++) {
                String answerDescription = req.getParameter("q" + id + "_a" + i + "_text");
                Long answerNextQuestionId = Long.parseLong(req.getParameter("q" + id + "_a" + i + "_next"));
                Optional<QuestionTo> first = questions.stream().filter(q -> q.getId().equals(answerNextQuestionId)).findFirst();
                if (first.isPresent()) {
                    QuestionTo nextQuestion = first.get();
                    AnswerTo answer = AnswerTo.builder()
                            .description(answerDescription)
                            .questionId(question.getId())
                            .nextQuestionId(nextQuestion.getId())
                            .build();
                    answers.add(answer);
                } else {
                    resp.sendRedirect("/create-answers");
                    return;
                }
                question.setAnswers(answers);
            }
        }
        questService.create(quest);

        //Очищаем сессию от уже ненужных аттрибутов
        UserTo user = (UserTo) req.getSession().getAttribute("user");
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
        return totalAnswers / 2;
    }
}

