package com.example.postmortem.LevelSystems;

import android.app.Activity;
import android.os.CountDownTimer;

public abstract class Level extends Activity {

  protected int difficulty;
  protected int wrongChoiceCountdown = 0;

  /** Constructor Class Easy = 1 Medium = 2 Hard = 3 No more than 3 levels */
  Level(int difficulty) {
    this.difficulty = difficulty;
  }

  public abstract int getScore();
}
