package com.javarush.ramis.repository;

import com.javarush.ramis.entity.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepository implements Repository<User> {
    private final Map<Long, User> users = new HashMap<>();
    public static final AtomicLong ID_GENERATOR = new AtomicLong(0);


    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public void create(User user) {
        user.setId(ID_GENERATOR.getAndIncrement());
        update(user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    @Override
    public void update(User user) {
        users.put(user.getId(), user);
    }
}
