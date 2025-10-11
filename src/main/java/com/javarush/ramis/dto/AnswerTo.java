package com.javarush.ramis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerTo {
    private Long id;
    private String description;
    private Long questionId;
    private Long nextQuestionId;
}
