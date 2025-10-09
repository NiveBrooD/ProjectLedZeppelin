package com.javarush.ramis.config;

import com.javarush.ramis.entity.Answer;
import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.entity.Question;
import com.javarush.ramis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
public class SessionCreator implements AutoCloseable {
    private volatile static SessionCreator instance;
    private final SessionFactory sessionFactory;

    public static SessionCreator getInstance() {
        if (instance == null) {
            synchronized (SessionCreator.class) {
                if (instance == null) {
                    instance = new SessionCreator();
                }
            }
        }
        return instance;
    }

    private SessionCreator() {
        try {
            Configuration configuration = new Configuration().configure();

            configuration.addAnnotatedClass(Answer.class);
            log.info("Added annotated class {}", Answer.class);
            configuration.addAnnotatedClass(Quest.class);
            log.info("Added annotated class {}", Quest.class);
            configuration.addAnnotatedClass(Question.class);
            log.info("Added annotated class {}", Question.class);
            configuration.addAnnotatedClass(User.class);
            log.info("Added annotated class {}", User.class);

            sessionFactory = configuration.buildSessionFactory();
            log.info("SessionFactory created successfully");

        } catch (Exception e) {
            log.error("Failed to create SessionFactory", e);
            throw new RuntimeException(e);
        }
    }

    public Session getSession() {
        log.info("Creating Session");
        return sessionFactory.openSession();
    }

    @Override
    public void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            log.info("SessionFactory closed successfully");
        }
    }
}
