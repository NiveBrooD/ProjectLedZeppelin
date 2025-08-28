package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.Quest;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class CreateQuestServletTest {

    @Test
    void doGet() throws ServletException, IOException {
        CreateQuestServlet servlet = new CreateQuestServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getRequestDispatcher("/WEB-INF/create-quest.jsp")).thenReturn(rd);
        servlet.doGet(req,resp);

        Mockito.verify(rd).forward(req, resp);
    }

    @Test
    void doPost() throws IOException {
        CreateQuestServlet servlet = new CreateQuestServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Map<String, String[]> allParams = Mockito.mock(Map.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getParameter("title")).thenReturn("testTitle");
        Mockito.when(req.getParameter("description")).thenReturn("testDescription");
        Mockito.when(req.getParameter("totalQuestions")).thenReturn("2");
        for (int i = 1; i <= Integer.parseInt(req.getParameter("totalQuestions")); i++) {
            Mockito.when(req.getParameter("q" + i + "ID")).thenReturn("" + i);
            Mockito.when(req.getParameter("q" + i)).thenReturn("Str" + i);
            Mockito.when(allParams.containsKey("q" + i + "_end")).thenReturn(true);
        }
        servlet.doPost(req,resp);

        Mockito.verify(session).setAttribute(Mockito.anyString(), Mockito.any(Quest.class));
        Mockito.verify(session).setAttribute(Mockito.anyString(), Mockito.any(List.class));
        Mockito.verify(resp).sendRedirect("/create-answers");
    }
}