package com.example.postmortem.LevelSystems;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.postmortem.MainActivity;
import com.example.postmortem.R;
import com.example.postmortem.SoundManager;

public class TapLevelActivity extends LevelActivity {
  private SoundManager sm = new SoundManager(MainActivity.getMContext());

  @SuppressLint("DefaultLocale")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tap_level);

    gameManager = getIntent().getParcelableExtra("GAME_MANAGER");

    getIntent().getIntExtra("DIFFICULTY", difficulty);
    level = new TapLevel(difficulty);

    scoreText = findViewById(R.id.score);
    scoreText.setText("0");

    timeLeft = 30 - (difficulty * 10);
    timerText = findViewById(R.id.timer);
    timerText.setText(String.format("%d Seconds Remaining", timeLeft + 1));

    startTimer(timeLeft);

    ImageView meric = findViewById(R.id.mericImage);
    meric.setVisibility(View.INVISIBLE);
    TextView mericQuote = findViewById(R.id.mericSpeech);
    mericQuote.setVisibility(View.INVISIBLE);
  }

  /**
   * Displays an image when the button is pressed a certain number of times
   */
  @SuppressLint("SetTextI18n")
  public void pressButton(View target) {
    // Do something in response to imageButton press
    TapLevel tapLevel = (TapLevel) level;
    tapLevel.incrementScore();
    int timesPressed = tapLevel.getScore();

    scoreText.setText(Integer.toString(timesPressed));

    // now switch image in button
    ImageButton imageButton = findViewById(R.id.imageButton);
    if (timesPressed % 2 == 0) {
      // set it to open
      imageButton.setImageResource(R.mipmap.laptop_open_foreground);
    } else {
      // set it to closed
      imageButton.setImageResource(R.mipmap.laptop_closed_foreground);
    }

    if (timesPressed == 25) {
      displayMeric();
      sm.playWowEffect();
    }
  }

  /**
   * Display an image when called
   */
  private void displayMeric() {
    ImageView meric = findViewById(R.id.mericImage);
    meric.setVisibility(View.VISIBLE);
    TextView mericQuote = findViewById(R.id.mericSpeech);
    mericQuote.setVisibility(View.VISIBLE);
  }

  /**
   * Handles the count tick
   */
  @SuppressLint("DefaultLocale")
  @Override
  public void countTickHandler() {
    timerText.setText(String.format("%d Seconds Remaining", timeLeft + 1));
  }

  /**
   * Updates total score
   */
  @Override
  public void countFinishHandler() {
    gameManager.totalScore += level.getScore();
    gameManager.play(this);
  }

  /**
   * Saves the score
   */
  @Override
  public void saveScore() {
    gameManager.getActiveUser().setScore(level.getScore(), LevelType.TAP);
  }

    /**
     * Triggers the cheat to the score
     *
     * @param view the current view of the app
     */
    public void cheatClickHandler(View view) {
        level.score += 100;
        scoreText.setText(String.format("%d", level.getScore()));
    }

}
