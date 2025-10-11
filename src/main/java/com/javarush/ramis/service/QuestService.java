package com.javarush.ramis.service;

import com.javarush.ramis.dto.QuestTo;
import com.javarush.ramis.exception.QuestException;
import com.javarush.ramis.mapping.Dto;
import com.javarush.ramis.repository.QuestRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public class QuestService {
    private final QuestRepository questRepository;
    private final Dto dto = Dto.MAPPER;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public void create(QuestTo quest) {
        questRepository.create(dto.from(quest));
    }
    public void delete(QuestTo quest) {
        questRepository.delete(dto.from(quest));
    }
    public void update(QuestTo quest) {
        questRepository.update(dto.from(quest));
    }
    public Collection<QuestTo> getAll() {
        return questRepository.getAll().stream().map(dto::from).collect(Collectors.toList());
    }
    public QuestTo get(long id) {
        return questRepository.get(id).map(dto::from).orElseThrow(
                () -> new QuestException("Quest with id " + id + "not found."));
    }

    public void restartQuest(QuestTo quest) {
        quest.setCurrentQuestion(quest.getFirstQuestion());
        questRepository.update(dto.from(quest));
    }
}
