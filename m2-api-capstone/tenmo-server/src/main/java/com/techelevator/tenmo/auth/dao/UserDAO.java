package com.techelevator.tenmo.auth.dao;

import com.techelevator.tenmo.auth.model.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    User findByUsername(String username);

    List<User> listAllUsersWhoAreNotTheCurrentUser(String currentUserUsername);

    int findIdByUsername(String username);

    boolean create(String username, String password);
}
