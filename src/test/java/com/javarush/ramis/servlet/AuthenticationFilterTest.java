package com.javarush.ramis.servlet;

import com.javarush.ramis.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;


class AuthenticationFilterTest {

    @Test
    @DisplayName("When user is null")
    public void whenUserIsNull() throws ServletException, IOException {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        HttpSession session = Mockito.mock(HttpSession.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse res = Mockito.mock(HttpServletResponse.class);


        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(null);
        authenticationFilter.doFilter(req, res, Mockito.mock(FilterChain.class));

        Mockito.verify(res).sendRedirect("/login");
    }

    @Test
    @DisplayName("When user is authorized")
    public void whenUserIsAuthorized() throws ServletException, IOException {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        HttpSession session = Mockito.mock(HttpSession.class);
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
        FilterChain chain = Mockito.mock(FilterChain.class);

        Mockito.when(req.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(Mockito.mock(User.class));
        authenticationFilter.doFilter(req, res, chain);

        Mockito.verify(chain).doFilter(Mockito.eq(req), Mockito.eq(res));
    }
}