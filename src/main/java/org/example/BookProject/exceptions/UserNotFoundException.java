package org.example.BookProject.exceptions;

public class UserNotFoundException extends Exception{
    private String message;

    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
