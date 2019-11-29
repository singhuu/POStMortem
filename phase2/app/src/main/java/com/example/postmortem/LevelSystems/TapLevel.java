package com.example.postmortem.LevelSystems;

public class TapLevel extends Level {

  /**
   * stores the number of times the screen is pressed
   */
  private int timesPressed = 0;

  /**
   * Constructor that references to the TapLevel difficulty
   *
   * @param difficulty integer variable that stores difficulty
   */
  public TapLevel(int difficulty) {
    super(difficulty);
  }

  /**
   * Increments the number of times pressed
   */
  public void incrementTimesPressed() {
    timesPressed++;
  }

  /**
   * Getter method that returns the number of times pressed
   *
   * @return timesPressed variable
   */
  public int getTimesPressed() {
    return timesPressed;
  }

  /**
   * Getter method that returns the score
   *
   * @return timesPressed as the score is the same as timesPressed
   */
  public int getScore() {
    return timesPressed;
  }
}
