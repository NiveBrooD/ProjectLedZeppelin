package com.javarush.ramis.repository;

import com.javarush.ramis.entity.Answer;
import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.entity.Question;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class QuestRepository implements Repository<Quest> {
    private static final Map<Long,Quest> quests = new ConcurrentHashMap<>();
    public static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private static volatile QuestRepository INSTANCE = null;

    private QuestRepository() {
        Question qf31 = new Question("Тебя вернули домой. \n Победа.", null, true);
        Question qf32 = new Question("Твою ложь разоблачили. \n Поражение.", null, true);
        Answer a31 = new Answer("Рассказать правду о себе.", qf31);
        Answer a32 = new Answer("Солгать о себе.", qf32);
        Question q3 = new Question("Ты поднялся на мостик. Ты кто?", List.of(a31, a32), false);
        Question qf22 = new Question("Ты не пошел на переговоры. \n Поражение.", null, true);
        Answer a22 = new Answer("Отказаться подниматься на мостик.", qf22);
        Answer a21 =  new Answer("Подняться на мостик.", q3);
        Question q2 = new Question("Ты принял вызов. \n Поднимаешься на мостик к капитану?", List.of(a21, a22), false);
        Question qf12 = new Question("Ты отклонил вызов. \n Поражение.",  null, true);
        Answer a12 = new Answer("Отклонить вызов.", qf12);
        Answer a11 =  new Answer("Принять вызов.", q2);
        Question q1 = new Question("Ты потерял память. Принять вызов НЛО?",  List.of(a11, a12), false);
        create(new Quest("Strange call.", "Ты просыпаешься черт знает где, и не знаешь, что делать. Сможешь ли ты выбраться из этой ситуации?", q1));
    }

    public static QuestRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (QuestRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new QuestRepository();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Collection<Quest> getAll() {
        return quests.values();
    }

    @Override
    public Optional<Quest> get(long id) {
        return Optional.ofNullable(quests.get(id));
    }

    @Override
    public void create(Quest quest) {
        quest.setId(ID_GENERATOR.getAndIncrement());
        update(quest);
    }

    @Override
    public void delete(Quest quest) {
        quests.remove(quest.getId());
    }

    @Override
    public void update(Quest quest) {
        quests.put(quest.getId(),quest);
    }
}
