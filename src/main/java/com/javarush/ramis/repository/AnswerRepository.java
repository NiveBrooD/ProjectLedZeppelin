package com.javarush.ramis.repository;

import com.javarush.ramis.entity.Answer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AnswerRepository implements Repository<Answer> {
    private static volatile AnswerRepository INSTANCE = null;
    private static final Map<Long, Answer> answers = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    private AnswerRepository() {}

    public static AnswerRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (AnswerRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AnswerRepository();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Collection<Answer> getAll() {
        return answers.values();
    }

    @Override
    public Optional<Answer> get(long id) {
        return Optional.ofNullable(answers.get(id));
    }

    @Override
    public void create(Answer answer) {
        answer.setId(ID_GENERATOR.getAndIncrement());
        update(answer);
    }

    @Override
    public void delete(Answer answer) {
        answers.remove(answer.getId());
    }

    @Override
    public void update(Answer answer) {
        answers.put(answer.getId(), answer);

    }
}
