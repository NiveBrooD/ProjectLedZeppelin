package com.javarush.ramis.repository;

import com.javarush.ramis.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T> {

    Collection<T> getAll();
    Optional<T> get(long id);
    void create(T type);
    void delete(T type);
    void update(T type);
}
