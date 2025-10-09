package com.javarush.ramis.service;

import com.javarush.ramis.dto.AnswerTo;
import com.javarush.ramis.entity.Question;
import com.javarush.ramis.exception.QuestException;
import com.javarush.ramis.mapping.Dto;
import com.javarush.ramis.repository.AnswerRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerService {
    private final AnswerRepository answerRepository;
    private final Dto dto = Dto.MAPPER;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Collection<AnswerTo> getAll() {
        return answerRepository.getAll()
                .stream()
                .map(dto::from)
                .collect(Collectors.toList());
    }

    public AnswerTo get(long id) {
        return answerRepository.get(id)
                .map(dto::from)
                .orElseThrow(() -> new QuestException("Answer not found"));
    }

    public void create(AnswerTo answer) {
        answerRepository.create(dto.from(answer));
    }

    public void delete(AnswerTo answer) {
        answerRepository.delete(dto.from(answer));
    }

    public void update(AnswerTo answer) {
        answerRepository.update(dto.from(answer));
    }

    public List<AnswerTo> getAnswersForQuestion(Question question) {
        return answerRepository.get(question).stream().map(dto::from).collect(Collectors.toList());
    }
}
