package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.Answer;
import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.service.AnswerService;
import com.javarush.ramis.service.QuestService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Optional;


class QuestServletTest {

    @Test
    void doGet() throws ServletException, IOException {
        QuestServlet questServlet = new QuestServlet();
        QuestService questService = Mockito.mock(QuestService.class);
        questServlet.setQuestService(questService);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);
        Optional<Quest> optional = Mockito.mock(Optional.class);
        Quest quest = Mockito.mock(Quest.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getRequestDispatcher("/WEB-INF/quest.jsp")).thenReturn(rd);
        Mockito.when(req.getParameter("id")).thenReturn("1");
        Mockito.when(questService.get(1L)).thenReturn(optional);
        Mockito.when(optional.isPresent()).thenReturn(true);
        Mockito.when(optional.get()).thenReturn(quest);
        questServlet.doGet(req, resp);

        Mockito.verify(rd).forward(req, resp);
    }

    @Test
    void doPost() throws ServletException, IOException {
        QuestServlet questServlet = new QuestServlet();
        QuestService questService = Mockito.mock(QuestService.class);
        AnswerService answerService = Mockito.mock(AnswerService.class);
        questServlet.setQuestService(questService);
        questServlet.setAnswerService(answerService);
        Optional<Quest> optionalQuest = Mockito.mock(Optional.class);
        Optional<Answer> optionalAnswer = Mockito.mock(Optional.class);
        Quest quest = Mockito.mock(Quest.class);
        Answer answer = Mockito.mock(Answer.class);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestDispatcher rd = Mockito.mock(RequestDispatcher.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getRequestDispatcher("/WEB-INF/quest.jsp")).thenReturn(rd);
        Mockito.when(req.getParameter("answerId")).thenReturn("1");
        Mockito.when(req.getParameter("questId")).thenReturn("2");
        Mockito.when(questService.get(2L)).thenReturn(optionalQuest);
        Mockito.when(optionalQuest.isPresent()).thenReturn(true);
        Mockito.when(answerService.get(1L)).thenReturn(optionalAnswer);
        Mockito.when(optionalAnswer.isPresent()).thenReturn(true);
        Mockito.when(optionalQuest.get()).thenReturn(quest);
        Mockito.when(optionalAnswer.get()).thenReturn(answer);
        questServlet.doPost(req, resp);

        Mockito.verify(quest).setCurrentQuestion(Mockito.any());
        Mockito.verify(req).setAttribute(Mockito.any(), Mockito.any());
        Mockito.verify(rd).forward(req, resp);
    }
}