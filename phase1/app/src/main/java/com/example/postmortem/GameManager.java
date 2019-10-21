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

  // Creates a level and adds it to level list
  public void createLevel(int difficulty) {
    Level level = new Level(difficulty);
    levels.add(level);
  }
}
