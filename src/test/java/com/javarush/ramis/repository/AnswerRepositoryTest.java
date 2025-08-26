package com.javarush.ramis.repository;

import com.javarush.ramis.entity.Answer;
import com.javarush.ramis.entity.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AnswerRepositoryTest {

    private AnswerRepository answerRepository ;
    private Answer answerTest;

    @BeforeEach
    void setUp() {
        answerRepository = AnswerRepository.getInstance();
        answerTest = new Answer("TestDesc", new Question("QuestionTestDesc", new ArrayList<>(), false));
        answerRepository.create(answerTest);
    }

    @AfterEach
    void tearDown() {
        answerRepository.delete(answerTest);
    }

    @Test
    void getAll() {
        Collection<Answer> values = answerRepository.getAll();
        Assertions.assertNotNull(values);
        Assertions.assertFalse(values.isEmpty());
    }

    @Test
    void get() {
        Optional<Answer> answerOptional = answerRepository.get(answerTest.getId());
        Optional<Answer> answerOptional1 = answerRepository.get(100L);
        assertEquals(answerTest, answerOptional.get());
        assertTrue(answerOptional1.isEmpty());
    }

    @Test
    void create() {
        Answer newAnswer = new Answer("TestDesc", new Question("QuestionTestDesc", new ArrayList<>(), false));
        answerRepository.create(newAnswer);
        assertTrue(answerRepository.getAll().contains(newAnswer));
    }

    @Test
    void delete() {
        answerRepository.delete(answerTest);
        Collection<Answer> all = answerRepository.getAll();
        assertFalse(all.contains(answerTest));
    }

    @Test
    void update() {
        answerTest.setDescription("Updated Description");
        answerRepository.update(answerTest);
        Answer answer = answerRepository.get(answerTest.getId()).get();
        assertEquals(answerTest.getDescription(), answer.getDescription());
    }
}