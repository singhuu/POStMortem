package com.example.postmortem.FileInterfacing;

import com.example.postmortem.LevelSystems.LevelType;
import com.example.postmortem.DataTypes.User;

import java.util.ArrayList;
import java.util.List;

public class UserFileController extends FileController {

    public UserFileController(String appDataDir){
        super(appDataDir, "usersdata.csv");
    }

    @Override
    List updateList(List<String[]> loadedData) {
        List<User> users = new ArrayList<>();
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

        return users;
    }

    @Override
    String formatOutputData(List users){
        StringBuilder out = new StringBuilder();
        for(Object obj : users) {
            User user = (User) obj;
            out.append(createDataFromUser(user));
            out.append("\n");
        }

        return out.toString();

    }

    /**
     * Creates the data for the User
     * @param user User object that stores data
     * @return data that is appended
     */
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


}
