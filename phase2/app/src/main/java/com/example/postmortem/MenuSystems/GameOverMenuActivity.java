package com.example.postmortem.MenuSystems;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.R;

public class GameOverMenuActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_over_menu);
    populateCurrentScores();
    populateHighScores();
  }

  private void populateCurrentScores() {
    // TODO ADD FUNCTIONALITY
  }

  private void populateHighScores() {
    // TODO ADD FUNCTIONALITY
  }

  public void restart(View target) {
    // TODO ADD FUNCTIONALITY
  }

  /**
   * Exit the game, return to Android.
   */
  public void quit(View target) {
    this.finish();
    System.exit(0);
  }
}
