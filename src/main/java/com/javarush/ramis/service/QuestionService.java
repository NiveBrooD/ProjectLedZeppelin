package com.javarush.ramis.service;

import com.javarush.ramis.dto.QuestionTo;
import com.javarush.ramis.exception.QuestException;
import com.javarush.ramis.mapping.Dto;
import com.javarush.ramis.repository.QuestionRepository;

import java.util.List;

public class QuestionService {
    private final QuestionRepository questionRepository;
    private final Dto dto  = Dto.MAPPER;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void create(List<QuestionTo> questionList) {
        for (QuestionTo question : questionList) {
            create(question);
        }
    }

    public QuestionTo get(Long id) {
        return questionRepository.get(id)
                .map(dto::from)
                .orElseThrow(() -> new QuestException("Question not found"));
    }

    public void create(QuestionTo question) {
        questionRepository.create(dto.from(question));
    }

}
