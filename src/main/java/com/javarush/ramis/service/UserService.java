package com.javarush.ramis.service;

import com.javarush.ramis.dto.UserTo;
import com.javarush.ramis.exception.UserNotFoundException;
import com.javarush.ramis.mapping.Dto;
import com.javarush.ramis.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Dto dto = Dto.MAPPER;

    public void create(UserTo user) {
        userRepository.create(dto.from(user));
    }

    public void delete(UserTo user) {
        userRepository.delete(dto.from(user));
    }

    public void update(UserTo user) {
        userRepository.update(dto.from(user));
    }

    public Collection<UserTo> getAll() {
        return userRepository.getAll()
                .stream()
                .map(dto::from)
                .collect(Collectors.toList());
    }

    public UserTo get(long id) {
        return userRepository.get(id)
                .map(dto::from)
                .orElseThrow(
                () -> new UserNotFoundException("User with id=" + id + " not found"));
    }

    public UserTo findByLoginAndPassword(String login, String password) {
        Optional<UserTo> user = userRepository.findByLoginAndPassword(login, password).map(dto::from);
        if (user.isPresent()) {
            return user.get();
        } else {
            log.error("User with login=" + login + " and password=" + password + " not found");
            throw new UserNotFoundException("Invalid login or password");
        }
    }
}
