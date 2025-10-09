package com.javarush.ramis.service;

import com.javarush.ramis.entity.User;
import com.javarush.ramis.exception.UserNotFoundException;
import com.javarush.ramis.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;

@Slf4j
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

    public User get(long id) {
        return userRepository.get(id).orElseThrow(
                () -> new UserNotFoundException("User with id=" + id + " not found"));
    }

    public User findByLoginAndPassword(String login, String password) {
        Optional<User> user = userRepository.findByLoginAndPassword(login, password);
        if (user.isPresent()) {
            return user.get();
        } else {
            log.error("User with login=" + login + " and password=" + password + " not found");
            throw new UserNotFoundException("Invalid login or password");
        }
    }
}
