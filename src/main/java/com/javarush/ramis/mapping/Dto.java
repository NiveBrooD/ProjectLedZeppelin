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
import org.mapstruct.factory.Mappers;

@Mapper
public interface Dto {
    Dto MAPPER = Mappers.getMapper(Dto.class);

    AnswerTo from(Answer answer);
    Answer from(AnswerTo answerTo);

    QuestionTo from(Question question);
    Question from(QuestionTo questionTo);

    QuestTo from(Quest quest);
    Quest from(QuestTo questTo);

    UserTo from(User user);
    User from(UserTo userTo);
}