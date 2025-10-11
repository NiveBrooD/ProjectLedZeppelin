package com.javarush.ramis.mapping;

import com.javarush.ramis.dto.AnswerTo;
import com.javarush.ramis.dto.QuestTo;
import com.javarush.ramis.dto.QuestionTo;
import com.javarush.ramis.dto.UserTo;
import com.javarush.ramis.entity.Answer;
import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.entity.Question;
import com.javarush.ramis.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {EntityResolver.class})
public interface Dto {
    Dto MAPPER = Mappers.getMapper(Dto.class);

    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "nextQuestionId", source = "nextQuestion.id")
    AnswerTo from(Answer answer);

    @Mapping(target = "question", source = "questionId", qualifiedByName = "getQuestionById")
    @Mapping(target = "nextQuestion", source = "nextQuestionId", qualifiedByName = "getQuestionById")
    Answer from(AnswerTo answerTo);

    @Mapping(target = "questId", source = "quest.id")
    @Mapping(target = "answers", ignore = true)
    QuestionTo from(Question question);

    @Mapping(target = "quest", source = "questId", qualifiedByName = "getQuestById")
    Question from(QuestionTo questionTo);

    @Mapping(target = "questions", ignore = true)
    QuestTo from(Quest quest);

    Quest from(QuestTo questTo);

    UserTo from(User user);

    User from(UserTo userTo);
}