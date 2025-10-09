package com.javarush.ramis.exception;

public class UserNotFoundException extends QuestException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
