package com.javarush.ramis.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionTo {
    private Long id;
    private String description;
    private QuestTo quest;
    private boolean end;
    private List<AnswerTo> answers;
}
