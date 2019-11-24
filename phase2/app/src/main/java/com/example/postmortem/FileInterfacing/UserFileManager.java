package com.example.postmortem.FileInterfacing;

import com.example.postmortem.LevelSystems.LevelType;
import com.example.postmortem.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserFileManager extends FileManager{
    private List<User> users;

    public UserFileManager(String appDataDir){
        super(appDataDir, "userdata.csv");
        users = new ArrayList<>();
    }

    @Override
    void updateList(List<String[]> loadedData) {
        //create the users
        for (String[] userData : loadedData) {
            String username = userData[0];
            String password = userData[1];
            int hiscore = Integer.parseInt(userData[2]);
            int tapScore = Integer.parseInt(userData[3]);
            int typeScore = Integer.parseInt(userData[4]);
            int pickupScore = Integer.parseInt(userData[5]);
            int levelType = Integer.parseInt(userData[6]);
            int levelsLeft = Integer.parseInt(userData[7]);
            int difficulty = Integer.parseInt(userData[8]);
            boolean runningAds = Boolean.parseBoolean(userData[9]);

            User newUser = new User(username, password);
            newUser.setHiscore(hiscore);
            newUser.setScore(tapScore, LevelType.TAP);
            newUser.setScore(typeScore, LevelType.TYPE);
            newUser.setScore(pickupScore, LevelType.PICKUP);
            newUser.setCurrentRunDifficulty(difficulty);
            newUser.setCurrentRunLevels(levelsLeft);
            newUser.setCurrentRunLevelType(levelType);
            newUser.setRunningAds(runningAds);
            users.add(newUser);

        }
    }

    @Override
    String formatOutputData(){
        StringBuilder out = new StringBuilder();
        for(User user : users) {
            out.append(createDataFromUser(user));
            out.append("\n");
        }

        return out.toString();

    }

    private  StringBuilder createDataFromUser(User user){
        StringBuilder data = new StringBuilder();
        data.append(user.getUsername());
        data.append(",");
        data.append(user.getPassword());
        data.append(",");
        data.append(user.getHiscore());
        data.append(",");
        data.append(user.getTapScore());
        data.append(",");
        data.append(user.getTypeScore());
        data.append(",");
        data.append(user.getPickupScore());
        data.append(",");
        data.append(user.getCurrentRunLevelType());
        data.append(",");
        data.append(user.getCurrentRunLevels());
        data.append(",");
        data.append(user.getCurrentRunDifficulty());
        data.append(",");
        data.append(user.isRunningAds());
        return data;
    }

    //TODO: Move everything below this to a separate user manager class

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
