package com.javarush.ramis.servlet;

import com.javarush.ramis.ContainerIT;
import com.javarush.ramis.config.SessionCreator;
import com.javarush.ramis.dto.Role;
import com.javarush.ramis.dto.UserTo;
import com.javarush.ramis.repository.QuestRepository;
import com.javarush.ramis.repository.UserQuestRepository;
import com.javarush.ramis.repository.UserRepository;
import com.javarush.ramis.service.QuestService;
import com.javarush.ramis.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RestartServletTest extends ContainerIT {
    QuestService questService = new QuestService(
            new QuestRepository(SessionCreator.sessionCreatorForTests(ContainerIT.PROPERTIES)),
            new UserQuestRepository(SessionCreator.sessionCreatorForTests(ContainerIT.PROPERTIES))
    );

    @Test
    void doPost() throws IOException {
        HttpSession session = Mockito.mock(HttpSession.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        UserTo user = UserTo.builder().id(1L).role(Role.ADMIN).login("test").password("test").build();

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(req.getParameter("questId")).thenReturn("1");
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        RestartServlet servlet = new RestartServlet();
        servlet.setQuestService(questService);
        servlet.doPost(req, resp);

        Mockito.verify(resp).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}