package com.javarush.ramis.repository;

import com.javarush.ramis.config.SessionCreator;
import com.javarush.ramis.entity.Quest;
import com.javarush.ramis.entity.User;
import com.javarush.ramis.entity.UserQuest;
import lombok.AllArgsConstructor;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserQuestRepository implements Repository<UserQuest> {
    private final SessionCreator sessionCreator;

    public UserQuestRepository() {
        this.sessionCreator = SessionCreator.getInstance();
    }

    public Optional<UserQuest> getUserQuest(Long questId, Long userId) {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try {
            Quest quest = session.get(Quest.class, questId);
            User user = session.get(User.class, userId);
            Optional<UserQuest> userQuestOptional = session.createQuery("select uq from UserQuest uq " +
                                                                        "inner join fetch uq.currentQuestion " +
                                                                        "where uq.quest=:quest AND uq.user=:user", UserQuest.class)
                    .setParameter("quest", quest)
                    .setParameter("user", user)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            return userQuestOptional;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public Collection<UserQuest> getAll() {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try {
            List<UserQuest> list = session.createQuery("select uq from UserQuest uq " +
                                                       "inner join fetch uq.user " +
                                                       "inner join fetch uq.quest " +
                                                       "left join fetch uq.currentQuestion", UserQuest.class).list();
            session.getTransaction().commit();
            return list;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<UserQuest> get(long id) {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try {
            Optional<UserQuest> userQuestOptional = session.createQuery("select uq from UserQuest uq " +
                                                                        "inner join fetch uq.user " +
                                                                        "inner join fetch uq.quest " +
                                                                        "left join fetch uq.currentQuestion " +
                                                                        "where uq.id = :id", UserQuest.class).setParameter("id", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            return userQuestOptional;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void create(UserQuest userQuest) {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try {
            session.persist(userQuest);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(UserQuest userQuest) {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try {
            session.remove(userQuest);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(UserQuest userQuest) {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try {
            UserQuest uq = session.get(UserQuest.class, userQuest.getId());
            uq.setCurrentQuestion(userQuest.getCurrentQuestion());
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public void restartQuest(UserQuest userQuest) {
        Session session = sessionCreator.getSession();
        session.beginTransaction();
        try {
            UserQuest uq = session.get(UserQuest.class, userQuest.getId());
            uq.setCurrentQuestion(uq.getQuest().getFirstQuestion());
            session.getTransaction().commit();
        }  catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        }  finally {
            session.close();
        }

    }
}
