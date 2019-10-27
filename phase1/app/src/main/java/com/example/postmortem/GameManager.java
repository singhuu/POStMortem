package com.example.postmortem;

import com.example.postmortem.LevelSystems.*;

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
  public boolean createLevel(int difficulty, LevelType levelType) {
    Level level;

    switch (levelType){

      case PICKUP:
        level = new PickUpLevel(difficulty);
        break;

      case TYPE:
        level = new TypeLevel(difficulty);
        break;

      case TAP:
        level = new TapLevel(difficulty);
        break;

      default:
        System.out.println("Unknown level type");
        return false;

    }

    levels.add(level);
    return true;
  }
}
