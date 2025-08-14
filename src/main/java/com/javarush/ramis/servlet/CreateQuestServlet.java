package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.entity.Question;
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
        req.getRequestDispatcher("/WEB-INF/create-quest.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Question> questions = new ArrayList<>();
        Map<String, String[]> allParams = req.getParameterMap();
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        int total = Integer.parseInt(req.getParameter("totalQuestions"));
        for (int i = 1; i <= total; i++) {
            Long id = Long.parseLong(req.getParameter("q" + i + "ID"));
            Question question = new Question(req.getParameter("q" + i), new ArrayList<>(), allParams.containsKey("q" + i + "_end"));
            question.setId(id);
            questions.add(question);
        }
        Quest quest = new Quest(title, description, questions.get(0));
        req.getSession().setAttribute("newQuest", quest);
        req.getSession().setAttribute("newQuestions", questions);
        resp.sendRedirect("/create-answers");
        System.out.println(total + "- Total");

    }
}
