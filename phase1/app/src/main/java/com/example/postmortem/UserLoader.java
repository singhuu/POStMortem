package com.example.postmortem;

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

    public static void load(){

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
        File usersFile = tryOpenFile("Users.csv");
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
            String currentLevel = userData[6];

            User newUser = new User(username, password);
            newUser.setHiscore(hiscore);
            newUser.setScore(tapScore, LevelType.TAP);
            newUser.setScore(typeScore, LevelType.TYPE);
            newUser.setScore(pickupScore, LevelType.PICKUP);
            users.add(newUser);

        }
    }

    private static void tryLoadHiscores(){
        File hiscoresFile = tryOpenFile("Hiscores.csv");
        Scanner scan = tryCreateReader(hiscoresFile);
        loadHiscoresFromFile(scan);
    }

    private static void loadHiscoresFromFile(Scanner scan){
        while(scan.hasNextLine()){
            String[] hiscore = scan.nextLine().split(",");

            hiscores.add(hiscore);
        }
    }

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

    public static void updateFiles(){

        updateUsersFile();
        updateHiscoresFile();

    }

    private static void updateUsersFile(){

        File target = tryOpenFile("users.csv");
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
        data.append(user.getHiScore());
        data.append(",");
        data.append(user.getTapScore());
        data.append(",");
        data.append(user.getTypeScore());
        data.append(",");
        data.append(user.getPickupScore());
        data.append(",");
        data.append("nihil");//TODO: add the saving and loading of current levels from file
        return data;
    }

    private static void updateHiscoresFile(){

        File target = tryOpenFile("Hiscores.csv");
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
