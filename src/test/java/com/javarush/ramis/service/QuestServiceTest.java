package com.javarush.ramis.service;

import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.entity.Question;
import com.javarush.ramis.repository.QuestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuestServiceTest {
    private QuestService questService;
    private Quest quest;

    @BeforeEach
    void setUp() {
        questService = new QuestService(QuestRepository.getInstance());
        quest = new Quest("title", "desc", new Question("desc", new ArrayList<>(), true));
    }

    @AfterEach
    void tearDown() {
        questService.delete(quest);
    }

    @Test
    void create() {
        questService.create(quest);
        assertTrue(questService.get(quest.getId()).isPresent());
        assertEquals(quest, questService.get(quest.getId()).get());
        assertTrue(questService.getAll().contains(quest));
    }

    @Test
    void delete() {
        questService.create(quest);
        questService.delete(quest);
        assertFalse(questService.get(quest.getId()).isPresent());
    }

    @Test
    void update() {
        questService.create(quest);
        quest.setDescription("new desc");
        questService.update(quest);
        assertTrue(questService.get(quest.getId()).get().getDescription().equals("new desc"));
    }

    @Test
    void getAll() {
        questService.create(quest);
        assertFalse(questService.getAll().isEmpty());
    }

    @Test
    void get() {
        questService.create(quest);
        assertTrue(questService.get(quest.getId()).isPresent());
        assertEquals(quest, questService.get(quest.getId()).get());
    }
}