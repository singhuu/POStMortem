package com.example.postmortem.DataTypes;

import java.util.Optional;
import java.util.regex.Pattern;

public class UserCredentialValidator {

    private UserManager manager;

    public UserCredentialValidator(){
        manager = UserManager.getManager();
    }

    /**
     * Gets the user matching the username and password
     *
     * @param username the username of the user to be logged in
     * @param password the ppassword of the user to be logged in
     * @return the user matching the username and password
     * @throws AccountException whatever went wrong in the login process
     */
    public User login(String username, String password) throws AccountException{
        Optional<User> user  = manager.attemptLogin(username, password);
        if(user.isPresent()){
            return user.get();
        } else {
            throw new AccountException("Username or password incorrect");
        }
    }

    /**
     * Create an account with given username and password
     * Throws an account exception if there is a problem in account creation
     *
     * @param username the username of the new account
     * @param password the password of the new account
     * @throws AccountException whatever went wrong in the creation of the account
     */
    public void createAccount(String username, String password) throws AccountException{
        if(manager.checkUserExists(username)){
            throw new AccountException("User already exists");
        } else {
            validateInputAndCreateAccount(username, password);
        }
    }

    /**
     * validates the input and creates the new user in the manager
     *
     * @param username the username of the new account
     * @param password the password of the new account
     * @throws AccountException whatever went wrong in the creation of the account
     */
    private void validateInputAndCreateAccount(String username, String password) throws AccountException{
        if(validInput(username, password)){
            createAccountInManager(username, password);
        } else {
            throw new AccountException("Username or password must be nonempty string with no \',\''s.");
        }
    }

    /**
     * Checks the inputs against a pattern of valid inputs
     *
     * @param username the username of the new account
     * @param password the password of the new account
     * @return if the username and password match the pattern of valid inputs
     */
    private boolean validInput(String username, String password){
        String pattern = "[^,]+";
        return Pattern.matches(pattern, username) && Pattern.matches(pattern, password);
    }

    /**
     * Creates the new account in the manager
     *
     * @param username the username of the new account
     * @param password the password of the new account
     */
    private void createAccountInManager(String username, String password){
        manager.createUser(username, password);
    }
}
