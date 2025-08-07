package com.javarush.ramis.entity;

import com.javarush.ramis.repository.AnswerRepository;
import com.javarush.ramis.service.AnswerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Question {
    private String description;
    private List<Answer> answers;
    private boolean end;
    AnswerService answerService = new AnswerService(AnswerRepository.getInstance());

    public Question(String description, List<Answer> answers, boolean end) {
        this.description = description;
        this.end = end;
        this.answers = answers;
        if (!end) {
            answers.forEach(answer -> {
                answerService.create(answer);
            });
        }
    }
}
