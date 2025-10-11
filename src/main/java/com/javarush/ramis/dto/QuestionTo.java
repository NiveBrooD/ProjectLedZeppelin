package com.javarush.ramis.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionTo {
    private Long id;
    private String description;
    private Long questId;
    private boolean end;
    private List<AnswerTo> answers;
}
