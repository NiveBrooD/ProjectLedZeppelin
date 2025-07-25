package com.javarush.ramis.service;

import com.javarush.ramis.entity.User;
import com.javarush.ramis.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void create(User user) {
        userRepository.create(user);
    }
    public void delete(User user) {
        userRepository.delete(user);
    }
    public void update(User user) {
        userRepository.update(user);
    }
    public Collection<User> getAll() {
        return userRepository.getAll();
    }
    public Optional<User> get(long id) {
        return userRepository.get(id);
    }
}
