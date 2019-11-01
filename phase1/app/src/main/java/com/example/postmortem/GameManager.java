package com.example.postmortem;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.LevelSystems.*;
import com.example.postmortem.MenuSystems.GameMenu;

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
    this.activeUser = UserLoader.getUser(in.readString());
    this.levels = in.readInt();
    this.difficulty = in.readInt();
    this.runningAds = in.readInt() == 1;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(currLevelType);
    if(activeUser == null){
      dest.writeString("null");
    } else {
      dest.writeString(activeUser.getUsername());
    }
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

  /**
   * Starts a new game based on the setting chosen by the user
   * @param context the current context of the app
   */
  public void start(AppCompatActivity context){

    play(context);
  }

  public void contineFromSave(AppCompatActivity context){
    loadSettingsFromUser();
    int currentLevelType = activeUser.getCurrentRunLevelType();
    createGivenLevel(context, currentLevelType);
  }

  private void loadSettingsFromUser(){
    levels = activeUser.getCurrentRunLevels();
    difficulty = activeUser.getCurrentRunDifficulty();
    runningAds = activeUser.isRunningAds();
  }

  public void play(AppCompatActivity context){
    if(levels > 0){
      levels--;
      createLevel(context);
    } else {
      gameOver(context);
    }
  }

  private void gameOver(AppCompatActivity context){
    Intent intent = GameMenu.openMenu(context, GameMenu.GAME_OVER_MENU);
    intent.putExtra(INTENT_NAME, this);

    context.startActivity(intent);
    context.finish();
  }

  // Creates a level and adds it to level list, returns true if success, false if failed
  private void createGivenLevel(AppCompatActivity context, int levelType) {
    Intent newLevelIntent = null;

    currLevelType = levelType;

    updateActiveUser();

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
      context.finish();
    }
    catch (NullPointerException e){
      //TODO handle error
    }
  }

  // Creates a level and adds it to level list, returns true if success, false if failed
  private void createLevel(AppCompatActivity context) {
    Intent newLevelIntent = null;

    int levelType = (int)(Math.random()* 3);
    while(levelType == currLevelType){
      levelType = (int)(Math.random()* 3);
    }

    currLevelType = levelType;

    updateActiveUser();

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
      context.finish();
    }
    catch (NullPointerException e){
      //TODO handle error
    }
  }

  private void updateActiveUser(){
    activeUser.setCurrentRunLevels(levels);
    activeUser.setRunningAds(runningAds);
    activeUser.setCurrentRunLevelType(currLevelType);
    activeUser.setCurrentRunDifficulty(difficulty);
    UserLoader.updateFiles();
  }
}
