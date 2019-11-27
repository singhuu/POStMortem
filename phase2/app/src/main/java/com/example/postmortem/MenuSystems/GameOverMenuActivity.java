package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.GameManager;
import com.example.postmortem.R;

public class GameOverMenuActivity extends AppCompatActivity {

  GameManager gameManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_over_menu);
    Bundle extras = getIntent().getExtras();
    gameManager = (GameManager) extras.get(GameManager.INTENT_NAME);
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
    gameManager.start(this);
  }

  /**
   * Return to main menu.
   */
  public void quit(View target) {
    Intent intent = new Intent(this, MainMenuActivity.class);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);
    startActivity(intent);
  }
}
