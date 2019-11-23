package com.example.postmortem.LevelSystems;

public class TapLevel extends Level {

  private int timesPressed = 0;

  public TapLevel(int difficulty) {
    super(difficulty);
  }

  public void incrementTimesPressed() {
    timesPressed++;
  }

  public int getTimesPressed() {
    return timesPressed;
  }

  public int getScore() {
    return timesPressed;
  }
}
