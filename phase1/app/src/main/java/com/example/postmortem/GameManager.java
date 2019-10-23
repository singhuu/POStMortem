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
    if(levelType.equals("tap")){
      Level level = new tapLevel(difficulty);
    }
    else if(levelType.equals("type")){
      Level level = new typeLevel(difficulty);
    }
    else if(levelType.equals("pickup")){
      Level level = new pickupLevel(difficulty);
    }
    else{
      System.out.println("Unknown level type");
      return false;
    }
    levels.add(level);
    return true;
  }
}
