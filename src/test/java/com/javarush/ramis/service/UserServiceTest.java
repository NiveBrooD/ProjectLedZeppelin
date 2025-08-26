package com.javarush.ramis.service;

import com.javarush.ramis.entity.Role;
import com.javarush.ramis.entity.User;
import com.javarush.ramis.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserService(UserRepository.getInstance());
        user = new User("login", "password", Role.ADMIN);
    }

    @AfterEach
    void tearDown() {
        userService.delete(user);
    }

    @Test
    void create() {
        userService.create(user);
        assertTrue(userService.get(user.getId()).isPresent());

    }

    @Test
    void delete() {
        userService.create(user);
        assertTrue(userService.get(user.getId()).isPresent());
        userService.delete(user);
        assertFalse(userService.get(user.getId()).isPresent());
    }

    @Test
    void update() {
        userService.create(user);
        User newUser = new User(user.getId(), "login", "password", Role.GUEST);
        userService.update(newUser);
        assertTrue(userService.get(user.getId()).isPresent());
        assertNotEquals(userService.get(newUser.getId()).get().getRole(), user.getRole());
        assertTrue(userService.getAll().contains(newUser));
        assertFalse(userService.getAll().contains(user));
        userService.delete(newUser);
    }

    @Test
    void getAll() {
        userService.create(user);
        Collection<User> all = userService.getAll();
        assertFalse(all.isEmpty());
        assertTrue(all.contains(user));
    }

    @Test
    void get() {
        userService.create(user);
        assertTrue(userService.get(user.getId()).isPresent());
        assertEquals(userService.get(user.getId()).get().getId(), user.getId());
    }
}