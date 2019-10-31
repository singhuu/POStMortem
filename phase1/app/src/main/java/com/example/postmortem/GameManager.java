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

  public GameManager() {
  }

  protected GameManager(Parcel in) {
    currLevelType = in.readInt();
    activeUser = (User) in.readSerializable();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(currLevelType);
    dest.writeSerializable(activeUser);
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

  public User getActiveUser() {
    return activeUser;
  }

  public void setActiveUser(User activeUser) {
    this.activeUser = activeUser;
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
