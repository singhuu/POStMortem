package com.example.postmortem;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.postmortem.LevelSystems.*;

public class GameManager implements Parcelable {

  public static final String INTENT_NAME = "manager";

  private int currLevelType = -1;
  private User activeUser;

  private int levels;
  private boolean runningAds;
  private int difficulty;

  public GameManager() {
    this.levels = 3;
    this.difficulty = 1;
    this.runningAds = true;
  }

  /** Parcel Methods */

  protected GameManager(Parcel in) {
    this.currLevelType = in.readInt();
    this.activeUser = (User) in.readSerializable();
    this.levels = in.readInt();
    this.difficulty = in.readInt();
    this.runningAds = in.readInt() == 1;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(currLevelType);
    dest.writeSerializable(activeUser);
    dest.writeInt(levels);
    dest.writeInt(difficulty);
    if(runningAds){
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
    }
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<GameManager> CREATOR = new Creator<GameManager>() {
    @Override
    public GameManager createFromParcel(Parcel in) {
      return new GameManager(in);
    }

    @Override
    public GameManager[] newArray(int size) {
      return new GameManager[size];
    }
  };

  /** GameManger methods */

  public User getActiveUser() {
    return activeUser;
  }

  public void setActiveUser(User activeUser) {
    this.activeUser = activeUser;
  }

  public boolean isRunningAds() {
    return runningAds;
  }

  public void setRunningAds(boolean runningAds){
    this.runningAds = runningAds;
  }

  public int getLevels(){
    return levels;
  }

  public void setLevels(int levels) {
    this.levels = levels;
  }

  public int getDifficulty(){
    return difficulty;
  }

  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }

  // Creates a level and adds it to level list, returns true if success, false if failed
  //TODO need to add way to end level selection and go to scoremenu
  public void createLevel(int difficulty, Context context) {
    Intent newLevelIntent = null;

    int levelType = (int)(Math.random()* 3);
    while(levelType == currLevelType){
      levelType = (int)(Math.random()* 3);
    }

    currLevelType = levelType;
    switch (levelType){
      case 0:
        newLevelIntent = new Intent(context, PickUpLevelActivity.class);
        break;

      case 1:
        newLevelIntent = new Intent(context, TapLevelActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        break;

      case 2:
        newLevelIntent = new Intent(context, TypeLevelActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        break;

      default:
        System.out.println("Unknown level type");
    }

    try{
      newLevelIntent.putExtra("DIFFICULTY", difficulty);
      newLevelIntent.putExtra("GAME_MANAGER", this);
      context.startActivity(newLevelIntent);
    }
    catch (NullPointerException e){
      //TODO handle error
    }
  }
}
