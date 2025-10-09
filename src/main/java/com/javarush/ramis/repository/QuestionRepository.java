package com.javarush.ramis.repository;

import com.javarush.ramis.config.SessionCreator;
import com.javarush.ramis.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
public class QuestionRepository implements Repository<Question> {
    private final SessionCreator sessionCreator;

    public QuestionRepository() {
        sessionCreator = SessionCreator.getInstance();
    }

    @Override
    public Collection<Question> getAll() {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            List<Question> questions = session.createQuery("select q from Question q", Question.class).list();
            transaction.commit();
            log.info("getAll questions successful, size={}", questions.size());
            return questions;
        } catch (Exception ex) {
            transaction.rollback();
            log.error("getAll questions failed: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Question> get(long id) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Optional<Question> question = session.createQuery("select q from Question q " +
                                                              "left join fetch q.quest " +
                                                              "left join fetch q.answers " +
                                                              "where q.id = :id", Question.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
            transaction.commit();
            log.info("get question successful, id={}", id);
            return question;
        } catch (Exception ex) {
            transaction.rollback();
            log.error("get question failed: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void create(Question question) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.persist(question);
            transaction.commit();
            log.info("create question successful, id={}", question.getId());
        } catch (Exception ex) {
            transaction.rollback();
            log.error("create question failed: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Question question) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.remove(question);
            transaction.commit();
            log.info("delete question successful, id={}", question.getId());
        } catch (Exception ex) {
            transaction.rollback();
            log.error("delete question failed: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Question question) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.merge(question);
            transaction.commit();
            log.info("update question successful, id={}", question.getId());
        } catch (Exception ex) {
            transaction.rollback();
            log.error("update question failed: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
