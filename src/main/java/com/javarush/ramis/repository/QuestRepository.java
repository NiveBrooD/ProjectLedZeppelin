package com.javarush.ramis.repository;

import com.javarush.ramis.config.SessionCreator;
import com.javarush.ramis.entity.Answer;
import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

@Slf4j
public class QuestRepository implements Repository<Quest> {
    private final SessionCreator sessionCreator;

    public QuestRepository( ) {
        this.sessionCreator = SessionCreator.getInstance();
    }

//    private QuestRepository() {
//        Question qf31 = new Question("Тебя вернули домой. \n Победа.", null, true);
//        Question qf32 = new Question("Твою ложь разоблачили. \n Поражение.", null, true);
//        Answer a31 = new Answer("Рассказать правду о себе.", qf31);
//        Answer a32 = new Answer("Солгать о себе.", qf32);
//        Question q3 = new Question("Ты поднялся на мостик. Ты кто?", List.of(a31, a32), false);
//        Question qf22 = new Question("Ты не пошел на переговоры. \n Поражение.", null, true);
//        Answer a22 = new Answer("Отказаться подниматься на мостик.", qf22);
//        Answer a21 =  new Answer("Подняться на мостик.", q3);
//        Question q2 = new Question("Ты принял вызов. \n Поднимаешься на мостик к капитану?", List.of(a21, a22), false);
//        Question qf12 = new Question("Ты отклонил вызов. \n Поражение.",  null, true);
//        Answer a12 = new Answer("Отклонить вызов.", qf12);
//        Answer a11 =  new Answer("Принять вызов.", q2);
//        Question q1 = new Question("Ты потерял память. Принять вызов НЛО?",  List.of(a11, a12), false);
//        create(new Quest("Strange call.", "Ты просыпаешься черт знает где, и не знаешь, что делать. Сможешь ли ты выбраться из этой ситуации?", q1));
//    }

    @Override
    public Collection<Quest> getAll() {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            List<Quest> quests = session.createQuery("select q from Quest q " +
                                                     "inner join fetch q.firstQuestion " +
                                                     "inner join fetch q.currentQuestion", Quest.class).list();
            log.info("get all quests: {}", quests);
            transaction.commit();
            return quests;
        } catch (Exception ex) {
            log.error("get all quests: {}", ex.getMessage());
            transaction.rollback();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Quest> get(long id) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Optional<Quest> quest = session.createQuery("select q from Quest q " +
                                                        "inner join fetch q.author " +
                                                        "inner join fetch q.currentQuestion " +
                                                        "inner join fetch q.firstQuestion " +
                                                        "where q.id =:id", Quest.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
            log.info("get quest: {}", quest);
            transaction.commit();
            return quest;
        }  catch (Exception ex) {
            log.error("get quest: {}", ex.getMessage());
            transaction.rollback();
            throw  new RuntimeException(ex);
        }
    }

    @Override
    public void create(Quest quest) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Question firstQuestion = quest.getFirstQuestion();
            quest.setFirstQuestion(null);
            quest.setCurrentQuestion(null);
            session.persist(quest);
            session.flush();

            List<Question> questions = quest.getQuestions();
            for (Question question : questions) {
                question.setId(null);
                question.setQuest(quest);
                session.persist(question);
            }
            session.flush();

            for (Question question : questions) {
                List<Answer> answers = question.getAnswers();
                for (Answer answer : answers) {
                    session.persist(answer);
                }
            }

            quest.setFirstQuestion(firstQuestion);
            quest.restartQuest();
            session.merge(quest);

            log.info("create quest: {}", quest);
            transaction.commit();
        }  catch (Exception ex) {
            log.error("create quest: {}", ex.getMessage());
            try {
                session.remove(quest);
            } catch (Exception e) {

            }
            transaction.rollback();
            throw  new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Quest quest) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.remove(quest);
            log.info("delete quest: {}", quest);
            transaction.commit();
        }   catch (Exception ex) {
            log.error("delete quest: {}", ex.getMessage());
            transaction.rollback();
            throw  new RuntimeException(ex);
        }
    }

    @Override
    public void update(Quest quest) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.merge(quest);
            log.info("update quest: {}", quest);
            transaction.commit();
        }   catch (Exception ex) {
            log.error("update quest: {}", ex.getMessage());
            transaction.rollback();
            throw  new RuntimeException(ex);
        }
    }
}
