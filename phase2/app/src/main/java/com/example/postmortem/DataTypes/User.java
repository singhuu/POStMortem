package com.example.postmortem.DataTypes;

import com.example.postmortem.LevelSystems.LevelType;

import java.io.Serializable;

public class User implements Serializable {

  private String username;
  private String password;

  private int pickupScore;
  private int tapScore;
  private int typeScore;
  private int hiScore;
  private int currentRunLevels;
  private int currentRunLevelType;
  private int currentRunDifficulty;
  private boolean runningAds;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.pickupScore = 0;
    this.tapScore = 0;
    this.typeScore = 0;
    this.hiScore = 0;
    this.currentRunLevelType = 1;
    this.currentRunLevels = 0;
    this.currentRunDifficulty = 1;
    this.runningAds = true;
  }

  public boolean isRunningAds() {
    return runningAds;
  }

  public void setRunningAds(boolean runningAds){
    this.runningAds = runningAds;
  }

  public void setCurrentRunDifficulty(int currentRunDifficulty) {
    this.currentRunDifficulty = currentRunDifficulty;
  }

  public int getCurrentRunDifficulty() {
    return currentRunDifficulty;
  }

  public void setCurrentRunLevels(int currentRunLevels){
    this.currentRunLevels = currentRunLevels;
  }

  public int getCurrentRunLevels(){
    return currentRunLevels;
  }

  public int getCurrentRunLevelType() {
    return currentRunLevelType;
  }

  public void setCurrentRunLevelType(int currentRunLevelType) {
    this.currentRunLevelType = currentRunLevelType;
  }

  public int getPickupScore() {
    return this.pickupScore;
  }

  public int getTapScore() {
    return this.tapScore;
  }

  public int getTypeScore() {
    return this.typeScore;
  }

  public int getScore(){
    return tapScore + typeScore + pickupScore;
  }

  public void setScore(int score, LevelType game) {
    switch (game){
      case TAP:
        this.tapScore = score;
        break;

      case TYPE:
        this.typeScore = score;
        break;

      case PICKUP:
        this.pickupScore = score;
        break;

      default:
        break;
    }
  }

  public int getHiscore() {
    return this.hiScore;
  }

  public void setHiscore(int hiscore) {
    this.hiScore = hiscore;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
