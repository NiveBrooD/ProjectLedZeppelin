package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Optional;


class RestartServletTest {

    @Test
    void doGet() throws IOException, ServletException {
        RestartServlet servlet = new RestartServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        QuestService questService = Mockito.mock(QuestService.class);
        servlet.setQuestService(questService);
        Optional<Quest> questOptional = Mockito.mock(Optional.class);
        Quest quest = Mockito.mock(Quest.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("questId")).thenReturn("1");
        Mockito.when(questService.get(1L)).thenReturn(questOptional);
        servlet.doGet(req, resp);

        Mockito.verify(resp).sendRedirect("/quest?id=1");
    }
}