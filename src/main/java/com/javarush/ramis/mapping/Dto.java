package com.javarush.ramis.mapping;

import com.javarush.ramis.dto.*;
import com.javarush.ramis.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Dto {
    Dto MAPPER = Mappers.getMapper(Dto.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    AnswerTo from(Answer answer);


    Answer from(AnswerTo answerTo);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "end", source = "end")
    QuestionTo from(Question question);


    Question from(QuestionTo questionTo);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "questions", source = "questions")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "firstQuestion", source = "firstQuestion")
    QuestTo from(Quest quest);

    Quest from(QuestTo questTo);


    UserTo from(User user);

    User from(UserTo userTo);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "currentQuestion", source = "currentQuestion")
    UserQuestTo from(UserQuest userQuest);


    UserQuest from(UserQuestTo userQuestTo);
}