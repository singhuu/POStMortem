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
    gameManager = (GameManager) extras.get(GameManager.INTENT_NAME);
  }

  public void continueGame(View target) {
    gameManager.continueFromSave(this);
  }

  public void start(View target) {
    Intent intent = new Intent(this, OptionMenuActivity.class);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);
    startActivity(intent);
  }

  public void logout(View target) {
    Intent intent = new Intent(this, UserSelectMenuActivity.class);
    intent.putExtra(GameManager.INTENT_NAME, gameManager);
    startActivity(intent);
  }
}
