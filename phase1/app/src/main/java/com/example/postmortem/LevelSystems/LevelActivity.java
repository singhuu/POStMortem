package com.example.postmortem.LevelSystems;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;

import com.example.postmortem.MainActivity;
import com.example.postmortem.MenuSystems.GameMenu;
import com.example.postmortem.MenuSystems.GameOverMenu;
import com.example.postmortem.MenuSystems.MenuActivity;

import androidx.appcompat.app.AppCompatActivity;

public abstract class LevelActivity extends AppCompatActivity {
  protected int timeLeft;
  protected Level level; //TODO is this necessary? Different classes in subclasses allow for logic function calls
  protected static int difficulty;
  protected CountDownTimer countTimer = null;

  /*LevelActivity(int layout, Level level) {
    this.layout = layout;
    this.level = level;
  }

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(layout);

    this.setup();
  }*/

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event)  {
    if (keyCode == KeyEvent.KEYCODE_BACK ) {
      // do something on back.
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

  /**
   * Perform initial setup of layout.
   * This includes setting the value of text boxes, buttons, etc.
   */
  //protected abstract void setup();

  /** Start timer function in SECONDS Devs reading this, Enter Time in Seconds */
  public void startTimer(int cTimeInSeconds) {
    countTimer =
            new CountDownTimer(cTimeInSeconds * 1000, 1000) {
              /** Left empty for now, I do not know what to do with this just yet */
              public void onTick(long millisUntilFinished) {
                  timeLeft -=1;
                  countTickHandler();
              }
              /** Same situation as above. Will implement as and when needed */
              public void onFinish() { countFinishHandler(); }
            };
    countTimer.start();
  }

  public abstract void countTickHandler();

  public abstract void countFinishHandler();

  /** cancelTimer that cancels the Count Down */
  public void cancelTimer() {
    if (countTimer != null) countTimer.cancel();
  }
}
