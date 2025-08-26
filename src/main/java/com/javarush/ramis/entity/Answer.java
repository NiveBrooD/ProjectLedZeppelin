package com.javarush.ramis.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer {
    private Long id;
    private String description;
    private Question nextQuestion;

    public Answer(String description, Question nextQuestion) {
        this.description = description;
        this.nextQuestion = nextQuestion;
    }
}
