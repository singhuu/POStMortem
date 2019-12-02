package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.postmortem.GameManager;
import com.example.postmortem.R;

public class MainMenuActivity extends MenuActivity {

  GameManager gameManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_menu);
    Bundle extras = getIntent().getExtras();
    assert extras != null;
    gameManager = (GameManager) extras.get(GameManager.INTENT_NAME);
  }

  /**
   * Continue the game from previous state
   *
   * @param target the view variable
   */
  public void continueGame(View target) {
    gameManager.continueFromSave(this);
  }

  /**
   * Start a new game by taking the user to the options menu
   *
   * @param target the current view of the app
   */
  public void start(View target) {
    Intent intent = new Intent(this, OptionMenuActivity.class);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);
    startActivity(intent);
  }

  /**
   * Log the user out from the game
   *
   * @param target the current view of the app
   */
  public void logout(View target) {
    Intent intent = new Intent(this, UserSelectMenuActivity.class);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);
    startActivity(intent);
  }
}
