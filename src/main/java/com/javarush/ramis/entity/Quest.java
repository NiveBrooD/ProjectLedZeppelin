package com.javarush.ramis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Quest {
    private int id;
    private String title;
    private String description;
    private String currentQuestion;
}
