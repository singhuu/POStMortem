package com.example.postmortem;

import android.content.Context;

import com.example.postmortem.DataTypes.User;
import com.example.postmortem.LevelSystems.LevelType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserLoader {

    private static List<User> users = new ArrayList<>();
    private static List<String[]> hiscores = new ArrayList<>();

    public static String dir;

    /**
     * Finds the directory of the project
     * @param context the project
     */
    public static void findFilePath(Context context){
        dir = context.getFilesDir().getPath() + System.getProperty("file.separator");
    }

    public static User getUser(String username){
        for (User user: users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    /**
     * Load the info from the files
     */
    public static void load(){

        users.clear();
        hiscores.clear();

        tryLoadUsers();
        tryLoadHiscores();

    }

    private static File tryOpenFile(String filePath) {
        File file = new File(filePath);
        try{

            file.createNewFile();

        } catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }

    private static Scanner tryCreateReader(File file){
        Scanner scan;

        try {
            scan = new Scanner(new FileInputStream(file));

        } catch (Exception e){
            //this code will never execute as is the file didn't exist than we created it earlier
            scan = new Scanner(System.in);
            e.printStackTrace();
        }

        return scan;
    }

    private static void tryLoadUsers(){

        File usersFile = tryOpenFile(dir + "usersdata.csv");
        Scanner scan = tryCreateReader(usersFile);
        loadUsersFromFile(scan);
    }


    private static void loadUsersFromFile(Scanner scan){
        List<String[]> data = new ArrayList<>();

        while(scan.hasNextLine()){
            data.add(scan.nextLine().split(","));
        }
        loadUsersFromData(data);


    }

    private static void loadUsersFromData(List<String[]> data) {
        //create the users
        for (String[] userData : data) {
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

    private static void tryLoadHiscores(){
        File hiscoresFile = tryOpenFile(dir + "Hiscores.csv");

        Scanner scan = tryCreateReader(hiscoresFile);
        loadHiscoresFromFile(scan);
    }

    private static void loadHiscoresFromFile(Scanner scan){
        while(scan.hasNextLine()){
            String[] hiscore = scan.nextLine().split(",");

            hiscores.add(hiscore);
        }
    }

    /**
     * Create a new user and add it to the users list
     * @param username the new accounts username
     * @param password the new accounts password
     * @return if the account was successfully created
     */
    public static boolean createUser(String username, String password){
        boolean canCreate = !checkUserExists(username);
        if(canCreate){
            users.add(new User(username, password));
        }
        return canCreate;
    }

    private static boolean checkUserExists(String username){
        for(User user: users){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * Try to log into an user account
     * @param username the username of the desired account
     * @param password the password of the desired account
     * @return optionally the user logged in to
     */
    public static Optional<User> attemptLogin(String username, String password){
        Optional user = Optional.empty();
        for (User account : users) {
            if(loginCorrect(account, username, password)){
                user = Optional.of(account);
                break;
            }
        }

        return user;
    }

    private static boolean loginCorrect(User user, String username, String password){
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }

    public static List<String[]> getHiscores(){
        return hiscores;
    }

    public static void updateHiscores(String username, int score){

        List<String[]> newHiscores = new ArrayList<>();
        int i = 0;
        boolean added = false;

        if(hiscores.size() == 0){
            String[] newElement = new String[2];
            newElement[0] = username;
            newElement[1] = Integer.toString(score);
            newHiscores.add(newElement);
        }

        while(i < hiscores.size() && newHiscores.size() < 5){
            String[] hiscore = hiscores.get(i);

            if(Integer.parseInt(hiscore[1]) < score && !added){
                String[] newElement = new String[2];
                newElement[0] = username;
                newElement[1] = Integer.toString(score);
                newHiscores.add(newElement);
            }

            if(newHiscores.size() < 5){
                newHiscores.add(hiscore);
            }
        }

        hiscores = newHiscores;

    }

    /**
     * Write the user and hiscore data to a file
     */
    public static void updateFiles(){

        updateUsersFile();
        updateHiscoresFile();

    }

    private static void updateUsersFile(){

        File target = tryOpenFile(dir + "usersdata.csv");
        String output = buildOutputToUsersFile();
        writeToFile(target, output);

    }

    private static String buildOutputToUsersFile(){
        StringBuilder out = new StringBuilder();
        for (User user : users) {
            out.append(createDataFromUser(user));
            out.append("\n");
        }

        return out.toString();

    }

    private static StringBuilder createDataFromUser(User user){
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

    private static void updateHiscoresFile(){

        File target = tryOpenFile(dir + "Hiscores.csv");
        String output = buildOutputToHiscoresFile();
        writeToFile(target, output);

    }

    private static String buildOutputToHiscoresFile(){
        StringBuilder out = new StringBuilder();
        for (String[] hiscore : hiscores) {
            out.append(createDataFromHiscore(hiscore));
            out.append("\n");
        }

        return out.toString();
    }

    private static StringBuilder createDataFromHiscore(String[] hiscore){
        StringBuilder out = new StringBuilder();
        out.append(hiscore[0]);
        out.append(",");
        out.append(hiscore[1]);
        return out;
    }

    private static void writeToFile(File target, String output){

        try(BufferedWriter out = new BufferedWriter(new FileWriter(target))){
            out.write(output);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
