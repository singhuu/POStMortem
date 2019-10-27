package com.example.postmortem.LevelSystems;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class LevelActivity extends AppCompatActivity {
  protected int layout;
  protected Level level;
  protected static int difficulty;

  LevelActivity(int layout, Level level) {
    this.layout = layout;
    this.level = level;
  }

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(layout);

    this.setup();
  }

  /**
   * Perform initial setup of layout.
   * This includes setting the value of text boxes, buttons, etc.
   */
  protected abstract void setup();
}
