package com.javarush.ramis.repository;

import com.javarush.ramis.entity.Role;
import com.javarush.ramis.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        userRepository = UserRepository.getInstance();
        user = new User("login", "password", Role.ADMIN);
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(user);
    }

    @Test
    void getAll() {
        userRepository.create(user);
        assertFalse(userRepository.getAll().isEmpty());
        assertTrue(userRepository.getAll().contains(user));
    }

    @Test
    void get() {
        userRepository.create(user);
        assertTrue(userRepository.get(user.getId()).isPresent());
        assertEquals(user, userRepository.get(user.getId()).get());
    }

    @Test
    void create() {
        userRepository.create(user);
        assertTrue(userRepository.getAll().contains(user));
    }

    @Test
    void delete() {
        userRepository.create(user);
        userRepository.delete(user);
        assertFalse(userRepository.getAll().contains(user));
    }

    @Test
    void update() {
        userRepository.create(user);
        user.setRole(Role.GUEST);
        userRepository.update(user);
        assertEquals(Role.GUEST, userRepository.get(user.getId()).get().getRole());
    }
}