package com.javarush.ramis.service;

import com.javarush.ramis.entity.Question;
import com.javarush.ramis.repository.QuestionRepository;

import java.util.List;

public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void create(List<Question> questionList) {
        for (Question question : questionList) {
            create(question);
        }
    }

    public void create(Question question) {
        questionRepository.create(question);
    }

}
