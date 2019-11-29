package com.example.postmortem.LevelSystems;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.GameManager;

public abstract class LevelActivity extends AppCompatActivity {

  /**
   * Level Object that stores information about level
   */
  protected static Level level;
  /**
   * static variable that stores the difficulty
   */
  protected static int difficulty;
  /**
   * The Time Left in the countdown
   */
  protected int timeLeft;
  /**
   * GameManager Object
   */
  protected GameManager gameManager;
  /**
   * CountDownTimer object that stores the timer
   * Initialized to null
   */
  protected CountDownTimer countTimer = null;
  /**
   * Stores the current username
   * Initialized to ""
   */
  protected String curr_username = "";

  /**
   * Stores TextView of timer
   */
  TextView timerText;

  /**
   * Stores TextView of score
   */
  TextView scoreText;

  /**
   * creates a Bundle and initializes the current username
   *
   * @param savedInstanceState a mapping from String keys to various Parcelable values
   */
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    curr_username = getIntent().getStringExtra("CURR_USERNAME");
  }

  // Used to disable back button usability in app
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      // do something on back.
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

  /**
   * Start timer function in SECONDS
   */
  public void startTimer(int cTimeInSeconds) {
    countTimer =
            new CountDownTimer(cTimeInSeconds * 1000, 1000) {
              public void onTick(long millisUntilFinished) {
                timeLeft -= 1;
                countTickHandler();
              }

              public void onFinish() {
                saveScore();
                countFinishHandler();
              }
            };
    countTimer.start();
  }

  public abstract void countTickHandler();

  public abstract void countFinishHandler();

  public abstract void saveScore();

  public abstract void cheatClickHandler(View view);

  /**
   * cancelTimer that cancels the Count Down
   */
  public void cancelTimer() {
    if (countTimer != null) countTimer.cancel();
  }
}
