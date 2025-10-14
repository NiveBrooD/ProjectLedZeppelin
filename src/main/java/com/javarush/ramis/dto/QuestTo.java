package com.javarush.ramis.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestTo {
    private Long id;
    private String title;
    private String description;
    private List<QuestionTo> questions;
    private QuestionTo firstQuestion;
    private UserTo author;
}
