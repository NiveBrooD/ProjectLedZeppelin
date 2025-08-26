package com.javarush.ramis.repository;

import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.entity.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class QuestRepositoryTest {
    private QuestRepository questRepository = null;
    private Quest quest = null;

    @BeforeEach
    void setUp() {
        questRepository = QuestRepository.getInstance();
        quest = new Quest(Long.MAX_VALUE, "title", "desc",
                    new Question("title", new ArrayList<>(), false),
                        new Question("title", new ArrayList<>(), false));
    }

    @AfterEach
    void tearDown() {
        questRepository.delete(quest);
    }

    @Test
    void getAll() {
        questRepository.create(quest);
        assertTrue(questRepository.getAll().contains(quest));
        assertFalse(questRepository.getAll().isEmpty());
    }

    @Test
    void get() {
        questRepository.create(quest);
        assertTrue(questRepository.get(quest.getId()).isPresent());
        assertEquals(quest, questRepository.get(quest.getId()).get());
    }

    @Test
    void create() {
       questRepository.create(quest);
       assertTrue(questRepository.getAll().contains(quest));
    }

    @Test
    void delete() {
        questRepository.create(quest);
        questRepository.delete(quest);
        assertFalse(questRepository.getAll().contains(quest));
    }

    @Test
    void update() {
        questRepository.create(quest);
        quest.setTitle("new title");
        quest.setDescription("new desc");
        questRepository.update(quest);
        assertTrue(questRepository.get(quest.getId()).isPresent());
        assertEquals("new title", questRepository
                .get(quest.getId())
                .get()
                .getTitle());
        assertEquals("new desc", questRepository
                .get(quest.getId())
                .get()
                .getDescription());
    }
}