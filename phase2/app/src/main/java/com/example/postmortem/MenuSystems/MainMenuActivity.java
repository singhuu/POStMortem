package com.example.postmortem.MenuSystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.postmortem.GameManager;
import com.example.postmortem.R;

public class MainMenuActivity extends AppCompatActivity {

  GameManager gameManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_menu);
    Bundle extras = getIntent().getExtras();
    gameManager = (GameManager) extras.get(GameManager.INTENT_NAME);
  }

  public void continueGame(View target) {
    // TODO PROPERLY IMPLEMENT CONTINUEGAME
    gameManager.continueFromSave(this);
  }

  public void start(View target) {
    // TODO PROPERLY IMPLEMENT START
    gameManager.start(this);
  }

  public void openOptions(View target) {
    Intent intent = new Intent(this, OptionMenuActivity.class);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);
    startActivity(intent);
  }

  public void logout(View target) {
    Intent intent = new Intent(this, UserSelectMenuActivity.class);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);
    startActivity(intent);
  }

  /**
   * Exit the game, return to Android.
   */
  public void quit(View target) {
    this.finish();
    System.exit(0);
  }
}
