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

    /**
     * The List of Users
     */
    private static List<User> users = new ArrayList<>();
    /**
     * The List of High Scores
     */
    private static List<String[]> high_scores = new ArrayList<>();
    /**
     * The Save Directory
     */
    public static String dir;

    /**
     * Finds the directory of the project
     *
     * @param context the current state of the program
     */
    public static void findFilePath(Context context) {
        dir = context.getFilesDir().getPath() + System.getProperty("file.separator");
    }

    /**
     * Getter method that return a particular User Object
     *
     * @param username the unique login name of the user
     * @return either null or the User object
     */
    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Parses and loads data from the files
     */
    public static void load() {

        users.clear();
        high_scores.clear();

        tryLoadUsers();
        tryLoadHighScores();

    }

    /**
     * Opens the file from the given Path
     *
     * @param filePath The path where the file is stored
     * @return An object of File
     */
    private static File tryOpenFile(String filePath) {
        File file = new File(filePath);
        try {

            file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Creates a Scanner that will read files
     *
     * @param file The File Object
     * @return the Scanner object
     */
    private static Scanner tryCreateReader(File file) {
        Scanner scan;

        try {
            scan = new Scanner(new FileInputStream(file));

        } catch (Exception e) {
            //this code will never execute as is the file didn't exist than we created it earlier
            scan = new Scanner(System.in);
            e.printStackTrace();
        }

        return scan;
    }

    /**
     * Loads User from the given file
     */
    private static void tryLoadUsers() {

        File usersFile = tryOpenFile(dir + "usersdata.csv");
        Scanner scan = tryCreateReader(usersFile);
        loadUsersFromFile(scan);
    }

    /**
     * Reads the file using Scanner and stores it in an ArrayList
     *
     * @param scan the Scanner Object
     */
    private static void loadUsersFromFile(Scanner scan) {
        List<String[]> data = new ArrayList<>();

        while (scan.hasNextLine()) {
            data.add(scan.nextLine().split(","));
        }
        loadUsersFromData(data);


    }

    /**
     * Unveils data from the file that is stored in the ArrayList
     *
     * @param data the ArrayList that stores data in loadUsersFromData class
     */
    private static void loadUsersFromData(List<String[]> data) {
        //create the users
        for (String[] userData : data) {
            String username = userData[0];
            String password = userData[1];
            int high_score = Integer.parseInt(userData[2]);
            int tapScore = Integer.parseInt(userData[3]);
            int typeScore = Integer.parseInt(userData[4]);
            int pickupScore = Integer.parseInt(userData[5]);
            int levelType = Integer.parseInt(userData[6]);
            int levelsLeft = Integer.parseInt(userData[7]);
            int difficulty = Integer.parseInt(userData[8]);
            boolean runningAds = Boolean.parseBoolean(userData[9]);

            User newUser = new User(username, password);
            newUser.setHiscore(high_score);
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

    /**
     * Scans High Score File
     */
    private static void tryLoadHighScores() {
        File highScoresFile = tryOpenFile(dir + "Hiscores.csv");

        Scanner scan = tryCreateReader(highScoresFile);
        loadHighScoresFromFile(scan);
    }

    private static void loadHighScoresFromFile(Scanner scan) {
        while (scan.hasNextLine()) {
            String[] high_scores = scan.nextLine().split(",");

            UserLoader.high_scores.add(high_scores);
        }
    }

    /**
     * Create a new user and add it to the users list
     *
     * @param username the new accounts username
     * @param password the new accounts password
     * @return if the account was successfully created
     */
    public static boolean createUser(String username, String password) {
        boolean canCreate = !checkUserExists(username);
        if (canCreate) {
            users.add(new User(username, password));
        }
        return canCreate;
    }

    /**
     * Checks if the username exists
     *
     * @param username String that stores username
     * @return true if username exists else false
     */
    private static boolean checkUserExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Try to log into an user account
     *
     * @param username the username of the desired account
     * @param password the password of the desired account
     * @return optionally the user logged in to
     */
    public static Optional<User> attemptLogin(String username, String password) {
        Optional user = Optional.empty();
        for (User account : users) {
            if (loginCorrect(account, username, password)) {
                user = Optional.of(account);
                break;
            }
        }

        return user;
    }

    /**
     * Checks if the information entered by the user is correct
     *
     * @param user     The User Object that stores all data pertaining to the user
     * @param username the String that stores username of a user
     * @param password the String that stores password of a user
     * @return true if Login is correct else false
     */
    private static boolean loginCorrect(User user, String username, String password) {
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }

    /**
     * Returns an ArrayList of High Scores
     *
     * @return ArrayList of high_scores
     */
    public static List<String[]> getHighScores() {
        return high_scores;
    }

    /**
     * Updates to new high Scores if the score in the parameter is a high score
     *
     * @param username The unique username of the user
     * @param score    the score of the user
     */
    public static void updateHighScores(String username, int score) {

        List<String[]> newHighScores = new ArrayList<>();
        int i = 0;
        boolean added = false;

        if (high_scores.size() == 0) {
            String[] newElement = new String[2];
            newElement[0] = username;
            newElement[1] = Integer.toString(score);
            newHighScores.add(newElement);
        }

        while (i < high_scores.size() && newHighScores.size() < 5) {
            String[] high_score = high_scores.get(i);

            if (Integer.parseInt(high_score[1]) < score && !added) {
                String[] newElement = new String[2];
                newElement[0] = username;
                newElement[1] = Integer.toString(score);
                newHighScores.add(newElement);
            }

            if (newHighScores.size() < 5) {
                newHighScores.add(high_score);
            }
        }

        high_scores = newHighScores;

    }

    /**
     * Write the user and hiscore data to a file
     */
    public static void updateFiles() {

        updateUsersFile();
        updateHighScoresFile();

    }

    /**
     * Write the new User data to a file
     */
    private static void updateUsersFile() {

        File target = tryOpenFile(dir + "usersdata.csv");
        String output = buildOutputToUsersFile();
        writeToFile(target, output);

    }

    /**
     * Exports the Output to the User file
     *
     * @return the string output of the user data
     */
    private static String buildOutputToUsersFile() {
        StringBuilder out = new StringBuilder();
        for (User user : users) {
            out.append(createDataFromUser(user));
            out.append("\n");
        }

        return out.toString();

    }

    /**
     * Builds data from the various parameters of the User to be stored in a csv
     *
     * @param user The User Object
     * @return the data string that stores user values
     */
    private static StringBuilder createDataFromUser(User user) {
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

    /**
     * Updates High Score of the user to the csv file
     */
    private static void updateHighScoresFile() {

        File target = tryOpenFile(dir + "Hiscores.csv");
        String output = buildOutputToHighScoresFile();
        writeToFile(target, output);

    }

    /**
     * Export the high score to the user file
     *
     * @return the string output of the high score
     */
    private static String buildOutputToHighScoresFile() {
        StringBuilder out = new StringBuilder();
        for (String[] high_score : high_scores) {
            out.append(createDataFromHighScore(high_score));
            out.append("\n");
        }

        return out.toString();
    }

    /**
     * Appends the new High Score to the high score file
     *
     * @param high_score Stores the updated high score
     * @return the output file
     */
    private static StringBuilder createDataFromHighScore(String[] high_score) {
        StringBuilder out = new StringBuilder();
        out.append(high_score[0]);
        out.append(",");
        out.append(high_score[1]);
        return out;
    }

    /**
     * Actually writes the output to the file and modifies it
     *
     * @param target the target File that will be updated
     * @param output the output depending on the context
     */
    private static void writeToFile(File target, String output) {

        try (BufferedWriter out = new BufferedWriter(new FileWriter(target))) {
            out.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
