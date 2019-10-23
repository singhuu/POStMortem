package com.example.postmortem;

public class tapLevel extends Level {

  private int timesPressed = 0;

  public tapLevel(int difficulty) {
    super(difficulty);
  }

  public void incrementTimesPressed() {
    timesPressed++;
  }

  public int getTimesPressed() {
    return timesPressed;
  }

  public int getScore() {
    return timesPressed * (100 / super.difficulty);
  }
}
