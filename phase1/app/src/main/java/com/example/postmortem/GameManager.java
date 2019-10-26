package com.example.postmortem;

import java.util.ArrayList;

public class GameManager {

  // Stores the users of the game
  static ArrayList<User> users;

  // Stores the levels of the game
  static ArrayList<Level> levels;

  public GameManager() {
    users = new ArrayList<>();
    levels = new ArrayList<>();
  }

  // Creates a level and adds it to level list, returns true if success, false if failed
  public boolean createLevel(int difficulty, String levelType) {
    Level level;
    if(levelType.equals("tap")){
      level = new TapLevel(difficulty);
    }
    else if(levelType.equals("type")){
      level = new TypeLevel(difficulty);
    }
    else if(levelType.equals("pickup")){
      level = new PickUpLevel(difficulty);
    }
    else{
      System.out.println("Unknown level type");
      return false;
    }
    levels.add(level);
    return true;
  }
}
