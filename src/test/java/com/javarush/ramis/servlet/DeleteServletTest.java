package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.Role;
import com.javarush.ramis.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class DeleteServletTest {

    @Test
    void doPost() throws IOException, ServletException {
        DeleteServlet deleteServlet = new DeleteServlet();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session =  Mockito.mock(HttpSession.class);
        User user = new User(0L, "test", "test", Role.ADMIN);
        /*
        Тест написан через костыль, т.е тест проходит проверку, потому что в репозитории уже есть user с id = 0,
        если поменять создание изначальных юзеров (или удалить это вовсе), то тест не будет проходить,
        если в будущем этот тест не проходится, проблема в этом скорее всего.
         */

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        deleteServlet.doPost(req, resp);


        Mockito.verify(session,Mockito.times(1)).invalidate();
        Mockito.verify(resp).sendRedirect("/login");
    }
}