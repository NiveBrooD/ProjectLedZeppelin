package com.javarush.ramis.service;

import com.javarush.ramis.entity.Answer;
import com.javarush.ramis.entity.Question;
import com.javarush.ramis.repository.AnswerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class AnswerServiceTest {
    private AnswerService answerService = null;
    private Answer answer = null;

    @BeforeEach
    void setUp() {
        answerService = new AnswerService(AnswerRepository.getInstance());
        answer = new Answer("Desc", new Question("desc", new ArrayList<>(), false));
    }

    @AfterEach
    void tearDown() {
        answerService.delete(answer);
    }

    @Test
    void create() {
        answerService.create(answer);
        assertTrue(answerService.get(answer.getId()).isPresent());
        assertEquals(answer, answerService.get(answer.getId()).get());
    }

    @Test
    void delete() {
        answerService.create(answer);
        assertTrue(answerService.get(answer.getId()).isPresent());
        answerService.delete(answer);
        assertFalse(answerService.get(answer.getId()).isPresent());
    }

    @Test
    void update() {
        answerService.create(answer);
        answer.setDescription("new desc");
        answerService.update(answer);
        assertTrue(answerService.get(answer.getId()).isPresent());
        assertEquals(answer, answerService.get(answer.getId()).get());
        assertEquals("new desc", answerService.get(answer.getId()).get().getDescription());
    }

    @Test
    void getAll() {
        answerService.create(answer);
        assertTrue(answerService.getAll().contains(answer));
        assertFalse(answerService.getAll().isEmpty());
    }

    @Test
    void get() {
        answerService.create(answer);
        assertTrue(answerService.get(answer.getId()).isPresent());
        assertEquals(answer, answerService.get(answer.getId()).get());
    }
}