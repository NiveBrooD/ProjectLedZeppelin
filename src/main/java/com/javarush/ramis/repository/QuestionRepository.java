package com.javarush.ramis.repository;

import com.javarush.ramis.config.SessionCreator;
import com.javarush.ramis.entity.Question;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class QuestionRepository implements Repository<Question> {
    private final SessionCreator sessionCreator;

    public QuestionRepository() {
        sessionCreator = SessionCreator.getInstance();
    }

    @Override
    public Collection<Question> getAll() {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try (session) {
            List<Question> questions = session.createQuery("select q from Question q", Question.class).list();
            session.getTransaction().commit();
            log.info("getAll questions successful, size={}", questions.size());
            return questions;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            log.error("getAll questions failed: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Question> get(long id) {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try (session) {
            Optional<Question> question = session.createQuery("select q from Question q " +
                                                              "left join fetch q.quest " +
                                                              "left join fetch q.answers " +
                                                              "where q.id = :id", Question.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            log.info("getQuest question successful, id={}", id);
            return question;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            log.error("getQuest question failed: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void create(Question question) {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try (session) {
            session.persist(question);
            session.getTransaction().commit();
            log.info("create question successful, id={}", question.getId());
        } catch (Exception ex) {
            session.getTransaction().rollback();
            log.error("create question failed: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Question question) {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try (session) {
            session.remove(question);
            session.getTransaction().commit();
            log.info("delete question successful, id={}", question.getId());
        } catch (Exception ex) {
            session.getTransaction().rollback();
            log.error("delete question failed: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Question question) {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try (session) {
            session.merge(question);
            session.getTransaction().commit();
            log.info("update question successful, id={}", question.getId());
        } catch (Exception ex) {
            session.getTransaction().rollback();
            log.error("update question failed: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public Optional<Question> getNextQuestionByAnswerId(long answerId) {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try {
            Optional<Question> question = session.createQuery("select a.nextQuestion from Answer a " +
                                                              "where a.id = :answerId", Question.class)
                    .setParameter("answerId", answerId)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            return question;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.clear();
        }
    }
}
