package com.example.postmortem;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

abstract class Level extends Activity {

  private int diffLevel;
  /** Constructor Class Easy = 1 Medium = 2 Hard = 3 No more than 3 levels */
  Level(int difficulty) {

    this.diffLevel = difficulty;
  }

  /** Getter class to call the level ID */
  int getLevelId() {

    return this.diffLevel;
  }

  void setLevelId(int difficulty2) {
    diffLevel = difficulty2;
  }

  /** Still not 100% sure. Might be removed altogether in the future */
  public abstract void draw(View view);

  /** CountDown timer that can be called by the Activity classes */
  CountDownTimer countTimer = null;
  /** Start timer function in SECONDS Devs reading this, Enter Time in Seconds */
  void startTimer(int cTimeInSeconds) {
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
  void cancelTimer() {
    if (countTimer != null) countTimer.cancel();
  }

  /** Creates a Button to be used by Subclasses */
  private Button clicky;

  /** Use method onClick */
  private View.OnClickListener LevelOnClickListener =
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          ButtonClickedConfirmed();
        }
      };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    clicky = new Button(this);

    clicky.setOnClickListener(LevelOnClickListener);
  }

  /** TODO: [FOR ALL] Fix this bug here in setText if you can please. */
  private void ButtonClickedConfirmed() {
    clicky.setText("CLICKY CLICKY");
  }
}
