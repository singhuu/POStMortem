package com.example.postmortem.DataTypes;

import com.example.postmortem.LevelSystems.LevelType;

import java.io.Serializable;

public class User implements Serializable {

    /**
     * String that stores the username
     */
    private String username;
    /**
     * String that stores the password
     */
    private String password;
    /**
     * Stores the score from pick up
     */
    private int pickupScore;
    /**
     * Stores scores from tap game
     */
    private int tapScore;
    /**
     * Stores scores from type game
     */
    private int typeScore;
    /**
     * Stores scores from swipe game
     */
    private int swipeScore;
    /**
     * Stores the high score
     */
    private int hiScore;
    /**
     * Stores the current Run Level
     */
    private int currentRunLevels;
    /**
     * Stores the current Run Level Type
     */
    private LevelType currentRunLevelType;
    /**
     * Stores the difficulty
     */
    private int currentRunDifficulty;
    /**
     * Stores if Ads are running or not
     */
    private boolean runningAds;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.pickupScore = 0;
        this.tapScore = 0;
        this.typeScore = 0;
        this.hiScore = 0;
        this.currentRunLevelType = LevelType.TAP;
        this.currentRunLevels = 0;
        this.currentRunDifficulty = 1;
        this.runningAds = true;
    }

    /**
     * Checks if Ads are running or not
     *
     * @return true if Ads are Running or false if not
     */
    public boolean isRunningAds() {
        return runningAds;
    }

    /**
     * Setter method that sets Running Ads
     *
     * @param runningAds variable that checks if Ads are running
     */
    public void setRunningAds(boolean runningAds) {
        this.runningAds = runningAds;
    }

    /**
     * Setter method that sets Current Run Difficulty
     */
    public void setCurrentRunDifficulty(int currentRunDifficulty) {
        this.currentRunDifficulty = currentRunDifficulty;
    }

    /**
     * Getter method that gets the Current Run Difficulty
     *
     * @return
     */
    public int getCurrentRunDifficulty() {
        return currentRunDifficulty;
    }

    /**
     * Setter method that sets current Run Levels
     *
     * @param currentRunLevels variable that stores the current Run Level
     */
    public void setCurrentRunLevels(int currentRunLevels) {
        this.currentRunLevels = currentRunLevels;
    }

    /**
     * Getter method that gets the current Run Levels
     */
    public int getCurrentRunLevels() {
        return currentRunLevels;
    }

    /**
     * Getter method that gets the Current Level Type
     */
    public LevelType getCurrentRunLevelType() {
        return currentRunLevelType;
    }

    /**
     * Setter method that sets Run Level Type
     *
     * @param currentRunLevelType variable that stores current Run Level Type
     */
    public void setCurrentRunLevelType(LevelType currentRunLevelType) {
        this.currentRunLevelType = currentRunLevelType;
    }

    /**
     * Getter method that gets the pickup Score
     */
    public int getPickupScore() {
        return this.pickupScore;
    }

    /**
     * Getter method that gets the Tap Score
     */
    public int getTapScore() {
        return this.tapScore;
    }

    /**
     * Getter method that gets the Type Score
     */
    public int getTypeScore() {
        return this.typeScore;
    }

    /**
     * Getter method that gets the Swipe Score
     */
    public int getSwipeScore(){return this.swipeScore;}

    /**
     * Getter method that gets total Score
     *
     * @return returns that total sum of tap+type+pickup scores
     */
    public int getScore() {
        return tapScore + typeScore + pickupScore + swipeScore;
    }

    public void setScore(int score, LevelType game) {
        switch (game) {
            case TAP:
                if(score > tapScore) {
                    this.tapScore = score;
                }
                break;

            case TYPE:
                if(score > typeScore) {
                    this.typeScore = score;
                }
                break;

            case PICKUP:
                if(score > pickupScore) {
                    this.pickupScore = score;
                }
                break;

            case SWIPE:
                if(score > swipeScore) {
                    this.swipeScore = score;
                }
                break;

            default:
                break;
        }
    }

    /**
     * Getter method that returns high score
     *
     * @return hiscore that stores high score
     */
    public int getHiscore() {
        return this.hiScore;
    }

    /**
     * Setter method that sets High score
     *
     * @param hiscore that stores high score
     */
    public void setHiscore(int hiscore) {
        this.hiScore = hiscore;
    }

    /**
     * Getter method that returns usernames
     *
     * @return username that stores usernames
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Setter method that sets username
     *
     * @param username that stores username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method that returns password
     *
     * @return password that stores password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter method that sets password
     *
     * @param password that stores password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
