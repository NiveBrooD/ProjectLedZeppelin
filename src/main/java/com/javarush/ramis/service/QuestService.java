package com.javarush.ramis.service;

import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.exception.QuestException;
import com.javarush.ramis.repository.QuestRepository;

import java.util.Collection;

public class QuestService {
    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public void create(Quest quest) {
        questRepository.create(quest);
    }
    public void delete(Quest quest) {
        questRepository.delete(quest);
    }
    public void update(Quest quest) {
        questRepository.update(quest);
    }
    public Collection<Quest> getAll() {
        return questRepository.getAll();
    }
    public Quest get(long id) {
        return questRepository.get(id).orElseThrow(
                () -> new QuestException("Quest with id " + id + "not found."));
    }

    public void restartQuest(Quest quest) {
        quest.restartQuest();
        questRepository.update(quest);
    }
}
