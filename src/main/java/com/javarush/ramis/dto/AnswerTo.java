package com.javarush.ramis.dto;

import com.javarush.ramis.entity.Question;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerTo {
    private Long id;
    private String description;
    private Question question;
    private Question nextQuestion;
}
