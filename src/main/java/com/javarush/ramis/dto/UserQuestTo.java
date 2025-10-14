package com.javarush.ramis.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserQuestTo {
    private Long id;
    private QuestionTo currentQuestion;
}
