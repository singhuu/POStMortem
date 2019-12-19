package com.example.postmortem.LevelSystems;

public class TapLevel extends Level {

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
  public void incrementScore() {
    score++;
  }

  /**
   * Getter method that returns the score
   *
   * @return timesPressed as the score is the same as timesPressed
   */
  public int getScore() {
    return score;
  }
}
