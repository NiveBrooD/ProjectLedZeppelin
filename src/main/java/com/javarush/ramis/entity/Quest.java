package com.javarush.ramis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Quest {
    private Long id;
    private String title;
    private String description;
    private Question firstQuestion;
    private Question currentQuestion;


    public Quest(String title, String description, Question firstQuestion) {
        this.title = title;
        this.description = description;
        this.firstQuestion = firstQuestion;
        restartQuest();
    }

    public void restartQuest() {
        currentQuestion = firstQuestion;
    }
}
