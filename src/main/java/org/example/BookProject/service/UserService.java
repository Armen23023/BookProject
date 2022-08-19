package org.example.BookProject.service;

import org.example.BookProject.exceptions.UserAlreadyExistsException;
import org.example.BookProject.exceptions.UserNotFoundException;
import org.example.BookProject.managers.UserManager;
import org.example.BookProject.models.User;

public class UserService {


    UserManager userManager = new UserManager();


    public User registration(User user) throws UserAlreadyExistsException {
        boolean exist = userManager.existByEmail(user.getEmail());
        if (exist)
            throw new UserAlreadyExistsException(String.format("User with email = %s already exist ", user.getEmail()));
        userManager.add(user);
        return user;
    }

    public User login(String email, String password) throws  UserNotFoundException{
        User user = userManager.getByEmailAndPassword(email, password);
        if (user == null)
            throw new UserNotFoundException(String.format("User with email = %s and password = %s  not found", email, password));
        return user;
    }
}
