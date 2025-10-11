package com.javarush.ramis.mapping;

import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.entity.Question;
import com.javarush.ramis.exception.QuestException;
import com.javarush.ramis.repository.QuestRepository;
import com.javarush.ramis.repository.QuestionRepository;
import org.mapstruct.Named;

public class EntityResolver {
    private final QuestRepository questRepository;
    private final QuestionRepository questionRepository;

    public EntityResolver() {
        this.questRepository = new QuestRepository();
        this.questionRepository = new QuestionRepository();
    }

    @Named("getQuestById")
    public Quest getQuestById(Long questId) {
        if (questId == null) {
            return null;
        }
        return questRepository.get(questId)
                .orElseThrow(() -> new QuestException("Quest Not Found"));
    }

    @Named("getQuestionById")
    public Question getQuestionById(Long questionId) {
        if (questionId == null) {
            return null;
        }
        return questionRepository.get(questionId)
                .orElseThrow(() -> new QuestException("Question Not Found"));
    }
}
