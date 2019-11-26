package com.example.postmortem.DataTypes;

import com.example.postmortem.DataTypes.User;
import com.example.postmortem.FileInterfacing.UserFileController;

import java.util.List;
import java.util.Optional;

public class UserManager {

    private static UserManager manager;

    private UserFileController fileController;
    private List<User> users;



    private UserManager(String appDataDir){
        this.fileController = new UserFileController(appDataDir);
        this.refresh();
    }

    /**
     * Sets the directory for the application data and creates the instance of the class
     *
     * @param directory the directory of the program files
     */
    public static void initialize(String directory){
        manager = new UserManager(directory);
    }

    /**
     * Gets the manager for users
     * There is only one instance of this class at a time, which can be accessed by many
     * classes at once
     *
     * @return the user manger
     */
    public static UserManager getManager(){
        return manager;
    }



    public void refresh(){
        this.users = (List<User>) fileController.load();
    }

    public void saveState(){
        this.fileController.save(users);
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

    public User getUser(String username){
        for (User user: users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}
