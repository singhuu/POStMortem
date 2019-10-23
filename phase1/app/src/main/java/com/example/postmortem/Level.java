package com.example.postmortem;

import android.app.Activity;
import android.os.CountDownTimer;

abstract class Level extends Activity {

  protected int difficulty;
  /** Constructor Class Easy = 1 Medium = 2 Hard = 3 No more than 3 levels */
  Level(int difficulty) {
    this.difficulty = difficulty;
  }

  /** CountDown timer that can be called by the Activity classes */
  CountDownTimer countTimer = null;
  /** Start timer function in SECONDS Devs reading this, Enter Time in Seconds */
  public void startTimer(int cTimeInSeconds) {
    countTimer =
        new CountDownTimer(cTimeInSeconds * 1000, 1000) {
          /** Left empty for now, I do not know what to do with this just yet */
          public void onTick(long millisUntilFinished) {}
          /** Same situation as above. Will implement as and when needed */
          public void onFinish() {}
        };
    countTimer.start();
  }

  /** cancelTimer that cancels the Count Down */
  public void cancelTimer() {
    if (countTimer != null) countTimer.cancel();
  }

  public abstract int getScore();
}
