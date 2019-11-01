package com.example.postmortem.LevelSystems;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.postmortem.R;

public class TapLevelActivity extends LevelActivity {
  TextView timerText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tap_level);

    gameManager = getIntent().getParcelableExtra("GAME_MANAGER");

    getIntent().getIntExtra("DIFFICULTY", difficulty);
    level = new TapLevel(1); //TODO temp value to be passed in as extra

    TextView textView = findViewById(R.id.score);
    textView.setText("0");

    timeLeft = 30; //TODO temporary until timeLeft is passed in
    timerText = findViewById(R.id.timer);
    timerText.setText(timeLeft + 1 + " Seconds Remaining");

    startTimer(30); //TODO temp until timeLeft passed in
  }

  public void pressButton(View target) {
    // Do something in response to imageButton press
    TapLevel tapLevel = (TapLevel) super.level;
    tapLevel.incrementTimesPressed();
    int timesPressed = tapLevel.getTimesPressed();
    TextView textView = findViewById(R.id.score);
    textView.setText(Integer.toString(timesPressed));

    // now switch image in button
    ImageButton imageButton = findViewById(R.id.imageButton);
    if (timesPressed % 2 == 0) {
      // set it to open
      imageButton.setImageResource(R.mipmap.laptop_open_foreground);
    } else {
      // set it to closed
      imageButton.setImageResource(R.mipmap.laptop_closed_foreground);
    }
  }

  @Override
  public void countTickHandler() {
    timerText.setText(timeLeft + 1 + " Seconds Remaining");
  }

  @Override
  public void countFinishHandler() {
    gameManager.play(this);
  }

}
