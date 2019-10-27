package com.example.postmortem;

import com.example.postmortem.LevelSystems.LevelType;

public class User {

  private String username;
  private String password;

  private int pickupScore;
  private int tapScore;
  private int typeScore;
  private int hiScore;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.pickupScore = 0;
    this.tapScore = 0;
    this.typeScore = 0;
    this.hiScore = 0;
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

  public int getHiScore() {
    return this.hiScore;
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
