package com.javarush.ramis.service;

import com.javarush.ramis.dto.QuestTo;
import com.javarush.ramis.dto.UserQuestTo;
import com.javarush.ramis.dto.UserTo;
import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.entity.UserQuest;
import com.javarush.ramis.exception.QuestException;
import com.javarush.ramis.mapping.Dto;
import com.javarush.ramis.repository.QuestRepository;
import com.javarush.ramis.repository.UserQuestRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public class QuestService {
    private final QuestRepository questRepository;
    private final UserQuestRepository userQuestRepository;
    private final Dto dto = Dto.MAPPER;

    public QuestService(QuestRepository questRepository, UserQuestRepository userQuestRepository) {
        this.questRepository = questRepository;
        this.userQuestRepository = userQuestRepository;
    }

    public void create(Quest quest) {
        questRepository.create(quest);
    }
    public void delete(QuestTo quest) {
        questRepository.delete(dto.from(quest));
    }
    public void update(QuestTo quest) {
        questRepository.update(dto.from(quest));
    }
    public void updateUserQuest(UserQuest userQuest) {
        userQuestRepository.update(userQuest);
    }

    public Collection<QuestTo> getAll() {
        return questRepository.getAll().stream()
                .map(dto::from)
                .collect(Collectors.toList());
    }

    public QuestTo getQuest(long id) {
        return questRepository.get(id)
                .map(dto::from)
                .orElseThrow(() -> new QuestException("Quest with id " + id + "not found."));
    }

    public void startQuest(QuestTo quest, UserTo user) {
        UserQuest userQuest = new UserQuest();
        userQuest.setUser(dto.from(user));
        userQuest.setQuest(dto.from(quest));
        userQuest.setCurrentQuestion(dto.from(quest.getFirstQuestion()));
        userQuestRepository.create(userQuest);
    }

    public UserQuestTo getUserQuest(QuestTo quest, UserTo user) {
        return userQuestRepository
                .getUserQuest(quest.getId(), user.getId())
                .map(dto::from)
                .orElse(null);
    }

    public void restartQuest(UserQuestTo userQuest) {
        userQuestRepository.restartQuest(dto.from(userQuest));
    }
}
