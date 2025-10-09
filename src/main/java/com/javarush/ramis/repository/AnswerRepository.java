package com.javarush.ramis.repository;

import com.javarush.ramis.config.SessionCreator;
import com.javarush.ramis.entity.Answer;
import com.javarush.ramis.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AnswerRepository implements Repository<Answer> {
    private final SessionCreator sessionCreator;

    public AnswerRepository() {
        sessionCreator = SessionCreator.getInstance();
    }

    @Override
    public Collection<Answer> getAll() {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            List<Answer> answers = session.createQuery("select a from Answer a " +
                                                       "inner join fetch a.nextQuestion " +
                                                       "inner join fetch a.question", Answer.class).list();
            log.info("getAll answers: {}", answers.size());
            transaction.commit();
            return answers;
        } catch (Exception ex) {
            transaction.rollback();
            log.error("getAll answers: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Answer> get(long id) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Optional<Answer> answer = session.createQuery("select a from Answer a " +
                                                "inner join fetch a.nextQuestion " +
                                                "inner join fetch a.question " +
                                                "where a.id = :id", Answer.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
            transaction.commit();
            log.info("get answer: {}", answer);
            return answer;
        } catch (Exception ex) {
            transaction.rollback();
            log.error("get answer: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void create(Answer answer) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.persist(answer);
            transaction.commit();
            log.info("create answer: {}", answer);
        } catch (Exception ex) {
            transaction.rollback();
            log.error("create answer: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Answer answer) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.remove(answer);
            transaction.commit();
            log.info("delete answer: {}", answer);
        } catch (Exception ex) {
            transaction.rollback();
            log.error("delete answer: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Answer answer) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.merge(answer);
            transaction.commit();
            log.info("update answer: {}", answer);
        } catch (Exception ex) {
            transaction.rollback();
            log.error("update answer: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public List<Answer> get(Question question) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            List<Answer> answers = session.createQuery("select a from Answer a where a.question = :question", Answer.class)
                    .setParameter("question", question).list();
            transaction.commit();
            log.info("get answer: {}", answers.size());
            return answers;
        } catch (Exception ex) {
            transaction.rollback();
            log.error("get answer: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
