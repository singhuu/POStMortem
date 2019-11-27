package com.example.postmortem.LevelSystems;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.GameManager;

public abstract class LevelActivity extends AppCompatActivity {

    /**
     * The Time Left in the countdown
     */
    protected int timeLeft;
    /**
     * Level Object that stores information about level
     */
    protected static Level level;
    /**
     * GameManager Object
     */
    protected GameManager gameManager;
    /**
     * static variable that stores the difficulty
     */
    protected static int difficulty;
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
     * creates a Bundle and initializes the current username
     *
     * @param savedInstanceState a mapping from String keys to various Parcelable values
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        curr_username = getIntent().getStringExtra("CURR_USERNAME");
    }

    /**
     * Creates a trigger for the event
     *
     * @param keyCode code of the Key
     * @param event   current event of the KeyEvent object
     * @return either true or waits for key down
     */
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

    /**
     * cancelTimer that cancels the Count Down
     */
    public void cancelTimer() {
        if (countTimer != null) countTimer.cancel();
    }
}
