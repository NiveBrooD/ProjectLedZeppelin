package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.Question;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;


class CreateAnswersServletTest {

    @Test
    void doGet() throws ServletException, IOException {
        CreateAnswersServlet servlet = new CreateAnswersServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);
        Question question = Mockito.mock(Question.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getRequestDispatcher("/WEB-INF/create-answers.jsp")).thenReturn(rd);
        Mockito.when(session.getAttribute("newQuestions")).thenReturn(List.of(question));
        servlet.doGet(req, resp);

        Mockito.verify(rd).forward(req, resp);
    }

    @Test
    void doGetNull() throws ServletException, IOException {
        CreateAnswersServlet servlet = new CreateAnswersServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("newQuestions")).thenReturn(null);
        servlet.doGet(req, resp);

        Mockito.verify(resp).sendRedirect("/create-quest");
    }
}