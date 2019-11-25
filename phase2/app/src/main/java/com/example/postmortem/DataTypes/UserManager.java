package com.example.postmortem.DataTypes;

import com.example.postmortem.DataTypes.User;
import com.example.postmortem.FileInterfacing.UserFileController;

import java.util.List;
import java.util.Optional;

public class UserManager {
    private UserFileController fileController;
    private List<User> users;

    public UserManager(String appDataDir){
        this.fileController = new UserFileController(appDataDir);
        this.users = (List<User>) fileController.load();
    }


    /**
     * Try to log into an user account
     * @param username the username of the desired account
     * @param password the password of the desired account
     * @return optionally the user logged in to
     */
    public Optional<User> attemptLogin(String username, String password){
        Optional user = Optional.empty();
        for (User account : users) {
            if(loginCorrect(account, username, password)){
                user = Optional.of(account);
                break;
            }
        }

        return user;
    }

    private boolean loginCorrect(User user, String username, String password){
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }

    /**
     * Create a new user and add it to the users list
     * @param username the new accounts username
     * @param password the new accounts password
     * @return if the account was successfully created
     */
    public boolean createUser(String username, String password){
        boolean canCreate = !checkUserExists(username);
        if(canCreate){
            users.add(new User(username, password));
        }
        return canCreate;
    }

    private boolean checkUserExists(String username){
        for(User user: users){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
}
