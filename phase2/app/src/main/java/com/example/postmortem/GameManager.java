package com.example.postmortem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.LevelSystems.*;
import com.example.postmortem.MenuSystems.GameMenu;

public class GameManager implements Parcelable {

  public static final int PICKUP_LEVEL_TYPE = 0;
  public static final int TAP_LEVEL_TYPE = 1;
  public static final int TYPE_LEVEL_TYPE = 2;

  public static final String INTENT_NAME = "manager";

  private int currLevelType = -1;
  private User activeUser;

  private int levels;
  private boolean runningAds;
  private int difficulty;

  public int totalScore;

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
   * Starts a new game based on the settings chosen by the user
   * @param context the current context of the app
   */
  public void start(AppCompatActivity context){
    Intent intent = createRandomLevel(context);
    updateActiveUser();
    transitionLevel(context, intent);
  }

  public void continueFromSave(AppCompatActivity context){
    loadSettingsFromUser();
    int currentLevelType = activeUser.getCurrentRunLevelType();
    Intent intent = createGivenLevel(context, currentLevelType);
    transitionLevel(context, intent);
  }

  private void loadSettingsFromUser(){
    levels = activeUser.getCurrentRunLevels();
    difficulty = activeUser.getCurrentRunDifficulty();
    runningAds = activeUser.isRunningAds();
  }

  public void play(AppCompatActivity context){
    if(levels > 0){
      levels--;
      continuePlaying(context);
    } else {
      gameOver(context);
    }
  }

  private void gameOver(AppCompatActivity context){
    Intent intent = GameMenu.openMenu(context, GameMenu.GAME_OVER_MENU);
    intent.putExtra(INTENT_NAME, this);

    /*int totalScore = getFinalScore(UserLoader.dir + "sessionScore.csv");
    intent.putExtra("FINAL_SCORE", totalScore);*/

    context.startActivity(intent);
    context.finish();
  }

  private void continuePlaying(AppCompatActivity context){
      Intent intent = createRandomLevel(context);
      updateActiveUser();
      tryRunAds(context, intent);
  }

  // Creates a level and adds it to level list, returns true if success, false if failed
  private Intent createRandomLevel(AppCompatActivity context) {

    int newLevelType = decideNewLevelType();
    return createGivenLevel(context, newLevelType);

  }

  private int decideNewLevelType() {
    int levelType = (int)(Math.random()* 3);
    while(levelType == currLevelType){
      levelType = (int)(Math.random()* 3);
    }

    return levelType;
  }

    /**
     *  creates and returns an intent to the specified level
     *
     * @param context the context of the program
     * @param levelType the type of level to create
     */
  private Intent createGivenLevel(AppCompatActivity context, int levelType) {

    currLevelType = levelType;
    Intent newLevelIntent = createLevelIntent(context);
    addExtras(newLevelIntent);

    return newLevelIntent;

  }

  private Intent createLevelIntent(Context context){
    Intent intent = null;

    switch (currLevelType){
      case PICKUP_LEVEL_TYPE:
        intent = new Intent(context, PickUpLevelActivity.class);
        break;

      case TAP_LEVEL_TYPE:
        intent = new Intent(context, TapLevelActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        break;

      case TYPE_LEVEL_TYPE:
        intent = new Intent(context, TypeLevelActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        break;

      default:
        System.out.println("Unknown level type");
    }

    intent.putExtra("CURR_USERNAME", activeUser.getUsername());
    return  intent;
  }

  private void addExtras(Intent intent){
    intent.putExtra("DIFFICULTY", difficulty);
    intent.putExtra("GAME_MANAGER", this);
  }

  private void updateActiveUser(){
    activeUser.setCurrentRunLevels(levels);
    activeUser.setRunningAds(runningAds);
    activeUser.setCurrentRunLevelType(currLevelType);
    activeUser.setCurrentRunDifficulty(difficulty);
    UserLoader.updateFiles();
  }

  private void tryRunAds(AppCompatActivity context, Intent intent){

    if(runningAds){
      showAd(context, intent);
    } else {
      dontShowAd(context, intent);
    }

  }

  private void showAd(final AppCompatActivity context, final Intent intent){

    AlertDialog.Builder builder = createEndPopup(context, intent);
    builder.setMessage("Level Complete \nScore: " + totalScore + "\nPlease Support the devs");
    builder.setPositiveButton("I support local devs", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        donate(context);
        //transitionLevel(context, intent);
      }
    });

    builder.setNegativeButton("I think local devs should starve", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        transitionLevel(context, intent);
      }
    });

    builder.setCancelable(false); //Removes ability to click off dialog to close

    AlertDialog dialog = builder.create();
    dialog.show();

  }

  private void dontShowAd(AppCompatActivity context, Intent intent){

    AlertDialog.Builder builder = createEndPopup(context, intent);
    AlertDialog dialog = builder.create();
    dialog.show();

  }

  private void donate(Context context){
    Intent intent = new Intent(Intent.ACTION_VIEW,
            Uri.parse("https://www.paypal.com/ca/home"));
    context.startActivity(intent);
  }

  private AlertDialog.Builder createEndPopup(final AppCompatActivity context, final Intent intent){
    AlertDialog.Builder builder = new AlertDialog.Builder(context);

    builder.setMessage("Level Complete \nScore: " + totalScore);
    builder.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        transitionLevel(context, intent);
      }
    });

    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
      @Override
      public void onDismiss(DialogInterface dialogInterface) {
        transitionLevel(context, intent);
      }
    });

    return builder;
  }

  private void transitionLevel(AppCompatActivity context, Intent intent){
    context.startActivity(intent);
    context.finish();
  }

}
