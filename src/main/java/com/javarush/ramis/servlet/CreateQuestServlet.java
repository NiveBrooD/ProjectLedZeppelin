package com.javarush.ramis.servlet;

import com.javarush.ramis.dto.QuestTo;
import com.javarush.ramis.dto.QuestionTo;
import com.javarush.ramis.dto.UserTo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/create-quest")
public class CreateQuestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/create-quest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> allParams = req.getParameterMap();

        List<QuestionTo> questions = new ArrayList<>();
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        UserTo user = (UserTo) req.getSession().getAttribute("user");

        int total = Integer.parseInt(req.getParameter("totalQuestions"));
        for (int i = 1; i <= total; i++) {
            Long tempId = Long.parseLong(req.getParameter("q" + i + "ID"));
            QuestionTo question = QuestionTo.builder()
                    .description(req.getParameter("q" + i))
                    .questId(null)
                    .answers(new ArrayList<>())
                    .end(allParams.containsKey("q" + i + "_end"))
                    .build();

            question.setId(tempId);
            questions.add(question);
        }
        QuestTo quest = QuestTo.builder()
                .title(title)
                .description(description)
                .firstQuestion(questions.get(0))
                .author(user)
                .build();
        quest.setQuestions(questions);

        req.getSession().setAttribute("newQuest", quest);
        req.getSession().setAttribute("newQuestions", questions);
        resp.sendRedirect("/create-answers");
        System.out.println(total + "- Total");
    }
}
