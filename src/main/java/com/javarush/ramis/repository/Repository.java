package com.javarush.ramis.repository;

import com.javarush.ramis.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T> {

    Collection<T> getAll();
    Optional<T> get(long id);
    void create(User user);
    void delete(User user);
    void update(User user);
}
