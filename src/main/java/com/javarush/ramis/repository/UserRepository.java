package com.javarush.ramis.repository;

import com.javarush.ramis.config.SessionCreator;
import com.javarush.ramis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserRepository implements Repository<User> {
    private final SessionCreator sessionCreator;

    public UserRepository() {
        sessionCreator = SessionCreator.getInstance();
    }

    @Override
    public Collection<User> getAll() {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            List<User> users = session.createQuery("from User", User.class).list();
            log.info("getAll: fromUserSize={}", users.size());
            transaction.commit();
            return users;
        } catch (Exception ex) {
            log.error("getAll Users failed: {}", ex.getMessage());
            transaction.rollback();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<User> get(long id) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            User user = session.get(User.class, id);
            log.info("get: user={}", user);
            transaction.commit();
            return Optional.ofNullable(user);
        }  catch (Exception ex) {
            log.error("get User by id failed: {}", ex.getMessage());
            transaction.rollback();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void create(User user) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.persist(user);
            log.info("create: user id={}, login='{}'", user.getId(), user.getLogin());
            transaction.commit();
        } catch (Exception ex) {
            log.error("create User failed for login {}: {}", user.getLogin(), ex.getMessage());
            transaction.rollback();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(User user) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.remove(user);
            log.info("delete: user={}", user);
            transaction.commit();
        } catch (Exception ex) {
            log.error("delete User failed: {}", ex.getMessage());
            transaction.rollback();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(User user) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.merge(user);
            log.info("update: userID={}", user.getId());
            transaction.commit();
        } catch (Exception ex) {
            log.error("update User failed: {}", ex.getMessage());
            transaction.rollback();
            throw new RuntimeException(ex);
        }
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Optional<User> user = session.createQuery("from User " +
                                                      "where login=:login and password=:password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .uniqueResultOptional();
            log.info("findByLoginAndPassword: optUser={}", user);
            transaction.commit();
            return user;
        } catch (Exception ex) {
            log.error("findByLoginAndPassword failed: {}", ex.getMessage());
            transaction.rollback();
            throw new RuntimeException(ex);
        }
    }
}
